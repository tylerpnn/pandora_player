#include <stdio.h>
#include <stdlib.h>
#include <termios.h>
#include <unistd.h>
#include <signal.h>
#include "Terminal.h"

void sig_exit();
void save_state();

struct termios savedState;

JNIEXPORT void JNICALL Java_com_tylerpnn_ui_cli_Terminal_construct(JNIEnv *env, jobject obj)
{
	save_state();
	signal(SIGINT, sig_exit);
}

JNIEXPORT void JNICALL Java_com_tylerpnn_ui_cli_Terminal_setEcho(JNIEnv *env, jobject obj, jint e)
{
	struct termios term;
	tcgetattr (STDIN_FILENO, &term);
	if (e == 0) {
		term.c_lflag &= ~ECHO;
	} else {
		term.c_lflag |= ECHO;
	}
	tcsetattr(STDIN_FILENO, TCSANOW, &term);
}

JNIEXPORT void JNICALL Java_com_tylerpnn_ui_cli_Terminal_setCanonical(JNIEnv *env, jobject obj, jint e)
{
	struct termios term;
	tcgetattr(STDIN_FILENO, &term);
	if(e == 0) {
		term.c_lflag &= ~ICANON;
		setvbuf (stdin, NULL, _IONBF, 1);
	} else {
		term.c_lflag |= ICANON;
		setlinebuf (stdin);
	}
	tcsetattr(STDIN_FILENO, TCSANOW, &term);
}

JNIEXPORT void JNICALL Java_com_tylerpnn_ui_cli_Terminal_listen(JNIEnv *env, jobject obj)
{
	jclass cls = (*env)->GetObjectClass(env, obj);
	jmethodID mid = (*env)->GetMethodID(env, cls, "fire_event", "(C)V" );
	if(cls == NULL || mid == NULL)
		return;

	while(1)
	{
		char c = getchar();
		(*env)->CallVoidMethod(env, obj, mid, c);
	}
}

void save_state()
{
	tcgetattr(STDIN_FILENO, &savedState);
}

void sig_exit()
{
	tcsetattr(STDIN_FILENO, TCSANOW, &savedState);
	exit(-1);
}