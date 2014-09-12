/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.baidu.cc.web.action;

import com.baidu.cc.configuration.bo.User;
import com.baidu.cc.configuration.service.UserService;
import com.baidu.cc.web.action.UserSaveActionBase.DataResult;
import com.baidu.cc.web.action.UserSaveActionBase.ReqParam;
import com.baidu.rigel.platform.util.Security;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

/**
 * 功能：保存用户信息.
 * 
 * @author BJF
 */
public class UserSaveAction extends UserSaveActionBase<ReqParam, DataResult> {

    /** serial Version UID. */
    private static final long serialVersionUID = 1L;

    /** UserService. */
    @Resource
    private UserService userService;

    // step1 validate parameters. could be ignore
    /**
     * <pre>
     * 参数验证。此处根据用户需求修改，平台工具不支持增量维护。
     * 备注：自定义验证不支持前后端一致性验证功能支持
     * </pre>
     * 
     * .
     * 
     * @param reqParam
     *            request param
     * @return req parameters valid result.
     */
    protected Map<String, String> doValidate(ReqParam reqParam) {
        return null;
    }

    // step2 customize parse parameters. could be ignore
    /**
     * 自定义参数解析。此处根据用户需求修改，平台工具不支持增量维护。.
     * 
     * @param reqParam
     *            request param
     * @return request parameters after user process.
     */
    protected ReqParam doParseParam(ReqParam reqParam) {
        return reqParam;
    }

    // step3 call service component
    /**
     * 服务执行方法.
     * 
     * @param reqParam
     *            request param
     * @return 返回结果对象
     */
    @Override
    protected DataResult doExecuteService(ReqParam reqParam) {
        User user = reqParam.getUser();
        Long userId = user.getId();
        Date now = new Date();
        user.setUpdateTime(now);
        user.setPassword(Security.MD5Encode(user.getPassword()));
        user.setApiPassword(Security.MD5Encode(user.getApiPassword()));

        if (userId == null) {
            user.setCreateTime(now);
            userService.saveEntitySelective(user);
        } else {
            userService.updateEntitySelective(user);
        }

        return null;
    }

    // step4 wrap result return to font page
    /**
     * 结果返回包装方法.
     * 
     * @param dr
     *            返回结果对象
     * @return result after user process.
     */
    protected Object doWrapResult(DataResult dr) {
        return dr;
    }

}
