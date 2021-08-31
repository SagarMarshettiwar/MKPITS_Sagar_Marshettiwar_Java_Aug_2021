//wap to convert string into upper case
#include<string.h>
int main()
{
	char name[30];
	printf("enter firstname");
	gets(name);
	strupr(name);
	printf("\n name = %s",name);
}
