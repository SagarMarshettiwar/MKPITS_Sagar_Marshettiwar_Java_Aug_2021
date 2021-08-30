int main(){
	int i,sum=0;
	for(i=100;i<=200;i++){
		if(i%9==0){
			printf("\n%d",i);
			sum=sum+i;
		}
	}
	printf("\nsum = %d",sum);
}
