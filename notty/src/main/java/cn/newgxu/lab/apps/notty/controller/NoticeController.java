/*
 * The MIT License (MIT)
 * Copyright (c) 2013 newgxu.cn <the original author or authors>.
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.apps.notty.controller;

import cn.newgxu.lab.apps.notty.Notty;
import cn.newgxu.lab.apps.notty.entity.AuthorizedUser;
import cn.newgxu.lab.apps.notty.entity.Notice;
import cn.newgxu.lab.apps.notty.service.AuthService;
import cn.newgxu.lab.apps.notty.service.NoticeService;
import cn.newgxu.lab.core.support.UploadPath;
import cn.newgxu.lab.util.FileTransfer;
import cn.newgxu.lab.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
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
    private NoticeService noticeService;
    @Inject
    private AuthService authService;

    private static String resolvedFileName(String header) {
        int a = header.indexOf("filename");
        return header.substring(a + 10, header.length() - 1);
    }

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
    public Notice create(Notice notice, HttpServletRequest request) {
	    AuthorizedUser user = authService.checkLogin(request);
	    notice.setAuthor(user);
	    noticeService.create(notice);
	    return notice;
    }

//    @RequestMapping(value = "/notices/{id}/upload_file", method = POST)
//    @ResponseBody
//    public String upload(HttpServletRequest request,
//                         @PathVariable("id") long id,
//                         @RequestPart("file") Part file) {
//        AuthorizedUser user = checkLogin(request.getSession(false));
//        Notice notice = noticeService.find(id);
////		delete the orignal file if necessary.
//        if (notice.getDocUrl() != null) {
//            fileDelete(notice);
//        }
//        String fileName = resolvedFileName(file.getHeader("content-disposition"));
//        String uri = fileUpload(file, fileName);
//        notice.setDocUrl(uri);
//        notice.setDocName(fileName);
//        noticeService.update(notice, user.getId());
//        return JSON_STATUS_OK;
//    }

    @RequestMapping(value = "/notices/{id}/upload_file", method = GET)
    public String bug(Model model, HttpServletRequest request, @PathVariable("id") long id) {
	    authService.checkLogin(request);
	    Notice notice = noticeService.find(id);
	    model.addAttribute(Notty.NOTICE, notice);
	    return Notty.APP + "/file_upload";
    }

    @RequestMapping(value = "/notices/{id}/upload_file", method = POST)
    @ResponseBody
    public String upload(HttpServletRequest request,
                         @PathVariable("id") long id,
                         @RequestPart("file") MultipartFile file) {
	    AuthorizedUser user = authService.checkLogin(request);
	    Notice notice = noticeService.find(id);
	    String fileName = request.getParameter(UploadPath.FINE_UPLOADER_FILE_NAME); // fine uploader param.
	    if (TextUtils.isEmpty(fileName)) {
		    fileName = file.getOriginalFilename();
	    }
	    String fileUri = null;
	    try {
		    fileUri = FileTransfer.fileTransfer(file.getBytes(), UploadPath.FILE_SYSTEM_UPLOAD_PATH, Notty.UPLOAD_RELATIVE_DIR + getMonth() + "/",
				    Notty.MAX_FILE_SIZE, Notty.ACCEPT_FILE_TYPE, fileName, true);
	    } catch (IOException e) {
		    File f = new File(UploadPath.FILE_SYSTEM_ROOT_PATH + fileUri);
		    if (f.exists()) {
			    f.delete();
		    }
		    throw new RuntimeException(Notty.FILE_UPLOAD_FAIL, e);
	    }

//		delete the original file if necessary.
	    if (!TextUtils.isEmpty(notice.getDocUrl())) {
		    fileDelete(notice);
	    }

	    notice.setDocUrl(UploadPath.UPLOAD_BASE_PATH + fileUri);
	    notice.setDocName(fileName);
	    noticeService.update(notice, user.getId());
	    return JSON_STATUS_OK;
    }

    @RequestMapping(value = "/notices/{id}/modify", method = PUT)
    @ResponseBody
    public Notice modify(
            HttpServletRequest request,
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
    public String block(
            Model model,
            HttpServletRequest request,
            @PathVariable("id") long id) {
	    AuthorizedUser user = authService.checkLogin(request);
	    noticeService.toggleBlock(id, user.getId());
	    return JSON_STATUS_OK;
    }

    @RequestMapping(method = GET, value = "/notices/newer_than")
    public String hasNew(
            Model model,
            @RequestParam("local_nid") long localNid) {
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
//		List<Notice> notices = noticeService.latest(user.getId(), 2);
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

//    private boolean uploadable(Part file) {
////		check file size first...
//        if (file.getSize() > Notty.MAX_FILE_SIZE) {
//            throw new IllegalArgumentException(Notty.FILE_SIZE_OVERFLOW);
//        }
////		check file type, just check the filename, not content type
//        String header = file.getHeader("content-disposition");
//        l.info("header:{}", header);
////		filename="windows8.1.zip"
//        String ext = TextUtils.getFileExt(resolvedFileName(header));
//        JsonArray types = Notty.ACCEPT_FILE_TYPE;
//        for (int i = 0; i < types.size(); i++) {
//            if (ext.equalsIgnoreCase(types.getString(i))) {
//                return true;
//            }
//        }
//        throw new IllegalArgumentException(Notty.FILE_TYPE_NOT_ALLOW);
//    }

//    private String fileUpload(Part file, String fileName) {
//        if (file.getSize() < 0) {
//            try {
//                file.delete();
//            } catch (IOException e) {
//                // do nothing
//            }
//            return null;
//        }
//        uploadable(file);
//        String savedFileName = System.currentTimeMillis()
//                + TextUtils.getFileExt(resolvedFileName(file.getHeader("content-disposition")));
//        try {
//            file.write(savedFileName);
//        } catch (IOException e) {
//            throw new RuntimeException(Notty.FILE_UPLOAD_FAIL, e);
//        }
//        return Notty.UPLOAD_RELATIVE_DIR + savedFileName;
//    }

    private void fileDelete(Notice notice) throws RuntimeException {
	    String path = UploadPath.FILE_SYSTEM_ROOT_PATH + notice.getDocUrl();
	    File f = new File(path);
	    if (f.exists() && !f.delete()) {
		    throw new RuntimeException(Notty.DELETE_FILE_ERROR);
	    }
	    l.info("delete old file: {} ok!", path);
	    notice.setDocName(null);
        notice.setDocUrl(null);
    }

	private static int getMonth() {
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.MONTH) + 1;
	}

    private static Logger l = LoggerFactory.getLogger(NoticeController.class);
}
