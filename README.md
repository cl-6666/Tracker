# sdk介绍
### 简单介绍  
在这里非常感谢神策平台给我带来了灵感编写了这样一个sdk,当然有很多需要完善的部分,我会继续维护下去直到完美，有问题欢迎留言.

### 埋点库里内部自动封装了一些公共参数  
![公共参数2](http://47.96.120.196/img_point1.png)  
![公共参数2](http://47.96.120.196/img_point2.png)  
![公共参数3](http://47.96.120.196/img_point3.png)

```Java
服务器请求数据格式：
这里只做演示
{
    "pointList":[
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
            "latitude":"123,09",
            "timeMillis":"1561635433687"
        }
    ]
}
```

### 项目引用  
```Java
compile 'com.cl:tracker-sdk:1.0.2'

需要全埋点请添加
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.com.trackerBuriedSdk.gradlePlugin:plugin:1.0.0"
  }
}
在你的build.gradle引用
apply plugin: "com.cl.android"

关闭埋点开关在gradle.properties里面声明
sensorsAnalytics.disablePlugin=true
```

### 数据上传策略  
```Java
  REAL_TIME(0),           // 实时传输，用于收集配置信息
  NEXT_LAUNCH(-1),        // 下次启动时上传
  TIME_MINUTER(2),    // 按分钟上传
  NEXT_CACHE(1),       // 使用本地缓存策略
  NEXT_KNOWN_MINUTER(-1); // 使用服务器下发的上传策略
```
### 初始化操作 
```Java
   TrackConfiguration configuration = new TrackConfiguration()
                // 开启log
                .openLog(true)
                // 设置日志的上传策略
                .setUploadCategory(UPLOAD_CATEGORY.REAL_TIME.getValue())
                // 设置埋点信息上传的URL
                .setServerUrl(SA_SERVER_URL)
                //本地缓存的最大事件数目，当累积日志量达到阈值时发送数据，默认值 10
                .setFlushBulkSize(10)
                //设置本地缓存最多事件条数，默认为 10000 条
                .setMaxCacheSize(10000)
                //设置多少分钟上传一次默认5分钟最大60分钟
                .setMinutes(20)
                .initializeDb(this);
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

### 可能遇到的问题  
```Java
如果说报了如下的错
java.io.IOException: Cleartext HTTP traffic to t1pvuv.lechuangtec.com not permitted
为保证用户数据和设备的安全，Google针对下一代 Android 系统(Android P) 的应用程序，将要求默认使用加密连接，这意味着 Android P 将禁止 App 使用所有未加密的连接，因此运行 Android P 系统的安卓设备无论是接收或者发送流量，未来都不能明码传输，需要使用下一代(Transport Layer Security)传输层安全协议。
也就是说，如果我们的应用是在Android 9或更高版本为目标平台，则默认情况下，是不支持HTTP明文请求的。
解决方法有很多举例其一
<?xml version="1.0" encoding="utf-8"?>
<manifest ...>
    <uses-permission android:name="android.permission.INTERNET" />
    <application
        ...
        android:usesCleartextTraffic="true"
        ...>
        ...
    </application>
</manifest>

```

### 博客地址  
https://blog.csdn.net/a214024475/article/details/94436562

