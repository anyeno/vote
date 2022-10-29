package com.vote.backend.Filter;

import com.vote.backend.Common.Utils.JwtUtil;
import com.vote.backend.Common.Utils.RedisCache;
import com.vote.backend.Model.LoginUser;
import io.jsonwebtoken.Claims;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;


//注入后配置才能生效
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

   @Autowired
    JwtUtil jwtUtil;
   @Autowired
    RedisCache redisCache;

    /**配置拦截逻辑*/
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//       获取token
        String token = request.getHeader("token");
        //string非空判断
        if (!StringUtils.hasText(token)) {
            //拦截器结尾配置方行
            /**注意区分：报错-直接抛出
             *         放行-交由其他拦截器处理*/
            filterChain.doFilter(request, response);
            //交还资源时返回
            return;
        }
        //解析token
        String id;
        try {
            id = jwtUtil.getId(token);
        } catch (Exception e) {
            //打印-抛出错误交给框架处理
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }

        //从redis获取用户信息
        String redisKey = "login:" + id;
        LoginUser loginUser=redisCache.getCacheObject(redisKey);
        if(Objects.isNull(loginUser)){
            throw new RuntimeException("用户未登录");
        }
        /**
         * 放入SecurityContent
         * 供后续filter读取放行
         * */
        //设置权限为已认证状态
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(
            loginUser,null,loginUser.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        /**验证结束放行*/
        filterChain.doFilter(request,response);
    }
}
