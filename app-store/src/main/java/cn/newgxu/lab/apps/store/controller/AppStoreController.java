/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai(龙凯)
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.apps.store.controller;

import cn.newgxu.lab.apps.store.entity.App;
import cn.newgxu.lab.apps.store.entity.AppVersion;
import cn.newgxu.lab.apps.store.entity.Category;
import cn.newgxu.lab.apps.store.entity.Platform;
import cn.newgxu.lab.apps.store.service.AppService;
import cn.newgxu.lab.apps.store.service.CommentService;
import cn.newgxu.lab.core.member.Member;
import cn.newgxu.lab.core.member.MemberService;
import cn.newgxu.lab.core.support.UploadPath;
import cn.newgxu.lab.util.Assert;
import cn.newgxu.lab.util.FileTransfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.newgxu.lab.util.ViewConstants.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @User longkai
 * @Date 13-8-14
 * @Mail im.longkai@gmail.com
 */
@Controller
@Scope("session")
public class AppStoreController {

	private static final String APP = "app-store";
	private static final String STAGED_APP = "xx_qq";
	private static final String STAGED_ICON = "__icon__";
	private static final String STAGED_FILE_NAME = "..FN..";
	private static final long MAX_BINARY_SIZE = 20 * 1024 * 1024; // 20M
	private static final long MAX_ICON_SIZE = 2 * 1024 * 1024; // 2M
	private static final String[] ICON_EXTS = {"png", "jpg"};
	// android, ios, windows8 phone
	private static final String[] BINARY_EXTS = {"apk", "ipa", "xap"};
	// seems that without the code below is ok
//    @InitBinder({"category", "platform"})
//    public void enumConverter(WebDataBinder binder) {
//        binder.registerCustomEditor(Category.class, "category", new PropertyEditorSupport() {
//            @Override
//            public void setAsText(String text) throws IllegalArgumentException {
//                setValue(Category.valueOf(text));
//            }
//        });
//        binder.registerCustomEditor(Platform.class, "platform", new PropertyEditorSupport() {
//            @Override
//            public void setAsText(String text) throws IllegalArgumentException {
//                setValue(Platform.valueOf(text));
//            }
//        });
//    }
	private static Logger l = LoggerFactory.getLogger(AppStoreController.class);
	@Inject
	private AppService appService;
	@Inject
	private MemberService memberService;
	@Inject
	private CommentService commentService;

	private static void checkPermission(Member member, App app) {
	// because we don' t load all property in the association, see the AppVersionRepo.xml for detail.
		if (member.getId() != app.getAuthor().getId()) {
			throw new SecurityException("oh no, this app is not belong to you, you have no permission to alter it.");
		}
	}

	@RequestMapping(value = "/app", method = GET)
	public String index(Model model) {
		List<App> apps = appService.apps(AppService.DEFAULT, 8, 0, false);
		model.addAttribute("apps", apps);
		return APP + "/index";
	}

	@RequestMapping(value = "/apps/new", method = POST)
	@ResponseBody
	public String createMetaData(HttpServletRequest request, App app, AppVersion version) {
		Member member = memberService.fromSession(request);
		app.setAuthor(member);
//        stage the data util the user upload the binary and icon etc., or till the session invalidate.
//        appService.create(app, version);
		version.setApp(app);
		request.getSession().setAttribute(STAGED_APP, version);
		return JSON_STATUS_OK;
	}

	/**
	 * 上传应用图标
	 *
	 * @param request
	 * @param icon
	 * @return
	 */
	@RequestMapping(value = "/apps/icon", method = POST, produces = MEDIA_TYPE_JSON)
	@ResponseBody
	public String createIcon(HttpServletRequest request, @RequestParam("icon") MultipartFile icon) {
		memberService.fromSession(request);
		// 同样，将上传的图标暂存到session里，等上传应用的2进制文件后再统一写到磁盘里
		try {
			request.getSession().setAttribute(STAGED_ICON, icon.getBytes());
			request.getSession().setAttribute(STAGED_FILE_NAME, icon.getOriginalFilename());
		} catch (IOException e) {
			throw new RuntimeException("upload file I/O exception!", e);
		}
		return JSON_STATUS_OK;
	}

