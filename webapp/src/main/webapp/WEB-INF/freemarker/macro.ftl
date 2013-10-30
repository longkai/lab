<#-- html5标记 -->
<#macro html>
<!DOCTYPE html>
<html>
    <#nested />
</html>
</#macro>

<#-- html头部的定义，这里嵌套了内联得到css -->
<#macro head author="龙凯 longkai @爱因斯坦的狗" email="im.longkai@gmail.com" title="" keywords="" description="" bootstrap="2.3.1">
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>${title}</title>
    <meta name="keywords" content="${keywords} 雨无声实验室 广西大学 广西大学雨无声网站 ${author} ${email}" />
    <meta name="description" content="${description} 雨无声实验室 广西大学 广西大学雨无声网站 ${author} ${email}" />
    <meta name="author" content="${author}" />
    <meta name="email" content="${email}" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="/resources/libs/bootstrap/${bootstrap}/css/bootstrap.min.css" />
    <link rel="stylesheet" href="/resources/libs/bootstrap/${bootstrap}/css/bootstrap-responsive.min.css" />
    <!--[if lt IE 9]>
    <script src="//cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <![endif]-->
    <#nested />
</head>
</#macro>


<#-- html主体的定义(不推荐使用，直接使用body标记就好了) -->
<#macro body id="" class="">
<body id="${id}" class="${class}">
    <#nested />
</body>
</#macro>

<#-- JavaScript的编写和引入请放在此处 -->
<#macro script jquery="1.9.1" bootstrap="2.3.1">
<script src="//libs.baidu.com/jquery/${jquery}/jquery.min.js"></script>
<script src="//libs.baidu.com/bootstrap/${bootstrap}/js/bootstrap.min.js"></script>
<script type="text/javascript">
    !window.jQuery && document.write('<script src="/resources/libs/jquery/${jquery}/jquery.min.js" type="text/javascript"><\/script>');
    !window.jQuery && document.write('<script src="/resources/libs/bootstrap/${bootstrap}/js/bootstrap.min.js" type="text/javascript"><\/script>');
</script>
<#if bootstrap=="3.0.0">
<script src="/resources/libs/bootstrap/${bootstrap}/js/bootstrap.min.js" type="text/javascript"></script>
</#if>
    <#nested />
</#macro>

<#-- 额外导入的模版文件 -->
<#include "google_analytics.ftl" />
