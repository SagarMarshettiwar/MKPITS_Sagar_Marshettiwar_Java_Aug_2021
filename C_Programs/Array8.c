//8-wap to create int array of 3 elements to store 3 integer no. and accept 3 integer no. from the user
//using for loop
// and add the 3 elements and
// then display the sum.
#include<stdio.h>
int main()
{
	int i,sum=0;
	int num[3];
	for(i=0;i<3;i++)
	{
		printf("\n enter number");
		scanf("\n %d",&num[i]);
		sum=sum+num[i];
	}
	printf("\n sum =%d",sum);
}

