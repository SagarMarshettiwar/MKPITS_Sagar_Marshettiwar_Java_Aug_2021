#include<stdio.h>
int main() {
	int n1,n2,res;
	char op;
	printf("enter 2 no");
	scanf("%d%d",&n1,&n2);
	fflush(stdin);
	printf("enter operator like +,-,*");
	scanf("%c",&op);
	if(op == '+' ) {
		goto addition ;
	}else if(op == '-' ) {
		goto substraction ;
	}else if(op == '*' ) {
		goto multiplication ;
	}else {
		printf("\n invalid operator");
		goto end;
	}
	addition:
		res=n1+n2;
		goto end;
	substraction:
		res=n1-n2;
		goto end;
	multiplication:
		res=n1*n2;
		goto end;
	end:
		printf("\nresult = %d",res);
}
