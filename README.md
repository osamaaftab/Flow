Flow
=====
[![](https://jitpack.io/v/osamaaftab/Flow.svg)](https://jitpack.io/#osamaaftab/Flow)

A Circular image loading and caching library for Android with circular Progressbar.

Download
=====

Step 1. Add the JitPack repository to your build file.
Add it in your root build.gradle at the end of repositories:
```
allprojects {
   repositories {
	...
	maven { url 'https://jitpack.io'}
   }
}
```
Step 2. Add the dependency
```
dependencies {
    implementation 'com.github.osamaaftab:Flow:Tag'
}
```

How do I use Flow?
=====
```kotlin
// For a simple view:
override fun onCreate(savedInstanceState: Bundle?) {
  ...
  Flow.getInstance(this, 4).displayImage("http://goo.gl/gEgYUd",image_view, R.drawable.place_holder)
}
```

Screenshot
=====
![](screenshot/Screenshot_1.png) ![](screenshot/Screenshot_2.png)

Here are the attributes you can specify through XML :

```border_color``` - Color of a border.

```border_width``` - Width of a border.

```progress_color``` - Color of a progress.

```draw_anticlockwise``` - If the progess is anticlockwise.

```progress_startAngle``` - Angle from where the progress should start.


XML
====
```xml
<com.osamaaftab.flow.CircularImageView
     xmlns:app="http://schemas.android.com/apk/res-auto"
     android:id="@+id/image_view"
     android:layout_width="150dp"
     android:layout_height="150dp"
     android:layout_gravity="center"
     app:border_color="@android:color/transparent"
     app:border_width="2dp"
     app:draw_anticlockwise="false"
     app:enable_touch="false"
     app:progress_color="@color/color_100"
     app:progress_startAngle="-90"/>
```
