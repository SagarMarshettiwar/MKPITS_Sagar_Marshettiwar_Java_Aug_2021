#include<stdio.h>
int main()
{
	int num,square;
	char ans;
	do
	{
		printf("\n enter number");
		scanf("%d",&num);
		square=num*num;
		printf("\square of a number =%d",square);
		printf("\ndo you want to continue if yes press y otherwise n");
		fflush(stdin);
		scanf("%c",&ans);
	}while(ans=='y');
		printf("\nbye");
}
