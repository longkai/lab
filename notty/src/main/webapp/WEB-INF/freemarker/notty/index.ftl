<#include "notty/macro.ftl" />
<@L.html>
<@L.head title="校园信息 - 雨无声实验室">
<style type="text/css">
body {
	padding-top: 60px;
	padding-bottom: 40px;
}
#show {
	position: relative;
}
#qrcode {
    padding-left: 20px;
    height: 100px;
    cursor: pointer;
}
#keynote span {
	color: #fff;
	font-size: 50px;
	padding-left: 10px;
	text-shadow: 0 1px 1px rgba(0,0,0,.4);
}
#header {
	margin-bottom: 15px;
}
#keynote {
	position: absolute;
	left: 5%;
	bottom: 5%;
}
#keynote h3 {
	color: #fff;
	text-shadow: 0 1px 1px rgba(0,0,0,.4);
}
.nav-header {
	font-size: 1.2em;
	padding: 5px;
}
.icon-chevron-right {
	opacity: .5;
	float: right;
}
@media (max-width: 980px) {
	body {
		padding-top: 0px;
	}
	#keynote h2 {
		font-size: 20px;
	}
	#keynote img {
		display: none;
	}
	#keynote span {
		font-size: 30px;
	}
	#header {
		/*margin-bottom: 30px;*/
	}
}
@media (max-width: 680px) {
	#keynote span {
		font-size: 25px;
	}
	#header {
		display: none;
	}
}
@media (max-width: 480px) {
	.mobile {
		display: block;
	}
	#show {
		display: none;
	}
	.mobile button {
		margin-bottom: 5px;
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
					<li><a href="/apps/1/download">客户端app</a></li>
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
<div class="container">
	<div id="show">
		<img src="/resources/notty/images/notice.jpg" alt="校园信息" />
		<div class="container" id="keynote">
			<div id="header">
				<img src="/resources/notty/images/notices_logo.jpg" alt="" id="logo" />
				<span class="">校园信息</span>
                <img id="qrcode" src="/resources/notty/images/notice_qr.png" alt="" title="下载校园信息客户端:-)" />
			</div>
			<h3>搭载App，轻松获取校园一手信息:-)</h3>
			<p>
				<a href="#" class="btn btn-info btn-large description">详细介绍</a>
				<a href="/resources/notty/html/auth.html" class="btn btn-success btn-large">申请认证</a>
				<a href="/resources/notty/notice-api.txt" class="btn btn-warning btn-large">开放API接口</a>
			</p>
		</div>
	</div>
</div>
<div class="container hide mobile">
	<div class="hero-unit">
		<h2>欢迎来到雨无声校园信息发布平台！</h2>
		<h4>搭载App，轻松获取校园一手信息:-)</h4>
		<a href="#" class="btn btn-info btn-block description">详细介绍</a>
		<a href="/resources/notty/html/auth.html" class="btn btn-success btn-block">申请认证</a>
		<a href="/resources/app/yws-notices.apk" class="btn btn-success btn-block">Android客户端</a>
	</div>
</div>
<hr />
<div class="container">
	<div class="row-fluid">
		<div class="span3">
			<ul class="nav nav-list" id="latest_auth_list">
				<li class="nav-header">最新认证</li>
<#list auth_users as u>
				<li>
					<a href="/auth_users/${u.id}" uid="${u.id}" class="view_profile"><i class="icon-chevron-right"></i> ${u.authorizedName}</a>
				</li>
</#list>
			</ul>
			<button id="more_user" class="btn btn-block btn-info">更多</button>
		</div>
		<div class="span9">
			<div class="row-fluid">
<#list notices as n>
				<div class="span4 primary_notice" notice_id="${n.id}">
<#--
	<#if n.title?length gt 20>
					<h2>${n.title?string[0..19]}...</h2>
	<#else>
					<h2>${n.title}</h2>
	</#if>
-->
					<h2>${n.title}</h2>
	<#--<#if n.content?length gt 100>
					<p>${n.content?string[0..99]}...</p>
	<#else>
					<p>${n.content}</p>
	</#if>-->
					<p><a href="/auth_users/${n.author.id}" class="btn btn-muted view_profile">${n.author.authorizedName}</a> <a class="btn btn-info" href="/notices/${n.id}" target="_blank">详细 &raquo;</a></p>
				</div>
