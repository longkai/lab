<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head>
	<title>应用市场 - 雨无声实验室</title>
	<meta name="keywords"
		  content=" 雨无声实验室 广西大学 广西大学雨无声网站 龙凯 longkai @爱因斯坦的狗 im.longkai@gmail.com" />
	<meta name="description"
		  content=" 雨无声实验室 广西大学 广西大学雨无声网站 龙凯 longkai @爱因斯坦的狗 im.longkai@gmail.com" />
	<meta name="author" content="龙凯 longkai @爱因斯坦的狗" />
	<meta name="email" content="im.longkai@gmail.com" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0-rc2/css/bootstrap-glyphicons.css"
		  rel="stylesheet">
	<link href="/r/libs/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet" media="screen">
	<style type="text/css">
		body {
			padding-top: 70px;
		}

		.navbar {
			background-color: rgb(179, 200, 51);
		}

		nav a:hover {
			color: #ffffff;
		}

		.navbar-brand {
			color: #ffffff;
			padding-top: 20px;
		}

		a:focus {
			background-color: rgb(179, 200, 51);
		}

		.navbar-nav > .active > a {
			background-color: rgb(179, 200, 51);

		}

		.navbar-collapse {
			/*max-height: 340px;*/
			padding: 5px 15px;
			overflow-x: visible;
			overflow-y: auto;
			border-top: 1px solid #e6e6e6;
			box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.1);
		}

		.holder {
			margin-top: 10px;
		}

		#name {
			color: #333;
			font-size: 28px;
			font-weight: 300;
			line-height: 35px;
			white-space: normal;
		}

		#author {
			font-size: 16px;
		}

		#date {
			color: #8d8d8d;
			display: inline-block;
			font-weight: 300;
			white-space: nowrap;
			font-size: 12px;
		}

		#category {
			color: #333;
			display: inline-block;
			font-weight: 300;
			font-size: 13px;
		}

		.app p {
			margin: 0 0 5px 0;
		}

		#meta-data {
			background-color: #f5f5f5;
		}

		#score {
			font-size: 60px;
			color: #333;
			font-weight: 100;
		}

		.user {
			font-size: 13px;
		}

		.comment {
			text-indent: 2em;
			font-size: 17px;
			color: #333;
		}

		.progress {
			width: 200px;
		}
	</style>
</head>
<body>

<nav class="navbar navbar-fixed-top" role="navigation">
	<!-- Brand and toggle get grouped for better mobile display -->
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-ex1-collapse">
				<span class="sr-only">导航</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">应用市场beta</a>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="navbar-collapse navbar-ex1-collapse">
			<ul class="nav navbar-nav">
				<li><a href="/app">首页</a></li>
				<li><a href="/">雨无声实验室</a></li>
			</ul>
			<button type="button" class="btn btn-default navbar-btn"
					u="${Session.session_member.id}" id="my">提交我的作品
			</button>
		<#if Session.session_member??>
			<button type="button" uid="${Session.session_member.id}"
					class="btn btn-primary navbar-btn pull-right"
					id="member">${Session.session_member.nick}</button>
		</#if>
		</div>
		<!-- /.navbar-collapse -->
	</div>
</nav>


<div class="container" id="meta-data">
	<img src="${app.icon}" class="pull-left" alt="" style="width: 200px;" />

	<div class="app">
		<h2 id="name">${app.name}</h2>

		<p><span id="author"><a href="#">${member.nick}</a></span> <span
				id="date">${version.update_date?string('yyyy年M月d日')}</span></p>

		<p id="category">${app.platform} / ${app.category}</p>

		<p id="os_requirement">系统要求：${app.os_requirement}</p>

		<div>
			<a class="btn btn-success" id="download"
			   href="/apps/${app.id}/download/${version.id}"><span
					class="glyphicon glyphicon-download-alt"></span> 下载</a>
		</div>
	</div>
