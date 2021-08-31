//18 -strlwr() function
//wap to convert string into lower case

#include<string.h>
int main()
{
	char name[30];
	printf("enter firstname");
	gets(name);
	strlwr(name);
	printf("\n name = %s",name);
}
