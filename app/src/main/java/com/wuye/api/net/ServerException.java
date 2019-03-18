package com.wuye.api.net;

/**
 * Description: 服务器异常
 * Copyright  : Copyright (c) 2017
 * Company    : 年糕妈妈
 * Author     : 段宇鹏
 * Date       : 4/11/17
 */
public class ServerException extends Exception {

    private int code;

    public ServerException(String message) {
        super(message);
    }

    public ServerException(int code, String desc){
        super(desc);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "ServerException{" +
                "code=" + code +
                '}';
    }
}
