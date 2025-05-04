//6-wap to create double array of 5 elements to store 5 long decimal no. and accept 5 long decimal no. from the user
//using for loop
// then display the long decimal no. stored in double array.
#include<stdio.h>
int main()
{
	int i;
	double num[5];
	for(i=0;i<5;i++)
	{
	printf("\n enter number");
	scanf("\n %lf",&num[i]);
}
	for(i=0;i<5;i++)
	{
		printf("\n num=%lf",num[i]);
	}
		
}



