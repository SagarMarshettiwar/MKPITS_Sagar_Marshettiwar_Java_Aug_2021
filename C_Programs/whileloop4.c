int main(){
	int fact=1,num;
	printf("enter the num");
	scanf("%d",&num);
	
	while(num>0){
		fact=fact*num;
		num--;
	}
	printf("\n the factorial is %d",fact);
}
