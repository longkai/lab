INSERT INTO auth_users
    (id, is_admin, authed_name, org, about, contact, join_date,
                blocked, account, password, last_login_ip, last_login_date, last_modified_date)
VALUES
    (null, 1, '雨无声', '雨无声网站', '雨无声网站官方', '0771-3273880', '2013-07-31',
                0, 'newgxu', '7dbca740ad4c547aaaf5f2fb4f6e772f', null, null, null);

INSERT INTO auth_users
    (id, is_admin, authed_name, org, about, contact, join_date,
                blocked, account, password, last_login_ip, last_login_date, last_modified_date)
VALUES
    (null, 0, '龙凯', 'com.gihub.longkai', 'longkai', 'im.longkai@gmail.com', '1991-12-17',
                0, 'longkai', '7dbca740ad4c547aaaf5f2fb4f6e772f', null, null, null);

INSERT INTO auth_users
    (id, is_admin, authed_name, org, about, contact, join_date,
                blocked, account, password, last_login_ip, last_login_date, last_modified_date)
VALUES
    (null, 0, 'Steve Jobs', 'Apple Inc.', '乔布斯，苹果公司联合创始人', 'http://apple.com', '1955-2-24',
                0, 'steve_jobs', '7dbca740ad4c547aaaf5f2fb4f6e772f', null, null, null);

INSERT INTO auth_users
    (id, is_admin, authed_name, org, about, contact, join_date,
                blocked, account, password, last_login_ip, last_login_date, last_modified_date)
VALUES
    (null, 0, 'Larry Page', 'Google Inc.', '佩奇，Google公司联合创始人', 'http://google.com', '1973-3-26',
                0, 'larry_page', '7dbca740ad4c547aaaf5f2fb4f6e772f', null, null, null);

INSERT INTO auth_users
    (id, is_admin, authed_name, org, about, contact, join_date,
                blocked, account, password, last_login_ip, last_login_date, last_modified_date)
VALUES
    (null, 0, 'Bill Gates', 'Microsoft Inc.', '比尔·盖茨，微软公司董事长', 'http://microsoft.com', '1955-10-28',
                0, 'bill_gates', '7dbca740ad4c547aaaf5f2fb4f6e772f', null, null, null);

INSERT INTO auth_users
    (id, is_admin, authed_name, org, about, contact, join_date,
                blocked, account, password, last_login_ip, last_login_date, last_modified_date)
VALUES
    (null, 0, 'Mark Zuckerberg', 'Facebook Inc.', '扎克伯格，Facebook社交网站创始人', 'http://facebook.com', '1984-5-14',
                0, 'mark_zuckerberg', '7dbca740ad4c547aaaf5f2fb4f6e772f', null, null, null);

INSERT INTO auth_users
    (id, is_admin, authed_name, org, about, contact, join_date,
                blocked, account, password, last_login_ip, last_login_date, last_modified_date)
VALUES
    (null, 0, '马云', 'Alibaba Inc.', '马云，淘宝网创始人', 'http://alibaba.com', '1964-10-15',
                1, 'ma_yun', '7dbca740ad4c547aaaf5f2fb4f6e772f', null, null, null);

INSERT INTO auth_users
    (id, is_admin, authed_name, org, about, contact, join_date,
                blocked, account, password, last_login_ip, last_login_date, last_modified_date)
VALUES
    (null, 0, '马化腾', 'Tencent Inc.', '马化腾，腾讯创始人', 'http://tencent.com', '1971-10-29',
                1, 'mahuateng', '7dbca740ad4c547aaaf5f2fb4f6e772f', null, null, null);


INSERT INTO notices
       (id, add_date, blocked, click_times, content, doc_name, doc_url, last_modified_date, title, uid)
VALUES
       (null, '2013-5-20', 0, 100, '雨无声实验室！', '认证申请书',
              'http://lab.newgxu.cn/resources/docs/info/application.doc', '2013-7-31', '雨无声实验室!', 1);

INSERT INTO notices
       (id, add_date, blocked, click_times, content, doc_name, doc_url, last_modified_date, title, uid)
VALUES
       (null, '2013-5-21', 0, 35, 'Bootstrap 3 RC1 发布，趋于扁平化的风格？', null,
              null, null, 'Bootstrap 3 RC1 发布', 2);

