int main(){
	int result,num;
	int i=1;
	printf("enter the num");
	scanf("%d",&num);
	
	while(i<=10){
		result=num*i;
		printf("\n %d * %d = %d",num,i,result);
		i++;
	}
}
