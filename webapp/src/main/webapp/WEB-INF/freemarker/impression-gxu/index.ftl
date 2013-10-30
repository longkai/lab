<#--<#macro categories cs>-->
<#--<#switch cs as c>-->
<#--<#case 'LITERATURE'>-->
<#--视界-->
<#--<#break />-->
<#--<#case 'NEWS'>-->
<#--资讯-->
<#--<#break />-->
<#--<#case 'VOICE'>-->
<#--声音-->
<#--<#break />-->
<#--</#switch>-->
<#--</#macro>-->
<#macro category c>
    <#if c == 'LITERATURE'>
    视界
    <#elseif c == 'NEWS'>
    资讯
    <#elseif c == 'VOICE'>
    声音
    </#if>
</#macro>
<#macro layout l>
    <#if l == 'TEXT'>
    文字
    <#elseif l == 'IMAGE_WITH_TEXT'>
    图文
    <#elseif l == 'LIST_IMAGE_WITH_TEXT'>
    图文列表
    <#elseif l == 'GALLERY'>
    相册
    <#elseif l == 'PAGER_TEXT'>
    分页文字
    <#elseif l == 'VIDEO'>
    视频
    <#elseif l == 'AUDIO'>
    音频
    </#if>
</#macro>
<@L.html>
    <@L.head>
    <link href="/resources/libs/fineuploader/3.7.1/fineuploader-3.7.1.min.css" rel="stylesheet" />
    <style type="text/css">
        body {
            padding-top: 60px;
            padding-bottom: 40px;
        }

        .span6 {
            margin: 0 0 10px 0;
        }

        .qq-upload-button {
            background-color: #62c462;
        }

        code {
            cursor: pointer;
        }
    </style>
    </@L.head>
<body>
<div class="navbar navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container">
            <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="brand" href="#">印象西大</a>

            <div class="nav-collapse collapse">
                <ul class="nav">
                    <li class="active"><a href="#">首页</a></li>
                    <li><a href="#">帮助</a></li>
                    <li id="logout"><a href="#">退出</a></li>
                    <li class="dropdown pull-right">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            雨无声实验室 <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="/">首页</a></li>
                            <li><a href="/app">应用市场</a></li>
                            <li><a href="/notice">校园信息</a></li>
                            <li><a href="/yundianbo">云点播</a></li>
                            <li><a href="/about.html">关于实验室</a></li>
                        </ul>
                    </li>
                <#--<li>
                    <form class="navbar-form">
                        <input class="span2" type="text" id="search_txt" placeholder="搜索试试~" />
                        <a class="btn btn-success" id="btn_search">搜索</a>
                    </form>
                </li>-->
                </ul>
            </div>
        </div>
    </div>
</div>

