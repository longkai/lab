<@L.html>
	<@L.head title="实验室成员中心 - 雨无声实验室">
	<link href="/resources/libs/fineuploader/3.7.1/fineuploader-3.7.1.min.css" rel="stylesheet" />
	<style type="text/css">
		body {
			padding-top: 60px;
			padding-bottom: 40px;
		}

		.holder {
			margin-top: 10px
		}

		.span6 {
			margin: 0 0 10px 0;
		}

		.qq-upload-button {
			background-color: #62c462;
		}
	</style>
	</@L.head>
<body>
<div class="navbar navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container">
			<button type="button" class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="brand" href="#">实验室成员中心</a>

			<div class="nav-collapse collapse">
				<ul class="nav">
					<li class="active"><a href="/members/center">首页</a></li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							雨无声实验室 <b class="caret"></b>
						</a>
						<ul class="dropdown-menu">
							<li><a href="/">首页</a></li>
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
				<div class="btn-group pull-right">
					<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
					${Session.session_member.nick}
						<span class="caret"></span>
					</a>
					<ul class="dropdown-menu">
						<li id="update"><a href="#">修改个人信息</a></li>
						<li id="logout"><a href="#">退出</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="container">
<div class="tabbable tabs-left">
<ul class="nav nav-tabs">
	<li class="active"><a href="#app_tab" data-toggle="tab">应用中心</a></li>
	<li class=""><a href="#lB" data-toggle="tab">Section 2</a></li>
	<li class=""><a href="#lC" data-toggle="tab">Section 3</a></li>
</ul>
<div class="tab-content">
<div class="tab-pane active" id="app_tab">
<div class="tabbable">
<ul class="nav nav-tabs">
	<li class="active"><a href="#app_tab1" data-toggle="tab">我的应用</a></li>
	<li><a href="#app_tab2" data-toggle="tab">升级应用</a></li>
	<li><a href="#app_tab3" data-toggle="tab">上传应用</a></li>
</ul>
<div class="tab-content">
<div class="tab-pane active" id="app_tab1">
	<table class="table table-striped">
		<thead>
		<tr>
			<th>#</th>
			<th>App</th>
			<th>查看历史版本</th>
			<th>更新</th>
			<th>平台</th>
			<th>类别</th>
			<th>总安装次数</th>
			<th>更改应用图标</th>
		</tr>
		</thead>
		<tbody id="apps">
		</tbody>
	</table>
	<table class="hide" id="app_table_template">
		<tbody class="row">
		<tr>
			<td class="index"></td>
			<td class="name"></td>
			<td class="history"></td>
			<td class="upgrade"></td>
			<td class="platform"></td>
			<td class="category"></td>
			<td class="total_install_count"></td>
			<td class="change_icon"></td>
		</tr>
		</tbody>
	</table>
	<button class="btn btn-info btn-block" id="refresh_apps">刷新</button>

	<div id="change-icon-section" class="hide holder">
		<div class="span6">
			<div id="change-icon"></div>
			<noscript>
				<p>Please enable JavaScript to use Fine Uploader.</p>
				<!-- or put a simple form for upload here -->
			</noscript>
		</div>
		<div class="span6">
			<button id="triggerUpload-change-icon" class="btn btn-info span6">
				<i class="icon-upload icon-white"></i> 上传!
			</button>

			<div id="change_icon_alert"></div>
		</div>
	</div>

