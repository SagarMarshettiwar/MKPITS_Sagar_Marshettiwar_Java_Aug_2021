void Greater(int num1,int num2,int num3){
	if(num1>num2 && num1>num3){
		printf("num1 is greater [%d]",num1);
	}else if(num2>num1 && num2>num3){
		printf("num2 is greater [%d]",num2);
	}else{
		printf("num3 is greater [%d]",num3);
	}
}
int main(){
	int n1,n2,n3;
	printf("enter the 3 nos.");
	scanf("%d%d%d",&n1,&n2,&n3);
	Greater(n1,n2,n3);
}
