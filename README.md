# sdk介绍
### 简单介绍  
在这里非常感谢神策平台给我带来了灵感编写了这样一个sdk,当然有很多需要完善的部分,我会继续维护下去直到完美，有问题欢迎留言.

### 埋点库里内部自动封装了一些公共参数  
```Java
id	bigint 主键，自增
event_code varchar(32)	事件编码
device_id varchar(32)	设备ID
user_id varchar(32)	用户ID
ip	varchar(64)	IP
country varchar(32)	国家，不传时通过IP解析
province	varchar(32)	省份，不传时通过IP解析
city varchar(32)	城市，不传时通过IP解析
app_version	varchar(32)	应用的版本，130，去除小数点
network_type varchar(32)	网络类型，例如4G
wifi_name varchar(32)	wifi名称
screen_width int	屏幕宽度
screen_height int	屏幕高度
os	varchar(32)	操作系统，例如 ios
os_version varchar(32)	操作系统版本，例如 811，去除小数点
browser varchar(32)	浏览器名，例如Chrome
browser_version varchar(32)	浏览器版本，例如Chrome 45
sdk varchar(32)	SDK类型，例如android，ios，java，javascript等
sdk_version	varchar(32)	SDK版本
manufacturer varchar(32)	设备制造商，例如 Apple
model	varchar(32)	设备型号，例如 iphone6
project	varchar(32)	这条数据所属项目名，若不指定该参数，则需要使用该字段时取值 default，即默认项目。
user_agent	varchar(128)	浏览器User-Agent字符串，识别客户使用的操作系统及版本、CPU 类型、浏览器及版本、浏览器渲染引擎、浏览器语言、浏览器插件等。
channel	varchar(32)	渠道编码
gmt_time	timestamp(3)	时间，精确到毫秒
attributes  varchar	事件的具体属性，自定义属性，JSON格式，不能与系统预设字段同名


distinct_id	bigint	标识ID
device_id	varchar	设备ID
user_id	varchar	业务用户唯一标识
device_id_list	varchar	设备ID组
wx_openid	varchar	微信openid
wx_unionid	varchar	微信unionid，绑定开放平台才有
status	boolean	有效状态：【1：有效，0：无效】

{
    "eventCode":"register_register_button_ck",
    "deviceId":"CA0AA7D817A33D4E7CFF",
    "userId":"5882426",
    "country":"China",
    "province":"zhejiang",
    "city":"hangzhou",
    "appVersion":"100",
    "networkType":"4G",
    "wifiName":"wifi123",
    "screenWidth":1920,
    "screenHeight":1080,
    "os":"ios",
    "osVersion":"811",
    "browser":"Chrome",
    "browserVersion":"Chrome 45",
    "sdk":"ios",
    "sdkVersion":"811",
    "manufacturer":"Apple",
    "model":"iphone8",
    "project":"test",
    "channel":"test",
    "appSource":"huawei",
    "attributes":{
        "test2":"dfd",
        "test":"a123"
    },
    "longitude":"1230.90",
    "latitude":"123,09"
}
```

### 数据上传策略  
```Java
   REAL_TIME(0),           // 实时传输，用于收集配置信息
    NEXT_LAUNCH(-1),        // 下次启动时上传
    NEXT_15_MINUTER(15),    // 每15分钟上传一次
    NEXT_30_MINUTER(30),    // 每30分钟上传一次
    NEXT_CACHE(1),       // 使用本地缓存策略
    NEXT_KNOWN_MINUTER(-1); // 使用服务器下发的上传策略
```
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
 点击事件-页面事件
 Tracker.getInstance().track("事件名称", json);
 需要获取页面标题
  Tracker.getInstance().getTitle(this).track("事件名称", json);
 假如上面的公共参数还不够你项目的需求,没有关系实现：
  //设置动态公共属性
        Tracker.getInstance().registerDynamicSuperProperties(new SensorsDataDynamicSuperProperties() {
            @Override
            public JSONObject getDynamicSuperProperties() {
                try {
                    return new JSONObject().put("isLogin", "测试属性");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
```
