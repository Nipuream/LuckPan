# 幸运转盘 #
<br/>
Android 抽奖转盘的实现，如下图所示：<br/>


<br/>
## 点击按钮滚动 ##
![](https://github.com/Nipuream/LuckPan/blob/master/luck_pan.gif)

<br/>
## 随手势滚动 ##
![](https://github.com/Nipuream/LuckPan/blob/master/scroll.gif)


<br/>
## 滚动到指定区域 ##
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


<br/>
## 更多信息 ##
[http://blog.csdn.net/yanghuinipurean/article/details/52251107](http://blog.csdn.net/yanghuinipurean/article/details/52251107)


