//
// Created by nicolas on 01/11/20.
//

#include "Principal.h"
#include <string>
//#include <thread>
#include <pthread.h>
#include <unistd.h>
void* tournicote1(void *pvParameters);


Principal::Principal() {};//constructeur
Principal::~Principal() {};//destructeur

std::string Principal::retourne() {
    std::string nom;
    nom = "Creation threads";
    struct thread_info *tinfo;
    pthread_t  GereAXI3;
    pthread_create(&GereAXI3, NULL, tournicote1, (void *) tinfo);

    return nom;

}

void* tournicote1(void *pvParameters) {

    for (;;) {
       // Volume1.handler()
        sleep(1);
    }
    return NULL;
}