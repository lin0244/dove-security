package com.licc.dove.web.util;

/**
* @author lichangchao
* @Description:
* @date  2017/12/27
*/
public class ResultUtil {
    public static com.licc.dove.web.exception.ResponseVo success(Integer code, String desc,Object data) {
        com.licc.dove.web.exception.ResponseVo<Object> responseVo = com.licc.dove.web.exception.ResponseVo
            .BUILDER();
        responseVo.setCode(code);
        responseVo.setDesc(desc);
        responseVo.setData(data);
        return responseVo;
    }
    public static com.licc.dove.web.exception.ResponseVo error(String code, String desc,Object data) {
        com.licc.dove.web.exception.ResponseVo<Object> responseVo = com.licc.dove.web.exception.ResponseVo
            .BUILDER();
        responseVo.setCode(null);
        responseVo.setErrorCode(code);
        responseVo.setDesc(desc);
        responseVo.setData(data);
        return responseVo;
    }

}

