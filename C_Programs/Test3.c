void even_no(int n){
	int i,sum=0;
	for(i=100;i<=n;i++){
		printf("\nsquares of no.s are %d",i*i);		
		sum=sum+i*i;
	}
	printf("\nSum= %d",sum);
}
int main(){
	int no;
	printf("enter the nos ");
	scanf("%d",&no);
	even_no(no);
}