</div>
<div class="container holder">
	<div class="panel panel-success">
		<div class="panel-heading">应用说明</div>
		<div class="panel-body">
		${app.description}
		<#if app.extra??>
			<blockquote>${app.extra}</blockquote>
		</#if>
		</div>
	</div>
	<div class="panel panel-info">
		<div class="panel-heading">新版变化</div>
		<div class="panel-body">
		${version.update_info}
		<#if app.extra??>
			<blockquote>${version.extra}</blockquote>
		</#if>
		</div>
	</div>
	<div class="panel" id="other">
		<div class="panel-heading">其他信息</div>

		<table class="table">
			<thead>
			<tr>
				<th>最近更新日期</th>
				<th>大小</th>
				<th>安装次数</th>
				<th>当前版本</th>
				<th>系统版本要求</th>
				<th>创建日期</th>
				<th>联系作者</th>
			</tr>
			</thead>
			<tbody>
			<tr class="success">
				<td>${version.update_date?string('yyyy年M月d日')}</td>
				<td>${version.size/(1024*1024)}M</td>
				<td>${install_count}</td>
				<td>${version.version}</td>
				<td>${app.os_requirement}</td>
				<td>${app.created_date?string('yyyy年M月d日')}</td>
				<td>${member.contact}</td>
			</tr>
			</tbody>
		</table>
	</div>
	<div class="panel">
		<div class="panel-heading">评价</div>
		<div class="panel-body">
			<div>
                <span id="score" total="${app.rate}" count="${app.rate_count}">
				<#if app.rate_count == 0>
					还没有人评分哦-.-
				<#else>
				${app.rate/app.rate_count}
				</#if>
				</span>
				<span class="glyphicon glyphicon-user"></span> <span id="_count"
																	 count="${app.rate_count}">${app.rate_count}</span>
				<button class="btn btn-success pull-right" id="make_comment"><span
						class="glyphicon glyphicon-star"></span> 我要评价
				</button>
			</div>
		<#if app.rate_count gt 0>
			<div class="rating" score="${app.rate/app.rate_count}"></div>
		</#if>
		</div>
		<ul class="list-group" id="comments">
		<#list comments as c>
			<li class="list-group-item c" cid="${c.id}">
				<p class="user text-muted">${c.nick} ${c.rate_date?string('yyyy-MM-dd HH:mm:ss')}</p>

				<div class="progress progress-striped active">
					<div class="progress-bar progress-bar-success" role="progressbar"
						 aria-valuemin="0" aria-valuemax="100"
						 style="width: ${(c.rate/5)*100}%"></div>
				</div>
				<p class="comment">${c.comment}</p>
			</li>
		</#list>
			<li class="list-group-item">
				<button class="btn btn-success btn-block" appid="${app.id}" id="more_comments">
					查看更多
				</button>
			</li>
		</ul>
	</div>
</div>

<div class="hide template">
	<ul class="item">
		<li class="list-group-item c">
			<p class="user text-muted"></p>

			<div class="progress progress-striped active">
				<div class="progress-bar progress-bar-success" role="progressbar" aria-valuemin="0"
					 aria-valuemax="100"></div>
			</div>
			<p class="comment"></p>
		</li>
	</ul>
</div>

<#--成员登陆modal-->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
	 aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
				<h4 class="modal-title">同步你的论坛账号</h4>
			</div>
			<div class="modal-body">
				首先，您需要现在实验室里同步论坛的账号方可提交自己的应用，若您已同步，请直接登录即可，若没有，您先需要同步论坛账号一次，仅此一次。
				<div class="container" style="margin-top: 10px">
					<form class="form-horizontal" role="form" id="login_form">
						<div class="form-group">
							<label for="account" class="col-lg-2 control-label">账号</label>

							<div class="col-lg-10">
								<input type="text" class="form-control" name="account" id="account"
									   placeholder="登录账号" />
							</div>
						</div>
						<div class="form-group">
							<label for="password" class="col-lg-2 control-label">密码</label>

							<div class="col-lg-10">
								<input type="password" class="form-control" name="password"
									   id="password"
									   placeholder="密码" />
							</div>
						</div>
						<div class="form-group">
							<div class="col-lg-offset-2 col-lg-10">
								<button type="submit" class="btn btn-success" id="login">登录</button>
							</div>
						</div>
					</form>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default btnClose" data-dismiss="modal">关闭
				</button>
				<button type="button" class="btn btn-primary sync">同步我的论坛账号</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<#--评价modal-->
