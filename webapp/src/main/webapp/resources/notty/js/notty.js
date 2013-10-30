/**
 * 自定义的一个js脚本，试用于信息发布平台。
 * @Author 龙凯
 * @Email im.longkai@gmail.com
 * @Date 2013-05-04
 */
var notty = (function($) {
	var Assert = {
		notNull: function(params, values) {
			var validate = true;
			$.each(values, function(i, item) {
				for (var i = 0; i < params.length; i++) {
					if (params[i] === item.name) {
						var val = item.value;
						if (!val) {
							validate = false;
							return;
						}
						if (typeof(val) === 'string') {
							var str = $.trim(val);
							if (str.length === 0) {
								validate = false;
								return;
							}
						}
					};
				}
			});
			return validate;
		}
	};

	return {
		login: function(options) {
			var settings = $.extend({
						url: null,
					  event: null,
					 method: 'GET',
					 params: null,
				   failCode: 0,
				  loginForm: '#login_form',
				  errorHint: '.__alert__',
				successCode: 1
			}, options || {});
			// console && console.log(settings);
			settings.event && event.preventDefault();

			if (settings.params) {
				if (!Assert.notNull(settings.params, $(settings.loginForm).serializeArray())) {
					$(settings.errorHint).children().remove();
					window.scrollTo(0, 0);
					var html = '<p class="text-center"><span id="status">登陆失败！</span><span id="info">您有未填写的表单信息，请检查后填写！</span></p>';
					$('<div>').addClass('alert alert-error').html(html).css({'font-size': '20px', 'margin': '10px 0'}).appendTo(settings.errorHint);
					return;
				}
			}

			var data = $(settings.loginForm).serialize();
			// console && console.log(data);
			$.ajax({
				url: settings.url,
				type: settings.method,
				data: data,
				dataType: 'json',
				success: function(user) {
					if (user.id) {
						location.reload();
					} else {
						$(settings.errorHint).children().remove();
						window.scrollTo(0, 0);
						var html = '<p class="text-center"><span id="status">登陆失败！</span><span id="info">' + user.msg + '</span></p>';
						$('<div>').addClass('alert alert-error').html(html).css({'font-size': '20px', 'margin': '10px 0'}).appendTo(settings.errorHint);
					}
				},
				error: function() {
					// 对于连接的错误，直接alter就好了
					alert('出错了，请稍后再试！');
				}
			})
			$(':password', settings.loginForm).val('');
		},

		logout: function(options) {
			options = $.extend({
				event: null,
				userService: '#user_service',
				method: 'GET',
				url: '/notice/logout',
				itemAttr: 'uid'
			}, options || {});
			// console && console.log(options);

			options.event && event.preventDefault();
			var uid = $(options.userService).attr(options.itemAttr);
			$.ajax({
				type: options.method,
				url: options.url
			})
			location.href = '/notice';
		}
	}
})(jQuery);
