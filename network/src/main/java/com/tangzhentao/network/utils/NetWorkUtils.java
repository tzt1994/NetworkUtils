package com.tangzhentao.network.utils;

/**
 * Description:
 *
 * @author tangzhentao
 * @date 2019/5/21
 */
public class NetWorkUtils {
    public static final String RESULT_CODE = "resultcode";
    public static final String RESULT_MSG = "reason";
    public static final String DATA = "result";
    public static final int SUCCESS_CODE = 200;

    //异常情况说明
    public static final int REQUEST_FAIL_CODE = 360000001;//请求失败
    public static final int PARSE_FAIL_CODE = 360000002;//JSON解析失败


    public static final String HTTP_BASE = "http://v.juhe.cn/weather";

    public static final String HTTP_MODULE_RIVER = "/river";

    public static final String HTTP_MODULE_CITYS = "/citys";

}
