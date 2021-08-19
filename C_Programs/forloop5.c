int main() {
	int num,fact=1,i;
	printf("entern number");
	scanf("%d",&num);
	for(i=1;i<=num;i++)
	{
		fact =fact *i;
	}	
	printf("fact = %d",fact);
}
