# ContourView 轮廓背景控件

## 新控件发布，欢迎大家 Star

![ContourView](http://obbu6r1mi.bkt.clouddn.com/github/contourview/reference.png)

在闲逛一个图片社区时看到这张图片，个人对炫酷的东西比较敏感（视觉肤浅），本来想下载一下这个 App 看一下实际效果，可是没找到。心有不甘，于是分析了一下，感觉实现起来不会太难，自己也花点时间实现了效果，发布了一个库。

## 具体相关

### 效果图

| 样式（contuor_style） | 效果 |
|:----:|:----:|
| Sand(默认) | ![sand](http://obbu6r1mi.bkt.clouddn.com/github/contourview/sand.png?imageView2/2/w/400) |
| Clouds | ![clouds](http://obbu6r1mi.bkt.clouddn.com/github/contourview/clouds.png?imageView2/2/w/400) |
| Beach | ![beach](http://obbu6r1mi.bkt.clouddn.com/github/contourview/beach.png?imageView2/2/w/400) |
| Ripples | ![ripples](http://obbu6r1mi.bkt.clouddn.com/github/contourview/ripples.png?imageView2/2/w/400) |
| Shell | ![shell](http://obbu6r1mi.bkt.clouddn.com/github/contourview/shell.png?imageView2/2/w/400) |

### 属性设置

| （xml）属性名称 | 说明 | 值类型 |
|:----:|:----|:----|
| contuor_style | 内置轮廓样式 | Beach，Ripples，Clouds，Sand，Shell |
| smoothness | 轮廓弯曲系数（没有必要的情况下，不建议设置） | Float类型 范围：0--1，建议范围：0.15--0.3，默认：0.25 |
| shader_mode | 轮廓内颜色的填充方式 | RadialGradient，SweepGradient，LinearGradient，不设置默认纯色填充 |
| shader_startcolor | 填充起始颜色 | color类型，默认白色，需设置shader_mode 才有效果 |
| shader_endcolor | 填充结束颜色 | 同上 |
| shader_style | 填充起始点及方向的控制 | LeftToBottom（左上角到右下角），RightToBottom（右上角到左下角），TopToBottom（上中点到下中点），Center（中点到右下角） |
| shader_color | 填充纯色颜色 | color类型，默认白色，不设置shader_mode时，可以通过此属性设置纯色填充颜色 |

### 动态属性

以上的（xml）属性都有对应的设置方法。
此外，还有一些可以动态设置的属性。

**轮廓锚点坐标集**

	public void setPoints(List<Point[]> pointsList)
	//List<> 表示轮廓的个数，Point[] 表示具体某个轮廓的坐标集，每个轮廓至少4个锚点。

| 方法参数 | 说明 |
|---------|------|
| setPoints(int... pts) | 单个轮廓，int[]{锚点1.x，锚点1.y，锚点2.x，锚点2.y......锚点n.x，锚点y} |
| setPoints(Point[]... pointsArr) | 单个轮廓 android.graphics.Point |
| setPoints(Point... points) | 多个轮廓 |
| setPoints(int[]... ptsArr) | 多个轮廓 |

**Shader 自定义轮廓绘制方式**

	public void setShader(Shader... shader)
	//Shader：传入自己自定义RadialGradient，SweepGradient，LinearGradient。
	//当传入多个Shader时,给多个轮廓设置不同的绘制方式，Shader[0]填充轮廓1，Shader[1]填充轮廓2...

## 使用步骤

#### Step1. 添加依赖
Gradle

	dependencies{
	    compile 'com.ocnyang.contourview:contourview:1.0.0'
	}

或者引用本地 lib

	implementation project(':contourview')
	//或者下面方式
	//compile project(':contourview')

#### Step2. 在布局文件中使用，也可以设置相应的自定义属性

	<com.ocnyang.contourview.ContuorView
        android:layout_width="match_parent"
        android:layout_height="400dp"
        app:contuor_style="Ripples"
        app:shader_endcolor="@color/endcolor"
        app:shader_mode="RadialGradient"
        app:shader_startcolor="@color/startcolor"
        app:shader_style="Center"
        app:smoothness="0.2"/>

根据自己的需要来设置属性。

> 我检讨，我自罚：当时想名字时有点随意，更不可饶恕的是把类名 ContourView 拼错了，已经把库上传发布了，那就将错就错吧！（这个程序猿，不靠谱 :stuck_out_tongue_closed_eyes:）

#### Step3. 如果需要自定义自己独特的轮廓，可以在代码中动态设置以下内容

    /**
     * Customize the coordinates of the anchor to control the area to be drawn。
     */
    private void initCustomContuorView() {
        ContuorView contuorViewCustom = (ContuorView) findViewById(R.id.contuorview_custom);
        int width = getWidth();//获取屏幕的宽度
        int hight = 400;
        int[] ints = {width / 2, 0, width, hight / 2, width / 2, hight, 0, hight / 2};
        int[] intArr = new int[]{width / 2, hight / 4, width / 4 * 3, hight / 2, width / 2, hight / 4 * 3, width / 4, hight / 2};
        contuorViewCustom.setPoints(ints, intArr);
        contuorViewCustom.setShaderStartColor(getResources().getColor(R.color.startcolor));
        contuorViewCustom.setShaderEndColor(getResources().getColor(R.color.endcolor));
        contuorViewCustom.setShaderMode(ContuorView.SHADER_MODE_RADIAL);
    }

    /**
     * Controls the color of the drawing.
     */
    private void initBeachContuorView() {
        ContuorView contuorViewBeach = ((ContuorView) findViewById(R.id.contuorview_beach));

        RadialGradient radialGradient = new RadialGradient(0, 0,4000,
                getResources().getColor(R.color.startcolor),
                getResources().getColor(R.color.endcolor),
                Shader.TileMode.CLAMP);
        LinearGradient linearGradient = new LinearGradient(0, 0, getWidth(), 400,
                Color.argb(30, 255, 255, 255), Color.argb(90, 255, 255, 255),
                Shader.TileMode.REPEAT);
        contuorViewBeach.setShader(radialGradient, linearGradient);
    }