int main(){
	int i,fact=1;
	printf("enter the no. to get factorial ");
	scanf("%d",&i);
	do{
		fact=fact*i;
		i--;
	}while(i>0);
		printf("\n the factorial %d ",fact);
}
