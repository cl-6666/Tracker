# sdk介绍
### 内部使用第三方库  
非常轻量级的库  
https://github.com/alibaba/fastjson  
https://github.com/LitePalFramework/LitePal  


### 初始化操作 
```Java
 TrackConfiguration configuration = new TrackConfiguration()
                // 开启log
                .openLog(true)
                .initializeDb(this)
                // 设置日志的上传策略
                .setUploadCategory(UPLOAD_CATEGORY.NEXT_CACHE.getValue())
                // 设置埋点信息上传的URL
                .setServerUrl(SA_SERVER_URL)
                //本地缓存的最大事件数目，当累积日志量达到阈值时发送数据，默认值 100
                .setFlushBulkSize(100)
                //设置本地缓存最多事件条数，默认为 10000 条
                .setMaxCacheSize(10000)
                // 设置上传埋点信息的公共参数
                //对于新设备的信息和公共参数，默认提供了包名，渠道，版本号，设备ID，手机品牌，手机系统版本，但在实际开发中，
                // 需要的参数可能有所差异，所以提供了自定义的功能，只需要将需要的参数以URL参数的格式进行拼接即可。
                .setCommonParameter("?channel=mi&version=1.0");
        Tracker.getInstance().init(this, configuration);
```
### 使用方法
```Java
 保存用户id
 Tracker.getInstance().getDistinctId("9771C579-71F0-4650-8EE8-8999FA717761");
 清空用户id-传空就好了
 Tracker.getInstance().getDistinctId("");
 上传经纬度
 Tracker.getInstance().setGPSLocation(1323.32, 4232.32);   //测试经纬度
 点击事件
 Tracker.getInstance().addClickEvent(view);
 页面事件
 Tracker.getInstance().addViewEvent();
```
### 埋点库里内部自动封装了一些公共参数  
```Java
 device_id	string	设备ID（有权限时候直接获取，没有权限获取硬件码生成唯一id，使用uuid）
 network_type	string	例如 WIFI、4G等
 wifi_name
 string	wifi名称（没有的话为空串）
 screen_width	int	屏幕宽度 例如 1080
 screen_height	int	屏幕高度 例如 2160
 os	string	操作系统，例如 ios、android
 os_version	string	操作系统版本，例如 8.1.0
 manufacturer
 string	设备制造商，例如 Xiaomi
 model	string	设备型号，例如 MI MAX 3
 longitude	string	经度
 latitude	string	纬度
 sdk	string	SDK类型，例如android，ios，java，javascript等
 sdk_version	string	SDK版本，例如3.1.5
 app_version	string	应用的版本，1.3.0
 app_name	string	应用的名称
 channel	string	渠道编码
 title	string	页面的标题（以Android为示例 首先读取 activity.getTitle()，如果使用 actionBar，并且 actionBar.getTitle() 不为空，
 screen_name	string	Activity 的包名.类名
 browser	string	浏览器名，例如Chrome
 browser_version	string	浏览器版本，例如Chrome 45
 user_agent	string	浏览器User-Agent字符串，识别客户使用的操作系统及版本、CPU 类型、浏览器及版本、浏览器渲染引擎、浏览器语言、浏览器插件等。
 event_code	string	事件编码
 user_id	string	用户ID（已经登录则传userId，未登录则为空串）
 gmt_time	long	时间，精确到毫秒
```
