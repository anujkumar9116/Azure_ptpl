#include <jni.h>
JNIEXPORT jstring JNICALL
Java_com_test_runHiddenApi_AzureApplication_getProUrl(JNIEnv *env, jobject instance) {
// return (*env)->  NewStringUTF(env, "https://www.previewtechnologies.com/hpe_kyc.json");
 return (*env)->  NewStringUTF(env, "R0NIWEpPeDZTR2lFc2NMeGNZOE5paEpkbWhiTHpMVllpYXBKUHlSTzBsaFZVaG5jK2FUZjFRb0FKMlVmamZabjVFU0JuS1hINm4za0J3ajJZLzQzdkE9PQ==");
}

JNIEXPORT jstring JNICALL
Java_com_test_runHiddenApi_AzureApplication_getX(JNIEnv *env, jobject instance) {
 return (*env)->  NewStringUTF(env, "fMSAbCwJihVcBKEfBLWZ2g==");
}

