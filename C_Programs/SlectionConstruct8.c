int main(){
	int num1,num2,num3;
	printf("enter three numbers");
	scanf("%d%d%d",&num1,&num2,&num3);
	if(num1<num2 && num1<num3){
		printf("num1 is smallest");
	}else if(num2<num3){
		printf("num2 is smallest");
	}else{
		printf("num3 is smallest");
	}
	return 0;
}
