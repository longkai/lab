<#include "notty/macro.ftl" />
<@L.html>
<@L.head title="修改认证信息 - 雨无声实验室">
<style type="text/css">
body {
	padding-top: 60px;
	padding-bottom: 40px;
}
#update {
	max-width: 680px;
	margin: 20px auto;
}
.alert {
	display: none;
}
#auth_info {
	border: 1px solid #f4f4f4;
}
#pwd_update {
	border: 1px solid #f4f4f4;
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

<div class="container" id="update">
	<div class="alert alert-success" id="success">
		<p>修改成功！</p>
	</div>
	<div class="alert alert-error" id="error">
		<p>
			修改失败！
			<span id="msg"></span>
			<span id="reason"></span>
		</p>
	</div>
	<form class="form-horizontal" method="post" action="#" id="profile_form">
		<p class="text-center text-info">修改认证信息</p>
		<div class="control-group">
			<label class="control-label" for="contact">联系方式</label>
			<div class="controls">
				<input type="text" id="contact" name="contact" value="${auth_user.contact}" required />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="about">关于自己</label>
			<div class="controls">
				<textarea rows="3" name="about">${auth_user.about}</textarea>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="password">请键入您的密码</label>
			<div class="controls">
				<input type="password" id="password" name="password" placeholder="" />
			</div>
		</div>
		<div class="control-group">
			<div class="controls">
				<a href="#" role="button" class="btn btn-success" id="update_profile">确认修改！</a>
			</div>
		</div>
	</form>
	<hr />
	<form action="" class="form-horizontal" id="pwd_update">
		<p class="text-center text-info">修改密码</p>
		<div class="control-group">
			<label class="control-label" for="password">旧密码</label>
			<div class="controls">
				<input type="password" id="password" name="old_password" placeholder="" required />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="pwd1">新密码</label>
			<div class="controls">
				<input type="password" id="pwd1" name="new_password" placeholder="密码至少6位" required />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="pwd2">确认新密码</label>
			<div class="controls">
				<input type="password" id="pwd2" name="confirmed_password" placeholder="密码至少6位" required />
			</div>
		</div>
		<div class="control-group">
			<div class="controls">
				<a href="#" role="button" id="update_pwd" class="btn btn-success">确认修改！</a>
			</div>
		</div>
	</form>
</div>
<div class="container">
	<footer>
		<p>&copy; 2001-2013 <a href="//www.newgxu.cn">newgxu.cn</a> Yws. All Rights Reserved.</p>
	</footer>
</div>
</body>
<@L.script>
<script type="text/javascript" src="/resources/notty/js/notty.js"></script>
<script type="text/javascript">
$(function() {
    $('._index').removeClass('active');
	$('#logout').click(function(e) {
		e.preventDefault();
		notty.logout();
	})
	
	$('#update_pwd').click(function(e) {
		e.preventDefault();
		$('.alert').hide();
		var uid = $('#user_service').attr('uid');
		$.ajax({
			url: '/auth_users/' + uid + '/password',
			type: 'PUT',
			data: $('#pwd_update').serialize(),
			dataType: 'json',
			success: function(data) {
				window.scrollTo(0, 0);
				if (data.status === 1) {
					$('#success').show();
				} else if (data.status === 0) {
					$('#msg').text(data.msg);
					// $('#reason').text(data.reason);
					$('#error').show();
				} else {
					$('#error').show();
				}
			},
			error: function() {
				$('#error').show();
			}
		})
		$(':password').val('');
	})

	$('#update_profile').click(function(e) {
		e.preventDefault();
		$('.alert').hide();
		var uid = $('#user_service').attr('uid');
		$.ajax({
			url: '/auth_users/' + uid + '/profile',
			type: 'PUT',
			data: $('#profile_form').serialize(),
			dataType: 'json',
			success: function(data) {
				if (data.status === 1) {
					$('#success').show();
				} else if (data.status === 0) {
					$('#msg').text(data.msg);
					// $('#reason').text(data.reason);
					$('#error').show();
				} else {
					$('#error').show();
				}
			},
			error: function() {
				$('#error').show();
			}
		})
		$(':password').val('');
	})
})
</script>
</@L.script>
</@L.html>