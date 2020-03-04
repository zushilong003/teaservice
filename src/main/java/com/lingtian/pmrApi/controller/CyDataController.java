package com.lingtian.pmrApi.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.util.StringUtil;
import com.lingtian.pmrApi.config.Constant;
import com.lingtian.pmrApi.model.dto.TeaUserListDto;
import com.lingtian.pmrApi.service.CyApiService;
import com.lingtian.pmrApi.service.impl.DataPmrResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("pmEmr/cy")
public class CyDataController {
    @Autowired
    CyApiService cyApiService;

    /**
     * 登录
     *
     * @param body
     * @param response
     * @return
     */
    @PostMapping("/cyLogin")
    @ResponseBody
    public DataPmrResult cyLogin(@RequestBody TeaUserListDto body, HttpServletResponse response) {
        log.info("CyDataController-cyLogin" + JSON.toJSONString(body));
        if (body == null) {
            return new DataPmrResult(Constant.ERROR_OTHER, "传参不能为空");
        }
        if (StringUtil.isEmpty(body.getUserName()) || StringUtil.isEmpty(body.getPassWord()) || StringUtil.isEmpty(body.getCaptchaId()) || StringUtil.isEmpty(body.getCaptcha())) {
            return new DataPmrResult(Constant.ERROR_OTHER, "登录参数不能为空");
        }
        DataPmrResult result = null;
        try {
            result= cyApiService.login(body.getUserName(), body.getPassWord(), body.getCaptchaId(), body.getCaptcha());
        } catch (Exception e) {
            log.error("cyLogin-fail", e);
            result = new DataPmrResult(Constant.ERROR_OTHER, e.getMessage());
        }
        return result;
    }

    /**
     * 查询验证码
     *
     * @param response
     * @return
     */
    @PostMapping("/getCaptCha")
    @ResponseBody
    public DataPmrResult getCaptCha(HttpServletResponse response) {
        log.info("getCaptCha-begin");
        DataPmrResult result = null;
        try {
            Map<String, Object> resMap = cyApiService.getCaptCha();
            result = new DataPmrResult(resMap);
        } catch (Exception e) {
            log.error("getCaptCha-fail", e);
            result = new DataPmrResult(Constant.ERROR_OTHER, e.getMessage());
        }
        return result;
    }

    /**
     * 查询用户列表
     *
     * @param response
     * @return
     */
    @PostMapping("/queryUserList")
    @ResponseBody
    public DataPmrResult queryUserList(HttpServletResponse response) {
        log.info("queryUserList-begin");
        DataPmrResult result = null;
        try {
            result = cyApiService.queryUserList();
        } catch (Exception e) {
            log.error("queryUserList-fail", e);
            result = new DataPmrResult(Constant.ERROR_OTHER, e.getMessage());
        }
        return result;
    }
}
