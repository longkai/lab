<#include "notty/macro.ftl" />
<@L.html>
<@L.head title="授权用户列表 - 雨无声实验室">
<style type="text/css">
body {
	padding-top: 60px;
	padding-bottom: 40px;
}
#template {
	display: none;
}
#alert {
	margin: 20px 0;
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
	<table class="table table-striped" id="users">
		<tr class="success">
			<td>#</td>
			<td>认证名称</td>
			<td>机构/组织</td>
			<td>登陆帐号</td>
			<td>联系方式</td>
			<td>申请时间</td>
			<td>重置密码？</td>
		</tr>
<#list auth_users as u>
		<tr uid="${u.id}">
			<td>${u_index + 1}</td>
			<td><code class="text-info">${u.authorizedName}</code></td>
			<td><code class="text-info">${u.org}</code></td>
			<td><code class="text-success">${u.account}</code></td>
			<td><code class="text-warning">${u.contact}</code></td>
			<td><code class="text-info">${u.joinDate?string('yyyy-MM-dd hh:mm:ss')}</code></td>
			<td><code><a href="#" class="reset_pwd" uid="${u.id}">重置密码</a></code></td>
		</tr>
</#list>
	</table>
	<button id="more" class="btn btn-block">查看更多！</button>
</div>
<table id="template" class="hide">
	<tbody class="item">
		<tr>
			<td class="index"></td>
			<td><code class="name text-info"></code></td>
			<td><code class="org text-info"></code></td>
			<td><code class="account text-success"></code></td>
			<td><code class="contact text-warning"></code></td>
			<td><code class="join_date text-info"></code></td>
			<td><a href="#" class="reset_pwd">重置密码</a></code></td>
		</tr>
	</tbody>
</table>
<div class="container">
	<footer>
		<p>&copy; 2001-2013 <a href="//www.newgxu.cn">newgxu.cn</a> Yws. All Rights Reserved.</p>
	</footer>
</div>

</body>
<@L.script>
<script type="text/javascript" src="/resources/notty/js/notty.js"></script>
<script type="text/javascript" src="/resources/js/common/datetime.js"></script>
<script type="text/javascript">
$(function() {
	var lastIndex = ${auth_users?size};
	$('.active').removeClass();

	$('#more').click(function(e) {
		e.preventDefault();
		var more$ = $(this);
		var last_uid = more$.attr('last_uid');
		if (!last_uid) {
			last_uid = $('#users tr:last').attr('uid');
		}
		var count = 5;
		more$.attr('disabled', 'true').text('正在努力加载中...');
		$.ajax({
			url: '/auth_users',
			type: 'GET',
			dataType: 'json',
			data: {
				count: count,
				append: true,
				type: 1,
				offset: last_uid
			},
			success: function(data) {
				var length = data.auth_users.length;
				$.each(data.auth_users, function(i, u) {
					lastIndex++;
					var item$ = $('#template .item').children().clone();
					item$.find('.index').text(lastIndex);
					item$.find('.name').text(u.authed_name);
					item$.find('.org').text(u.org);
					item$.find('.account').text(u.account);
					item$.find('.contact').text(u.contact);
					item$.find('.join_date').text(datetime.format('yyyy-MM-dd HH:mm:ss', u.join_date));
					item$.find('.reset_pwd').attr('uid', u.id);

					item$.appendTo('#users');

					if (length === i + 1) {
						more$.attr({
							'last_uid': u.id
						});
					}
				})
				if (length < count) {
					more$.attr('disabled', true).text('没有更多啦！');
				} else {
					more$.attr('disabled', false).text('查看更多！');
				}
			}
		})
	})

	$('#logout').click(function(e) {
		e.preventDefault();
		notty.logout();
	})
})
$('body').on('click', 'a.reset_pwd', function(e) {
	e.preventDefault();
	var uid = $(this).attr('uid');
	var pwd = prompt('请输入需要重置的密码！');
	if (pwd) {
		$.ajax({
			url: '/auth_users/reset_password',
			type: 'PUT',
			dataType: 'json',
			data: {
				uid: uid,
				password: $.trim(pwd)
			},
			success: function() {
				alert('修改成功，请通知该用户密码已经被重置！');
			},
			error: function(jqxhr, status, error) {
				alert(error + ' -> ' + jqxhr.responseText);
			}
		});
	} else {
		// do nothing...
	}
})
</script>
</@L.script>
</@L.html>