//11 -wap to find length of a string
#include<string.h>
int main()
{
	char ch[15];
	int length;
	printf("enter string");
	gets(ch);
	length=strlen(ch);
	printf("lenght of string = %d",length);
}
