/*
 * The MIT License (MIT)
 * Copyright (c) 2013 newgxu.cn <the original author or authors>.
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.notty.controller;

import cn.newgxu.extras.FileUpload;
import cn.newgxu.lab.http.RequestEntityTooLargeException;
import cn.newgxu.lab.http.UnauthorizedException;
import cn.newgxu.lab.http.UnsupportedMediaTypeException;
import cn.newgxu.lab.notty.Notty;
import cn.newgxu.lab.notty.entity.AuthorizedUser;
import cn.newgxu.lab.notty.entity.Notice;
import cn.newgxu.lab.notty.service.AuthService;
import cn.newgxu.lab.notty.service.NoticeService;
import cn.newgxu.lab.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static cn.newgxu.lab.util.ViewConstants.BAD_REQUEST;
import static cn.newgxu.lab.util.ViewConstants.JSON_STATUS_OK;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * 校园信息主控制器。
 *
 * @User longkai
 * @Date 13-8-1
 * @Mail im.longkai@gmail.com
 */
@Controller
@Scope("session")
public class NoticeController {

	@Inject
	private AuthService authService;

	@Inject
	private NoticeService noticeService;

	@Inject
	private MultipartConfigElement multipartConfig;

	@RequestMapping(value = {"/notice", "/info"}, method = GET)
	public String index(Model model) {
		List<Notice> notices = noticeService.latest(Notty.DEFAULT_NOTICES_COUNT);
		List<AuthorizedUser> users = authService.users(AuthService.LATEST, Notty.DEFAULT_USERS_COUNT);
		model.addAttribute(Notty.AUTH_USERS, users);
		model.addAttribute(Notty.NOTICES, notices);
		return Notty.APP + "/index";
	}

	@RequestMapping(value = "/notices/{id}", method = GET)
	public String notice(Model model, @PathVariable("id") long id) {
		model.addAttribute(Notty.NOTICE, noticeService.view(id));
		return Notty.APP + "/notice";
	}

	@RequestMapping(value = "/notices/{id}/modify", method = GET)
	public String notice(Model model, HttpServletRequest request,
						 @PathVariable("id") long id) {
		AuthorizedUser user = authService.checkLogin(request);
		Notice notice = noticeService.find(id);
		l.info("auth_user: {} requiring modify notice: {}",
				user.getAuthorizedName(), notice.getId(), user.getAuthorizedName());
		model.addAttribute(Notty.NOTICE, notice);
		return Notty.APP + "/notice_modifying";
	}

