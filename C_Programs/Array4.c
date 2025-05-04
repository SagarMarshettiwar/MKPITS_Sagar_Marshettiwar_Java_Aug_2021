//4-wap to create float array of 5 elements to store 5 decimal no. and accept 5 decimal no.from the user
//using for loop
// then display the value stored in float array.
#include<stdio.h>
int main()
{
	int i;
	float num[5];
	for(i=0;i<5;i++)
	{
	printf("\n enter no.");
	scanf("\n %f",&num[i]);
}
	for(i=0;i<5;i++)
	{
		printf("\n num[%d]=%f",i,num[i]);
	}
		
}

