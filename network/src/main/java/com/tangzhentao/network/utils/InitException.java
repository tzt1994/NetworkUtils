package com.tangzhentao.network.utils;


import android.support.annotation.NonNull;

/**
 * Description:
 *
 * @author tangzhentao
 * @date 2019/5/21
 */
public class InitException extends RuntimeException{
    public InitException(String message) {
        super(message);
    }

    public InitException(@NonNull Class clazz) {
        super(clazz.getName());
    }
}
