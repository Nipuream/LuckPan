package com.hr.nipuream.luckpan.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ScrollerCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import com.hr.nipuream.luckpan.R;
import com.hr.nipuream.luckpan.Util;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * 作者：Nipuream
 * 时间: 2016-08-16 10:18
 * 邮箱：571829491@qq.com
 */
public class RotatePan extends View {

    private Context context;

    private Paint dPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint sPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int InitAngle = 30;
    private int radius = 0;

    public static final int FLING_VELOCITY_DOWNSCALE = 4;

    private int[] images = new int[]{R.mipmap.huawei,R.mipmap.image_one,R.mipmap.iphone,R.mipmap.macbook,R.mipmap.meizu,R.mipmap.xiaomi};

    private String[] strs = {"华为手机","谢谢惠顾","iPhone 6s","mac book","魅族手机","小米手机"};

    private List<Bitmap> bitmaps = new ArrayList<>();

    private GestureDetectorCompat mDetector;
    protected ScrollerCompat scroller;


    public RotatePan(Context context) {
        this(context,null);
    }

    public RotatePan(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RotatePan(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        mDetector = new GestureDetectorCompat(context,new RotatePanGestureListener());
        scroller = ScrollerCompat.create(context);

        dPaint.setColor(Color.rgb(255,133,132));
        sPaint.setColor(Color.rgb(254,104,105));
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(Util.dip2px(context,16));
        setClickable(true);

        for(int i=0 ;i<6;i++){
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), images[i]);
            bitmaps.add(bitmap);
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //wrap_content value
        int mHeight = Util.dip2px(context, 300);
        int mWidth = Util.dip2px(context, 300);

        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if(widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(mWidth, mHeight);
        }else if(widthSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(mWidth, heightSpecSize);
        }else if(heightSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSpecSize, mHeight);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final int paddingLeft = getPaddingLeft();
        final int paddingRight = getPaddingRight();
        final int paddingTop = getPaddingTop();
        final int paddingBottom = getPaddingBottom();

        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;

        int MinValue = Math.min(width,height);

        radius = MinValue/2;

        RectF rectF = new RectF(getPaddingLeft(),getPaddingTop(),width,height);

        int angle = InitAngle - 30;

        for(int i= 0;i<6;i++){

            if(i%2 == 0){
                canvas.drawArc(rectF,angle,60,true,dPaint);
            }else
            {
                canvas.drawArc(rectF,angle,60,true,sPaint);
            }

            angle += 60;

        }

        for(int i=0;i<6;i++){
            drawIcon(width/2, height/2, radius, InitAngle, i, canvas);
            InitAngle += 60;
        }

        for(int i=0;i<6;i++){
            drawText(InitAngle+30,strs[i], 2*radius, textPaint, canvas,rectF);
            InitAngle += 60;
        }

    }


    private void drawText(float startAngle, String string,int mRadius,Paint mTextPaint,Canvas mCanvas,RectF mRange)
    {

        Path path = new Path();
        path.addArc(mRange, startAngle, 60);
        float textWidth = mTextPaint.measureText(string);

        float hOffset = (float) (mRadius * Math.PI / 6 / 2 - textWidth / 2);
        float vOffset = mRadius / 2 / 4;
        mCanvas.drawTextOnPath(string, path, hOffset, vOffset, mTextPaint);
    }



    private void drawIcon(int xx,int yy,int mRadius,float startAngle, int i,Canvas mCanvas)
    {
        int imgWidth = mRadius / 4;

//        float angle = (float) ((60 + startAngle) * (Math.PI / 180));
        float angle = (float) Util.change(60+startAngle);

        int x = (int) (xx + mRadius / 2 * Math.cos(angle));
        int y = (int) (yy + mRadius / 2  * Math.sin(angle));

        // 确定绘制图片的位置
        Rect rect = new Rect(x - imgWidth *3/ 4, y - imgWidth*3 / 4, x + imgWidth
                *3/ 4, y + imgWidth*3/4);

//        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), images[i]);
        Bitmap bitmap = bitmaps.get(i);


        Matrix matrix = new Matrix();
        matrix.postRotate(startAngle+180);

        Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);

//        bitmap.recycle();

