<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>雨无声云点播</title>
		<meta name="description" content="雨无声云点播，体验VIP级的流畅播放感受，零等待，随意拖放！" />
		<meta name="keywords" content="雨无声云点播" />

		<link rel="shortcut icon" href="favicon.ico" >
		<link rel="icon" href="animated_favicon1.gif" type="image/gif" >
		<link href="http://cdnjs.bootcss.com/ajax/libs/twitter-bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
		<script src="http://libs.baidu.com/jquery/1.9.0/jquery.min.js"></script>
		<script src="http://libs.baidu.com/swfobject/2.2/swfobject.js"></script>
		<script src="http://cdnjs.bootcss.com/ajax/libs/twitter-bootstrap/2.3.1/js/bootstrap.min.js"></script>
		

		<style>
			body{ background: url(icon/bg_body.jpg) repeat-x #e5e5e5;margin:20px;text-align:center}
			#centerdiv{magin:0 auto}
			.box_div{width:950px;height:40px;margin:16px auto 0}
			.box_div a{text-decoration:none;outline:0 none;font-family:\5FAE\8F6F\96C5\9ED1}
			.inp_div{float:left;width:760px;height:38px;position:relative;overflow:hidden;border:#d0d0d0 1px solid;border-radius:2px}
			.inp_div input{width:730px;height:20px;line-height:20px;padding:9px 15px;color:#111;font-size:16px;outline:medium}
			.inp_div input:hover{background:rgba(255,255,200,0.2)}
			#play-button,#upload-button{float:left;margin:1px 0 0 5px;display:inline-block;background:#3b8ede;color:#fff;font-size:18px;padding:6px 25px;border-radius:2px;box-shadow:0 1px 3px rgba(0,0,0,0.3);min-height:26px}
			#play-button:hover,#upload-button:hover{background:#52a4f3;color:#fff;text-decoration:none}
			#upload-button{position: relative; padding-bottom: 6px; padding-left: 10px; padding-right: 10px; padding-top: 6px}
			.ll{margin:20px}
			.ll span{margin-right: 10px}
		</style>
		
	</head>
	<body>
		<!-- Baidu Button BEGIN -->
		<script type="text/javascript" id="bdshare_js" data="type=slide&amp;img=0&amp;mini=1&amp;pos=right&amp;uid=6731140" ></script>
		<script type="text/javascript" id="bdshell_js"></script>
		<script type="text/javascript">
		var bds_config={"bdTop":0};
		document.getElementById("bdshell_js").src = "http://bdimg.share.baidu.com/static/js/shell_v2.js?cdnversion=" + Math.ceil(new Date()/3600000);
		</script>
		<!-- Baidu Button END -->

		<div class="navbar navbar-inverse navbar-fixed-top">
			<div class="navbar-inner">
				<div class="container">
					<button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<a class="brand active" href="http://www.newgxu.cn/"><img src="/resources/entertainment/yundianbo/logo.png" width='20'height='20' style="float:left" /> 雨无声网站</a>
					<div class="nav-collapse collapse">
						<ul class="nav">
							<li class="active"><a href="http://lab.newgxu.cn/">实验室</a></li>
							<li class="active"><a href="http://lab.newgxu.cn/tv">网络电视</a></li>
							<li class="active"><a href="http://lab.newgxu.cn/yundianbo">云点播</a></li>
							<li class="active"><a href="http://lab.newgxu.cn/onlinetv">在线电视</a></li>
							<li class="active"><a href="http://lab.newgxu.cn/yinyuetai">音悦台</a></li>
							<li class="active"><a href="http://lab.newgxu.cn/xiami">虾米音乐</a></li>
							<li class="active"><a href="http://lab.newgxu.cn/kugou-music">酷狗音乐</a></li>
							<li class="active"><a href="http://lab.newgxu.cn/kugou-radio">酷狗电台</a></li>
							<li class="active"><a href="http://lab.newgxu.cn/fm">广播电台</a></li>	
			 			</ul>
					</div>
				</div>
			</div>
		</div>

		<div id="centerdiv" style="padding-top: 40px;">
			<iframe scrolling="no" frameborder="0" name="win_vod" id="win_vod" border="0" style="width: 950px; height: 550px" src="http://www.happyfuns.com/happyvod/api.html"></iframe>			
			
			<div class="box_div">
				<div class="inp_div">
					<input id="u" placeholder="请在此输入视频链接，或上传/拖入BT种子文件" type="text" value="请在此输入视频链接，或上传/拖入BT种子文件" />
				</div>
				<a class="button" href="#" id="play-button" onclick="return false;">播放</a>
				<a class="button" href="#" id="upload-button" onclick="return false;">上传种子</a>
			</div>
		</div>
		

		<div id="footer" style="padding-top: 40px;">
			<p>Copyright © 2004-2013 <a href="www.newgxu.cn">www.newgxu.cn</a> All rights reserved.
			<br>地址：广西大学办公楼南楼305室雨无声  电话:0771-3273880</br>  <a href="http://www.happyfuns.com/"><img src="/resources/entertainment/yundianbo/2.png" width='50'height='50'/> </a></p>
		</div>
		
		
		


<!--下面是用于播放控制的所有javascript,功能可由自己丰富-->
<script>
function playUrl(url){
	$('#u').val(url);
	$('#play-button').click();
	return false
}
(function () {
	var c = {},
	e = this,
	u = "localhost" == e.location.host,
	v = !!(e.navigator.userAgent + "").match(/(?:android|iphone|ipod|ipad|ios)/i),
	s = swfobject.hasFlashPlayerVersion("10");
	(e.navigator.userAgent + "").match(/(?:chrome)/i);
	c.uploader = {
		uploadFile : function () {
			if (c.uploader.uploader && c.uploader.uploader.x2_uploader_uploadFile)
				return c.uploader.uploader.x2_uploader_uploadFile()
		},
		setStyle : function (a) {
			if (c.uploader.uploader && c.uploader.uploader.x2_uploader_setStyle)
				return c.uploader.uploader.x2_uploader_setStyle(a)
		},
		uploadError : function (a, b) {
			$('#u').val(b);
			alert(6 == a ? "BT文件上传超时，请稍候重试" : 5 == a ? "BT文件大小超过6M，请选择其他BT文件" : "BT文件上传失败，请稍候重试")
		},
		browser : function () {},
		uploadSuccess : function (a) {
			if (a = jQuery.parseJSON(a)) {
				var b = a.ret;
				0 == b && 40 == a.infohash.length ? ($("#u").val("magnet:?xt=urn:btih:" + a.infohash), c.checkUrl()) : alert(2 == b ? "请求参数有误，请检查后重新添加" : 6 == b ? "该种子文件解析失败，请稍后再试" : "BT文件上传失败，请稍候重试")
			}
		},
		uploadProgress : function (a, b) {
			$('#u').val(b + "  正在上传  "
				+parseInt(100 * a, 10) + "%")
		},
		setFilename : function (a) {
			$('#u').val("开始上传 " + l(a))
		},
		init : function () {
			var a = $("#upload-button");
			if (a.length)
				if (s) {
					var b = a.outerHeight(),
					d = a.outerWidth();
					$('<div id="uploader"><div id="_uploader"></div></div>').css({
						position : "absolute",
						left : 0,
						top : 0,
						width : d,
						height : b,
						cursor : "pointer"
					}).appendTo(a.css({
							position : "relative"
						}).off("click"));
					swfobject.embedSWF("http://vod.xunlei.com/media/fileUploader.swf", "_uploader", d, b, "10.0.0", "http://vod.xunlei.com/library/expressInstall.swf", {
						description : "请选择BT种子文件(*.torrent)",
						extension : "*.torrent",
						timeOut : 10,
						url : "http://dynamic.vod.lixian.xunlei.com/interface/upload_bt",
						label : "",
						limitSize : 6442450944,
						jsPrefix : "x2.uploader.",
						asPrefix : "x2_uploader_",
						isImmediately : !0
					}, {
						wmode : "transparent",
						allowScriptAccess : "always"
					}, {
						id : "_uploader",
						name : "_uploader"
					}, function (b) {
						b.success && (c.uploader.uploader = (-1 != e.navigator.appName.indexOf("Microsoft") ? e : document)._uploader, a.prop("disabled", !1))
					})
				} else
					a.hide()
		}
	};
	c.checkUrl = function () {
		var b = $.trim($("#u").val());
		b.match(/^[a-fA-F0-9]{40}$/i) && (b = "magnet:?xt=urn:btih:" + b.toUpperCase()) && ($("#u").val(b));
		var x = "xlpan://|thunder://|ftp://|http://|https://|ed2k://|mms://|magnet:|rtsp://|flashget://|qqdl://|bt://",
		j = new RegExp("^(" + x + ").{9,}", "i");
		if (b.match(j)) {
			c.play(b);
		} else
			alert("您输入的视频下载地址有误。"), $("#u").focus()
	};
	c.play = function (a) {
		var frame = $("#win_vod"),
		url = "http://www.happyfuns.com/happyvod/api.html?url=" + a;
		frame.attr({
			"src" : url
		});
	};
	$(document).ready(function () {
		if ($("#play-button").length > 0) {
			$("#u").click(function () {
				var txt_value = $(this).val();
				if (txt_value == this.defaultValue) {
					$(this).val("");
				} else
					this.select();
			});
			$("#u").blur(function () {
				var txt_value = $(this).val();
				if (txt_value == "") {
					$(this).val(this.defaultValue);
				};
			});
			$("#play-button").click(function (a) {
				c.checkUrl()
			});
			c.uploader.init();
		}
	});
	e.x2 = c
})();
</script>
<script>var _hmt=_hmt||[];(function(){var hm=document.createElement("script");hm.src="//hm.baidu.com/hm.js?cb32f6bd39c68d530cd8ad7977be748d";var s=document.getElementsByTagName("script")[0];s.parentNode.insertBefore(hm,s);})();</script>						
</body>
</html>