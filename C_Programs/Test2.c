void even_no(int n){
	int i,sum=0;
	for(i=1;i<=n;i++){
		if(i%2!=0){
			printf("\neven no.s are %d",i);
		}		
	}
	sum=sum+i;
	printf("\nSum= %d",sum);
}
int main(){
	int no;
	printf("enter the nos");
	scanf("%d",&no);
	even_no(no);
}
