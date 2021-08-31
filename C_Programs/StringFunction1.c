//12 strcpy
//program to copy one string into another
#include<string.h>
int main()
{
	char ch[15];
	char ch1[15];
	int length;
	printf("enter string");
	gets(ch);
	strcpy(ch1,ch);//ch ki value ch1 me aa gaye.
	printf("\nstring1 = %s",ch);
	printf("\nstring2 = %s",ch1);
}
