<#include "notty/macro.ftl" />
<@L.html>
<@L.head title="我的信息列表 - 雨无声实验室">
<style type="text/css">
body {
	padding-top: 60px;
	padding-bottom: 40px;
}
#template {
	display: none;
}
#alert {
	margin: 20px 0;
}
footer {
	margin: 20px 0;
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
	<table class="table table-striped" id="notices">
		<tr class="success">
			<td>#</td>
			<td>标题</td>
			<td>发表日期</td>
			<td>修改日期</td>
			<td>上传文件</td>
			<td>查看次数</td>
			<td>状态</td>
			<td>需要修改？</td>
		</tr>
<#list notices as n>
		<tr notice_id="${n.id}">
			<td class=".index">${n_index + 1}</td>
			<td><code class="title"><a href="/notices/${n.id}" target="_blank">${n.title}</a></code></td>
			<td><code class="text-info add-date">${n.addDate?string('yyyy-MM-dd HH:mm:ss')}</code></td>
			<td><code class="text-warning modify-date">${n.lastModifiedDate?string('yyyy-MM-dd HH:mm:ss')}</code></td>
	<#if n.docUrl??>
			<td><code class="upload-file"><a class="text-success" target="_blank" href="${n.docUrl}">${n.docName?default('文件')}</a></code></td>
	<#else>
			<td><code class="upload-file">没有上传</code></td>
	</#if>
			<td><code class="click-times">${n.clickTimes}</code></td>
			<td><code class="block"><a href="/notices/${n.id}/block" class="${n.blocked?string('text-success', 'text-error')}">${n.blocked?string('取消屏蔽', '将其屏蔽')}</a></code></td>
			<td><code><a href="/notices/${n.id}/modify">走起~</a></code></td>
		</tr>
</#list>
	</table>
	<button id="more" class="btn btn-block" uid="${Session.session_auth_user.id}">查看更多！</button>
</div>

<table id="template">
	<tbody class="item">
		<tr>
			<td class="index"></td>
			<td><code class="title"></code></td>
			<td><code class="text-info add-date"></code></td>
			<td><code class="text-warning modify-date"></code></td>
			<td><code class="upload-file"></code></td>
			<td><code class="click-times"></code></td>
			<td><code class="block"></code></td>
			<td><code class="modify"><a href="#">走起~</a></code></td>
		</tr>
	</tbody>
</table>
<div class="container">
	<footer>
		<p>&copy; 2001-2013 <a href="//www.newgxu.cn">newgxu.cn</a> Yws. All Rights Reserved.</p>
	</footer>
</div>
</body>
<@L.script>
<script type="text/javascript" src="/resources/notty/js/notty.js"></script>
<script type="text/javascript" src="/resources/js/common/datetime.js"></script>
<script type="text/javascript">
$(function() {
	var lastIndex = ${notices?size};
	$('#_index').removeClass('active');

	$('#logout').click(function(e) {
		e.preventDefault();
		notty.logout();
	})

	$('#more').click(function(e) {
		e.preventDefault();
		var this$ = $(this);
		var uid = this$.attr('uid');
		var last_notice_id = this$.attr('last_notice_id');
		if (!last_notice_id) {
			last_notice_id = $('#notices tr:last').attr('notice_id');
		}
		var count = 3;
		$.ajax({
			url: '/notices',
			type: 'GET',
			dataType: 'json',
			data: {
				type: 3,
				count: count,
				uid: uid,
				nid: last_notice_id,
				append: 1
			},
			success: function(data) {
				var length = data.notices.length;
				$.each(data.notices, function(i, item) {
					lastIndex++;
					var _item$ = $('#template .item').children().clone();
					_item$.find('.index').text(lastIndex);
					_item$.find('.title').append($('<a>').attr('href', '/notices/' + item.id).attr('target', '_blank').text(item.title));
					_item$.find('.add-date').text(datetime.format('yyyy-MM-dd HH:mm:ss', item.add_date));
					_item$.find('.modify-date').text(datetime.format('yyyy-MM-dd HH:mm:ss', item.last_modified_date));
					if (item.doc_url) {
						var docLink$ = $('<a>').addClass('text-success').attr('href', item.doc_url).attr('target', '_blank');
						if (item.doc_name) {
							docLink$.text(item.doc_name);
						} else {
							docLink$.text('文件');
						}
						_item$.find('.upload-file').append(docLink$);
					} else {
						_item$.find('.upload-file').text('没有上传');
					}
					_item$.find('.click-times').text(item.click_times);
					if (item.blocked) {
						_item$.find('.block').append($('<a>').attr('href', '/notices/' + item.id + '/block').addClass('text-success').text('取消屏蔽'));
					} else {
						_item$.find('.block').append($('<a>').attr('href', '/notices/' + item.id + '/block').addClass('text-error').text('将其屏蔽'));
					}
					_item$.find('.modify a').attr('href', '/notices/' + item.id + '/modify');
					_item$.appendTo('#notices');

					if (length === i + 1) {
						this$.attr('last_notice_id', item.id);
					}
				})
				if (length < count) {
					this$.attr('disabled', true).text('没有更多啦！');
				} else {
					this$.attr('disabled', false).text('查看更多！');
				}
			},
			error: function() {
				$('#msg').text('加载时出错啦-_-，请稍后再试或者联系管理员');
			}
		})
	})
})

$('body').on('click', 'code.block', function(e) {
	e.preventDefault();
	var link$ = $(this).find('a');
	var url = link$.attr('href');
	// console.log(url);
	$.ajax({
		url: url,
		dataType: 'json',
		type: 'PUT',
		success: function(data) {
			if (!data.status) {
				alert('操作失败，错误信息：' + data.msg);
			} else {
				if (link$.hasClass('text-error')) {
					link$.text('取消屏蔽').removeClass().addClass('text-success');
				} else {
					link$.text('将其屏蔽').removeClass().addClass('text-error');
				}
			}
		},
		error: function() {
			alert('操作失败，请联系管理员或者稍后再试！');
		}
	})
})
</script>
</@L.script>
</@L.html>