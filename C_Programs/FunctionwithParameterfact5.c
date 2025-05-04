//wap to create function result with 3 int parameters to accept 3 subject marks
//calculate and display total, percentage and grade.
void Result(int n1,int n2,int n3){
	int total;
	int per;
	total=n1+n2+n3;
	printf("total of three subjects %d",total);
	per=(total*100)/300;
	if(per>=75){
		printf("\ndistinction");
	}else if(per>=60 && per<75){
		printf("\nfirst class");
	}else if(per<60){
		printf("\nsecond class");
	}else{
		printf("\nfail");
	}	
}
int main(){
	int num1,num2,num3;
	printf("enter three subject marks");
	scanf("%d%d%d",&num1,&num2,&num3);
	Result(num1,num2,num3);
}	

