<@L.html>
    <@L.head title="雨无声实验室">
    <style type="text/css">
        body {
            padding-bottom: 40px;
            color: #5a5a5a;
        }
        .navbar-wrapper {
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            z-index: 10;
            margin-top: 20px;
            margin-bottom: -90px;
        }
        .navbar .navbar-inner {
            border: 0;
            box-shadow: 0 2px 10px rgba(0, 0, 0, .25);
        }
        .navbar .btn-navbar {
            margin-top: 10px;
        }
        .navbar .brand {
            padding: 14px 20px 16px 20px;
            font-size: 16px;
            font-weight: bold;
            color: #fff;
            text-shadow: 0 -1px 0 rgba(0, 0, 0, .5);
        }
        .navbar .nav > li > a {
            padding: 15px 20px;
        }
        .btn-group .caret {
            padding: 4px 0;
        }
        .btn-group {
            margin-top: 10px !important;
        }
            /* show time */
        .carousel-control {
            background-color: transparent;
            height: 60px;
            font-size: 120px;
            top: 44%;
            left: 10px;
            right: 10px;
            text-shadow: 0 1px 1px rgba(0, 0, 0, .4);
            border: 0;
            z-index: 10;
        }
        .carousel .item {
            height: 300px;
        }
        .carousel img {
            position: absolute;
            top: 0;
            left: 0;
            min-width: 100%;
            height: 300px;
        }
        .carousel .container {
            position: relative;
            z-index: 1;
        }
        .carousel-caption {
            background-color: transparent;
            max-width: 600px;
            padding: 0 20px;
            position: static;
            margin-top: 100px;
        }
        .carousel-caption h1, .carousel-caption .lead {
            margin-bottom: 10px;
            line-height: 1.25;
            color: #333;
            text-shadow: 0 1px 1px rgba(0, 0, 0, .4);
        }
            /* featrue */
        .feature {
            padding-bottom: 20px;
            overflow: hidden;
        }
        .feature-heading {
            padding: 40px 0 0 0;
        }
        @media (max-width: 979px) {
            .btn-group {
                padding-bottom: 10px !important;
            }
            .carousel .item {
                height: 300px;
            }
            .carousel img {
                width: auto;
                height: 300px;
            }
            .feature img {
                display: block;
                float: none;
                max-width: 40%;
                margin:  0 auto 20px;
            }
        }
        @media (max-width: 767px) {
            .navbar-inner {
                margin: -20px 0;
            }
            .carousel {
                margin-left: -20px;
                margin-right: -20px;
            }
            .carousel img {
                height: 200px;
            }
            .carousel .item {
                height: 200px;
            }
            .carousel-caption {
                width: 60%;
                padding: 0 70px;
                margin-top: 60px;
            }
            .carousel-caption h1 {
                font-size: 30px;
            }
            .carousel-caption .lead, .carousel-caption .btn {
                font-size: 18px;
            }
            .navbar button.btn-navbar {
                margin-right: 20px;
            }
            .feature-heading {
                font-size: 25px;
            }
            .featrue .lead {
                font-size: 16px;
                line-height: 1.5;
            }
        }
        @media (max-width: 480px) {
            #features {
                padding-top: 40px;
            }
            #showtime {
                display: none;
            }/*
            .carousel-caption h1 {
                font-size: 15px;
            }
            .carousel-caption .lead, .carousel-caption .btn {
                font-size: 12px;
            }*/
            .feature-heading {
                font-size: 20px;
            }
            .featrue .lead {
                font-size: 12px;
            }
        }
    </style>
    </@L.head>
