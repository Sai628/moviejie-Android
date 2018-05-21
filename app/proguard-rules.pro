# To enable ProGuard in your project, edit project.properties
# to define the proguard.config property as described in that file.
#
# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in ${sdk.dir}/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the ProGuard
# include property in project.properties.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}


-dontwarn
-dontshrink
-dontoptimize

-dontwarn org.apache.commons.**

-dontwarn java.awt.**
-dontwarn javax.xml.**
-dontwarn javax.xml.stream.events.**

-keepattributes *Annotation*,EnclosingMethod

-keep public class com.sai628.moviejie.R$*{
    public static final int *;
}
-keep public class com.sai628.moviejie.model {*;}
-keep public class com.sai628.moviejie.model.** {*;}

-keep public class com.sai628.moviejie.model.**{
	public void set*(***);
	public *** get*();
}


-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

-dontnote android.net.http.*
-dontnote org.apache.http.**

-dontwarn android.support.v4.**  
-keep class android.support.v4.** {*;}  
-keep public class * extends android.support.v4.**  
-dontwarn android.support.v13.**  
-keep class android.support.v13.** {*;}  
-keep public class * extends android.support.v13.** 

-keep public class javax.**
-dontwarn android.webkit.WebView
-keep public class android.webkit.**

-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}


-keep class org.apache.http.entity.mime.** {*;}
-keep class vi.com.gdi.bgl.android.java.** {*;}
-keep class org.jsoup*.** {*;}


-keepattributes Exceptions,InnerClasses,Signature
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keepattributes Signature

-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
