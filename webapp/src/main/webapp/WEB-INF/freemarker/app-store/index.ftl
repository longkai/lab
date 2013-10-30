<@L.html>
<@L.head bootstrap="3.0.0" title="应用市场 - 雨无声实验室">
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

        .navbar-nav>.active>a {
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

        .thumbnail {
            margin-bottom: 10px;
        }

        img.pull-right {
            margin-top: -10px;
        }

    </style>
</@L.head>
<body>


<nav class="navbar navbar-fixed-top" role="navigation">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                <span class="sr-only">导航</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">应用市场beta</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="navbar-collapse navbar-ex1-collapse">
            <#--<ul class="nav navbar-nav">
                <li><a href="/app">首页</a></li>
                <li><a href="/">雨无声实验室</a></li>
            </ul>-->
            <button type="button" class="btn btn-default navbar-btn" u="${Session.session_member.id}" id="my">提交我的作品</button>
        <#if Session.session_member??>
            <button type="button" uid="${Session.session_member.id}" class="btn btn-primary navbar-btn pull-right"
                    id="member">${Session.session_member.nick}</button>
        </#if>
        </div>
        <!-- /.navbar-collapse -->
    </div>
</nav>

<div class="container">
    <div class="page-header">
        <h1>欢迎来到应用市场！
            <small>在这里下载和提交我们西大人的应用！</small>
            <a class="btn btn-primary btn-lg">了解更多</a></h1>
    </div>
    <div class="row">
    <#list apps as a>
        <div class="col-lg-3">
            <div class="thumbnail">
                <img src="${a.icon}" alt="${a.name}">

                <div class="caption">
                    <h3>${a.name}</h3>
                    <h4 class="ratings">
                        <#if a.rate_count == 0>
                            还没有人评分哦-.-
                            <#else>
                            <div class="rating" score="${a.rate / a.rate_count}"></div>
                        </#if>
                    </h4>
                    <a href="/apps/${a.id}" class="btn btn-primary view_app">查看</a> <a href="/apps/${a.id}/download" class="btn btn-default">下载</a>
                    <#switch a.platform>
                        <#case 'ANDROID'>
                            <img src="/r/app-store/android.png" alt="android" class="pull-right" />
                            <#break />
                        <#case 'APPLE'>
                            <img src="/r/app-store/apple.png" alt="apple" class="pull-right" />
                            <#break />
                        <#case 'WINDOWS'>
                            <img src="/r/app-store/windows.png" alt="apple" class="pull-right" />
                            <#break />
                    </#switch>
                    </p>
                </div>
            </div>
        </div>
    </#list>
    </div>
    <button type="button" class="btn btn-success btn-lg btn-block">查看更多！</button>
</div>

<#--成员登陆modal-->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
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
                                <input type="password" class="form-control" name="password" id="password"
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
                <button type="button" class="btn btn-default btnClose" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary sync">同步我的论坛账号</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<div class="modal fade" id="member_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title title"></h4>
            </div>
            <div class="modal-body">
                <div class="container" style="margin-top: 10px">
                    <div class="list-group">
                        <a href="#" class="list-group-item">
                            <h4 class="list-group-item-heading">联系方式</h4>

                            <p class="list-group-item-text" id="_contact"></p>
                        </a>
                        <a href="#" class="list-group-item">
                            <h4 class="list-group-item-heading">标签</h4>

                            <p class="list-group-item-text" id="_label"></p>
                        </a>
                        <a href="#" class="list-group-item">
                            <h4 class="list-group-item-heading">个人简介</h4>

                            <p class="list-group-item-text" id="_description"></p>
                        </a>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default btnClose" data-dismiss="modal">关闭</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<div class="modal fade" id="participate" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">同步你的论坛账号</h4>
            </div>
            <div class="modal-body">
                <div class="container" style="margin-top: 10px">
                    <form class="form-horizontal" role="form" id="sync_form">
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
                                <input type="password" class="form-control" name="password" id="password"
                                       placeholder="密码" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="contact" class="col-lg-2 control-label">联系方式</label>

                            <div class="col-lg-10">
                                <input type="text" class="form-control" name="contact" id="contact"
                                       placeholder="qq，电话，邮件等" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="label" class="col-lg-2 control-label">个人标签</label>

                            <div class="col-lg-10">
                                <input type="个人标签" class="form-control" name="label" id="label" placeholder="几个关键词！" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="description" class="col-lg-2 control-label">个人简介</label>

                            <div class="col-lg-10">
                                <textarea class="form-control" name="description" id="description"
                                          placeholder="简单介绍一下自己吧:-)" /></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-lg-offset-2 col-lg-10">
                                <button type="submit" class="btn btn-success" id="sync">同步</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default btnClose" data-dismiss="modal">关闭</button>
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
                    if (data.id) {
                        $('.modal').modal('hide');
                        location.href = '/members/' + data.id + '/center';
                    } else {
                        if (data.msg) {
                            alert(data.msg);
                        } else {
                            alert('登录失败，请稍后再试！');
                        }
                    }
                }
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
                success: function (data) {
                    if (data.id) {
                        alert("同步成功！");
                        $('.modal').modal('hide');
                    } else {
                        if (data.msg) {
                            alert(data.msg);
                        } else {
                            alert('同步失败，请稍后再试！');
                        }
                    }
                }
            })
            $(':password').val('');
        })

        $('.sync').click(function (e) {
            $('.modal').modal('hide');
            $('#participate').modal();
            return true;
        })

        $('#member').click(function (e) {
            e.preventDefault();
            $.ajax({
                url: '/members/' + $(this).attr('uid'),
                dataType: 'JSON',
                type: 'GET',
                success: function (data) {
                    var m = data.member;
                    $('.title').text(m.nick);
                    $('#_description').text(m.description);
                    $('#_label').text(m.label);
                    $('#_contact').text(m.contact);
                    $('#member_modal').modal();
                }
            });
        })

    })
    // bootstrap3 bug fix
//    $(".close, .btnClose").on("click", function (e) {
//        $(".modal").modal("hide");
//        $(':password').val('');
//        e.stopPropagation();
//    });
</script>
</@L.script>
</body>
</@L.html>