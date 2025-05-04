#include<stdio.h>
int main() {
	int num1,num2;
	printf("enter the 2 number");
	scanf("%d%d",&num1,&num2);
	if(num1<num2){
		printf("num1 is smallest");
	}else{
		printf("num2 is smallest");
	}
	return 0;
}
