int Sample(){
	int no1,no2,sum;
	printf("enter the number = ");
	scanf("%d%d",&no1,&no2);
	sum=no1+no2;
	return sum;
}
int main(){
	int result;
	result=Sample();
	printf("your sum =%d",result);
}