</div>
<div class="tab-pane" id="app_tab2">
	<table class="table table-striped">
		<thead>
		<tr>
			<th>#</th>
			<th>版本号</th>
			<th>更新日期</th>
			<th>安装次数</th>
			<th>大小</th>
			<th>查看更新信息</th>
		</tr>
		</thead>
		<tbody id="history">
		</tbody>
	</table>
	<table class="hide" id="app_history_template">
		<tbody class="row">
		<tr>
			<td class="index"></td>
			<td class="version"></td>
			<td class="update_date"></td>
			<td class="install_count"></td>
			<td class="size"></td>
			<td class="info"></td>
		</tr>
		</tbody>
	</table>
	<button class="btn btn-success btn-block" id="new_version">有新版本了？！</button>

	<form action="#" method="post" class="hide" id="new_version_form">
		<div class="holder text-info lead">填写新版本信息(填写则表示您已遵守国家相关法律法规并尊重用户隐私！)</div>
		<hr />
		<div class="control-group">
			<label class="control-label" for="version">版本号</label>

			<div class="controls">
				<input type="text" class="version" name="version" placeholder="版本号" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="update_info">更新信息</label>

			<div class="controls">
				<textarea type="text" class="update_info" name="update_info"
						  placeholder="更新信息（255字以内）"></textarea>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="extra">额外信息（可选）</label>

			<div class="controls">
				<textarea type="text" class="extra" name="extra"
						  placeholder="额外信息（可选，255字以内）"></textarea>

				<div class="holder">
					<button class="btn btn-primary" id="submit_new_version">提交</button>
					<p class="text-info lead holder">提交通过后方可上传应用哦</p>
				</div>
			</div>
		</div>
	</form>

	<div id="version-bin-section" class="hide">
		<p class="text-info lead">接下来上传应用程序的可执行文件吧（目前最大20M）</p>
		<hr />
		<div class="span6">
			<div id="create-version-bin"></div>
			<noscript>
				<p>Please enable JavaScript to use Fine Uploader.</p>
				<!-- or put a simple form for upload here -->
			</noscript>
		</div>
		<div class="span6">
			<button id="triggerUpload-version-bin" class="btn btn-info span6">
				<i class="icon-upload icon-white"></i>确定，完成app创建!
			</button>

			<div id="version_bin_alert"></div>
		</div>
	</div>

</div>
<div class="tab-pane" id="app_tab3">
	<form class="form-horizontal" id="new_app_form">
		<div class="control-group">
			<label class="control-label" for="name">应用名</label>

			<div class="controls">
				<input type="text" class="name" name="name" placeholder="应用名">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="os_requirement">系统要求</label>

			<div class="controls">
				<input type="text" class="os_requirement" name="os_requirement"
					   placeholder="系统要求" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="description">应用介绍</label>

			<div class="controls">
				<textarea type="text" class="description" name="description"
						  placeholder="应用介绍"></textarea>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="extra">额外信息（可选）</label>

			<div class="controls">
				<textarea type="text" class="extra" name="extra"
						  placeholder="额外信息（可选）"></textarea>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="platform">系统平台</label>

			<div class="controls">
				<select multiple="multiple" name="platform">
					<option value="ANDROID" selected="selected">Android</option>
					<option value="APPLE">Apple</option>
					<option value="WINDOWS">Windows</option>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="category">应用类型</label>

			<div class="controls">
				<select multiple="multiple" name="category">
					<option value="DEFAULT" selected="selected">默认</option>
					<option value="GAME">游戏</option>
					<option value="INDIVIDUAL">个性化</option>
					<option value="EMTERTAINMENT">娱乐</option>
					<option value="STUDY">学习</option>
				</select>
			</div>
		</div>
		<hr />
		<p class="lead muted">接下来，描述下你的应用的第一个版本吧^_^</p>

		<div class="control-group">
			<label class="control-label" for="version">版本号</label>

			<div class="controls">
				<input type="text" class="version" name="version" placeholder="版本号" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="update_info">更新信息</label>

			<div class="controls">
				<textarea type="text" class="update_info" name="update_info"
						  placeholder="更新信息（255字以内）"></textarea>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="extra">额外信息（可选）</label>

			<div class="controls">
				<textarea type="text" class="extra" name="extra"
						  placeholder="额外信息（可选，255字以内）"></textarea>

				<div class="holder">
					<button class="btn btn-primary" id="submit_new_app">提交</button>
					<p class="text-info lead holder">提交通过后方可上传应用以及图片哦</p>
				</div>
			</div>
		</div>
	</form>
	<div id="new_app_form_error"></div>
	<div id="create-icon-section" class="hide">
		<div class="span6">
			<div id="create-icon"></div>
			<noscript>
				<p>Please enable JavaScript to use Fine Uploader.</p>
				<!-- or put a simple form for upload here -->
			</noscript>
		</div>
		<div class="span6">
			<button id="triggerUpload-icon" class="btn btn-info span6">
				<i class="icon-upload icon-white"></i> 上传!
			</button>

			<div id="icon_alert"></div>
		</div>
	</div>

	<div id="create-bin-section" class="hide">
		<div class="span6">
			<div id="create-bin"></div>
			<noscript>
				<p>Please enable JavaScript to use Fine Uploader.</p>
				<!-- or put a simple form for upload here -->
			</noscript>
		</div>
		<div class="span6">
			<button id="triggerUpload-bin" class="btn btn-info span6">
				<i class="icon-upload icon-white"></i>确定，完成app创建!
			</button>

			<div id="bin_alert"></div>
		</div>
	</div>