<div class="modal fade" id="comment_modal" tabindex="-1" role="dialog"
	 aria-labelledby="myModalLabel"
	 aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
				<h4 class="modal-title">给 ${app.name} 评分</h4>
			</div>
			<div class="modal-body">
				<p class="lead">您需要输入您在雨无声论坛的登陆账号和密码哦</p>

				<div class="container holder">
					<form class="form-horizontal" role="form">
						<div class="form-group">
							<label for="account" class="col-lg-2 control-label">账号</label>

							<div class="col-lg-10">
								<input type="text" class="form-control" name="account" id="account"
									   placeholder="登录账号" />
							</div>
						</div>
						<div class="form-group">
							<label for="password" class="col-lg-2 control-label">密码</label>

							<div class="col-lg-10">
								<input type="password" class="form-control" name="password"
									   id="password"
									   placeholder="密码" />
							</div>
						</div>
						<div class="form-group">
							<label for="rate" class="col-lg-2 control-label">评分</label>

							<div class="col-lg-10">
								<select multiple class="form-control" name="rate">
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4" selected="selected">4</option>
									<option value="5">5</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="comment" class="col-lg-2 control-label">评论内容</label>

							<div class="col-lg-10">
								<textarea class="form-control comment" name="comment"
										  rows="3"></textarea>
							</div>
						</div>
					</form>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default btnClose" data-dismiss="modal">取消
				</button>
				<button type="button" class="btn btn-success apply" appid="${app.id}">评论</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<#--查看作者信息modal -->
<div class="modal fade" id="member_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
	 aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
				<h4 class="modal-title title"></h4>
			</div>
			<div class="modal-body">
				<div class="container" style="margin-top: 10px">
					<div class="list-group">
						<a href="#" class="list-group-item">
							<h4 class="list-group-item-heading">联系方式</h4>

							<p class="list-group-item-text" id="_contact">${member.contact}</p>
						</a>
						<a href="#" class="list-group-item">
							<h4 class="list-group-item-heading">标签</h4>

							<p class="list-group-item-text" id="_label">${member.label}</p>
						</a>
						<a href="#" class="list-group-item">
							<h4 class="list-group-item-heading">个人简介</h4>

							<p class="list-group-item-text"
							   id="_description">${member.description}</p>
						</a>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default btnClose" data-dismiss="modal">关闭
				</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->


