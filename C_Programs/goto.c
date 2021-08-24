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
		goto add ;
	}
	add:
		printf("\nadd ");
		goto end;
	sub:
		printf("\nsub");
	mul:
		printf("\nmul");
	end:
	printf("\nbye");
}