</div>
</div>
</div>
</div>
<div class="tab-pane" id="lB">
	<p>Howdy, I'm in Section B.</p>
</div>
<div class="tab-pane" id="lC">
	<p>What up girl, this is Section C.</p>
</div>
</div>
</div>
</div>

<#--更新成员信息modal-->
<div id="update_modal" class="modal hide fade" tabindex="-1" role="dialog"
	 aria-labelledby="myModalLabel"
	 aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		<h3 class="title"></h3>
	</div>
	<div class="modal-body">
		<form class="form-horizontal">
			<div class="control-group">
				<label class="control-label" for="contact">联系方式</label>

				<div class="controls">
					<input type="text" class="contact" name="contact" placeholder="联系方式" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="label">个人标签</label>

				<div class="controls">
					<input type="text" class="_label" id="label" name="label" placeholder="个人标签" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="description">个人介绍</label>

				<div class="controls">
					<textarea type="text" class="description" name="description"
							  placeholder="个人介绍"></textarea>
				</div>
			</div>
		</form>
	</div>
	<div class="modal-footer">
		<button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
		<button class="btn btn-primary" id="apply_changes">确定</button>
	</div>
</div>
<#--更新app信息modal-->
<div id="app_modal" class="modal hide fade" tabindex="-1" role="dialog"
	 aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		<h3 class="title"></h3>
	</div>
	<div class="modal-body">
		<form class="form-horizontal">
			<div class="control-group">
				<label class="control-label" for="os_requirement">系统要求</label>

				<div class="controls">
					<input type="text" class="os_requirement" name="os_requirement"
						   placeholder="系统要求" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="outside_link">外链（可选）</label>

				<div class="controls">
					<input type="text" class="outside_link" name="outside_link"
						   placeholder="外链（可选）" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="description">描述</label>

				<div class="controls">
					<textarea type="text" class="description" name="description"
							  placeholder="描述"></textarea>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="extra">额外信息（可选）</label>

				<div class="controls">
					<textarea type="text" class="extra" name="extra"
							  placeholder="额外信息（可选）"></textarea>
				</div>
			</div>
		</form>
	</div>
	<div class="modal-footer">
		<button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
		<button class="btn btn-primary apply_changes">确定</button>
	</div>
</div>
<#--更新版本信息modal-->
<div id="update_version_modal" class="modal hide fade" tabindex="-1" role="dialog"
	 aria-labelledby="myModalLabel"
	 aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		<h3 class="title"></h3>
	</div>
	<div class="modal-body">
		<form class="form-horizontal">
			<div class="control-group">
				<label class="control-label" for="update_info">更新信息</label>

				<div class="controls">
					<textarea type="text" class="update_info" name="update_info"
							  placeholder="更新信息"></textarea>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="extra">额外信息（可选）</label>

				<div class="controls">
					<textarea type="text" class="extra" name="extra"
							  placeholder="额外信息（可选）"></textarea>
				</div>
			</div>
		</form>
	</div>
	<div class="modal-footer">
		<button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
		<button class="btn btn-primary apply_changes">更新</button>
	</div>
