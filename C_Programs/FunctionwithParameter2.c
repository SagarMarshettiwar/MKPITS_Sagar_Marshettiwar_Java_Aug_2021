//wap to create a function sum with 2 int parameters and write code in it to add and
//display the addition of 2 no.
void sum(int num1,int num2){
	int result=num1+num2;
	printf("\n addtion of 2 no. is %d",result);
}
int main() {
	int num1,num2;
	printf("enter 2 number");
	scanf("%d%d",&num1,&num2);
	//calling function sum and passing
	sum(num1,num2);
	printf("\n bye from main function");
}
