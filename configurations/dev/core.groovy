jdbc {
	url = 'jdbc:mysql://192.168.1.251/lab'
	username = 'root'
	password = 'root'
	auto_commit = false
}

file_upload {
	location = '/Users/longkai/tmp'
	max_file_size = 52428800
	max_request_size = 52428800
	file_size_threshold = 0
}

freemarker {
	template_update_delay = 0
}

logback {
	log_home = 'build/logback/'
	log_level = 'DEBUG'
	log_appender = 'STDOUT'
}
