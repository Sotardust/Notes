### 关于Density 分辨率、-hdpi等res目录之间的关系
|DensityDpi|分辨率|res|Density|
|-|-|-|-|
|160dpi|320x533|mdpi|1|
|240dpi|480x800|hdpi|1.5|
|320dpi|720x1280|xhdpi|2|
|480dpi|1080x192|xxhdpi|3|
|560dpi|1440x2560|xxxhdpi|3.5|

dp与px的换算公式：px = dp * Density

### DisplayMetrics::densityDpi与density的区别
```
getResources().getDisplayMetrics().densityDpi //表示屏幕的像素密度 
getResources().getDisplayMetrics().density //1dp等于多少个像素(px)

```
