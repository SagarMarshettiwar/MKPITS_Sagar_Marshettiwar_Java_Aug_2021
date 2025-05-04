//16 - strrev function
//wap to reverse a string
#include<string.h>
int main()
{
	char name[30];
	char revname[30];
	printf("enter firstname");
	gets(name);
	printf("\nname =%s",name);
	strrev(name);
	printf("\nafter reversing name =%s",name);
}