INSERT INTO notices
       (id, add_date, blocked, click_times, content, doc_name, doc_url, last_modified_date, title, uid)
VALUES
       (null, '2013-5-27', 1, 75, '如今，有来自国外的阿森纳球迷对温格的“所作所为”忍无可忍了，这位枪手死忠甚至在推特上以死相逼！如图所示，这位名叫“Yoann”的阿森纳球迷在推特上发布了一张拿刀架着脖子的图片，像是要刎颈自杀，眼神中充满了哀怨和失望，俨然是在发泄对阿森纳引援不力的不满，他在推特上写道：“温格，如果你下周末之前还不签下苏亚雷斯，那么……”',
        null, null, '2013-6-30', '阿森纳球迷骇人照片逼温格:不买苏牙我就自刎(图)', 3);

INSERT INTO notices
       (id, add_date, blocked, click_times, content, doc_name, doc_url, last_modified_date, title, uid)
VALUES
       (null, '2013-6-5', 0, 12, '生鲜电商，从不被看好到被风投追捧，在这两年间发生了巨大变化。这是一个电商新市场，同时也面临着新挑战。怎样给用户送上最新鲜的美味？是生鲜电商与传统商超共同思考的问题。本期五道口沙龙，与四位生鲜电商领域创业者一同讨论生鲜电商背后的物流与供应链。',
        null, null, '2013-6-21', '生鲜电商背后的供与流', 1);

INSERT INTO notices
       (id, add_date, blocked, click_times, content, doc_name, doc_url, last_modified_date, title, uid)
VALUES
       (null, '2013-6-13', 0, 25, '网易科技讯 7月31日消息，据路透社报道，苹果首席执行官蒂姆·库克（Tim Cook）周二会见了中国移动高管。中国移动是全球用户最多的运营商，也是中国唯一没有销售iPhone和iPad的运营商。',
        null, null, '2013-7-21', '库克会见中移动奚国华讨论合作事宜', 5);

INSERT INTO notices
       (id, add_date, blocked, click_times, content, doc_name, doc_url, last_modified_date, title, uid)
VALUES
       (null, '2013-6-23', 0, 99, 'iOS 7 introduces great new features like Control Center, AirDrop for iOS, and smarter multitasking. It also makes the things you do every day even easier, faster, and more enjoyable. And while many of the apps look different, the way you do things feels perfectly familiar. So comefrom day one, you know how to use the world’s most advanced mobile OS. In its most advanced form.',
        null, null, '2013-7-31', 'iOS 7', 3);

INSERT INTO notices
       (id, add_date, blocked, click_times, content, doc_name, doc_url, last_modified_date, title, uid)
VALUES
       (null, '2013-7-5', 0, 86, '北京时间7月31日早间消息，英特尔的首款“开源PC”已经出货。这是一种基本的计算机，瞄准x86应用的开发者以及希望自主组装电脑的爱好者。这一PC名为MinnowBoard，仅仅只有一个主板，而没有外壳。',
        null, null, null, '英特尔首款开源PC开售：仅售199美元', 7);

INSERT INTO notices
       (id, add_date, blocked, click_times, content, doc_name, doc_url, last_modified_date, title, uid)
VALUES
       (null, '2013-7-5', 0, 3, '我们正在审核你提交到App Store的应用，并向让你知道审核过程需要额外的时间。我们对延迟表示道歉，并会在第一时间通知你App审核的进度。',
        null, null, null, '苹果开发者中心 8 天宕机导致 App 审核延迟', 4);

INSERT INTO notices
       (id, add_date, blocked, click_times, content, doc_name, doc_url, last_modified_date, title, uid)
VALUES
       (null, '2013-7-9', 0, 37, 'Firefox 23.0的进展相当迅速，Beta 10已经来到Beta测试目录中，新版开始采用Gecko 23内核，预计正式发布时间2013年8月，Moziila在新版本23中加入了混合内容锁定模式，保证了在安全传输模式下第三方中间人攻击无法实施，此外开始对OS X 10.7以上版本提供了新的滑动导航动画等新特性。',
        null, null, null, 'Mozilla Firefox 23.0 Beta 10 发布', 4);

INSERT INTO notices
       (id, add_date, blocked, click_times, content, doc_name, doc_url, last_modified_date, title, uid)
