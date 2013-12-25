<#include "notty/macro.ftl" />
<@L.html>
<@L.head title="${notice.title} - 校园信息">
<style type="text/css">
body {
	padding-top: 60px;
	padding-bottom: 40px;
}
.container-narrow > hr {
	margin: 30px 0;
}
.jumbotron h1 {
	font-size: 52px;
	line-height: 1;
}
.jumbotron .btn {
	font-size: 21px;
	padding: 14px 24px;
}
#content {
	padding: 10px 0;
}
#alert {
	display: none;
}
#profile span {

}
#show {
	margin: 0 auto;
}
p {
	clear: both;
}
footer {
	margin: 40px 0;
}
@media (min-width: 1170px) {
    #show {
        width: 960px;
    }
}
@media (max-width: 980px) {
	body {
		padding-top: 0px;
	}
}
@media (max-width: 767px) {
    #show h1 {
        font-size: 38px;
    }
}
</style>
</@L.head>
<body>
<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container">
			<button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="brand" href="#">校园信息</a>
			<div class="nav-collapse collapse">
				<ul class="nav">
					<li class="active"><a href="/info/">首页</a></li>
					<li><a href="/resources/notty/html/auth.html">认证</a></li>
					<li><a href="/resources/notty/html/help.html">帮助</a></li>
					<li><a href="/static/apps/notices.apk">客户端app</a></li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							雨无声实验室 <b class="caret"></b>
						</a>
						<ul class="dropdown-menu">
							<li><a href="/">首页</a></li>
							<li><a href="/about.html">关于实验室</a></li>
						</ul>
					</li>
					<li>
						<form class="navbar-form">
							<input class="span2" type="text" id="search_txt" placeholder="搜索试试~" />
							<a class="btn btn-success" id="btn_search">搜索</a>
						</form>
					</li>
				</ul>
<#if Session.session_auth_user??>
				<div class="btn-group pull-right" id="user_service" uid="${Session.session_auth_user.id}">
					<a class="btn btn-primary" href="#"><i class="icon-user icon-white"></i> ${Session.session_auth_user.authorizedName}</a>
					<a class="btn btn-primary dropdown-toggle" data-toggle="dropdown" href="#"><span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="/resources/notty/html/publication.html"><i class="icon-pencil"></i> 发表信息</a></li>
						<li><a href="/auth_users/${Session.session_auth_user.id}/modify"><i class="icon-edit"></i> 修改个人信息</a></li>
	<#if Session.session_auth_user.admin>
						<li><a href="/auth_users/auth"><i class="icon-plus"></i> 授权</a></li>
						<li><a href="/auth_users/view"><i class="icon-list-alt"></i> 授权用户列表</a></li>
	</#if>
						<li><a href="/notices/my"><i class="icon-th-list"></i> 查看我发表的信息</a></li>
						<li class="divider"></li>
						<li id="logout"><a href="#"><i class="i"></i> 退出</a></li>
					</ul>
				</div>
<#else>
				<form class="navbar-form pull-right" method="post" id="login_form">
					<input class="span2" type="text" name="account" placeholder="账号">
					<input class="span2" type="password" name="password" placeholder="密码">
					<a class="btn btn-success" id="login_btn">登陆</a>
				</form>
</#if>
			</div>
		</div>
	</div>
</div>

<div id="error" class="container">

</div>

<div class="container" id="show">
<@view_notice />
</div>
<div id="modal_profile" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="modal_profileLabel" aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		<h3 class="title"></h3>
	</div>
	<div class="modal-body">
		<div id="profile">
			<p>Ta是： <span id="authed_name"></span></p>
			<p>来自： <span id="org"></span></p>
			<p>关于Ta： <span id="about"></span></p>
			<p>联系Ta吧： <span id="contact"></span></p>
		</div>
	</div>
	<div class="modal-footer">
		<a href="#" class="btn btn-success" data-dismiss="modal" aria-hidden="true">关闭</a>
	</div>
</div>
<div id="modal_search_results" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="modal_descriptionLabel" aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		<h3 class="title"></h3>
	</div>
	<div class="modal-body">
		<ol id="results">
			
		</ol>
	</div>
	<div class="modal-footer">
		<a href="#" class="btn btn-success" data-dismiss="modal" aria-hidden="true">关闭</a>
	</div>
</div>
<div class="container">
	<footer>
		<p class="pull-right"><a href="#">回到顶部</a></p>
		<p>&copy; 2001-2013 <a href="//www.newgxu.cn">newgxu.cn</a> Yws. All Rights Reserved.</p>
	</footer>
</div>
</body>
<@L.script>
<script type="text/javascript" src="/resources/notty/js/notty.js"></script>
<#if notice.id <= 31>
<script type="text/javascript" src="/resources/libs/pagedown/1.1.0/Markdown.Converter.js"></script>
</#if>
<script type="text/javascript">
<#if notice.id <= 31>
window.onload = function() {
	var converter = new Markdown.Converter();
	var html = converter.makeHtml('${notice.content?js_string}');
	$('#content').html(html);
}
</#if>

$(function() {
	$('.active').removeClass('active');
	$('#profile span').addClass('text-info');

	$('#login_btn').click(function(e) {
		e.preventDefault();
		notty.login({
			login_form: '#login_form',
			url: '/notice/login',
			errorHint: '#error',
			params: ['account', 'password']
		})
	})
	$('#logout').click(function(e) {
		e.preventDefault();
		notty.logout();
	})

	$('#btn_search').click(function(e) {
		e.preventDefault();
		var modal$ = $('#modal_search_results');
		modal$.find('#results').empty();
		var keywords = $('#search_txt').val();
		if (!$.trim(keywords)) {
			modal$.find('.title').text('您没有输入有意义的关键字哦！');
			$('#search_txt').val('');
			modal$.modal();
			return;
		};
		$.ajax({
			url: '/notices/q',
			type: 'GET',
			dataType: 'json',
			data: {
				q: keywords,
				count: 50
			},
			success: function(data) {
				modal$.find('.title').text('找到了最近的 ' + data.notices.length + ' 条和 \'' + keywords + '\' 相关的通告！');
				$.each(data.notices, function(i, n) {
					$('<li>').append($('<a>').attr({
						href: '/notices/' + n.id,
						target: '_blank'
					}).text(n.title).addClass('lead')).appendTo(modal$.find('#results'));
				})
				modal$.modal();
			},
			error: function(jqxhr, status, error) {
				alert(error + ' -> ' + jqxhr.responseText);
			}
		})
	})
})
$('body').on('click', 'a.view_profile', function(e) {
		e.preventDefault();
		var url = $(this).attr('href');
		// console.log(url);
		$.ajax({
			 url: url,
			type: 'GET',
		dataType: 'json',
		success: function(u) {
			$('#authed_name').text(u.authed_name);
			$('#org').text(u.org);
			$('#about').text(u.about);
			$('#contact').text(u.contact);
			$('#modal_profile .title').text(u.authed_name);
			$('#modal_profile').modal();
		},
		error: function(jqxhr, status, error) {
			alert(error + ' -> ' + jqxhr.responseText);
		}
	})
})
</script>
<@L.ga_notices_view />
</@L.script>
</@L.html>