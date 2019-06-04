package com.tangzhentao.libbase.base;

import android.graphics.Color;

import com.tangzhentao.libbase.utils.DimenSizeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:标题栏参数类
 *
 * @author tangzhentao
 * @date 2019/5/28
 */
public class TitleParams {
    //标题栏背景颜色
    public int bgColor = Color.parseColor("#1874cd");
    //标题栏高度
    public int titleHeight = DimenSizeUtils.dpTopx(48);
    //标题栏左边的内容，可拓展为List<BaseTitle.BaseAction>
    public BaseTitle.BaseAction leftAction;
    //标题栏标题
    public String title;
    //鼻涕蓝右边内容 可添加多个
    public List<BaseTitle.BaseAction> rightActions;

    public TitleParams(BaseTitle.BaseAction leftAc, String title) {
        this.leftAction = leftAc;
        this.title = title;
    }

    public TitleParams(BaseTitle.BaseAction leftAc, String title, BaseTitle.BaseAction rightAc) {
        this.leftAction = leftAc;
        this.title = title;
        List<BaseTitle.BaseAction> rights = new ArrayList<>(1);
        rights.add(rightAc);
        this.rightActions = rights;
    }

    public TitleParams(BaseTitle.BaseAction leftAc, String title, List<BaseTitle.BaseAction> rights) {
        this.leftAction = leftAc;
        this.title = title;
        this.rightActions = rights;
    }
}