	@RequestMapping(value = "/notices/create", method = POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	public Notice create(Notice notice, HttpServletRequest request) {
		AuthorizedUser user = authService.checkLogin(request);
		notice.setAuthor(user);
		noticeService.create(notice);
		return notice;
	}

	/**
	 * 给通告上传文件（新上传或者覆盖旧的文件）
	 */
	@RequestMapping(value = "/notices/{id}/upload_file", method = POST)
	@ResponseBody
	public String upload(HttpServletRequest request, @PathVariable("id") long id)
			throws IOException, ServletException {
		// 验证权限
		AuthorizedUser user = authService.checkLogin(request);
		Notice notice = noticeService.find(id);
		if (!notice.getAuthor().equals(user)) {
			throw new UnauthorizedException("你没有权限修改这通告！");
		}

		Part file = request.getPart("file");
		// 判断是否溢出
		if (file.getSize() > Notty.MAX_FILE_SIZE) {
			throw new RequestEntityTooLargeException("文件太大了");
		}
		// 取得在客户端文件系统上的初始文件名
		String originalName = FileUpload.resolveFileName(file);
		// 简单的通过文件扩展名判断类型
		String ext = TextUtils.getFileExt(originalName);
		boolean supported;
		for (int i = 0; !(supported = Notty.ACCEPT_FILE_TYPE[i].equalsIgnoreCase(ext)); i++)
			;
		if (!supported) {
			throw new UnsupportedMediaTypeException("不受支持的文件类型！我们支持："
					+ Arrays.toString(Notty.ACCEPT_FILE_TYPE));
		}
		// 生成存放在本地的文件名
		String url = makeFileURL(ext, id);
		// 开始上传，注意，必须是使用相对路径（相对于multipart config location），否则会写不到想要的目录
		// 请查阅java doc
		file.write(url);
		// 设置为绝对路径
		url = multipartConfig.getLocation() + url;
		l.info("文件：{} 写入成功！", url);

		// 执行到这一步表示文件已经上传ok了，不需要事务了，原来的文件删除与否意义不大（出错的概率非常小了）
		String oldDocURL = notice.getDocUrl();
		// 两者的文件名不相同才删，否则刚才上传的文件只是被覆盖了而已
		if (!url.equals(oldDocURL)) {
			File originalFile = new File(oldDocURL);
			if (originalFile.exists()) {
				if (originalFile.delete()) {
					l.info("original file {} delete ok!", oldDocURL);
				} else {
					l.info("original file delete error!", oldDocURL);
				}
			}
		}

		// 设置文件名，是一个人类易读的文件名（并非是存放的目录名），没有的话就是客户端文件最初的名字
		String customizedName = request.getParameter("qqfilename");
		if (TextUtils.isEmpty(customizedName, true)) {
			notice.setDocName(FileUpload.resolveFileName(file));
		} else {
			notice.setDocName(customizedName);
		}
		notice.setDocUrl(url);
		noticeService.update(notice, user.getId());
		return JSON_STATUS_OK;
	}

	@RequestMapping(value = "/notices/{id}/upload_file", method = GET)
	public String bug(Model model, HttpServletRequest request, @PathVariable("id") long id) {
		authService.checkLogin(request);
		Notice notice = noticeService.find(id);
		model.addAttribute(Notty.NOTICE, notice);
		return Notty.APP + "/file_upload";
	}

	@RequestMapping(value = "/notices/{id}/modify", method = PUT)
	@ResponseBody
	public Notice modify(HttpServletRequest request,
						 @RequestParam("title") String title,
						 @RequestParam("content") String content,
						 @PathVariable("id") long id) {
		AuthorizedUser user = authService.checkLogin(request);
		Notice notice = noticeService.find(id);
		notice.setTitle(title);
		notice.setContent(content);
		noticeService.update(notice, user.getId());
		return notice;
	}

	@RequestMapping(value = "/notices/{id}/block", method = PUT)
	@ResponseBody
	public String block(HttpServletRequest request, @PathVariable("id") long id) {
		AuthorizedUser user = authService.checkLogin(request);
		noticeService.toggleBlock(id, user.getId());
		return JSON_STATUS_OK;
	}

	@RequestMapping(method = GET, value = "/notices/newer_than")
	public String hasNew(Model model, @RequestParam("local_nid") long localNid) {
		long count = noticeService.newerCount(localNid);
		model.addAttribute("count", count);
		return BAD_REQUEST;
	}

	@RequestMapping(value = "/notices", method = GET)
	public String notices(Model model,
						  @RequestParam("count") int count,
						  @RequestParam("type") int type,
						  @RequestParam(value = "uid", defaultValue = "0") long uid,
						  @RequestParam(value = "nid", defaultValue = "0") long nid,
						  @RequestParam(value = "append", defaultValue = "false") boolean append) {
		List<Notice> notices;
		switch (type) {
			case 1:
				notices = noticeService.latest(uid, count);
				break;
			case 2:
				notices = noticeService.notices(nid, append, count);
				break;
			case 3:
				notices = noticeService.notices(uid, nid, append, count);
				break;
			default:
				notices = noticeService.latest(count);
				break;
		}
		model.addAttribute(Notty.NOTICES, notices);
		return BAD_REQUEST;
	}

	@RequestMapping(value = "/notices/my", method = GET)
	public String myNotices(Model model, HttpServletRequest request) {
		AuthorizedUser user = authService.checkLogin(request);
		List<Notice> notices = noticeService.latest(user.getId(), Notty.MAX_NOTICES_COUNT);
		model.addAttribute(Notty.NOTICES, notices);
		return Notty.APP + "/notices";
	}

	@RequestMapping(value = "/notices/q", method = GET)
	public String search(Model model, @RequestParam("q") String q, @RequestParam("count") int count) {
		List<Notice> notices = noticeService.search(q, count);
		model.addAttribute(Notty.NOTICES, notices);
		return BAD_REQUEST;
	}

	@RequestMapping(value = "/notice/sync", method = GET)
	public String sync(Model model, @RequestParam("last_timestamp") long last, @RequestParam("count") int count) {
		model.addAllAttributes(noticeService.sync(last, count));
		return BAD_REQUEST;
	}

	/**
	 * 返回 {root_dir}/notices/2013/12-12-{notice_id}.{fileType} 格式的文件名
	 *
	 * @param fileType 文件名
	 * @param nid      通告id
	 * @return 存储在服务器上的路径
	 */
	private String makeFileURL(String fileType, long nid) {
		Calendar now = Calendar.getInstance();
		StringBuilder url = new StringBuilder();
		// 本项目上传文件存放目录
		url
			.append("notices").append("/")
			.append(now.get(Calendar.YEAR)).append("/");
		// servlet3.0 只管把文件写在localtion后，如果目录不存在，那么就是一个随机的文件名（Multixxx....）
		// 所以我们需要自己创建相对的目录
		String path = multipartConfig.getLocation() + url.toString();
		File dir = new File(path);
		if (!dir.exists()) {
			if (!dir.mkdirs()) {
				l.error("目录：{} 创建失败！", path);
				throw new RuntimeException("无法创建目录！文件上传失败！");
			}
			l.info("目录：{} 创建成功！", path);
		}
		// 真正的文件名
		url.append(now.get(Calendar.MONTH) + 1).append("-")
				.append(now.get(Calendar.DAY_OF_MONTH)).append("-")
				.append(nid).append(".").append(fileType);
		return url.toString();
	}

	private static Logger l = LoggerFactory.getLogger(NoticeController.class);
}
