package com.dht.notes.code.animation;

import android.animation.TypeEvaluator;

/**
 * Created by dai on 2018/1/22.
 */

public class PointEvaluator implements TypeEvaluator<Point> {
    @Override
    public Point evaluate(float fraction, Point startValue, Point endValue) {
        // 将动画初始值startValue 和 动画结束值endValue 强制类型转换成Point对象

        // 根据fraction来计算当前动画的x和y的值
        System.out.println("fraction = " + fraction);
        float x = startValue.getX() + fraction * (endValue.getX() - startValue.getX());
        float y = startValue.getY() + fraction * (endValue.getY() - startValue.getY());

        // 将计算后的坐标封装到一个新的Point对象中并返回
        return new Point(x, y);
    }
}
