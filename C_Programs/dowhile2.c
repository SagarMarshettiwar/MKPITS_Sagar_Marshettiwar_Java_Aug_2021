#include<stdio.h>
int main()
{
	int num,result,i=1;
	char ans;
	do
	{
		printf("\n enter number");
		scanf("%d",&num);
		while(i<=10) {
			result=num * i;
			printf("\n %d * %d = %d",num,i,result);
			i++;
		}
		printf("\ndo you want to continue if yes press y otherwise n");
		fflush(stdin);
		scanf("%c",&ans);
	}while(ans=='y');
		printf("\nbye");
}
