#include<stdio.h>
int main()
{
	int num1,num2;
	int result;
	char op;
	printf("enter two no. \n");
	scanf("%d%d" ,&num1,&num2);
	printf("enter the operator (+,-,*,/)");
	fflush(stdin);
	scanf("%c ",&op);
	if(op == '+'){
		result=num1+num2;
	}else if(op == '-'){
		result=num1-num2;
	}else if(op == '*'){
		result=num1*num2;
	}else if(op == '/'){
		result=num1/num2;
	}else{
		printf("invalid operator");
	}
		printf("the result is = %d",result);
	getch();	
	return 0;
}