<div class="container">
    <div class="tabbable tabs-left">
        <ul class="nav nav-tabs">
            <li class="active"><a href="#articles_tab" data-toggle="tab">文章列表</a></li>
            <li class=""><a href="#lB" data-toggle="tab">推送文章</a></li>
        <#--<li class=""><a href="#lC" data-toggle="tab">修改文章</a></li>-->
        </ul>
        <div class="tab-content">
            <div class="tab-pane active" id="articles_tab">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>标题</th>
                        <th>作者</th>
                        <th>栏目</th>
                        <th>排版</th>
                        <th>发表时间</th>
                        <th>是否推荐</th>
                        <th>是否置顶</th>
                        <th>是否屏蔽</th>
                    </tr>
                    </thead>
                    <tbody id="articles">
                        <#list articles as a>
                        <tr index="${a.id}">
                            <td class="index">${a_index+1}</td>
                            <td class="title" aid="${a.id}"><a href="#">${a.title}</a></td>
                            <td class="author">${a.author_name?default('佚名')}</td>
                            <td class="category"><@category c=a.category /></td>
                            <td class="layout"><@layout l=a.layout /></td>
                            <td class="added_date">${a.added_date?string('yyyy-MM-dd HH:mm:ss')}</td>
                            <td class="recommended">${a.recommended?string('<code class="text-warning">取消推荐</code>',
                            '<code class="text-success">设为推荐</code>')}</td>
                            <td class="top">${a.top?string('<code class="text-warning">取消置顶</code>',
                            '<code class="text-success">设为置顶</code>')}</td>
                            <td class="blocked">${a.blocked?string('<code class="text-success">取消屏蔽:-)</code>',
                            '<code class="text-warning">屏蔽:-(</code>')}</td>
                        </tr>
                        </#list>
                    </tbody>
                </table>
                <table class="hide" id="article_template">
                    <tbody class="row">
                    <tr>
                        <td class="index"></td>
                        <td class="title"></td>
                        <td class="author"></td>
                        <td class="category"></td>
                        <td class="layout"></td>
                        <td class="added_date"></td>
                        <td class="recommended"></td>
                        <td class="top"></td>
                        <td class="blocked"></td>
                    </tr>
                    </tbody>
                </table>
                <button class="btn btn-info btn-block" id="more_articles">加载更多</button>
            </div>
            <div class="tab-pane" id="lB">
                <form action="#" method="post" class="form-horizontal" id="push_article_form">
                    <div class="control-group">
                        <label class="control-label" for="title">标题</label>

                        <div class="controls">
                            <input type="text" class="input-xlarge" name="title" placeholder="文章标题" />
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="controls">
                            <label class="checkbox" for="recommended">
                                <input type="checkbox" name="recommended" /> 是否推荐？
                            </label>
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="controls">
                            <label class="checkbox" for="top">
                                <input type="checkbox" name="top" /> 是否置顶？
                            </label>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="author_name">作者</label>

                        <div class="controls">
                            <input type="text" class="input-xlarge" name="author_name" placeholder="作者" />
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="author_contact">作者联系方式</label>

                        <div class="controls">
                            <input type="text" class="input-xlarge" name="author_contact" placeholder="作者联系方式（可选）" />
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="author_about">作者信息</label>

                        <div class="controls">
                            <textarea rows="5" class="author_about input-xlarge" name="author_about"
                                      placeholder="作者信息（255字以内）"></textarea>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="category">文章栏目</label>

                        <div class="controls">
                            <select multiple="multiple" name="category">
                            <#--<#list categories as category>-->
                            <#--<#if category_index==0>-->
                            <#--<option value="${category}" selected="selected"><@category c=category /></option>-->
                            <#--<#else>-->
                            <#--<option value="${category}"><@category c=category /></option>-->
                            <#--</#if>-->
                            <#--</#list>-->
                                <option value="LITERATURE" selected="selected">视界</option>
                                <option value="NEWS">资讯</option>
                                <option value="VOICE">声音</option>
                            </select>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="layout">排版</label>

                        <div class="controls">
                            <select multiple="multiple" name="layout">
                                <option value="TEXT" selected="selected">文本</option>
                                <option value="IMAGE_WITH_TEXT">图文</option>
                                <option value="LIST_IMAGE_WITH_TEXT">图文列表</option>
                                <option value="GALLERY">相册</option>
                                <option value="PAGER_TEXT">分页文字</option>
                                <option value="VIDEO">视频</option>
                                <option value="AUDIO">音频</option>
                            </select>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="content">文字</label>

                        <div class="controls">
                            <textarea rows="20" class="content input-xxlarge" name="content"
                                      placeholder="文字:-)"></textarea>
                        </div>
                    </div>
                    <div class="control-group">
                        <h2 class="text-info">是否需要上传文件？</h2>
                        <button id="required_file_upload" class="btn btn-success">需要</button>
                        <button id="no_file_upload" class="btn btn-warning">不需要</button>
                    </div>
                    <div class="control-group hide" id="file_upload_section">
                        <p class="text-info lead">上传文件（如果有）只能是一张图片(png, jpg)或者ZIP格式的压缩包</p>
                        <hr />
                        <div class="span6">
                            <div id="file_upload"></div>
                            <noscript>
                                <p>Please enable JavaScript to use Fine Uploader.</p>
                                <!-- or put a simple form for upload here -->
                            </noscript>
                        </div>
                        <div class="span6">
                            <button id="trigger_upload" class="btn btn-info span6">
                                <i class="icon-upload icon-white"></i>确定，完成文章推送!
                            </button>

                            <div id="upload_alert"></div>
                        </div>
                    </div>
                    <div class="control-group" id="no_file_upload_section">
                        <button class="btn btn-success hide" id="submit">提交</button>
                    </div>
                </form>

            </div>
        <#--<div class="tab-pane" id="lC">
            <p class="hint">请在文章列表中选择一篇文章来修改:-)</p>
        </div>-->
        </div>
    </div>
</div>

