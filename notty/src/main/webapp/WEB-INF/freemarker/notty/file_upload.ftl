<#include "notty/macro.ftl" />
<@L.html>
    <@L.head title="上传文件 - 校园信息 - 雨无声实验室">
    <link href="/resources/libs/fineuploader/3.7.1/fineuploader-3.7.1.min.css" rel="stylesheet" />
    <style type="text/css">
        body {
            padding-top: 60px;
            padding-bottom: 40px;
        }

        .span12 {
            margin: 0 0 10px 0;
        }

        .qq-upload-button {
            background-color: #62c462;
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
    <h2>给 ${notice.title} 上传文件</h2>
    <div id="jquery-wrapped-fine-uploader"></div>
    <noscript>
        <p>Please enable JavaScript to use Fine Uploader.</p>
        <!-- or put a simple form for upload here -->
    </noscript>
</div>


<div class="container">
    <button id="triggerUpload" class="btn btn-info span12">
        <i class="icon-upload icon-white"></i> 上传!
    </button>

    <div id="alert"></div>
    <button href="/notices/${notice.id}" id="go" class="btn btn-success hide span12">查看更新后的通告！</button>
</div>

<@L.script>
<script src="/resources/libs/fineuploader/3.7.1/jquery.fineuploader-3.7.1.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $('#jquery-wrapped-fine-uploader').fineUploader({
            autoUpload: false,
            allowMultipleItems: true,
            multiple: true,
            request: {
                inputName: 'file',
                endpoint: '/notices/${notice.id}/upload_file'
            },
            validation: {
                itemLimit: 1,
                allowedExtensions: ["zip", "rar", "doc", "docx", "pdf", "txt", "xls", "xlsx", "7z", "ppt", "pptx", "jpg", "png"],
                sizeLimit: 5242880 // 5 MB = 5 * 1024 * 1024 bytes
            },
            editFilename: {
                enabled: true
            },
            text: {
                uploadButton: '<i class="icon-upload icon-white"></i> 请选择文件或者将文件拖放到此处'
            },
            template: '<div class="qq-uploader span12">' +
                    '<pre class="qq-upload-drop-area span12"><span>{dragZoneText}</span></pre>' +
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
                $('#alert').append('<div class="alert alert-error lead">' + message + '</div>');
            }

        }).on({
            "complete": function (event, id, fileName, responseJSON) {
//                console.log(responseJSON);
                if (responseJSON.success) {
                    $('#go').show();
                }
            }
        });

        $('#go').click(function(e) {
            e.preventDefault();
            $(this).addClass('hide');
            location.href = $(this).attr('href');
        })
    });
    $('#triggerUpload').click(function () {
        $('#jquery-wrapped-fine-uploader').fineUploader('uploadStoredFiles');
    });
</script>
</@L.script>
</body>
</@L.html>