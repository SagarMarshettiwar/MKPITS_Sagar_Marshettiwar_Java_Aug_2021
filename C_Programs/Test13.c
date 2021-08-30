void factorial(int num){
	int fact=1,i;
	for(i=1;i<=num;i++){
		fact=fact*i;
	}
	printf("%d",fact);
}
int main(){
	int no;
	scanf("%d",&no);
	factorial(no);
}
