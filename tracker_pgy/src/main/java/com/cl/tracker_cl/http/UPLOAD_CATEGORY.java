package com.cl.tracker_cl.http;


/**
 * 数据上传策略
 */
public enum UPLOAD_CATEGORY {

    REAL_TIME(0),           // 实时传输，用于收集配置信息
    NEXT_LAUNCH(-1),        // 下次启动时上传
    TIME_MINUTER(2),    // 按分钟上传
    NEXT_CACHE(1),       // 使用本地缓存策略
    NEXT_KNOWN_MINUTER(-1); // 使用服务器下发的上传策略


    private int value;

    UPLOAD_CATEGORY(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static UPLOAD_CATEGORY getCategory(int value) {
        UPLOAD_CATEGORY category;
        if (value == REAL_TIME.value) {
            category = REAL_TIME;
        } else if (value == NEXT_LAUNCH.value) {
            category = NEXT_LAUNCH;
        } else if (value == TIME_MINUTER.value) {
            category = TIME_MINUTER;
        } else if (value == NEXT_CACHE.value) {
            category = NEXT_CACHE;
        } else {
            NEXT_KNOWN_MINUTER.value = value;
            category = NEXT_KNOWN_MINUTER;
        }

        return category;
    }
}
