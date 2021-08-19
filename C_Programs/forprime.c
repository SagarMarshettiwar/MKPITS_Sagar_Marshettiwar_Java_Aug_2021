int main(){
	int i,num;
	printf("enter the no ");
	scanf("%d",&num);
	for(i=2;i<num;i++){
		if(num%i==0){
			printf("not prime");
			break;
		}
	}
	if(i==num){
		printf("prime");
	}
}
