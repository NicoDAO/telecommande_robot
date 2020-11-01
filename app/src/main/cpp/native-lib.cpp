#include <jni.h>
#include <string>
#include "Principal.h"
extern "C" JNIEXPORT jstring JNICALL
Java_com_telecommande_1robot_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    Principal textC;

    std::string hello = "Hello from C++";
    int cpt = 0;
   // while(1){
        std::string envoie;
        envoie = textC.retourne() + std::to_string(cpt++);
       // env->NewStringUTF(envoie.c_str());
       // sleep(1);
   // }


    return env->NewStringUTF(envoie.c_str());
   // return env->NewStringUTF(hello.c_str());
}