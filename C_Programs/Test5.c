void Greater(int num1,int num2){
	if(num1>num2){
		printf("num1 is greater [%d]",num1);
	}else{
		printf("num2 is greater [%d]",num2);
	}
}
int main(){
	int n1,n2;
	printf("enter the 2 nos.");
	scanf("%d%d",&n1,&n2);
	Greater(n1,n2);
}
