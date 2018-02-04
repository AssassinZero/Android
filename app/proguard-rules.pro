# ************************************************************************************************
# *****************************************  定制化区域  *****************************************
# ************************************************************************************************
#---------------------------------1.实体类---------------------------------
# 实体类 混淆keep规则
-keepclassmembers class **.dataModel.** { *; }

#---------------------------------2.第三方包-------------------------------
# GSON 混淆keep规则
-keepattributes EnclosingMethod
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }

# gilde 混淆keep规则
-keep class com.bumptech.glide.integration.okhttp3.OkHttpGlideModule
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# banner 的混淆代码
-keep class com.youth.banner.** { *; }

# Retrofit 2.X 混淆keep规则
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Exceptions
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# OkHttp3 混淆keep规则
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**

# Okio 混淆keep规则
-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn okio.**

# GreenDAO 混淆keep规则
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
    public static java.lang.String TABLENAME;
}
-keep class **$Properties
-dontwarn org.greenrobot.greendao.database.**
-dontwarn rx.**

# SweetAlert 混淆keep规则
-keep class cn.pedant.SweetAlert.Rotate3dAnimation {
    public <init>(...);
 }

# BaseRecyclerViewAdapterHelper 混淆keep规则
-keep class com.chad.library.adapter.** { *; }
-keepclassmembers class * extends com.chad.library.adapter.base.BaseViewHolder {
    public <init>(...);
}

# ARouter 混淆keep规则
-keep public class com.alibaba.android.arouter.routes.**{ *; }
-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{ *; }
-keep class * implements com.alibaba.android.arouter.facade.template.IProvider{ *; }
-dontwarn com.alibaba.android.arouter.facade.model.RouteMeta

# Bugtags 混淆keep规则
-keepattributes LineNumberTable,SourceFile
-keep class com.bugtags.library.** { *; }
-dontwarn com.bugtags.library.**
-keep class io.bugtags.** { *; }
-dontwarn io.bugtags.**
-dontwarn org.apache.http.**
-dontwarn android.net.http.AndroidHttpClient

# 商汤身份证卡片 混淆keep规则
-keep class com.sensetime.** { *; }

# ************************************************************************************************
# ****************************************  固定配置区域  ****************************************
# ************************************************************************************************
#---------------------------------基本指令区----------------------------------
# 代码混淆压缩比，在0~7之间，默认为5,一般不下需要修改
-optimizationpasses 5
# 混淆时不使用大小写混合，混淆后的类名为小写
-dontusemixedcaseclassnames
# 指定重新打包,所有包重命名,这个选项会进一步模糊包名,将包里的类混淆成n个再重新打包到一个个的package中
-flattenpackagehierarchy ''
# 将包里的类混淆成n个再重新打包到一个统一的package中  会覆盖 flattenpackagehierarchy 选项
-repackageclasses ''
# 指定不去忽略非公共的库的类
# 默认跳过，有些情况下编写的代码与类库中的类在同一个包下，并且持有包中内容的引用，此时就需要加入此条声明
-dontskipnonpubliclibraryclasses
# 指定不去忽略非公共的库的类的成员
-dontskipnonpubliclibraryclassmembers
# 不做预检验
-dontpreverify
# 打印详细
-verbose
# 忽略警告，继续执行
-ignorewarnings
-printmapping proguardMapping.txt
# 指定混淆时采用的算法，后面的参数是一个过滤器
# 这个过滤器是谷歌推荐的算法，一般不改变
-optimizations !code/simplification/cast,!field/*,!class/merging/*
# 保持注解、内部类
-keepattributes *Annotation*,InnerClasses
# 保持签名
-keepattributes Signature
# 保持源文件和行号的信息,用于混淆后定位错误位置
-keepattributes SourceFile,LineNumberTable

#---------------------------------默认保留区---------------------------------
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}

-keep class android.support.v4.**
-dontwarn android.support.v4.**
-keep public class android.support.v7.widget.** { *; }
-keep public class android.support.v7.internal.widget.** { *; }
-keep public class android.support.v7.internal.view.menu.** { *; }
-keep public class * extends android.support.v4.view.ActionProvider {
    public <init>(android.content.Context);
}

# 所有native的方法不混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

# 枚举类不混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# 继承自View的构造方法不混淆
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# 保留 Parcelable 序列化的类不被混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

# 保留 Serializable 序列化的类不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keep class **.R$* { *; }

-keepclassmembers class * {
    void *(**On*Event);
}

# 被 Keep 注解修饰的不混淆
-keep class android.support.annotation.Keep
-keep @android.support.annotation.Keep class * {*;}
-keepclasseswithmembers class * {
    @android.support.annotation.Keep <methods>;
}
-keepclasseswithmembers class * {
    @android.support.annotation.Keep <fields>;
}
-keepclasseswithmembers class * {
    @android.support.annotation.Keep <init>(...);
}

#---------------------------------WebView------------------------------------
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String);
}
-keep class * extends android.webkit.WebChromeClient { *; }
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

#---------------------------------------------------------------------------------------------------
# 删除代码中Log相关的代码
#-assumenosideeffects class android.util.Log {
#    public static boolean isLoggable(java.lang.String, int);
#    public static int v(...);
#    public static int i(...);
#    public static int w(...);
#    public static int d(...);
#    public static int e(...);
#}