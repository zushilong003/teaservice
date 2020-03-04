package com.lingtian.pmrApi.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class TeaUserListDto implements Serializable {
    private String code;

    private String userName;

    private String passWord;

    private String realname;

    private String roles;

    private String tbDesc;

    private String captchaId;

    private String captchaUrl;

    private String captcha;

    private String token;

    private String loginStatus = "未登录";

    private String updateTime;

}
