#include <stdio.h>
enum {success, fail} status;
int main(void) {
	status=success;
	printf("The value of status is %d", status);
	return 0;
}
