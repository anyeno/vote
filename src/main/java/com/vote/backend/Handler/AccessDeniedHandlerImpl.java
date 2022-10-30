package com.vote.backend.Handler;

import com.alibaba.fastjson.JSON;
import com.vote.backend.Common.Result.CommonResult;
import com.vote.backend.Common.Utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**替换权限不足forbidden的默认设置*/
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Autowired
    WebUtils webUtils;
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        //封装错误信息-转为json字符串
        CommonResult commonResult=new CommonResult(
                //Spring自带的错误码
                HttpStatus.FORBIDDEN.value(),
                "用户权限不足",
                null
        );

        String res= JSON.toJSONString(commonResult);
        webUtils.renderString(httpServletResponse,res);
    }
}
