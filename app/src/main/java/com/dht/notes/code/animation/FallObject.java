package com.dht.notes.code.animation;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

/**
 * created by dht on 2022/6/29 13:56
 */
public class FallObject {

    private int initX;
    private int initY;
    private Random random;
    private int parentWidth;//父容器宽度
    private int parentHeight;//父容器高度
    private float objectWidth;//下落物体宽度
    private float objectHeight;//下落物体高度

    public int initSpeed;//初始下降速度

    public float presentX;//当前位置X坐标
    public float presentY;//当前位置Y坐标
    public float presentSpeed;//当前下降速度

    private Bitmap bitmap;
    public Builder builder;

    private static final int defaultSpeed = 10;//默认下降速度

    public FallObject(Builder builder, int parentWidth, int parentHeight){
        random = new Random();
        this.parentWidth = parentWidth;
        this.parentHeight = parentHeight;
        initX = random.nextInt(parentWidth);//随机物体的X坐标
        initY = random.nextInt(parentHeight)- parentHeight;//随机物体的Y坐标，并让物体一开始从屏幕顶部下落
        presentX = initX;
        presentY = initY;

        initSpeed = builder.initSpeed;

        presentSpeed = initSpeed;
        bitmap = builder.bitmap;
        objectWidth = bitmap.getWidth();
        objectHeight = bitmap.getHeight();
    }

    private FallObject(Builder builder) {
        this.builder = builder;
        initSpeed = builder.initSpeed;
        bitmap = builder.bitmap;
    }

    public static final class Builder {
        private int initSpeed;
        private Bitmap bitmap;

        public Builder(Bitmap bitmap) {
            this.initSpeed = defaultSpeed;
            this.bitmap = bitmap;
        }

        /**
         * 设置物体的初始下落速度
         * @param speed
         * @return
         */
        public Builder setSpeed(int speed) {
            this.initSpeed = speed;
            return this;
        }

        public FallObject build() {
            return new FallObject(this);
        }
    }

    /**
     * 绘制物体对象
     * @param canvas
     */
    public void drawObject(Canvas canvas){
        moveObject();
        canvas.drawBitmap(bitmap,presentX,presentY,null);
    }

    /**
     * 移动物体对象
     */
    private void moveObject(){
        moveY();
        if(presentY>parentHeight){
            reset();
        }
    }

    /**
     * Y轴上的移动逻辑
     */
    private void moveY(){
        presentY += presentSpeed;
    }

    /**
     * 重置object位置
     */
    private void reset(){
        presentY = -objectHeight;
        presentSpeed = initSpeed;
    }

}
