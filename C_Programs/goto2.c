#include<stdio.h>
int main() {
	int sal,bonus;
	char desig;
	printf("enter basic salary");
	scanf("%d",&sal);
	fflush(stdin);
	printf("enter operator like m,p,c*");
	scanf("%c",&desig);
	if(desig == 'm' ) {
		goto manager ;
	}else if(desig == 'c' ) {
		goto clerk ;
	}else if(desig == 'p' ) {
		goto peon ;
	}else {
		printf("\n invalid designation");
		goto end;
	}
	manager:
		bonus=1200;
		sal=sal+bonus;
		goto end;
	clerk:
		bonus=200;
		sal=sal+bonus;
		goto end;
	peon:
		bonus=100;
		sal=sal+bonus;	
		goto end;
	end:
		printf("\ntotal salary = %d",sal);
}
