# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
# http://www.jianshu.com/p/0aa044263d4d
# https://www.diycode.cc/topics/380
# Written in the prguard-rules.pro file, in fact, is to confuse the rules, which provisions do not need to be confused.
# The code itself is written in some important classes need to be confused, and the essence of confusion is to streamline the class name, with a simple a, b, c and other words to replace the previously written such as DataUtil easy to understand the class name.
# So, understand this point, it is easy to understand how to write this confused file, the general idea is:
# Do not confuse the third party library, do not confuse the system components, generally can not be confused Bean and other model classes, because these are useless for others, after all, are open source. The The

-optimizationpasses 5 # Specify the code compression level
-dontusemixedcaseclassnames # Confusion does not produce all kinds of class names
-dontskipnonpubliclibraryclasses # Specify that the non-public class library is not ignored
-dontskipnonpubliclibraryclassmembers # Specify that non-public class library members are not ignored
-dontpreverify  # Do not pre-check, if you need pre-verification, is -dontoptimize
-ignorewarnings # Shield warning
-verbose # Logging logs when confusing
-printmapping priguardMapping.txt
-optimizations !code/simplification/artithmetic,!field/*,!class/merging/* #optimization
# Marshmallow removed Notification.setLatestEventInfo()
-dontwarn android.app.Notification


################common###############

-keep public class * implements me.xiaobailong24.mvvmarms.repository.ConfigModule

 # Entity does not participate in confusion
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepnames class * implements java.io.Serializable
-keepattributes Signature
-keep class **.R$* {*;} # Filter the confusion of R files
-ignorewarnings
-keepclassmembers class **.R$* {
    public static <fields>;
}

-keepclasseswithmembernames class * { # Keep the native method not confused
    native <methods>;
}

-keepclassmembers enum * {  # Use the enum type need to pay attention to avoid the following two methods of confusion, because the specificity of the enum class, the following two methods will be reflected call,
    public static **[] values();
    public static ** valueOf(java.lang.String);
}


################support###############
-keep class android.support.** { *; }
-keep interface android.support.** { *; }
-dontwarn android.support.**


################annotation###############
-keep class android.support.annotation.** { *; }
-keep interface android.support.annotation.** { *; }


################retrofit###############
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-dontwarn okio.**
-dontwarn javax.annotation.**

-dontnote retrofit2.Platform
-dontwarn retrofit2.Platform$Java8

-dontwarn org.xmlpull.v1.**
-dontwarn retrofit2.**

-dontwarn javax.annotation.GuardedBy

################okhttp###############
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn com.squareup.okhttp.**
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault


################glide###############
-keep public class * implements com.bumptech.glide.module.AppGlideModule
-keep public class * implements com.bumptech.glide.module.LibraryGlideModule
-keep public class * extends com.bumptech.glide.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}


################gson###############
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
# Application classes that will be serialized/deserialized over Gson
#-keep class com.sunloto.shandong.bean.** { *; }


################androidEventBus###############
-keep class org.simple.** { *; }
-keep interface org.simple.** { *; }
-keepclassmembers class * {
    @org.simple.eventbus.Subscriber <methods>;
}
-keepattributes *Annotation*


################Rxjava and RxAndroid###############
-dontwarn org.mockito.**
-dontwarn org.junit.**
-dontwarn org.robolectric.**

-keep class io.reactivex.** { *; }
-keep interface io.reactivex.** { *; }

-keepattributes Signature
-keepattributes *Annotation*
-keep class com.squareup.okhttp.** { *; }
-dontwarn okio.**
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**

-dontwarn io.reactivex.**
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}

-keep class sun.misc.Unsafe { *; }

-dontwarn java.lang.invoke.*

-keep class io.reactivex.schedulers.Schedulers {
    public static <methods>;
}
-keep class io.reactivex.schedulers.ImmediateScheduler {
    public <methods>;
}
-keep class io.reactivex.schedulers.TestScheduler {
    public <methods>;
}
-keep class io.reactivex.schedulers.Schedulers {
    public static ** test();
}
-keepclassmembers class io.reactivex.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class io.reactivex.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    long producerNode;
    long consumerNode;
}

-keepclassmembers class io.reactivex.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    io.reactivex.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class io.reactivex.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    io.reactivex.internal.util.atomic.LinkedQueueNode consumerNode;
}

-dontwarn io.reactivex.internal.util.unsafe.**


################RxErrorHandler#################
 -keep class com.gm.repository.rxerrorhandler.** { *; }
 -keep interface com.gm.repository.rxerrorhandler.** { *; }


################espresso###############
-keep class android.support.test.espresso.** { *; }
-keep interface android.support.test.espresso.** { *; }

################Timber#################
-dontwarn org.jetbrains.annotations.**


################Canary#################
#https://github.com/square/leakcanary/issues/815
-dontwarn com.squareup.haha.guava.**
-dontwarn com.squareup.haha.perflib.**
-dontwarn com.squareup.haha.trove.**
-dontwarn com.squareup.leakcanary.**
-keep class com.squareup.haha.** { *; }
-keep class com.squareup.leakcanary.** { *; }


################Dagger#################
-dontwarn com.google.errorprone.annotations.*



################Write your own class of operations#################
-keep class com.gm.archmvvm.**{*;}
