package com.hr.nipuream.luckpan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.hr.nipuream.luckpan.view.LuckPanLayout;
import com.hr.nipuream.luckpan.view.RotatePan;

public class MainActivity extends AppCompatActivity implements RotatePan.AnimationEndListener{

    private RotatePan rotatePan;
    private LuckPanLayout luckPanLayout;
    private ImageView goBtn;
    private ImageView yunIv;
    private String[] strs ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        strs = getResources().getStringArray(R.array.names);
        luckPanLayout = (LuckPanLayout) findViewById(R.id.luckpan_layout);
        luckPanLayout.startLuckLight();
        rotatePan = (RotatePan) findViewById(R.id.rotatePan);
        rotatePan.setAnimationEndListener(this);
        goBtn = (ImageView)findViewById(R.id.go);
        yunIv = (ImageView)findViewById(R.id.yun);
        luckPanLayout.post(new ReLayoutRunnable());
    }

    public void rotation(View view){
        rotatePan.startRotate(-1);
        luckPanLayout.setDelayTime(100);
        goBtn.setEnabled(false);
    }

    @Override
    public void endAnimation(int position) {
        goBtn.setEnabled(true);
        luckPanLayout.setDelayTime(500);
        Toast.makeText(this,"Position = "+position+","+strs[position],Toast.LENGTH_SHORT).show();
    }

    /**
     * 根据屏幕大小重新布局
     * 也可以写到LuckPanLayout里面降低耦合度，这里就没有继续封装了
     */
    private class ReLayoutRunnable implements Runnable{

        @Override
        public void run() {
            int height =  getWindow().getDecorView().getHeight();
            int width = getWindow().getDecorView().getWidth();

            int backHeight = 0;

            int MinValue = Math.min(width,height);
            MinValue -= Util.dip2px(MainActivity.this,10)*2;
            backHeight = MinValue/2;

            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) luckPanLayout.getLayoutParams();
            lp.width = MinValue;
            lp.height = MinValue;

            luckPanLayout.setLayoutParams(lp);

            MinValue -= Util.dip2px(MainActivity.this,28)*2;
            lp = (RelativeLayout.LayoutParams) rotatePan.getLayoutParams();
            lp.height = MinValue;
            lp.width = MinValue;

            rotatePan.setLayoutParams(lp);


            lp = (RelativeLayout.LayoutParams) goBtn.getLayoutParams();
            lp.topMargin += backHeight;
            lp.topMargin -= (goBtn.getHeight()/2);
            goBtn.setLayoutParams(lp);

        }
    }


}
