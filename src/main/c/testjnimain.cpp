#include <stdio.h>
#include "com_cinbo_filesync_TestJniMain.h"

JNIEXPORT void JNICALL Java_com_cinbo_filesync_TestJniMain_HelloWord(JNIEnv * env, jclass obj){

  printf("hello word!/n");

}

JNIEXPORT jstring JNICALL Java_com_cinbo_filesync_TestJniMain_cToJava(JNIEnv * env, jclass obj){
	jstring jstr;

	char str[]="Hello,word!/n";

	jstr=env->NewStringUTF(str);

	return jstr;
}