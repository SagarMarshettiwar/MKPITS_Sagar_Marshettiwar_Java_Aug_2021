//17 wap to find whether string is palindrome or not
//(palindrome after reversing the string should be same ie madam)
#include<string.h>
int main()
{
	char name[30];
	char orgname[30];
	//char revname[30];
	printf("enter firstname");
	gets(name);
	strcpy(orgname,name);
	printf("\n org name =%s",orgname);
	strrev(name);
	printf("\n rev name =%s",name);
	if(strcmp(orgname,name)==0) {
		printf("\na string is palindrome");
	}
	else{
		printf("\n string is not palindrome");
	}
}
