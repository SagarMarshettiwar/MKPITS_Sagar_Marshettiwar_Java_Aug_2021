int main(){
	int num,i=2,t=0;
	printf("enter the no to be checked ");
	scanf("%d",&num);
	while(i<=num-1){
		if(num%i==0){
			t=t+1;
		}
		i++;
	}
	if(t>0){
		printf("not prime");
	}else{
		printf("prime");
	}
}
