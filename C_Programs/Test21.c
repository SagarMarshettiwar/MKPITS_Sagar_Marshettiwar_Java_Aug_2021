float calculate(float num1,float num2,float num3){
	float sum;
	sum=num1+num2+num3;
	return sum;
}
int main(){
	float no1,no2,no3,result;
	printf("enter the number = ");
	scanf("%f%f%f",&no1,&no2,&no3);
	result=calculate(no1,no2,no3);
	printf("your sum =%.2f",result);
}
