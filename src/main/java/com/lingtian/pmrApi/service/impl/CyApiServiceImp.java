package com.lingtian.pmrApi.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.util.StringUtil;
import com.lingtian.pmrApi.Entity.UserEntity;
import com.lingtian.pmrApi.config.Constant;
import com.lingtian.pmrApi.model.dao.UserEntityMapper;
import com.lingtian.pmrApi.model.dto.TeaUserListDto;
import com.lingtian.pmrApi.service.CyApiService;
import com.lingtian.pmrApi.util.AwsEmrHttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class CyApiServiceImp implements CyApiService {

    private static final String CY_URL = "https://www.5169168.com/";

    @Autowired
    UserEntityMapper userEntityMapper;


    @Override
    public DataPmrResult login(String username, String password, String captchaId, String captcha) {
        String captchaUrl = CY_URL + "api/front/login/post";
        Map<String, String> params = new HashMap<>();
        params.put("uID", username);
        params.put("uPwd", password);
        params.put("CaptchaId", captchaId);//验证码编号
        params.put("Captcha", captcha);//验证码数字

        String response = AwsEmrHttpUtil.postForm(captchaUrl, params, null);
        log.info(response);
        String errorMsg = "其他错误!";
        Map<String, Object> resMap = JSON.parseObject(response, Map.class);
        if (resMap != null) {
            Boolean flag = (Boolean) resMap.get("ok");
            if (flag == true) {
                return new DataPmrResult(resMap);
            } else if (null != resMap.get("errmsg")) {
                errorMsg = (String) resMap.get("errmsg");
            }
        }
        return new DataPmrResult(Constant.ERROR_OTHER, errorMsg);
    }

    @Override
    public DataPmrResult queryUserList() {
        List<UserEntity> userList = userEntityMapper.selectUserList();
        if (userList == null) {
            userList = new ArrayList<>();
        }
        List<TeaUserListDto> resUserList = new ArrayList<>();
        TeaUserListDto userDto = null;
        for (UserEntity user : userList) {
            userDto = new TeaUserListDto();
            userDto.setCaptcha("");
            userDto.setCaptchaUrl("");
            userDto.setCaptchaId("");
            BeanUtils.copyProperties(user, userDto);
            resUserList.add(userDto);
        }
        return new DataPmrResult(resUserList);
    }

    @Override
    public Map<String, Object> getCaptCha() {
        //1:获取验证码
        // https://www.5169168.com/api/captcha/req?reload=1581224754537&_rnd=0.3362548074403884
        String captcha = CY_URL + "api/captcha/req";
        Map<String, Object> params = new HashMap<>();
        params.put("reload", System.currentTimeMillis());
        params.put("_rnd", "0.3362548074403884");
        Map<String, String> headers = new HashMap<>();
        // headers.put("Cookie", AwsEmrHttpUtil.LOGIN_COOKIE);
        String response = AwsEmrHttpUtil.get(captcha, params, null);
        log.info(response);
        Map<String, Object> resMap = JSON.parseObject(response, Map.class);
        // readImgNum((String) resMap.get("CaptchaId"), (String) resMap.get("CaptchaURI"));
        return resMap;
    }

    /**
     * @param CaptchaId  验证码编号
     * @param CaptchaURI 验证码地址
     * @return
     */
    public Map<String, Object> readImgNum(String CaptchaId, String CaptchaURI) {
        //1:获取验证码
        // https://www.5169168.com/api/captcha/req?reload=1581224754537&_rnd=0.3362548074403884
        String captcha = CY_URL + CaptchaURI;
        Map<String, Object> params = new HashMap<>();
        params.put("CaptchaId", CaptchaId);
        log.info("captcha-url=" + captcha);
        Map<String, String> headers = new HashMap<>();
        //  headers.put("Cookie", AwsEmrHttpUtil.LOGIN_COOKIE);
        String response = AwsEmrHttpUtil.get(captcha, params, null);
        //  log.info(response);
        Map<String, Object> resMap = new HashMap<>();
        return resMap;
    }

    /**
     * 查询用户关系详情
     *
     * @param userName 用户名
     * @return
     */
    public Map<String, Object> queryUserRelationDetail(String userName) {
        //1:获取验证码
        // https://www.5169168.com/api/captcha/req?reload=1581224754537&_rnd=0.3362548074403884
        String captcha = CY_URL + "api/front/coupon/detail";
        Map<String, Object> params = new HashMap<>();
        params.put("couponid", userName);
        params.put("_rnd", 0.13895847933872088);
        log.info("queryUserRelationDetail-url=" + captcha);
        Map<String, String> headers = new HashMap<>();
        headers.put("Cookie", AwsEmrHttpUtil.LOGIN_COOKIE.get(userName));
        log.info(AwsEmrHttpUtil.LOGIN_COOKIE.get(userName));
        //  headers.put("Cookie", "sec_tc=AQAAAAoqw1KIeAMAewX7W6depuiGOw+K; wetalk_sess=21aa36f279d84eb6e6de04c9415388e9;lang=en-US; _xsrf=S09OYmxjNkFlRDNaeG1kVUxBeDhhYWg2Sm1lQkxrb3E=|1581424140272235024|96f73729493a019cfd3db029e53c7ab20d76cee3");
        String response = AwsEmrHttpUtil.get(captcha, params, headers);
        log.info(response);
        Map<String, Object> resMap = JSON.parseObject(response, Map.class);

        return resMap;
    }

    public static void main(String args[]) {
        //获取验证码
        //Map<String, Object> map= getCaptCha();
        CyApiServiceImp service = new CyApiServiceImp();
        service.login("zuqi9a", "z123456", "Gzg3HdYsF05kfxa", "8202");
        service.queryUserRelationDetail("zuqi9a");

    }
}