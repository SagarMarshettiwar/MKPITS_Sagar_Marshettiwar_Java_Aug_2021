void Table(int num){
	int i,result;
	for(i=1;i<=10;i++){
		result=num*i;
		printf("\n%d * %d = %d",num,i,result);
	}
}
int main(){
	int no;
	printf("enter the no ");
	scanf("%d",&no);
	Table(no);
}