<div id="update_article_modal" class="modal hide fade" tabindex="-1" role="dialog"
     aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3 class="modal_title"></h3>
    </div>
    <div class="modal-body">
        <form action="#" method="post" class="form-horizontal" id="push_article_form">
            <div class="control-group">
                <label class="control-label" for="title">标题</label>

                <div class="controls">
                    <input type="text" class="input-xlarge title" name="title" placeholder="文章标题" />
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="author_name">作者</label>

                <div class="controls">
                    <input type="text" class="input-xlarge author_name" name="author_name" placeholder="作者" />
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="author_contact">作者联系方式</label>

                <div class="controls">
                    <input type="text" class="input-xlarge author_contact" name="author_contact"
                           placeholder="作者联系方式（可选）" />
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="author_about">作者信息</label>

                <div class="controls">
                    <textarea rows="5" class="author_about input-xlarge" name="author_about"
                              placeholder="作者信息（255字以内）"></textarea>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="category">文章栏目</label>

                <div class="controls">
                    <select multiple="multiple" name="category">
                        <option value="LITERATURE" selected="selected">视界</option>
                        <option value="NEWS">资讯</option>
                        <option value="VOICE">声音</option>
                    </select>
                </div>
            </div>
        <#--<div class="control-group">
            <label class="control-label" for="layout">排版</label>

            <div class="controls">
                <select multiple="multiple" name="layout">
                    <option value="TEXT" selected="selected">文本</option>
                    <option value="IMAGE_WITH_TEXT">图文</option>
                    <option value="LIST_IMAGE_WITH_TEXT">图文列表</option>
                    <option value="GALLERY">相册</option>
                    <option value="PAGER_TEXT">分页文字</option>
                    <option value="VIDEO">视频</option>
                    <option value="AUDIO">音频</option>
                </select>
            </div>
        </div>-->
            <div class="control-group">
                <label class="control-label" for="content">文字</label>

                <div class="controls">
                    <textarea rows="20" class="content input-xlarge" name="content"
                              placeholder="文字:-)"></textarea>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
        <button class="btn btn-primary" id="apply">修改</button>
    </div>
