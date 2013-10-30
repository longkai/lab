<#include "notty/macro.ftl" />
<@L.html>
<@L.head title="修改信息 - 雨无声实验室">
<link rel="stylesheet" href="/resources/libs/pagedown/1.1.0/pagedown.css" media="screen" />
<style type="text/css">
body {
	padding-top: 60px;
	padding-bottom: 40px;
}
footer {
	margin: 20px 0;
}
@media (max-width: 980px) {
	body {
		padding-top: 0px;
	}
}
</style>
</@L.head>
<body>
<@header />
<div class="container">
	<div id="main">
		<div class="input-prepend">
  			<span class="add-on">标题</span>
			<input type="text" id="title" name="title" value="${notice.title}" placeholder="别忘了填写标题哦~" class="input-xxlarge" />
  		</div>
  		<!-- 只能上传一个文件，文件超过一个请自行压缩，大小不能超过5M。 -->
		<a class="pull-right btn btn-info" href="/notices/${notice.id}/upload_file" id="file">上传文件</a>
		<form action="show.jsp" id="upForm" name="upForm" method="post">
			<script type="text/plain" id="notice_editor">${notice.content}</script>
		</form>
		<button class="btn btn-block btn-success" id="post">修改！</button>
	</div>
</div>
<div id="dialog" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="modal_descriptionLabel" aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		<h3 class="title">通告修改成功！</h3>
	</div>
	<div class="modal-body">
		<div id="_content"></div>
	</div>
	<div class="modal-footer">
		<a href="#" id="upload_file" class="btn btn-success">上传文件?</a>
		<a href="#" id="lookat" class="btn btn-info" data-dismiss="modal" aria-hidden="true">在新页面查看！</a>
		<a href="#" class="btn" data-dismiss="modal" aria-hidden="true">关闭</a>
	</div>
</div>
<div class="container">
	<footer>
		<p>&copy; 2001-2013 newgxu.cn Yws. All Rights Reserved.</p>
	</footer>
</div>
</body>
<@L.script>
<script type="text/javascript">
!window.jQuery && document.write('<script src="/resources/js/libs/jquery/1.9.1/jquery.min.js" type="text/javascript"><\/script>');
window.UEDITOR_HOME_URL = "/resources/libs/ueditor/1.2.6.1/";
</script>
<script type="text/javascript" charset="utf-8" src="/resources/libs/ueditor/1.2.6.1/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="/resources/libs/ueditor/1.2.6.1/ueditor.all.js"></script>
<script type="text/javascript">
$(document).ready(function(){
		var editor = new UE.ui.Editor({
			textarea : 'content',
			initialFrameHeight:400
		});
		editor.render("notice_editor");

		$('#post').click(function(e) {
			e.preventDefault();
			$.ajax({
				url: '/notices/${notice.id}/modify',
				type: 'PUT',
				dataType: 'json',
				data: {
					title: $('#title').val(),
					content: editor.getContent()
				},
				success: function(notice) {
					if (notice.id) {
						$('#lookat').attr('href', '/notices/' + notice.id);
						$('#_content').html(notice.content);
                        $('#upload_file').attr('href', '/notices/' + notice.id + '/upload_file');
						$('#dialog').modal();
					}
				}
			})
		})

		$('#lookat').click(function(e) {
			e.preventDefault();
			window.location.href = $(this).attr('href');
		})

    $('#upload_file').click(function(e) {
        e.preventDefault();
        location.href = $(this).attr('href');
    })

});

$(function() {
	// 消除include的影响
	$('#_index').removeClass('active');

	$('#logout').click(function(e) {
		e.preventDefault();
		notty.logout();
	})
})
</script>
</@L.script>
</@L.html>