#include<stdio.h>
int main(){
	int num1,num2,result=0;
	char op;
	printf("enter 2 number");
	scanf("%d%d",&num1,&num2);
	printf("enter the operator (+,-,*,/)");
	fflush(stdin);
	scanf("%c",&op);
	switch(op){
		case '+':
			result=num1+num2;
			break;
		case '-':
			result=num1-num2;
			break;
		case '*':
			result=num1*num2;
			break;
		case '/':
			result=num1/num2;
			break;
		default :
			printf("invalid operator");
	}
	printf("result =%d",result);
	return 0;
}
