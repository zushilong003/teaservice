package com.lingtian.pmrApi.service.impl;


import java.util.HashMap;


public class DataPmrResult extends HashMap<String, Object> {

    /**
     * @param errorCode 500,//errorCode
     * @param errorMsg  '',//errorMsg
     * @param data      {}//返回数据
     */
    public DataPmrResult(int errorCode, String errorMsg, Object data) {
        super(4);
        this.put("code", errorCode);
        this.put("msg", errorMsg);
        this.put("info", data);
    }


    /**
     * 错误时返回
     *
     * @param errorCode
     * @param errorMsg
     */
    public DataPmrResult(int errorCode, String errorMsg) {
        super(4);
        this.put("code", errorCode);
        this.put("msg", errorMsg);
        this.put("info", null);
    }


    /**
     * 操作成功，有返回数据
     *
     * @param data
     */
    public DataPmrResult(Object data) {
        super(4);
        this.put("code", 200);
        this.put("msg", "");
        this.put("info", data);
    }

    /**
     * 操作成功，无返回数据
     */
    public DataPmrResult() {
        super(4);
        this.put("code", 200);
        this.put("msg", "");
        this.put("info", null);
    }


}
