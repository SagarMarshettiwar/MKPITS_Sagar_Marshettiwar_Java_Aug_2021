//wap to create a function factorial with one int parameter , calculate factorial and return the factorial.
#include<stdio.h>
int factorial(int n1)
{
	int fact=1;
	while(n1 > 0)
	{
		fact = fact * n1;
		n1--;
	}
	return fact;
}
int main() {
	int num,result;
	printf("enter number");
	scanf("%d",&num);
	result=factorial(num);
	printf("\n factorial = %d",result);
}
