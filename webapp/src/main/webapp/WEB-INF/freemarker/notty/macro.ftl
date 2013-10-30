<#-- 顶部的导航栏 -->
<#macro header>
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
					<li class="active _index"><a href="/notice/">首页</a></li>
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
				</ul>
<#if Session.session_auth_user??>
				<div class="btn-group pull-right" id="user_service" uid="${Session.session_auth_user.id}">
					<a class="btn btn-primary" href="#"><i class="icon-user icon-white"></i> ${Session.session_auth_user.authorizedName}</a>
					<a class="btn btn-primary dropdown-toggle" data-toggle="dropdown" href="#"><span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="/resources/notty/html/publication.html"><i class="icon-pencil"></i> 发表信息</a></li>
						<li><a href="/auth_users/${Session.session_auth_user.id}/modify"><i class="icon-edit"></i> 修改个人信息</a></li>
	<#if Session.session_auth_user.isAdmin>
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
					<input class="span2" type="password" name="pwd" placeholder="密码">
					<a class="btn" id="login_btn">登陆</a>
				</form>
</#if>
			</div>
		</div>
	</div>
</div>
</#macro>

<#-- 查看文章 -->
<#macro view_notice>
<div class="jumbotron">
	<h1>${notice.title}</h1>
	<p class="pull-right">
		<span id="author"><code><a href="/auth_users/${notice.author.id}" class="view_profile">${notice.author.authorizedName}</a></code></span> <span class="time"><code>[${notice.addDate?string('yyyy-MM-dd HH:mm')}]</code></span> <span><code class="text-info">${notice.clickTimes}点击</code></span>
<#if Session.session_auth_user??>
	<#if Session.session_auth_user.id == notice.author.id>
		<code><a href="/notices/${notice.id}/modify" class="text-warning">修改我发表的信息</a></code>
	</#if>
</#if>
	</p>
	<p id="content">
        <#if (notice.id > 31)>
            ${notice.content}
        </#if>
    </p>
<#if notice.docUrl??>
	<hr />
	<p id="file">
		<h4>附件下载</h4>
		<a href="${notice.docUrl}">${notice.docName?default('下载')}</a>
	</p>
</#if>
	<p class="pull-right">
		<code>
			<#if notice.lastModifiedDate??>
				最后修改于${notice.lastModifiedDate?string('yyyy-MM-dd HH:mm')}
			</#if>
		</code>
	</p>
</div>
</#macro>