VALUES
       (null, '2013-7-13', 0, 43, '据路透社报道，Reddit联合创始人、互联网活动家艾伦·斯沃茨(Aaron Swartz)自杀事件让麻省理工学院（MIT）备受各方指责，该校对其在事件中的角色展开了调查。负责调查的人员周二发布报告称，如果MIT当时能采取一些举措，例如对斯沃茨受到的有争议指控采取公开反对立场，斯沃茨承受的压力可能会减少。',
        null, null, '2013-7-18', 'MIT公布“著名黑客之死”调查报告', 4);

INSERT INTO notices
       (id, add_date, blocked, click_times, content, doc_name, doc_url, last_modified_date, title, uid)
VALUES
       (null, '2013-7-18', 0, 85, '社交化购物网站Fancy在2012年初亮相的时候，这个照片分享社区看起来非常类似于社交媒体网站Pinterest。用户可以将各种物品的照片钉在电子照片板上，供其他人点击购买。Fancy因而被称为“购物版Pinterest”。',
        null, null, null, '社交化购物网站Fancy：超越Pinterest', 7);

INSERT INTO notices
       (id, add_date, blocked, click_times, content, doc_name, doc_url, last_modified_date, title, uid)
VALUES
       (null, '2013-7-21', 0, 21, '一个灵活的、自定义程度高的系统，被第三方硬件制造商广泛接受，拥有决定性的市场份额，但却没盈利？听起来是不是感觉有些似曾相识。',
        null, null, null, '评论称安卓战略与Windows类似 终将胜过苹果', 5);

INSERT INTO notices
       (id, add_date, blocked, click_times, content, doc_name, doc_url, last_modified_date, title, uid)
VALUES
       (null, '2013-7-25', 0, 9, '联发科四核芯片、支持双卡双待、售价799、8月12日开放首批10万台购买……这次的确是给了我们一个“惊喜”。惊的自然是直到公布的前一秒，相信还有不少人都以为小米这次要推出的是代号“紫米”的米pad，喜的则是，小米这次799的价格确实十分诱人，说不定能把其他主流机型拉到千元以下的坑里……',
        null, null, '2013-7-26', '小米选择了QQ空间，为什么不是其他呢？', 5);

INSERT INTO notices
       (id, add_date, blocked, click_times, content, doc_name, doc_url, last_modified_date, title, uid)
VALUES
       (null, '2013-7-27', 0, 38, '“已经提交A1表的传闻是错的。”了解阿里巴巴上市安排的消息人士对记者再次确认，无论是承销团还是上市地点都还没有最后敲定，甚至，连把哪些资产打包整体上市都没有确定的版本。',
        null, null, '2013-7-28', '阿里巴巴IPO上市地摇摆之谜 ：美国还是香港', 2);

INSERT INTO notices
       (id, add_date, blocked, click_times, content, doc_name, doc_url, last_modified_date, title, uid)
VALUES
       (null, '2013-7-29', 0, 65, '2011年11月，李明远从UC重回百度，任百度移动·云事业部高级总监，三个月后升任移动·云事业部总经理，现又升至百度副总裁。公开资料显示，李明远今年仅29岁，是百度历史上最年轻的副总裁。',
        null, null, null, '百度移动云事业部总经理李明远晋升公司副总裁', 6);

INSERT INTO notices
       (id, add_date, blocked, click_times, content, doc_name, doc_url, last_modified_date, title, uid)
VALUES
       (null, '2013-7-30', 1, 25, '7月31日消息，据国外媒体报道，欧盟反垄断机构日前加大了对谷歌商业行为的审查力度。当局发放问卷询问谷歌的竞争对手：谷歌在搜索引擎上对其给予较低排名，这是否影响到了这些网站的访问人数。',
        null, null, null, '欧盟发问卷询谷歌对手意见 加大反垄断审查力度', 8);

INSERT INTO notices
       (id, add_date, blocked, click_times, content, doc_name, doc_url, last_modified_date, title, uid)
VALUES
       (null, '2013-7-31', 0, 32, '7月31日消息，几周前传出消息称Facebook正在对其移动游戏发行项目进行试验。据科技博客TechCrunch报道，Facebook今天在旧金山的Casual Connect大会上正式公布了该项目。该公司将向参与的开发者收取分成，不过它并未披露具体的分成比例。',
        null, null, '2013-7-31 14:20:32', 'Facebook正式发布移动游戏发行平台', 8);
