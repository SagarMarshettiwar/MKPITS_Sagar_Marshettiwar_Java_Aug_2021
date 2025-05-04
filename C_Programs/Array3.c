//-wap to create an integer array of 5 elements to store 5 no. and accept 5 no.from the user
//using for loop
// then display the value stored in an array.
#include<stdio.h>
int main()
{
	int i;
	int num[5];
	for(i=0;i<5;i++)
	{
	printf("\n enter no.");
	scanf("\n %d",&num[i]);
}
	for(i=0;i<5;i++)
	{
		printf("\n num[%d]=%d",i,num[i]);
	}
		
}