</div>

    <@L.script>
    <script src="/resources/libs/fineuploader/3.7.1/jquery.fineuploader-3.7.1.min.js"></script>
    <script src="/resources/js/common/datetime.js"></script>
    <script type="text/javascript">
    var success = false;
    var targetArticleId = 0;

    function category(c) {
        switch (c) {
            case 'LITERATURE':
                return '文学';
            case 'NEWS':
                return '资讯';
            case 'VOICE':
                return '声音';
            default :
                return '未知。。。';
        }
    }

    function layout(l) {
        switch (l) {
            case 'TEXT':
                return '文本';
            case 'IMAGE_WITH_TEXT':
                return '图文';
            case 'LIST_IMAGE_WITH_TEXT':
                return '图文列表';
            case 'GALLERY':
                return '相册';
            case 'PAGER_TEXT':
                return '分页文字';
            case 'VIDEO':
                return '视频';
            case 'AUDIO':
                return '音频';
        }
    }

    $(function () {
        $('#logout').click(function (e) {
            e.preventDefault();
            $.ajax({
                url: '/impression/logout',
                type: 'GET',
                dataType: 'json',
                success: function () {
                    location.href = '/impression/entrance';
                }
            })
        })

        $('#required_file_upload').click(function (e) {
            e.preventDefault();
            $('#submit').hide();
            $('#file_upload_section').show();
        })

        $('#no_file_upload').click(function (e) {
            e.preventDefault();
            $('#submit').show();
            $('#file_upload_section').hide();
        })

    <#--no file upload-->
        $('#submit').click(function (e) {
            e.preventDefault();
            if (success) {
                var ok = confirm('您刚才已经成功推送了一篇文章，你确认需要再次推送吗？');
                if (!ok) {
                    return;
                }
            }
            var data = $('#push_article_form').serialize();
            $.ajax({
                url: '/articles/create',
                type: 'POST',
                dataType: 'json',
                data: data,
                success: function (data) {
                    if (data.id) {
                        success = true;
                        alert('推送文章成功！');
                        // todo append to the articles' list
                    } else {
                        alert('推送文章失败！ ' + data.msg);
                    }
                }
            })
        })

    <#--file upload-->
        $('#file_upload').fineUploader({
            autoUpload: false,
            request: {
                inputName: 'file',
                endpoint: '/articles/create/file'
            },
            validation: {
                itemLimit: 1,
                allowedExtensions: ['png', 'jpg', 'zip'],
                sizeLimit: 10485760 // 10 mb = 5 * 1024 * 1024 bytes
            },
            editFilename: {
                enabled: true
            },
            text: {
                uploadButton: '<i class="icon-upload icon-white"></i> 请选择文件或者将其拖放到此处'
            },
            template: '<div class="qq-uploader span6">' +
                    '<pre class="qq-upload-drop-area span6"><span>{dragZoneText}</span></pre>' +
                    '<div class="qq-upload-button btn btn-success" style="width: auto;">{uploadButtonText}</div>' +
                    '<span class="qq-drop-processing"><span>{dropProcessingText}</span><span class="qq-drop-processing-spinner"></span></span>' +
                    '<ul class="qq-upload-list" style="margin-top: 10px; text-align: center;"></ul>' +
                    '</div>',
            classes: {
                success: 'alert alert-success',
                fail: 'alert alert-error'
            },
            showMessage: function (message) {
                // Using Bootstrap's classes
                $('#upload_alert').append('<div class="alert alert-error lead">' + message + '</div>');
            }

        }).on({
                    'submit': function (event, id, fileName) {
                        $(this).fineUploader('setParams', {
                            title: $('#push_article_form input[name="title"]').val(),
                            content: $('#push_article_form textarea[name="content"]').val(),
                            recommended: $('#push_article_form input[name="recommended"]').val(),
                            top: $('#push_article_form input[name="top"]').val(),
                            category: $('#push_article_form select[name="category"]').val(),
                            layout: $('#push_article_form select[name="layout"]').val(),
                            author_name: $('#push_article_form input[name="author_name"]').val(),
                            author_contact: $('#push_article_form input[name="author_contact"]').val(),
                            author_about: $('#push_article_form textarea[name="author_about"]').val()
                        });
                    },
                    'complete': function (event, id, fileName, responseJSON) {
                        if (responseJSON.success) {
                            success = true;
                            alert('推送成功！');
                            // todo clear the file history
                        } else {
                            alert('推送失败！' + responseJSON.msg);
                        }
                    },
                    'error': function (event, id, fileName, responseJSON) {
                        alert('出错了-.- ' + responseJSON.msg);
                    }
                });

    <#--update article-->
        $('#apply').click(function (e) {
            e.preventDefault();
            $.ajax({
                url: '/articles/' + targetArticleId + '/update',
                type: 'PUT',
                dataType: 'json',
                data: $('#update_article_modal form').serialize(),
                success: function (data) {
                    if (data.success) {
                        alert('修改成功！可能需要刷新才能看到新修改的变化哦:-）');
                        $('.modal').modal('hide');
                    } else {
                        alert('修改失败！' + data.msg);
                    }
                },
                complete: function () {
                    targetArticleId = 0;
                }
            });
        })
    })

    <#--load more articles-->
    $('#more_articles').click(function (e) {
        e.preventDefault();
        var this$ = $(this);
        var last = this$.attr('last');
        if (!last) {
            last = $('#articles tr:last').attr('index');
        }
        var lastIndex = this$.attr('last_index');
        if (!lastIndex) {
            lastIndex = $('#articles tr:last').attr('index');
        }
        var count = 10;
        this$.attr('disabled', 'disabled').text('正在努力加载中:-)');
        $.ajax({
            url: '/articles',
            type: 'GET',
            dataType: 'json',
            data: {
                type: 1,
                count: count,
                append: true,
                offset: last
            },
            success: function (data) {
                var articles = data.articles;
                if (!articles) {
                    this$.text('没有更多啦');
                    return;
                }
                $.each(articles, function (i, a) {
                    var row$ = $('#article_template .row').children().clone();
                    row$.attr('index', a.id);
                    row$.find('.index').text(++lastIndex);
                    row$.find('.title').attr('aid', a.id).html('<a href="#">' + a.title + '</a>');
                    row$.find('.author').text(a.author_name);
                    row$.find('.category').text(category(a.category));
                    row$.find('.layout').text(layout(a.layout));
                    row$.find('.added_date').text(datetime.format('yyyy-MM-dd HH:mm:ss', a.added_date));
                    row$.find('.recommended').html(a.recommended ? '<code class="text-warning">取消推荐</code>'
                            : '<code class="text-success">设为推荐</code>');
                    row$.find('.top').html(a.top ? '<code class="text-warning">取消置顶</code>'
                            : '<code class="text-success">设为置顶</code>');
                    row$.find('.blocked').html(a.blocked ? '<code class="text-success">取消屏蔽:-)</code>'
                            : '<code class="text-warning">屏蔽:-(</code>');
                    row$.appendTo('#articles');
                    if (i == articles.length - 1) {
                        if (i < count) {
                            this$.text('没有更多啦');
                        } else {
                            this$.removeAttr('disabled').text('加载更多！');
                        }
                    }
                })
            }
        });
    })

    $('#trigger_upload').click(function (e) {
        e.preventDefault();
        if (success) {
            var ok = confirm('您刚才已经成功推送了一篇文章，你确认需要再次推送吗？');
            if (!ok) {
                return;
            }
        }
        $('#upload_alert').empty();
        $('#file_upload').fineUploader('uploadStoredFiles');
    });

    $('body').on('click', '#articles .title', function (e) {
        e.preventDefault();
        var aid = $(this).attr('aid');
        $.ajax({
            url: '/articles/' + aid,
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                var a = data.article;
                if (!a) {
                    alert('没有找到该篇文章！' + data.msg);
                } else {
                    var modal = $('#update_article_modal');
                    modal.find('.modal_title').text(a.title);
                    modal.find('.title').val(a.title);
                    modal.find('.content').val(a.content);
                    modal.find('.author_name').val(a.author_name);
                    modal.find('.author_contact').val(a.author_contact);
                    modal.find('.author_about').val(a.author_about);
                    targetArticleId = a.id;
                    // todo make options available!
                    modal.modal('show');
                }
            }
        });
    })

    <#--toggle recommend-->
    $('body').on('click', '#articles td.recommended', function (e) {
        e.preventDefault();
        var ok = confirm('确定要对这篇文章进行 [推荐/取消推荐] 的操作吗?');
        if (!ok) {
            return;
        }
        var this$ = $(this);
        var id = this$.closest('tr').attr('index');
        $.ajax({
            url: '/articles/' + id + '/recommend',
            type: 'PUT',
            dataType: 'json',
            success: function (data) {
                if (data.success) {
                    var code = this$.find('code');
                    if (code.hasClass('text-success')) {
                        code.removeClass('text-success').addClass('text-warning').text('取消推荐');
                    } else {
                        code.removeClass('text-warning').addClass('text-success').text('设为推荐');
                    }
                } else {
                    alert('操作失败！' + data.msg);
                }
            }
        });
    })

    <#--togle top-->
    $('body').on('click', '#articles td.top', function (e) {
        e.preventDefault();
        var ok = confirm('确定要对这篇文章进行 [置顶/取消置顶] 的操作吗?');
        if (!ok) {
            return;
        }
        var this$ = $(this);
        var id = this$.closest('tr').attr('index');
        $.ajax({
            url: '/articles/' + id + '/top',
            type: 'PUT',
            dataType: 'json',
            success: function (data) {
                if (data.success) {
                    var code = this$.find('code');
                    if (code.hasClass('text-success')) {
                        code.removeClass('text-success').addClass('text-warning').text('取消置顶');
                    } else {
                        code.removeClass('text-warning').addClass('text-success').text('设为置顶');
                    }
                } else {
                    alert('操作失败！' + data.msg);
                }
            }
        });
    })

    <#--toggle top-->
    $('body').on('click', '#articles td.blocked', function (e) {
        e.preventDefault();
        var ok = confirm('确定要对这篇文章进行 [屏蔽/取消屏蔽] 的操作吗?');
        if (!ok) {
            return;
        }
        var this$ = $(this);
        var id = this$.closest('tr').attr('index');
        $.ajax({
            url: '/articles/' + id + '/block',
            type: 'PUT',
            dataType: 'json',
            success: function (data) {
                if (data.success) {
                    var code = this$.find('code');
                    if (code.hasClass('text-success')) {
                        code.removeClass('text-success').addClass('text-warning').text('屏蔽:-(');
                    } else {
                        code.removeClass('text-warning').addClass('text-success').text('取消屏蔽:-)');
                    }
                } else {
                    alert('操作失败！' + data.msg);
                }
            }
        });
    })
    </script>
    </@L.script>
</body>
</@L.html>