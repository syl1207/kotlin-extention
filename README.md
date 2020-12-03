#常用kotlin扩展库(持续更新)

##View
- #####TextView.drawableLeft(right/Top/Bottom)
>给textView设置CompoundDrawables

```kotlin
textView.drawableLeft(R.mipmap.ic_launcher, 10)
textView.drawableTop(R.mipmap.ic_launcher)

```
- #####TextView.pressedBackground
> 给TextView快速设置background selector

```kotlin
textView.pressedStateBackground("#ff0000", "#66ff0000")
textView.pressedStateBackground("#ff0000", "#66ff0000", 20f)
```
- ##### TextView.pressedStateTextColor
> 给textView快速设置textColor selector

```kotlin
textView.pressedStateTextColor("#0000ff", "#00ff00")
```
- ##### View.setNoFastClickListener
> view防止快速点击

```kotlin
btn.setNoFastClickListener{

}
```
- ##### View.matchParent(wrapContent..)
> 动态更改layoutParams
```kotlin
view.matchParent()
view.wrapContent()
view.fullWidthWrapHeight()
view.wrapWidthFullHeight()
```

```kotlin
it.matchParent()
it.wrapContent()
it.fullWidthWrapHeight()
it.wrapWidthFullHeight()
```


##Bitmap
- #####Bitmap.compress
>压缩bitmap为指定大小的byte数组

```kotlin
bitmap.compress(30_000)
```

- #####Bitmap.scale
>缩放bitmap到指定宽高或者指定比例

```kotlin
bitmap.scale(800,600)
bitmap.scale(1.5f)
```

- ####Bitmap.rotate
>旋转bitmap

```kotlin
bitmap.rotate(90f)
```
- ####Bitmap.toJpegBytes
>bitmap转换为jpeg格式的byte数组

```kotlin
bitmap.toJpegBytes()
```
- ####Bitmap.save
>将bitmap保存为JPEG格式的文件

```kotlin
img.drawToBitmap().save(file)
```

- ####ByteArray.nv21ToBitmap
> 将nv21数据解析成bitmap?

```kotlin
byteArray.nv21ToBitmap(640,480)
```
##fastjson

- ####toJsonObjectOrNull
> 将json格式的字符串解析成JSONObject?

```kotlin
str.toJsonObjectOrNull()
```

- ####String.toJsonArrayOrNull
> 将json格式字符串解析成JSONArray?

```kotlin
str.toJsonObjectOrNull()
```

- ####String.toObjectOrNull
> 将json格式字符串解析成T?

```kotlin
str.toObjectOrNull<KItem>()
```

- ####String.toObjectListOrNull
> 将json格式字符串解析成List<T>?

```kotlin
str.toObjectListOrNull<KItem>()
```

- ####JSONArray.toObjectListOrNull
> 将JSONArray解析成List<T>?

```kotlin
array.toObjectListOrNull<KItem>()
```

##Context

- ####Context.readAssets
> 读取assets下面文件为string

```kotlin
context.readAssets("abc.txt")
```
- ####Context.hasPermission
> 是否有权限

```kotlin
context.hasPermission(Manifest.permission.READ_PHONE_STATE)
context.hasPermission(
            arrayOf(
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
```

- ####Context.getScreenWidth
> 获取屏幕宽高


- ####Context.dp2Px
> dp 转为px


##其他
- ####InputStream.toByteArray
> inputStream 转为byte数组

