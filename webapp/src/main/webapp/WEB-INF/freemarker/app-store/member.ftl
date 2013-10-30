<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <title>Bootstrap 101 Template</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0-wip/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <style type="text/css">
        body {
            padding-top: 70px;
        }

        .navbar-brand {
            color: #ffffff;
            padding-top: 20px;
        }

        .navbar-collapse {
            /*max-height: 340px;*/
            padding: 5px 15px;
            overflow-x: visible;
            overflow-y: auto;
            border-top: 1px solid #e6e6e6;
            box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.1);
        }

    </style>
</head>
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
            <a class="navbar-brand" href="#">应用市场</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="navbar-collapse navbar-ex1-collapse">
            <ul class="nav navbar-nav">
                <li><a href="#">首页</a></li>
                <li><a href="#">雨无声实验室</a></li>
            </ul>
            <button type="button" class="btn btn-default navbar-btn" u="${Session.member.id}" id="my">提交我的作品</button>
        <#if Session.member??>
            <button type="button" uid="${Session.member.id}" class="btn btn-primary navbar-btn pull-right" id="member">${Session.member.nick}</button>
        </#if>
        </div>
        <!-- /.navbar-collapse -->
    </div>
</nav>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="//code.jquery.com/jquery.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0-wip/js/bootstrap.min.js"></script>

<!-- Enable responsive features in IE8 with Respond.js (https://github.com/scottjehl/Respond) -->
<script src="//cdn.staticfile.org/respond.js/1.2.0/respond.min.js"></script>
</body>
</html>