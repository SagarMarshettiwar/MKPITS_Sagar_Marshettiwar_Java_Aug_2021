//2-wap to create an integer array of 5 elements to store 5 no. and accept 5 no.from the user then display the value stored in an array.
#include<stdio.h>
int main()
{
	int num[5];
	printf("\n enter value of num[0]");
	scanf("\n %d",&num[0]);
	
	printf("\n enter value of num[1]");
	scanf("\n %d",&num[1]);
	
	printf("\n enter value of num[2]");
	scanf("\n %d",&num[2]);
	
	printf("\n enter value of num[3]");
	scanf("\n %d",&num[3]);
	
	printf("\n enter value of num[4]");
	scanf("\n %d",&num[4]);
	
	//diplaying the stored values
	
	printf("\n num[0]=%d",num[0]);
	printf("\n num[1]=%d",num[1]);
	printf("\n num[2]=%d",num[2]);
	printf("\n num[3]=%d",num[3]);
	printf("\n num[4]=%d",num[4]);				
}

