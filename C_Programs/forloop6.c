int main() {
	int num,result,i;
	printf("entern number");
	scanf("%d",&num); 
	for(i=1;i<=10;i++)
	{
	result=num * i;
	printf("\n%d * %d = %d",num,i,result);
	}
}
