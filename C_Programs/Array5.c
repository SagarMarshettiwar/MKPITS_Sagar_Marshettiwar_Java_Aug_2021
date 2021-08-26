//5-wap to create char array of 5 elements to store 5 character and accept 5 character from the user
//using for loop
// then display the characters stored in char array.
#include<stdio.h>
int main()
{
	int i;
	char ch[5];
	for(i=0;i<5;i++)
	{
	printf("\n enter character");
	scanf("\n %c",&ch[i]);
}
	for(i=0;i<5;i++)
	{
		printf("%c",ch[i]);
	}
		
}

