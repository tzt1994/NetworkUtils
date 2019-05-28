package com.tangzhentao.libbase.base;

import android.graphics.Color;

import com.tangzhentao.libbase.utils.DimenSizeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author tangzhentao
 * @date 2019/5/28
 */
public class TitleParams {
    public int BgColor = Color.TRANSPARENT;
    public int statusBarTextColor = Color.GRAY;
    public int Height = DimenSizeUtils.dpTopx(48);
    public BaseTitle.BaseAction leftAction;
    public String title;
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
