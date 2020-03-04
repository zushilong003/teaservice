package com.lingtian.pmrApi.util;

public class NormalEnglish {
    public static String EnglishtoChinese(String englistSta) {
        if (englistSta.equals("STARTING")) {
            return "正在启动";
        } else if (englistSta.equals("RUNNING")) {
            return "正在运行";
        } else if (englistSta.equals("BOOTSTRAPPING")) {
            return "引导状态";
        } else if (englistSta.equals("WAITING")) {
            return "正在等待";
        } else if (englistSta.equals("TERMINATING")) {
            return "正在终止";
        } else if (englistSta.equals("TERMINATED")) {
            return "已终止";
        } else if (englistSta.equals("TERMINATED_WITH_ERRORS")) {
            return "因错误终止";
        }
        return null;


    }
}