<@L.script bootstrap="3.0.0">
<script src="//cdn.staticfile.org/respond.js/1.2.0/respond.min.js"></script>
<script src="/resources/libs/raty/2.5.2/jquery.raty.min.js"></script>
<script type="text/javascript" src="/resources/js/common/datetime.js"></script>
<script type="text/javascript">
	$(function () {
		$('.rating').each(function () {
			$(this).raty({ readOnly: true, score: $(this).attr('score'), path: '/resources/libs/raty/2.5.2/img' })
		});
		$('#my').click(function (e) {
			e.preventDefault();
			if ($(this).attr('u')) {
				alert("亲爱的实验室朋友，你已经登陆啦^_^，请移步到实验室成员中心！");
				location.href = '/members/' + $(this).attr('u') + '/center';
				return;
			}
			$(':password').val('');
			$('#myModal').modal();
		})

		$('#login').click(function (e) {
			e.preventDefault();
			var account = $.trim($('#account').val());
			var password = $.trim($('#password').val());
			if (!account || !password) {
				alert("请填写表单再提交！");
				return;
			}
			$.ajax({
				url: '/members/login',
				data: {
					account: account,
					password: password,
					from: 0
				},
				dataType: 'JSON',
				success: function (data) {
					console.log(data);
					$('.modal').modal('hide');
					location.href = '/members/' + data.id + '/center';
				},
				error: function (jqxhr, status, error) {
					alert(error + ' -> ' + jqxhr.responseText);
				}
			})
		})
	})

	$('#sync').click(function (e) {
		e.preventDefault();
		var data = $('#sync_form').serialize();
		console.log(data)
		$.ajax({
			url: '/members/participate',
			type: 'POST',
			data: data,
			dataType: 'JSON',
			success: function () {
				alert("同步成功！");
				$('.modal').modal('hide');
			},
			error: function (jqxhr, status, error) {
				alert(error + ' -> ' + jqxhr.responseText);
			}
		})
		$(':password').val('');
	})

	$('.sync').click(function (e) {
		$('.modal').modal('hide');
		$('#participate').modal();
		return true;
	})

	<#--查看作者信息-->
	$('#author').click(function (e) {
		e.preventDefault();
		$('#member_modal').modal();
	})

	<#--请求评价app-->
	$('#make_comment').click(function (e) {
		e.preventDefault();
		$(':password').val('');
		$('#comment_modal').modal();
	});

	<#--提交评价-->
	$('#comment_modal button.apply').click(function (e) {
		e.preventDefault();
		var appid = $(this).attr('appid');
		var data = $('#comment_modal form').serialize();
		$.ajax({
			url: '/app_comments/post?appid=' + appid,
			type: 'post',
			data: data,
			dataType: 'json',
			success: function (data) {
				alert('发表成功！');
				var item$ = $('div.template .item').children().clone();
				item$.find('.user').text(data.nick + ' ' + datetime.format('yyyy-MM-dd HH:mm:ss', data.rate_date));
				item$.find('.progress-bar').css("width", (Math.round(data.rate / 5) * 100) + "%");
				var score$ = $('#score');
				var score = score$.attr('total') + data.rate;
				var count = score$.attr('count') + 1;
				score$.attr({
					total: score,
					count: count
				}).text((score / count).toFixed(2));
				var count$ = $('#_count');
				count$.attr('count', count);
				count$.text(count);
				item$.find('.comment').text(data.comment);
				item$.prependTo('#comments');
				$('.modal').modal('hide');

			},
			error: function (jqxhr, status, error) {
				alert(error + ' -> ' + jqxhr.responseText);
			}
		})
	});

	<#--查看更多评价-->
	$('#more_comments').click(function (e) {
		e.preventDefault();
		var appid = $(this).attr('appid');
		var count = 5;
		var offset = $('#comments li:last').prev('li').attr('cid');
		$.ajax({
			url: '/app_comments/',
			type: 'get',
			dataType: 'json',
			data: {
				appid: appid,
				count: count,
				offset: offset,
				append: true
			},
			success: function (data) {
				if (data.comments.length < count) {
					$('#more_comments').attr('disabled', 'disabled');
					$('#more_comments').text('没有更多啦!');
				}
				$.each(data.comments, function (i, c) {
					var item$ = $('div.template .item').children().clone();
					item$.find('.user').text(c.nick + ' ' + datetime.format('yyyy-MM-dd HH:mm:ss', c.rate_date));
					item$.find('.progress-bar').css("width", (Math.round(c.rate / 5) * 100) + "%");
					item$.find('.comment').text(c.comment);
					item$.find('.c').attr('cid', c.id);
					item$.insertBefore('#more_comments');
				})
			}
		});
	})

	// bootstrap3 bug fix
	$(".close, .btnClose").on("click", function (e) {
		$(".modal").modal("hide");
		$(':password').val('');
		e.stopPropagation();
	});
</script>
</@L.script>
</body>
</html>