//package com.vote.backend.Handler;
//
//
//import com.alibaba.fastjson.JSON;
//import com.vote.backend.Common.Result.CommonResult;
//import com.vote.backend.Common.Utils.WebUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
///**Handler 对应处理方法--由filter在对应情况出现时调用
// * 在登录验证时使用--替换自动跳出forbidden的默认处理
// * */
//@Component
//public class AuthenticationEntryPointImp implements AuthenticationEntryPoint {
//    @Autowired
//    WebUtils webUtils;
//
//    @Override
//    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
//        //封装错误信息-转为json字符串
//        CommonResult commonResult=new CommonResult(
//                //Spring自带的错误码
//                HttpStatus.UNAUTHORIZED.value(),
//                "用户认证失败请登录",
//                null
//        );
//
//        String res= JSON.toJSONString(commonResult);
//        webUtils.renderString(httpServletResponse,res);
//
//    }
//}