</#list>
			</div>
			<button class="btn btn-block btn-success" id="more_notices">更多</button>
		</div>
	</div>
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
<div id="modal_description" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="modal_descriptionLabel" aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		<h3 class="title">雨无声校园信息</h3>
	</div>
	<div class="modal-body">
		<div id="description">
			<h4 class="head text-success">这是一个轻松获取校园信息的地方</h4>
			<h4>这上面发表的信息会出现在雨无声新闻网，论坛上以及校园信息的 Android app上:-)</h4>
			<h4>任何院校机构，学生组织经过雨无声的认证后均可在其上发布信息</h4>
			<h4>校园信息是开放源代码与API接口的，详见雨无声实验室首页</h4>
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
		<p>&copy; 2001-2013 <a href="//www.newgxu.cn">newgxu.cn</a> Yws. All Rights Reserved.</p>
	</footer>
</div>
</body>
<@L.script>
<script type="text/javascript" src="/resources/notty/js/notty.js"></script>
<script type="text/javascript">
$(function() {
	$('#profile span').addClass('text-info');
    $('#qrcode').click(function(e) {
        e.preventDefault();
        window.open("/apps/1/download");
    })

	$('.description').click(function(e) {
		e.preventDefault();
		$('#modal_description').modal();
	})

	$('#login_btn').click(function(e) {
		// ff 传event有问题，所以手动搞了
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

	$('#more_user').click(function(e) {
		e.preventDefault();
		var last_user_id = $(this).attr('last_user_id');
		// console && console.log(last_user_id);
		if (!last_user_id) {
			last_user_id = $('#latest_auth_list li:last a').attr('uid');
		}
		// console && console.log(last_user_id);
		var default_count = 5;
		$('#more_user').attr('disabled', true).text('正在努力加载中^_^');
		// console.log(last_user_id);
		$.ajax({
			 url: '/auth_users',
			type: 'GET',
			data: {
				type: 1,
				count: default_count,
				append: true,
				offset: last_user_id
			},
			dataType: 'json',
			success: function(data) {
				var length = data.auth_users.length;
				$.each(data.auth_users, function(i, item) {
					$('<li>')
						.append($('<a>').text(item.authed_name).attr('href', '/auth_users/' + item.id).attr('uid', item.id).addClass('view_profile')
								.append($('<i>').addClass('icon-chevron-right')))
						.appendTo('#latest_auth_list');
					if (i === length - 1) {
						$('#more_user').attr('last_user_id', item.id);
					}
				})
				if (length < default_count) {
					$('#more_user').attr('disabled', true).text('没有更多啦！');
					all = true;
				} else {
					$('#more_user').attr('disabled', false).text('更多');
				}
			},
			error: function(jqxhr, status, error) {
				alert(error + ' -> ' + jqxhr.responseText);
			}
		})
	})

	$('#more_notices').click(function(e) {
		e.preventDefault();
		$('#more_info_error').hide();
		var last = $(this).attr('last_notice_id');
		if (!last) {
			last = $('.primary_notice:last').attr('notice_id');
		}
		// console.log(last);
		var default_count = 3;
		$('more_notices').text('正在努力加载中^_^').attr('disabled', true);
		$.ajax({
			url: '/notices',
			type: 'GET',
			data: {
				type: 2,
				count: default_count,
				nid: last,
				append: true
			},
			dataType: 'json',
			success: function(data) {
				var length = data.notices.length;
				var column$ = $('<div>').addClass('row-fluid');
				$.each(data.notices, function(i, item) {
//					var content = item.content;
//					if (content.length > 100) {
//						content = content.substring(0, 99) + '...';
//					}
					var title = item.title;
//					if (title.length > 20) {
//						title = title.substring(0, 19) + '...';
//					}
//					$('<div>').addClass('span4').append($('<h2>').text(title)).append($('<p>').text(content))
					$('<div>').addClass('span4').append($('<h2>').text(title))
						.append($('<p>').append($('<a>').attr('href', '/auth_users/' + item.author.id).addClass('btn view_profile').text(item.author.authed_name)).append(' ').append($('<a>').addClass('btn btn-info').attr({
							target: '_blank',
							href: '/notices/' + item.id
						}).html('详细 &raquo;'))).appendTo(column$);

					if (i === length - 1) {
						$('#more_notices').attr('last_notice_id', item.id);
					}
				})
				column$.insertBefore('#more_notices');
				if (length < default_count) {
					$('#more_notices').attr('disabled', true).text('没有更多啦！');
				} else {
					$('#more_notices').attr('disabled', false).text('更多');
				}
			},
			error: function(jqxhr, status, error) {
				$('#more_notices_error strong').text(error + ' -> ' + jqxhr.responseText);
				$('#more_notices_error').show();
			}
		})
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
</@L.script>
<@L.ga_notices />
</@L.html>
