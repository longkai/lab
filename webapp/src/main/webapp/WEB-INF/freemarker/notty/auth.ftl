<#include "notty/macro.ftl" />
<@L.html>
<@L.head title="用户认证 - 雨无声实验室">
<style type="text/css">
body {
	padding-top: 60px;
	padding-bottom: 40px;
}
@media (max-width: 980px) {
	body {
		padding-top: 0px;
	}
}
#auth {
	max-width: 680px;
	margin: 20px auto;
}
.alert {
	display: none;
}
</style>
</@L.head>
<body>
<@header />
<div class="container" id="auth">
	<div class="alert alert-success" id="success">
		<p id="msg">申请成功</p>
	</div>
	<div class="alert alert-error" id="error">
		<p>认证失败！<span id="info"></span> <span id="reason"></span></p>
	</div>
	<form class="form-horizontal" method="post" action="#" id="auth_form">
		<div class="control-group">
			<label class="control-label" for="authorizedName">显示名称</label>
			<div class="controls">
				<input type="text" id="authorizedName" name="authorizedName" placeholder="显示在信息发布处" required />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="org">我是</label>
			<div class="controls">
				<input type="text" id="org" name="org" placeholder="组织，单位" required maxlength="20" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="contact">联系方式</label>
			<div class="controls">
				<input type="text" id="contact" name="contact" placeholder="电子邮件或者电话" required />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="account">登陆账号</label>
			<div class="controls">
				<input type="text" id="account" name="account" placeholder="不少于6个字符" maxlength="30" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="password">密码</label>
			<div class="controls">
				<input type="password" id="password" name="password" placeholder="不少于6个字符" maxlength="20" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="inputPassword">确认密码</label>
			<div class="controls">
				<input type="password" id="inputPassword" name="confirmed_password" placeholder="不少于6个字符" maxlength="20" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="about">关于自己</label>
			<div class="controls">
				<textarea rows="3" name="about" placeholder="简单地介绍一下自己吧:)"></textarea>
			</div>
		</div>
		<div class="control-group">
			<div class="controls">
				<a href="#license" role="button" class="btn btn-success" data-toggle="modal">申请认证</a>
			</div>
		</div>
	</form>

	<div id="license" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3>雨无声信息发布条款</h3>
		</div>
		<div class="modal-body">
			<p>请阅读雨无声校园信息发布条款</p>
			<ol>
				<li>发布信息时请您认证审核，必须符合国家法律以及学校规定</li>
				<li>您所发布的信息并不代表雨无声的立场</li>
				<li>对于违规用户，雨无声有权回收帐号</li>
				<li>本应用在较旧的浏览器上表现不佳，为了获得较好的使用体验，请使用较新的浏览器</li>
				<li>雨无声承诺保护您的隐私和数据，不会做任何干涉。本条款的最终解释权归雨无声所有。</li>
			</ol>
		</div>
		<div class="modal-footer">
			<button class="btn btn-danger" id="no" data-dismiss="modal" aria-hidden="true">拒绝</button>
			<button class="btn btn-success" id="yes" data-dismiss="modal" aria-hidden="true">接受</button>
		</div>
	</div>
</div>
</body>
<@L.script>
<script type="text/javascript" src="/resources/notty/js/notty.js"></script>
<script type="text/javascript">
$(function() {
	$('#yes').click(function() {
		$('.alert').hide();
		$.ajax({
			url: 		'/auth_users/create',
			type: 		'POST',
			data: 		$('#auth_form').serialize(),
			dataType: 	'json',
			success: function(data) {
				if (data.id) {
					$('#success').fadeIn();
				} else if (data.status === 0) {
					$('#info').text(data.msg);
					// $('#reason').text('原因：' + data.reason);
					$('#error').fadeIn();
				} else {
					$('#error').fadeIn();
				}
			},
			error: function() {
				$('#error').fadeIn();
			}
		})
		$(':password').val('');
		return true;
	})
})
</script>
</@L.script>
<@L.ga_notices />
</@L.html>