<div class="navbar-wrapper">
    <div class="container">
        <div class="navbar navbar-inverse">
            <div class="navbar-inner">
                <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a href="#" class="brand">雨无声实验室</a>
                <div class="nav-collapse collapse">
                    <ul class="nav">
                        <li><a href="#">关于</a></li>
                        <li><a href="#">服务条款</a></li>
                        <li><a href="//github.com/longkai/lab.newgxu.cn/commits/master" target="_blank">更新说明</a></li>
                        <li><a href="//longkai.github.io/lab.newgxu.cn/" target="_blank">实验室开源项目</a></li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">实验室产品 <b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li><a href="//share.newgxu.cn">分享平台</a></li>
                                <li><a href="/notice">校园信息</a></li>
                                <li><a href="/yundianbo">云点播</a></li>
                                <li><a href="/app">应用市场</a></li>
                                <li><a href="/apps/2">印象西大</a></li>
                            </ul>
                        </li>
                    </ul>
                    <div class="btn-group pull-right">
                        <a class="btn btn-success" target="_blank" href="//www.newgxu.cn"><i class="icon-home icon-white"></i> 雨无声</a>
                        <a class="btn btn-info dropdown-toggle" data-toggle="dropdown" href="#"><span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="//www.newgxu.cn"><i class="icon-home"></i> 首页</a></li>
                            <li class="divider"></li>
                            <li><a href="//bbs.newgxu.cn"><i class="icon-hand-right"></i> 雨无声论坛</a></li>
                            <li><a href="//www.newgxu.cn/7.0/html/zxkd/"><i class="icon-hand-right"></i> 雨无声新闻网</a></li>
                            <li><a href="//www.newgxu.cn/7.0/html/sjw/"><i class="icon-hand-right"></i> 雨无声视界网</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="showtime" class="carousel slide">
    <div class="carousel-inner">
        <div class="item active">
            <img src="/resources/images/lab/index/lab.jpg" alt="雨无声实验室" />
            <div class="container">
                <div class="carousel-caption">
                    <h1>雨无声实验室</h1>
                    <p class="lead">创意与创造的地方</p>
                    <a href="#" class="btn btn-large btn-primary">详细介绍</a>
                </div>
            </div>
        </div>
        <div class="item">
            <img src="/resources/images/lab/index/impression_gxu.jpg" alt="印象西大" />
            <div class="container">
<#--
                <div class="carousel-caption">
                    <h1>印象西大</h1>
                    <p class="lead">欢迎2013届学弟学妹！</p>
                    <a href="/resources/apps/newgxu.apk" class="btn btn-large btn-primary">下载Android客户端:-)</a>
                </div>
-->
            </div>
        </div>
        <div class="item">
            <img src="/resources/images/lab/index/share.jpg" alt="" />
            <div class="container">
                <div class="carousel-caption">
                    <h1>分享平台</h1>
                    <p class="lead">一起打造我们的西大文库</p>
                    <a class="btn btn-large btn-primary" href="#app_share">简介</a>
                    <a class="btn btn-large btn-success" href="//share.newgxu.cn">传送门</a>
                </div>
            </div>
        </div>
        <div class="item">
            <img src="/resources/images/lab/index/info.jpg" alt="" />
            <div class="container">
                <div class="carousel-caption">
                    <h1>校园信息</h1>
                    <p class="lead">轻松，即时获取校园一手信息</p>
                    <a class="btn btn-large btn-primary" href="#current_version">简介</a>
                    <a class="btn btn-large btn-success" href="/notice/">传送门</a>
                    <a class="btn btn-large btn-success" href="/apps/1">Android客户端</a>
                </div>
            </div>
        </div>
    </div>
    <a href="#showtime" class="left carousel-control" data-slide="prev">&lsaquo;</a>
    <a href="#showtime" class="right carousel-control" data-slide="next">&rsaquo;</a>
