#include <jni.h>

extern "C" jstring Java_org_dualword_demo_android_1jni_MainActivity_jniString (
        JNIEnv *env, jobject obj) {

    return env->NewStringUTF("test.");

}
