/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_tylerpnn_ui_cli_Terminal */

#ifndef _Included_com_tylerpnn_ui_cli_Terminal
#define _Included_com_tylerpnn_ui_cli_Terminal
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_tylerpnn_ui_cli_Terminal
 * Method:    construct
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_tylerpnn_ui_cli_Terminal_construct
  (JNIEnv *, jobject);

/*
 * Class:     com_tylerpnn_ui_cli_Terminal
 * Method:    setEcho
 * Signature: (C)V
 */
JNIEXPORT void JNICALL Java_com_tylerpnn_ui_cli_Terminal_setEcho
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_tylerpnn_ui_cli_Terminal
 * Method:    setCanonical
 * Signature: (C)V
 */
JNIEXPORT void JNICALL Java_com_tylerpnn_ui_cli_Terminal_setCanonical
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_tylerpnn_ui_cli_Terminal
 * Method:    listen
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_tylerpnn_ui_cli_Terminal_listen
  (JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif
#endif