</div>
<div class="container" id="features">
    <div class="feature">
        <img class="feature-image pull-right" src="/resources/images/lab/index/lab_feature.png">
        <h2 class="feature-heading">雨无声实验室 <span class="muted">创意与创造的地方</span></h2>
        <p class="lead about">雨无声实验室是一个面向于西大师生的服务型平台。提供一系列实际生活，学习等方面的便捷服务。另一方面，实验室也乐意提供给那些渴望展现自己的能力，分享自己的创意的同学。在实验室，不管是你是雨无声人，还是西大人，都可以参与进来.不要担心自己是电脑盲，计算机技术只是实现创意的一种手段，我们需要的是创意。把你的想法，能力共享出来，雨无声人，西大人，会技术的，不会技术的，我们一起来孵化。</p>
    </div>
    <div class="feature" id="impression_gxu">
        <img class="feature-image pull-left" src="/resources/images/lab/index/impression_gxu_feature.jpg" title="下载印象西大:-)">
        <h2 class="feature-heading" title="下载印象西大:-)">印象西大 <span class="muted">欢迎2013届学弟学妹:-)</span></h2>
        <p class="lead about">欢迎你们走进大学！祝你们找到自己的所爱，释放自己的天性！  这是一款校园杂志app，这是一个属于我们的西大印象，记录你我在大学生活中的文字与图片，汇聚成我们的印象西大！</p>
    </div>
    <div class="feature" id="appstore">
        <img class="feature-image pull-right" src="/resources/images/lab/index/appstore_feature.jpg" title="去应用市场看看">
        <h2 class="feature-heading" title="去应用市场看看">应用市场 <span class="muted">创造&分享，属于我们的应用</span></h2>
        <p class="lead about">我们的应用市场，欢迎广大编程爱好者在这里展现自己的应用，也欢迎同学们的支持与鼓励！我们一起创造，分享，进步！</p>
    </div>
    <div class="feature" id="app_yundianbo">
        <img class="feature-image pull-left" src="/resources/entertainment/yundianbo/yundianbo.png">
        <h2 class="feature-heading" title="去点播看看:)">雨无声云点播 <span class="muted">在线观看，无需下载</span></h2>
        <p class="lead">你是否还在为下载视频观看而烦恼？又没有钱开通迅雷云点播，浪费很多时间等待下载，雨无声云点播，支持HTTP、FTP、ed2k（电骡）、magnet（磁力）、thunder（迅雷）链接以及torrent种子，即点即看，极速缓冲，在线高清，智能字幕。快点来体验VIP级的流畅播放感受，零等待，随意拖放!</p>
    </div>
    <div class="feature" id="current_version">
        <img class="feature-image pull-right" src="/resources/images/lab/index/info_feature.png" title="去瞅瞅有什么信息~">
        <h2 class="feature-heading" title="去瞅瞅有什么信息~">校园信息<span class="muted"> 轻松获取校园一手信息</span></h2>
        <p class="lead">这是一个信息的集中站，西大任何的院（校）机构，学生社团组织只要经过雨无声的认证后，都可以在这上面发布信息，这些信息会出现在雨无声网站全平台的校园公告上，所有人都可见。并且，接下来推出的Android客户端更是能够让大家轻松，即时，获取到校园一手信息:)</p>
    </div>
    <div class="feature" id="app_share">
        <img class="feature-image pull-left" src="/resources/images/lab/index/share_feature.png" title="到分享平台淘淘:)">
        <h2 class="feature-heading" title="到分享平台淘淘:)">雨文分享平台 <span class="muted">西大文库，你值得拥有！</span></h2>
        <p class="lead">分享平台个由西大人共建，共享的资料分享文库。同学们可以把自己日常生活，学习，与考试中相关的资料上传在这里与大家分享，共同进步，共同打造我们的西大文库。此外，这里还提供了一些小的东西，比如投票系统，答题系统等服务。任何人或者集体只要向雨无声申请并且提供相关的资料即可使用~</p>
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
    <script>
        !function ($) {
            $(function(){
                $('#showtime').carousel();
                $('#current_version h2').css('cursor', 'pointer').click(function(e) {
                    e.preventDefault();
                    location.href = '/info/';
                })
                $('#current_version img').css('cursor', 'pointer').click(function(e) {
                    e.preventDefault();
                    location.href = '/info/';
                })
                $('#app_share h2').css('cursor', 'pointer').click(function(e) {
                    e.preventDefault();
                    location.href = 'http://share.newgxu.cn';
                })
                $('#app_share img').css('cursor', 'pointer').click(function(e) {
                    e.preventDefault();
                    location.href = 'http://share.newgxu.cn';
                })
                $('#app_yundianbo h2').css('cursor', 'pointer').click(function(e) {
                    e.preventDefault();
                    location.href = '/yundianbo';
                })
                $('#app_yundianbo img').css('cursor', 'pointer').click(function(e) {
                    e.preventDefault();
                    location.href = '/yundianbo';
                })
                $('#appstore img, #appstore h2').css('cursor', 'pointer').click(function(e) {
                    e.preventDefault();
                    location.href = '/app';
                })
                $('#impression_gxu img, #impression_gxu h2').css('cursor', 'pointer').click(function(e) {
                    e.preventDefault();
                    location.href = '/apps/2';
                })
            })
        }(window.jQuery)
    </script>
        <@L.ga_lab />
    </@L.script>
</@L.html>