        if(!bm.isRecycled())
            mCanvas.drawBitmap(bm, null, rect, null);
    }


//    public void setImages(List<Bitmap> bitmaps){
//        this.bitmaps = bitmaps;
//        this.invalidate();
//    }

    public void setStr(String... strs){
        this.strs = strs;
        this.invalidate();
    }

    //圈数
    private int[] lapsSource = { 5, 7, 9, 15 };
    //角度
    private int[] anglesSource = { 0, 60, 120, 180, 240, 300 };

    private int startDegree = 0;

    //旋转一圈所需要的时间
    private static final long ONE_WHEEL_TIME = 500;

    //旋转动画
    private RotateAnimation rotateAnimation;


    public void startRotate(){
        int lap = lapsSource[(int) (Math.random() * 4)];
        int angle = anglesSource[(int) (Math.random() * 6)];

        int increaseDegree = lap * 360 + angle;
        rotateAnimation = new RotateAnimation(
                startDegree, startDegree + increaseDegree,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        startDegree += increaseDegree;
        long time = (lap + angle / 360) * ONE_WHEEL_TIME;

        rotateAnimation.setDuration(time);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        rotateAnimation.setAnimationListener(al);
        startAnimation(rotateAnimation);
    }

    private Animation.AnimationListener al = new Animation.AnimationListener() {

        @Override
        public void onAnimationStart(Animation animation) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            int pos = startDegree % 360 / 60;

            if(pos >= 0 && pos <= 3){
                pos = 3 - pos;
            }else{
                pos = (6-pos) + 3;
            }

            if(l != null)
                l.endAnimation(pos);
        }
    };

    public interface AnimationEndListener{
        void endAnimation(int position);
    }

    private AnimationEndListener l;

    public void setAnimationEndListener(AnimationEndListener l){
        this.l = l;
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(rotateAnimation != null)
            rotateAnimation.cancel();
    }


//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//
//        boolean consume = mDetector.onTouchEvent(event);
//        if(consume)
//        {
//            getParent().requestDisallowInterceptTouchEvent(true);
//            return true;
//        }
//
//        return super.onTouchEvent(event);
//    }


    public void setRotate(int rotation){
        rotation = (rotation % 360 + 360) % 360;
        InitAngle = rotation;
        ViewCompat.postInvalidateOnAnimation(this);
    }


    @Override
    public void computeScroll() {

        if(scroller.computeScrollOffset()){
            setRotate(scroller.getCurrY());
        }

        super.computeScroll();
    }

    private class RotatePanGestureListener extends GestureDetector.SimpleOnGestureListener{

        @Override
        public boolean onDown(MotionEvent e) {
            return super.onDown(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float centerX = (RotatePan.this.getLeft() + RotatePan.this.getRight())*0.5f;
            float centerY = (RotatePan.this.getTop() + RotatePan.this.getBottom())*0.5f;


            float scrollTheta = vectorToScalarScroll(distanceX, distanceY, e2.getX() - centerX, e2.getY() -
                    centerY);
            int rotate = InitAngle -
                    (int) scrollTheta / FLING_VELOCITY_DOWNSCALE;

            setRotate(rotate);
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float centerX = (RotatePan.this.getLeft() + RotatePan.this.getRight())*0.5f;
            float centerY = (RotatePan.this.getTop() + RotatePan.this.getBottom())*0.5f;

            float scrollTheta = vectorToScalarScroll(velocityX, velocityY, e2.getX() - centerX, e2.getY() -
                    centerY);
            scroller.abortAnimation();
            scroller.fling(0, InitAngle , 0, (int) scrollTheta / FLING_VELOCITY_DOWNSCALE,
                    0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
            return true;
        }

    }

    private float vectorToScalarScroll(float dx, float dy, float x, float y) {
        // get the length of the vector
        float l = (float) Math.sqrt(dx * dx + dy * dy);

        // decide if the scalar should be negative or positive by finding
        // the dot product of the vector perpendicular to (x,y).
        float crossX = -y;
        float crossY = x;

        float dot = (crossX * dx + crossY * dy);
        float sign = Math.signum(dot);

        return l * sign;
    }



}
