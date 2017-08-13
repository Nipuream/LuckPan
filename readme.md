# 幸运转盘

<br/>

## 特性

Android 抽奖转盘基于View的实现，主要有以下几点特性：<br/>

### 点击按钮滚动
![点击按钮滚动](https://github.com/Nipuream/LuckPan/blob/master/luck_pan.gif)


### 随手势滚动
![随手势滚动](https://github.com/Nipuream/LuckPan/blob/master/scroll.gif)


## 使用

### 滚动到指定区域
     /**
     * 开始转动
     * @param pos 如果 pos = -1 则随机，如果指定某个值，则转到某个指定区域
     */
    public void startRotate(int pos){

        int lap = (int) (Math.random()*12) + 4;

        int angle = 0;
        if(pos < 0){
            angle = (int) (Math.random() * 360);
        }else{
            int initPos  = queryPosition();
            if(pos > initPos){
                angle = (pos - initPos)*60;
                lap -= 1;
                angle = 360 - angle;
            }else if(pos < initPos){
                angle = (initPos - pos)*60;
            }else{
                //nothing to do.
            }
        }



###改变转盘个数
~~~
   <com.hr.nipuream.luckpan.view.RotatePan
        android:id="@+id/rotatePan"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="78dp"
        android:layout_centerHorizontal="true"
        luckpan:pannum="8"
        luckpan:names="@array/names"
        luckpan:icons="@array/icons"
        />

        将pannum改为你想要的数量，然后names和icons定义在arrays.xml文件中

        <resources>
            <string-array name="names">
                <item>action</item>
                <item>adventure</item>
                <item>combat</item>
                <item>moba</item>
                <item>other</item>
                <item>role</item>
                <item>sports</item>
                <item>words</item>
            </string-array>

            <string-array name="icons">
                <item>action</item>
                <item>adventure</item>
                <item>combat</item>
                <item>moba</item>
                <item>other</item>
                <item>role</item>
                <item>sports</item>
                <item>words</item>
            </string-array>
        </resources>

        其中arrays.xml中的数量要和转盘的数量一致
~~~

详细操作见Demo和更多信息。

## 更多信息
[Android 抽奖转盘的实现](http://blog.csdn.net/YanghuiNipurean/article/details/52251107)


