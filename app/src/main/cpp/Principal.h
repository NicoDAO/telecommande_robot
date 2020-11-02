//
// Created by nicolas on 01/11/20.
//

#ifndef TELECOMMANDE_ROBOT_PRINCIPAL_H
#define TELECOMMANDE_ROBOT_PRINCIPAL_H
#include <string>
#include <jni.h>
class Principal {
public :
    Principal();
    ~Principal();
    void setEnv( JNIEnv* env);
    std::string retourne(void);
private :
    JNIEnv* env;
};


#endif //TELECOMMANDE_ROBOT_PRINCIPAL_H
