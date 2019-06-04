package com.tangzhentao.libbase.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tangzhentao.libbase.R;
import com.tangzhentao.libbase.utils.DimenSizeUtils;
import com.tangzhentao.libbase.utils.ResUtils;

import java.util.List;

/**
 * Description:
 *
 * @author tangzhentao
 * @date 2019/5/27
 */
public class BaseTitle extends RelativeLayout implements View.OnClickListener{

    private int mHeight;
    private LinearLayout mLeftLayout, mRightLayout;
    private RelativeLayout mCenterLayout;
    private TextView mTitle;

    public BaseTitle(Context context) {
        super(context);
        initView();
    }

    public BaseTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        mLeftLayout = new LinearLayout(getContext());
        mLeftLayout.setOrientation(LinearLayout.HORIZONTAL);
        mLeftLayout.setGravity(Gravity.CENTER_VERTICAL);
        RelativeLayout.LayoutParams leftParams = new RelativeLayout.LayoutParams(-2, -1);
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        leftParams.leftMargin = DimenSizeUtils.dpTopx(15);
        this.addView(mLeftLayout, leftParams);

        //初始化标题文字控件，文字大小颜色
        mTitle = new TextView(getContext());
        mTitle.setId(R.id.center_title_tv);
        mTitle.setTextColor(Color.WHITE);
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        mTitle.setSingleLine(true);
        mTitle.setEllipsize(TextUtils.TruncateAt.END);
        mTitle.setGravity(Gravity.CENTER_VERTICAL);
        mTitle.setText("标题");
        RelativeLayout.LayoutParams centerParams = new RelativeLayout.LayoutParams(-2, -1);
        centerParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);
        this.addView(mTitle, centerParams);

        mRightLayout = new LinearLayout(getContext());
        mRightLayout.setOrientation(LinearLayout.HORIZONTAL);
        mRightLayout.setGravity(Gravity.CENTER_VERTICAL);
        RelativeLayout.LayoutParams rightParams = new RelativeLayout.LayoutParams(-2, -1);
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
        rightParams.rightMargin = DimenSizeUtils.dpTopx(15);
        this.addView(mRightLayout, rightParams);
    }

    @Override
    public void removeAllViews() {
        mLeftLayout.removeAllViews();
        mRightLayout.removeAllViews();
    }

    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        if (tag instanceof BaseAction) {
            BaseAction action = (BaseAction) tag;
            action.doClick(v);
        }
    }

    /**
     * 传入高度
     * @param height 高度
     */
    public void setHeight(int height) {
        this.mHeight = height;
    }

    /**
     * 设置标题
     * @param title 标题
     */
    public void setCenterTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            return;
        }
        mTitle.setText(title);
    }

    /**
     *添加左边布局
     * @param action 左边view
     */
    public void addLeftBaseAction(BaseAction action) {
        if (action == null) {
            return;
        }

        View actionView = action.getView();
        if (action.getDrawable() > 0 && actionView == null ) {
            ImageView ivLeft = new ImageView(getContext());
            ivLeft.setImageResource(action.getDrawable());
            ivLeft.setScaleType(ImageView.ScaleType.FIT_XY);
            LinearLayout.LayoutParams ivParams = new LinearLayout.LayoutParams(mHeight, mHeight);
            ivLeft.setLayoutParams(ivParams);
            int p = DimenSizeUtils.dpTopx(10);
            ivLeft.setPadding(p,p,p,p);
            actionView = ivLeft;
        }

        actionView.setTag(action);
        actionView.setOnClickListener(this);
        mLeftLayout.addView(actionView);
    }

    /**
     * 添加右边布局
     * @param actions 右边view的list
     */
    public void addRightBaseAction(List<BaseAction> actions) {
        if (actions == null) {
            return;
        }

        for (BaseAction action : actions) {
            View actionView = action.getView();
            if (action.getDrawable() > 0 && actionView == null ) {
                ImageView ivLeft = new ImageView(getContext());
                ivLeft.setImageResource(action.getDrawable());
                ivLeft.setScaleType(ImageView.ScaleType.FIT_XY);
                LinearLayout.LayoutParams ivParams = new LinearLayout.LayoutParams(mHeight, mHeight);
                ivLeft.setLayoutParams(ivParams);
                int p = DimenSizeUtils.dpTopx(10);
                ivLeft.setPadding(p,p,p,p);
                actionView = ivLeft;
            }

            if (actions.indexOf(action) > 0) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) actionView.getLayoutParams();
                params.leftMargin = DimenSizeUtils.dpTopx(10);
            }

            actionView.setTag(action);
            actionView.setOnClickListener(this);
            mRightLayout.addView(actionView);
        }
    }


    public static abstract class BaseAction {
        public int getDrawable() { return 0; }
        public View getView() { return null; }
        public abstract void doClick(View v);
    }


    /**
     * 创建通用的左边返回按钮
     * @return BaseAction
     */
    public static BaseAction createLeftAction(final Activity activity) {
        return new BaseAction() {
            @Override
            public int getDrawable() {
                return R.drawable.left_back;
            }

            @Override
            public void doClick(View v) {
                if (activity != null) {
                    activity.finish();
                }
            }
        };
    }

    /**
     * 创建通用的右边文字
     * @param activity 当前activity
     * @param text 文字
     * @param intent 跳转逻辑
     * @return BaseAction
     */
    public static BaseAction createRightTextAction(final Activity activity, final String text, final Intent intent) {
        return new BaseAction() {
            @Override
            public View getView() {
                TextView view = new TextView(activity);
                view.setGravity(Gravity.CENTER_VERTICAL);
                view.setText(text);
                view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                view.setTextColor(ResUtils.getColor(R.color.white));

                return view;
            }

            @Override
            public void doClick(View v) {
                if (activity != null && intent !=null) {
                    activity.startActivity(intent);
                }
            }
        };
    }
}
