package com.vote.backend.Common.Utils;

import com.vote.backend.Model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")//手动注入
    private  String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     *生成token
     * 涉及secret方法的部分不能对外暴露
     */
    private String generateToken(Map<String,Object>claims){
        return Jwts.builder()
                //头部
                .setHeaderParam("typ","JWT")

                //载荷
                .setClaims(claims)//jwt挂载
                .setIssuedAt(new Date())//签发时间
//                .setExpiration(new Date(System.currentTimeMillis()+expiration))//过期时间
                .setNotBefore(new Date())//生效日期

                //签名
                .signWith(SignatureAlgorithm.HS512,secret)//加密方法，密匙
                .compact();//压缩加密
    }

    public String generatToken(String id){
        Map<String,Object>claims=new HashMap<>();
        /**仅添加能够证明身份和权限的信息*/
//        claims.put("username",user.getName());
        claims.put("id",id);
//        claims.put("isadmin",user.isAdmin());
        return generateToken(claims);
    }

    /**
     *解析token
     *  注意必须要考虑验证不通过的情况
     *  将异常交给上层处理
     */
    public Claims parseToken(String token)throws Exception{
        Claims claims=null;
            claims=Jwts.parser()
                    .setSigningKey(secret)//输入密匙
                    .parseClaimsJws(token)//输入token
                    .getBody();

        return claims;
    }

    public String getUsrname(String token)throws Exception{
        String username;
        try {
            Claims claims = parseToken(token);//认证错误则会中途抛出
            username=(String) claims.get("username");//强制转型即可
        }
        catch (Exception e){
            username=null;
        }
        return username;
    }
    public Boolean getIsAdmin(String token)throws Exception{
        Boolean isadmin;
        try {
            Claims claims=parseToken(token);
            isadmin= (Boolean) claims.get("isadmin");//强制转型即可
        }
        catch (Exception e) {
            isadmin = null;
        }

        return isadmin;
    }
    public String getId(String token) throws Exception{
       String id;
       try {
           Claims claims=parseToken(token);
           id= claims.get("id").toString();//强制转型即可
       }
        catch (Exception e){
           id = null;
        }
        return id;
    }




}