	/**
	 * 上传应用
	 *
	 * @param request
	 * @param bin
	 * @return
	 */
	@RequestMapping(value = "/apps/bin", method = POST, produces = MEDIA_TYPE_JSON)
	@ResponseBody
	public String createBin(HttpServletRequest request, @RequestParam("bin") MultipartFile bin) {
		Member member = memberService.fromSession(request);
		HttpSession session = request.getSession();
		Object attribute = session.getAttribute(STAGED_APP);
		Assert.notNull("we' re sorry, you need to complete the app' s meta information form first!", attribute);
		AppVersion rootVersion = (AppVersion) attribute;


		String binUri = null;
		String iconUri = null;
//        transactional
		try {
			byte[] bytes = (byte[]) session.getAttribute(STAGED_ICON);
			Assert.notNull("we' re sorry, you need to upload your app' s icon again.", bytes);
			String fileName = (String) session.getAttribute(STAGED_FILE_NAME);
			iconUri = FileTransfer.fileTransfer(bytes, UploadPath.FILE_SYSTEM_ROOT_PATH, "apps/" + member.getId(),
					MAX_ICON_SIZE, ICON_EXTS, fileName, true);

			binUri = FileTransfer.fileTransfer(bin.getBytes(), UploadPath.FILE_SYSTEM_ROOT_PATH, "apps/" + member.getId(),
					MAX_BINARY_SIZE, BINARY_EXTS, bin.getOriginalFilename(), true);
		} catch (Exception e) {
//            clean the dirty files
			File icon = new File(UploadPath.FILE_SYSTEM_ROOT_PATH + iconUri);
			if (icon.exists()) {
				icon.delete();
			}
			File app = new File(UploadPath.FILE_SYSTEM_ROOT_PATH + binUri);
			if (app.exists()) {
				app.delete();
			}
			throw new RuntimeException("upload fail, please try again later.", e);
		}

		rootVersion.setSize(bin.getSize());
		rootVersion.setUri(binUri);
		rootVersion.getApp().setIcon(UploadPath.WEB_ROOT_PATH + iconUri);

		appService.create(rootVersion.getApp(), rootVersion);
//        clean the session staged files.
		session.removeAttribute(STAGED_APP);
		session.removeAttribute(STAGED_ICON);
		session.removeAttribute(STAGED_FILE_NAME);
		return JSON_STATUS_OK;
	}

	@RequestMapping(value = "/apps/{id}/update", method = GET)
	@ResponseBody
	public App update(HttpServletRequest request, @PathVariable("id") long id) {
		Member member = memberService.fromSession(request);
		App app = appService.find(id);
		checkPermission(member, app);
		return app;
	}

	@RequestMapping(value = "/apps/{id}/apply", method = PUT)
	@ResponseBody
	public String apply(HttpServletRequest request, @PathVariable("id") long id,
						@RequestParam("os_requirement") String os_requirement,
						@RequestParam("description") String description,
						@RequestParam("outside_link") String outside_link,
						@RequestParam("extra") String extra) {
		Member member = memberService.fromSession(request);
		App app = appService.find(id);
		checkPermission(member, app);
		appService.update(id, os_requirement, description, outside_link, extra);
		return JSON_STATUS_OK;
	}

