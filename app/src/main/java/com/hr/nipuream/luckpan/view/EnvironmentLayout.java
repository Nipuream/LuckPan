package com.hr.nipuream.luckpan.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hr.nipuream.luckpan.R;
import com.hr.nipuream.luckpan.Util;

/**
 * 描述：
 * 作者：Nipuream
 * 时间: 2016-08-15 15:33
 * 邮箱：571829491@qq.com
 */
public class EnvironmentLayout extends RelativeLayout implements View.OnClickListener{

    private Context context;
    private Paint panPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int width , height;

    public EnvironmentLayout(Context context) {
        this(context,null);
    }

    public EnvironmentLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public EnvironmentLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        setBackgroundResource(R.mipmap.background);
        setPadding(0,0,0,0);
    }

    private void createLayerOne(){
        ImageView imageView = new ImageView(context);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        imageView.setImageResource(R.mipmap.shan);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setId(R.id.shan_id);
        lp.addRule(ALIGN_PARENT_BOTTOM);
        addView(imageView,lp);
    }

    private void createLayerTwo(){
        ImageView imageView = new ImageView(context);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        imageView.setImageResource(R.mipmap.yun);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setId(R.id.yun_id);
        lp.addRule(ABOVE,R.id.shan_id);
        imageView.setBackgroundColor(Color.RED);
        addView(imageView,lp);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        final int paddingLeft = getPaddingLeft();
        final int paddingRight = getPaddingRight();
        final int paddingTop = getPaddingTop();
        final int paddingBottom = getPaddingBottom();

        width = getWidth() - paddingLeft - paddingRight;
        height = getHeight() - paddingTop - paddingBottom;

        createLayerOne();
        createLayerTwo();

        createLuckPanLayout(width,height);
        luckPanLayout.startLuckLight();

        createRotatePan(width,height);
//        createNode();
    }

    private LuckPanLayout luckPanLayout;

    private void createLuckPanLayout(int width,int height){
        int MinValue = Math.min(width,height);
        MinValue -= Util.dip2px(context,10)*2;
        luckPanLayout = new LuckPanLayout(context);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(MinValue,MinValue);
        lp.addRule(CENTER_IN_PARENT);
        addView(luckPanLayout,lp);
    }

    private RotatePan rotatePan;

    private void createRotatePan(int width,int height){
        int MinValue = Math.min(width,height);
        MinValue -= Util.dip2px(context,28)*2;
        rotatePan = new RotatePan(context);
        rotatePan.setId(R.id.rotate_pan);
        rotatePan.setClickable(true);
        rotatePan.setFocusable(true);
        rotatePan.requestFocus();
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(MinValue,MinValue);
        lp.addRule(CENTER_IN_PARENT);
        addView(rotatePan,lp);
    }

    private ImageView node;

    private void createNode(){
        node = new ImageView(context);
        node.setId(R.id.node_id);
        node.setOnClickListener(this);
        node.setImageResource(R.mipmap.node);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(CENTER_IN_PARENT);
        addView(node,lp);
    }


    public void startLuck(){
        if(rotatePan != null)
            rotatePan.startRotate(-1);
    }



    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.node_id){
            startLuck();
        }
    }





}
