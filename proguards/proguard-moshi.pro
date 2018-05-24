-dontwarn okio.**
-dontwarn javax.annotation.**
-keepclasseswithmembers class * {
    @com.squareup.moshi.* <methods>;
}
-keep @com.squareup.moshi.JsonQualifier interface *

-keepclassmembers class kotlin.Metadata {
    public <methods>;
}

-keep class **JsonAdapter {
    <init>(...);
    <fields>;
}
-keepnames @com.squareup.moshi.JsonClass class *