</div>
</body>
	<@L.script>
	<script src="/resources/libs/fineuploader/3.7.1/jquery.fineuploader-3.7.1.min.js"></script>
	<script src="/resources/js/common/datetime.js"></script>
	<script type="text/javascript">
	var id = ${Session.session_member.id};
	var current_target_app_id = null;
	$(function () {
	<#--上传新版本的应用图标-->
		$('#create-icon').fineUploader({
			autoUpload: false,
			request: {
				inputName: 'icon',
				endpoint: '/apps/icon'
			},
			validation: {
				itemLimit: 1,
				allowedExtensions: ['jpg', 'png'],
				sizeLimit: 2097152 // 2 mb = 2 * 1024 * 1024 bytes
			},
			editFilename: {
				enabled: true
			},
			text: {
				uploadButton: '<i class="icon-upload icon-white"></i> 请选择应用图标或者将图标拖放到此处'
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
				$('#icon_alert').append('<div class="alert alert-error lead">' + message + '</div>');
			}

		}).on({
					'complete': function (event, id, fileName, responseJSON) {
						if (responseJSON.success) {
							$('#create-bin-section').show();
						}
					},
					'error': function (event, id, fileName, responseText) {
						$('#icon_alert').append('<div class="alert alter-error lead">' + responseText + '</div>')
					}
				});

	<#--上传新应用的首个版本的二进制文件-->
		$('#create-bin').fineUploader({
			autoUpload: false,
			request: {
				inputName: 'bin',
				endpoint: '/apps/bin'
			},
			validation: {
				itemLimit: 1,
				allowedExtensions: ['apk', 'ipa', 'xap'],
				sizeLimit: 20971520 // 20 mb = 20 * 1024 * 1024 bytes
			},
			editFilename: {
				enabled: true
			},
			text: {
				uploadButton: '<i class="icon-upload icon-white"></i> 请选择应用二进制或者将其拖放到此处'
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
				$('#bin_alert').append('<div class="alert alert-error lead">' + message + '</div>');
			}

		}).on({
					'complete': function (event, id, fileName, responseJSON) {
						$.parseJSON(responseJSON)
						if (responseJSON.success) {
							alert('新建app成功！');
						}
					},
					'error': function (event, id, fileName, responseText) {
						alert(responseText);
					}
				});

	<#--上传应用新版本的二进制文件-->
		$('#create-version-bin').fineUploader({
			autoUpload: false,
			request: {
				inputName: 'bin',
				endpoint: '/versions/bin'
			},
			validation: {
				itemLimit: 1,
				allowedExtensions: ['apk', 'ipa', 'xap'],
				sizeLimit: 20971520 // 20 mb = 20 * 1024 * 1024 bytes
			},
			editFilename: {
				enabled: true
			},
			text: {
				uploadButton: '<i class="icon-upload icon-white"></i> 请选择应用二进制或者将其拖放到此处'
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
				$('#version-bin_alert').append('<div class="alert alert-error lead">' + message + '</div>');
			}

		}).on({
					'submit': function (event, id, fileName) {
						$(this).fineUploader('setParams', {appId: $('#new_version').attr('appid')});
					},
					'complete': function (event, id, fileName, responseJSON) {
						if (responseJSON.success) {
							var v = responseJSON.version;
							alert('更新app版本 ' + v.version + ' 成功！');
							$('#new_version').attr('disabled', 'disabled');
							$('#version-bin-section').hide();
							$('#new_version_form').hide();
							var row$ = $('#app_history_template .row').children().clone();
							row$.find('.index').html('<p class="text-success">lastest!</p>');
							row$.find('.version').text(v.version);
							row$.find('.size').text(Math.round(v.size / 1024) + ' kb');
							row$.find('.install_count').text(v.install_count);
							row$.find('.update_date').text(datetime.format('yyyy-MM-dd HH:mm:ss', v.update_date));
							row$.find('.info').html('<button class="btn btn-info view_version_info" version_id="' + v.id + '">查看更新信息</button>');
							row$.prependTo('#history');
						}
					},
					'error': function (event, id, fileName, responseText) {
						alert('新建app失败！' + responseText);
					}
				});


		$('#change-icon').fineUploader({
			autoUpload: false,
			request: {
				inputName: 'icon',
				endpoint: '/apps/change_icon'
			},
			validation: {
				itemLimit: 1,
				allowedExtensions: ['jpg', 'png'],
				sizeLimit: 2097152 // 2 mb = 2 * 1024 * 1024 bytes
			},
			editFilename: {
				enabled: true
			},
			text: {
				uploadButton: '<i class="icon-upload icon-white"></i> 请选择应用图标或者将图标拖放到此处'
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
				$('#change_icon_alert').append('<div class="alert alert-error lead">' + message + '</div>');
			}

		}).on({     'submit': function (event, id, fileName) {
					if (!current_target_app_id) {
						alert('上传失败，请稍后再试！');
						return false;
					}
					$(this).fineUploader('setParams', {id: current_target_app_id});
				},
					'complete': function (event, id, fileName, responseJSON) {
						current_target_app_id = null;
						alert("更新成功！");
						$('#change-icon-section').hide();
					},
					'error': function (event, id, fileName, responseText) {
						alert('更新图标失败！' + responseText);
					}
				});


		$('#logout').click(function (e) {
			e.preventDefault();
			$.ajax({
				url: '/members/logout'
			})
			location.href = '/';
		})

	<#--成员更新信息-->
		$('#update').click(function (e) {
			e.preventDefault();
			$.ajax({
				url: '/members/' + id,
				type: 'GET',
				dataType: 'JSON',
				success: function (data) {
					var m = data.member;
					var modal = $('#update_modal');
					modal.find('.title').text('修改 ' + m.nick + '  的信息');
					modal.find('.contact').val(m.contact);
					modal.find('._label').val(m.label);
					modal.find('.description').val(m.description);
					modal.modal();
				},
				error: function (jqxhr, status, error) {
					alert(error + ' -> ' + jqxhr.responseText);
				}
			})
		})

	<#--成员确定更新信息-->
		$('#apply_changes').click(function (e) {
			e.preventDefault();
			var data = $('#update_modal form').serialize();
			$.ajax({
				url: '/members/' + id + '/update',
				data: data,
				dataType: 'JSON',
				type: 'PUT',
				success: function (data) {
					alert('更新成功！');
					$('.modal').modal('hide');
				},
				error: function (jqxhr, status, error) {
					alert(error + ' -> ' + jqxhr.responseText);
				}
			});
		})

	<#--刷新成员的应用列表-->
		$('#refresh_apps').click(function (e) {
			e.preventDefault();
			$('#apps').empty();
			$.ajax({
				url: '/apps/member/' + id,
				dataType: 'JSON',
				type: 'GET',
				success: function (data) {
					$.each(data.apps, function (i, a) {
						var row$ = $('#app_table_template .row').children().clone();
						row$.find('.index').text(i + 1);
						row$.find('.name').text(a.name);
						row$.find('.history').html('<button class="btn btn-success view-history" appid="' + a.id + '">查看</button>');
						row$.find('.upgrade').html('<button class="btn btn-success view-history" appid="' + a.id + '">更新版本</button>');
						row$.find('.platform').text(a.platform);
						row$.find('.category').text(a.category);
						row$.find('.total_install_count').text(a.total_install_count);
						row$.find('.change_icon').html('<button class="btn btn-warning change_icon" appid="' + a.id + '">更改图标</button>');
						row$.appendTo('#apps');
					})
				}
			})
		})

	<#--更新应用信息-->
		$('#app_modal .apply_changes').click(function (e) {
			e.preventDefault();
			var url = $(this).attr('href');
			var modal = $('#app_modal');
			var data = modal.find('form').serialize();
			$.ajax({
				url: url,
				type: 'PUT',
				dataType: 'json',
				data: data,
				success: function (data) {
					alert('修改成功！');
					modal.modal('hide');
				},
				error: function (jqxhr, status, error) {
					alert(error + ' -> ' + jqxhr.responseText);
				}
			});
		})

	<#--提交新应用的表单（接下来是提交icon）-->
		$('#submit_new_app').click(function (e) {
			e.preventDefault();
		<#--simple check-->
			$('#new_app_form_error').empty();
			var fields = $('#new_app_form').serializeArray();
			var ok = true;
			$.each(fields, function (i, f) {
				if (f.name !== 'extra') {
					if (!$.trim(f.value)) {
						$('<div>').addClass('alert text-error')
								.text('您有未填写的表单，请填写完了再添加！')
								.appendTo('#new_app_form_error');
						ok = false;
						return false;
					}
				}
			})
			if (!ok) {
				return;
			}
			var data = $('#new_app_form').serialize();
			$.ajax({
				url: '/apps/new',
				data: data,
				type: 'post',
				dataType: 'json',
				success: function (data) {
					$('#create-icon-section').show();
				},
				error: function (jqxhr, status, error) {
					$('<div/>').addClass('alert text-error').text('添加失败！' + jqxhr.responseText);
				}
			})
		})

		// avoid user manually triggering the tab.
		$('#app_tab .nav-tabs li:nth-child(2) a').click(function (e) {
			e.preventDefault();
			alert('请在我的应用列表中选择一项应用！');
			return false;
		})

		// uodate version info
		$('#update_version_modal .apply_changes').click(function (e) {
			e.preventDefault();
			var vid = $(this).attr('version_id');
			$.ajax({
				url: '/versions/' + vid + '/apply',
				type: 'PUT',
				dataType: 'json',
				data: $('#update_version_modal form').serialize(),
				success: function () {
					alert('修改成功！');
					$('.modal').modal('hide');
				},
				error: function (jqxhr, status, error) {
					alert(error + ' -> ' + jqxhr.responseText);
				}
			})
		});

	<#--更新版本-->
		$('#new_version').click(function (e) {
			e.preventDefault();
			$('#new_version_form').show();
		})

	<#--提交新版本信息-->
		$('#submit_new_version').click(function (e) {
			e.preventDefault();
			var appid = $('#new_version').attr('appid');
			$.ajax({
				url: '/apps/' + appid + '/upgrade',
				type: 'post',
				data: $('#new_version_form').serialize(),
				dataType: 'json',
				success: function () {
					$('#version-bin-section').show();
				},
				error: function (jqxhr, status, error) {
					alert(error + ' -> ' + jqxhr.responseText);
				}
			})
		})

	})

	<#--查看app信息-->
	$('body').on('click', 'li.view_app', function (e) {
		e.preventDefault();
		var url = $(this).find('a').attr('href');
		$.ajax({
			url: url + '.json',
			type: 'GET',
			dataType: 'JSON',
			success: function (data) {
				var a = data.app;
				var modal = $('#app_modal');
				modal.find('.title').text('修改 ' + a.name + ' 的信息');
				modal.find('.os_requirement').val(a.os_requirement);
				modal.find('.outside_link').val(a.outside_link);
				modal.find('.description').val(a.description);
				modal.find('.extra').val(a.extra);
				modal.find('.apply_changes').attr('href', '/apps/' + a.id + '/apply');
				modal.modal();
			},
			error: function (jqxhr, status, error) {
				alert(error + ' -> ' + jqxhr.responseText);
			}
		});
	})

	<#--查看某个app的版本历史记录-->
	$('body').on('click', 'button.view-history', function (e) {
		e.preventDefault();
		var appid = $(this).attr('appid');
		$('#history').empty();
		$.ajax({
			url: '/apps/' + appid + '/history',
			type: 'get',
			dataType: 'json',
			success: function (data) {
				$.each(data.versions, function (i, v) {
					var row$ = $('#app_history_template .row').children().clone();
					row$.find('.index').text(i + 1);
					row$.find('.version').text(v.version);
					row$.find('.size').text(Math.round(v.size / 1024) + ' kb');
					row$.find('.install_count').text(v.install_count);
					row$.find('.update_date').text(datetime.format('yyyy-MM-dd HH:mm:ss', v.update_date));
					row$.find('.info').html('<button class="btn btn-info view_version_info" version_id="' + v.id + '">查看更新信息</button>');
					row$.appendTo('#history');
				})
			}
		})
		$('#new_version').attr('appid', appid);
		// ensure the new version form is hide.
		$('#new_version_form').hide();
		$('#version-bin-section').hide();
		$('#new_version').removeAttr('disabled');
		$('#app_tab .nav-tabs li:nth-child(2) a').tab('show');
	})

	<#--查看（更新）某个版本的信息-->
	$('body').on('click', 'button.view_version_info', function (e) {
		e.preventDefault();
		var vid = $(this).attr('version_id');
		$.ajax({
			url: '/versions/' + vid,
			type: 'get',
			dataType: 'json',
			success: function (data) {
				var v = data.version;
				var modal = $('#update_version_modal');
				modal.find('.title').text('修改 ' + v.version + ' 版本的信息');
				modal.find('.update_info').val(v.update_info);
				modal.find('.extra').val(v.extra);
				modal.find('button.apply_changes').attr('version_id', v.id);
				modal.modal();
			},
			error: function (jqxhr, status, error) {
				alert(error + ' -> ' + jqxhr.responseText);
			}
		})
	})

	$('body').on('click', 'button.change_icon', function (e) {
		e.preventDefault();
		current_target_app_id = $(this).attr('appid');
		$('#change-icon-section').show();
	})

	$('#triggerUpload-icon').click(function () {
		$('#icon_alert').empty();
		$('#create-icon').fineUploader('uploadStoredFiles');
	});

	$('#triggerUpload-bin').click(function () {
		$('#bin_alert').empty();
		$('#create-bin').fineUploader('uploadStoredFiles');
	});

	$('#triggerUpload-version-bin').click(function () {
		$('#version_bin_alert').empty();
		$('#create-version-bin').fineUploader('uploadStoredFiles');
	});

	$('#triggerUpload-change-icon').click(function () {
		$('#change_icon_alert').empty();
		$('#change-icon').fineUploader('uploadStoredFiles');
	});
	</script>
	</@L.script>
</@L.html>