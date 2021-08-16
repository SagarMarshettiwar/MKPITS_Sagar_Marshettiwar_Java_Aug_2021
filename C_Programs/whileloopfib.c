int main(){
	int n1=0,n2=1,n3,i=2,no;
	printf("enter the number to find fibo ");
	scanf("%d",&no);
	printf("%d %d",n1,n2);
	while(i<no){
		n3=n1+n2;
		printf(" %d ",n3);
		n1=n2;
		n2=n3;
		i++;
	}
}
