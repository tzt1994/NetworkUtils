package com.tangzhentao.networkutils;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Description:
 *
 * @author tangzhentao
 * @date 2019/5/22
 */
public class TestResponse implements Serializable {
    @SerializedName("id")
    public String id;


    @SerializedName("province")
    public String province;

    @SerializedName("city")
    public String city;

    @SerializedName("district")
    public String district;

}
