package com.vote.backend.Common.Result;

/**通过枚举提供固定的情况选项*/
public enum ResultCodeImp implements ResultCode{



    /**初始化函数私有而仅提供范例使用*/
    SUCCES(200,"操作成功"),//逗号连接
    FAILED(401,"操作失败"),
    User_FAILED(402,"用户验证未通过");



    private long code;
    private String message;

    private ResultCodeImp(long l,String s){
        this.code=l;this.message=s;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
