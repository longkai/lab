/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai(龙凯)
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.store.controller;

import cn.newgxu.lab.store.entity.App;
import cn.newgxu.lab.store.entity.Comment;
import cn.newgxu.lab.store.service.CommentService;
import cn.newgxu.lab.support.member.MemberUtils;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;

import static cn.newgxu.lab.util.ViewConstants.BAD_REQUEST;
import static cn.newgxu.lab.util.ViewConstants.MEDIA_TYPE_JSON;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created with IntelliJ IDEA.
 *
 * @User longkai
 * @Date 13-8-16
 * @Mail im.longkai@gmail.com
 */
@Controller
@Scope("session")
public class CommentController {

	@Inject
	private CommentService commentService;

	@RequestMapping(value = "/app_comments/post", method = POST, produces = MEDIA_TYPE_JSON)
	@ResponseBody
	public Comment post(@RequestParam("appid") long appid, @RequestParam("account") String account,
						@RequestParam("password") String password,
						@RequestParam(value = "from", defaultValue = "0") int from,
						Comment comment) {
		JsonNode result = MemberUtils.lookat(from, account, password);
		if (result.get("status").asInt() != 1) {
			throw new SecurityException("account password not matches!");
		}
		comment.setUid(result.get("_ID").asLong());
		comment.setNick(result.get("nick").asText());
		App app = new App(); // just create it here
		app.setId(appid);
		comment.setApp(app);
		commentService.create(comment);
		return comment;
	}

	@RequestMapping(value = "/app_comments", method = GET)
	public String comments(Model model, @RequestParam("appid") long appid,
						   @RequestParam("count") int count,
						   @RequestParam(value = "offset", defaultValue = "0") long offset,
						   @RequestParam(value = "append", defaultValue = "false") boolean append) {
		model.addAttribute("comments", commentService.comments(appid, count, offset, append));
		return BAD_REQUEST;
	}

//    private static Logger l = LoggerFactory.getLogger(CommentController.class);
}
