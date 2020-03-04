package com.lingtian.pmrApi.service;

import com.lingtian.pmrApi.service.impl.DataPmrResult;

import java.util.Map;

public interface CyApiService {
    /**
     * @param username
     * @param password
     * @param captchaId 驗證碼編號
     * @param captcha   驗證碼
     * @return
     */
    public DataPmrResult login(String username, String password, String captchaId, String captcha);


    /**
     * 查询用户列表
     *
     * @return
     */
    public DataPmrResult queryUserList();

    /**
     * 获取并识别验证码
     *
     * @return
     */
    public Map<String, Object> getCaptCha();

}
