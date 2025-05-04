//wap to add two string using strcat function
#include<string.h>
int main()
{
	char firstname[30];
	char lastname[15];
	printf("enter firstname");
	gets(firstname);
	printf("enter lastname");
	gets(lastname);
	strcat(firstname,lastname);//lastname ,firstname ko attach ho gaya
	printf("\n name = %s",firstname);
}
