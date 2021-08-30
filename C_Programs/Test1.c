void even_no(int n){
	int i,sum=0;
	for(i=1;i<=n;i++){
		printf("\neven no.s are %d",2*i);		
		sum=sum+2*i;
	}
	printf("\nSum= %d",sum);
}
int main(){
	int no;
	printf("enter the nos ");
	scanf("%d",&no);
	even_no(no);
}
