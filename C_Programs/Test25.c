int main(){
	int no;
	printf("enter the no ");
	scanf("%d",&no);
	int i,result;
	for(i=1;i<=10;i++){
		result=no*i;
		printf("\n%d * %d = %d",no,i,result);
	}
}
