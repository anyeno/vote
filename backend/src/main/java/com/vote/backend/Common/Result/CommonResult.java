package com.vote.backend.Common.Result;

import com.fasterxml.jackson.annotation.JsonView;
import com.vote.backend.Model.User;
import lombok.Data;

/**泛型实现各种操作信息的接受*/
@Data
public class CommonResult<T> {
    /**格式控制*/

    public interface CommonResultView{};


    @JsonView(CommonResultView.class)
    private long code;
    @JsonView(CommonResultView.class)
    private String message;
    @JsonView(User.UserSimpleView.class)
    private T data;

    private CommonResult(long code,String message,T data){
        this.code=code;this.message=message;this.data=data;
    }

    /**仅公开成功，失败的返回构造*/
    public static <T> CommonResult<T> Success(T data){
        return new CommonResult<T>(ResultCodeImp.SUCCES.getCode()
                ,ResultCodeImp.SUCCES.getMessage(),data);
    }
    public static <T> CommonResult<T> Success(String s,T data){
        return new CommonResult<T>(ResultCodeImp.SUCCES.getCode()
                ,s,data);
    }


    public static <T> CommonResult<T> Failed(T data){
        return new CommonResult<T>(ResultCodeImp.FAILED.getCode()
                ,ResultCodeImp.FAILED.getMessage(),data);
    }
    public static <T> CommonResult<T> Failed(String s,T data){
        return new CommonResult<T>(ResultCodeImp.FAILED.getCode()
                ,s,data);
    }


    public static <T> CommonResult<T> Failed_User(T data){
        return new CommonResult<T>(ResultCodeImp.User_FAILED.getCode()
                ,ResultCodeImp.User_FAILED.getMessage(),data);
    }
    public static <T>  CommonResult<T> Failed_User(String s,T data){
        return new CommonResult<T>(ResultCodeImp.User_FAILED.getCode()
                ,s,data);
    }

}
