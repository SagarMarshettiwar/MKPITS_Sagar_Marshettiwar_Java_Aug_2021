int calculate(int num1,int num2){
	int sum;
	sum=num1+num2;
	return sum;
}
int main(){
	int no1,no2,result;
	printf("enter the number = ");
	scanf("%d%d",&no1,&no2);
	result=calculate(no1,no2);
	printf("your sum =%d",result);
}