	@RequestMapping(value = "/apps/{id}", method = GET)
	public String app(Model model, @PathVariable("id") long id) {
		AppVersion version = appService.findLatestVersion(id);
		model.addAttribute("version", version);
		model.addAttribute("app", version.getApp());
		model.addAttribute("member", memberService.find(version.getApp().getAuthor().getId()));
		long count = version.getApp().getTotal_install_count();
		String relativeCount;
		if (count < 100) {
			relativeCount = "1-100";
		} else if (count >= 100 && count <= 1000) {
			relativeCount = "100-1000";
		} else {
			relativeCount = "1000+"; // for the moment, is enough.
		}
		model.addAttribute("install_count", relativeCount);
		model.addAttribute("comments", commentService.comments(version.getApp().getId(), 5, 0, false));
		return APP + "/app";
	}

	@RequestMapping(value = "/versions/{id}", method = GET)
	public String version(Model model, @PathVariable("id") long vid) {
		model.addAttribute("version", appService.findVersion(vid));
		return BAD_REQUEST;
	}

	@RequestMapping(value = "/versions/{id}/update", method = GET)
	public String update(Model model, HttpServletRequest request, @PathVariable("id") long id) {
		Member member = memberService.fromSession(request);
		AppVersion version = appService.findVersion(id);
		checkPermission(member, version.getApp());
		model.addAttribute("version", version);
		return APP + "/version";
	}

	@RequestMapping(value = "/versions/{id}/apply", method = PUT)
	@ResponseBody
	public String apply(HttpServletRequest request, @PathVariable("id") long id,
						@RequestParam("update_info") String update_info,
						@RequestParam("extra") String extra) {
		Member member = memberService.fromSession(request);
		AppVersion v = appService.findVersion(id);
		checkPermission(member, v.getApp());

		appService.update(id, update_info, extra);
		return JSON_STATUS_OK;
	}

	// 先上传新版本的文字信息，接下来再上传2进制文件
	@RequestMapping(value = "/apps/{appId}/upgrade", method = POST)
	@ResponseBody
	public String upgrade(HttpServletRequest request, @PathVariable("appId") long appId,
						  AppVersion version) {
		memberService.fromSession(request);
		Assert.notEmpty("version cannot be empty!", version.getUpdate_info());
		Assert.notEmpty("update information cannot be empty!", version.getUpdate_info());
		request.getSession().setAttribute(STAGED_APP, version);
		return JSON_STATUS_OK;
	}

	@RequestMapping(value = "/versions/bin", method = POST, produces = MEDIA_TYPE_JSON)
	@ResponseBody
	public Map<String, ?> upgrade(HttpServletRequest request, @RequestParam("appId") long appId,
								  @RequestParam("bin") MultipartFile bin) {
		Member member = memberService.fromSession(request);
		HttpSession session = request.getSession();
		Object o = session.getAttribute(STAGED_APP);
		Assert.notNull("We' re sorry, you need to submit your new app version' s information again!", o);
		AppVersion version = (AppVersion) o;

		App app = appService.find(appId);
		checkPermission(member, app);

		// transaction, if failed, clean the dirty file
		String binUri = null;
		try {
			binUri = FileTransfer.fileTransfer(bin.getBytes(), UploadPath.FILE_SYSTEM_ROOT_PATH, "apps/" + member.getId(),
					MAX_BINARY_SIZE, BINARY_EXTS, bin.getOriginalFilename(), true);

			version.setUri(binUri);
			version.setSize(bin.getSize());
			appService.upgrade(app, version);
		} catch (Exception e) {
			File f = new File(UploadPath.FILE_SYSTEM_ROOT_PATH + binUri);
			if (f.exists()) {
				f.delete();
			}
			throw new RuntimeException("We' re sorry, submit new app' s version failed.", e);
		}
		// clean the session' s attribute
		session.removeAttribute(STAGED_APP);
		Map<String, Object> model = new HashMap<String, Object>(3);
		model.put("version", version);
		model.put(STATUS, OK);
		model.put(SUCCESS, true); //upload must have it!
		return model;
	}

	@RequestMapping(value = "/apps/{appid}/download/{version}", method = GET)
	public String download(@PathVariable("appid") long id, @PathVariable("version") long versionId) {
		return "redirect:" + UploadPath.WEB_ROOT_PATH + appService.download(id, versionId).getUri();
	}

