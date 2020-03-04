package com.example.demo.utils;

import com.example.demo.bean.RespVO;

public class RespUtil {

    public static RespVO success(Object data) {
        RespVO respVO = new RespVO();
        respVO.setStatus(0);
        respVO.setData(data);
        respVO.setMessage("操作成功");
        return respVO;
    }

    public static RespVO fail(int status, String message) {
        RespVO respVO = new RespVO();
        respVO.setStatus(status);
        respVO.setMessage(message);
        return respVO;
    }
}