	@RequestMapping(value = "/apps/{appid}/download", method = GET)
	public String download(@PathVariable("appid") long id) {
		AppVersion version = appService.findLatestVersion(id);
		appService.download(id, version.getId());
		return "redirect:" + UploadPath.WEB_ROOT_PATH + version.getUri();
	}

	@RequestMapping(value = "/apps/{appid}/latest", method = GET)
	public String lastestVersion(Model model, @PathVariable("appid") long appId) {
		AppVersion v = appService.findLatestVersion(appId);
		model.addAttribute("version", v);
		return BAD_REQUEST;
	}

	@RequestMapping(value = "/apps/platform", method = GET)
	public String apps(Model model,
					   @RequestParam("platform") Platform platform,
					   @RequestParam("count") int count,
					   @RequestParam(value = "offset", defaultValue = "0") long offset,
					   @RequestParam(value = "append", defaultValue = "false") boolean append) {
		model.addAttribute("apps", appService.apps(platform, count, offset, append));
		return BAD_REQUEST;
	}

	@RequestMapping(value = "/apps/category", method = GET)
	public String apps(Model model,
					   @RequestParam("category") Category category,
					   @RequestParam("count") int count,
					   @RequestParam(value = "offset", defaultValue = "0") long offset,
					   @RequestParam(value = "append", defaultValue = "false") boolean append) {
		model.addAttribute("apps", appService.apps(category, count, offset, append));
		return BAD_REQUEST;
	}

	@RequestMapping(value = "/apps", method = GET)
	public String apps(Model model,
					   @RequestParam("type") int type,
					   @RequestParam("count") int count,
					   @RequestParam(value = "offset", defaultValue = "0") long offset,
					   @RequestParam(value = "append", defaultValue = "false") boolean append) {
		model.addAttribute("apps", appService.apps(type, count, offset, append));
		return BAD_REQUEST;
	}

	@RequestMapping(value = "/apps/member/{id}", method = GET)
	public String apps(Model model, @PathVariable("id") long id) {
		model.addAttribute("apps", appService.apps(id));
		return BAD_REQUEST;
	}

	@RequestMapping(value = "/apps/{id}/history", method = GET)
	public String histroy(Model model, @PathVariable("id") long id) {
		model.addAttribute("versions", appService.history(id));
		return BAD_REQUEST;
	}

	@RequestMapping(value = "/apps/{id}/photos", method = PUT)
	public String changePhotos(HttpServletRequest request, @RequestPart("images") MultipartFile[] photos) {
// todo  do file upload!
		return null;
	}

	@RequestMapping(value = "/apps/change_icon", method = POST, produces = MEDIA_TYPE_JSON)
	@ResponseBody
	public String changeIcon(HttpServletRequest request, @RequestParam("id") long id,
							 @RequestParam("icon") MultipartFile icon) {
		Member member = memberService.fromSession(request);
		App app = appService.find(id);
		checkPermission(member, app);
		String originalIconUri = app.getIcon();
		String iconUri = null;
		try {
			iconUri = FileTransfer.fileTransfer(icon.getBytes(), UploadPath.FILE_SYSTEM_ROOT_PATH, "apps/" + member.getId(),
					MAX_ICON_SIZE, ICON_EXTS, icon.getOriginalFilename(), true);
			appService.changeIcon(id, iconUri);
		} catch (Exception e) {
			// if update fail, delete the dirty new uploaded icon.
			File newIcon = new File(UploadPath.FILE_SYSTEM_ROOT_PATH + iconUri);
			if (newIcon.exists()) {
				newIcon.delete();
			}
			throw new RuntimeException("update app' s icon fail!", e);
		}
		// if success, delete the original icon.
		new File(UploadPath.FILE_SYSTEM_ROOT_PATH + originalIconUri).delete();
		return JSON_STATUS_OK;
	}
}
