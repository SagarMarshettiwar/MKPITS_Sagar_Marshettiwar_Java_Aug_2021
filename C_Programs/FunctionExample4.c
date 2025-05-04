//4 wap to accept actno,amount,and transaction(whether you want to deposit or withdrawl or
//showbalance) and call the appropriate function.
int main() {
	int actno,amount,bal1;
	int bal=1000;
	char transaction[20];
	printf("enter account no");
	scanf("%d",&actno);
	printf("enter amount ");
	scanf("%d",&amount);
	printf("enter transaction type : deposit or withdrawl ");
	scanf("%s",&transaction);
	if(strcmp(transaction,"deposit") == 0)
	{
		bal1=deposit(actno,amount,bal);
		showbalance(actno,bal1);
	}
	if(strcmp(transaction,"withdrawl") == 0)	
	{
		bal1=withdrawl(actno,amount,bal);
		showbalance(actno,bal1);
	}
}
//function returning value
int deposit(int actno,int amount,int bal )
{
	if(amount > 0 )
	{
		bal=bal+amount;
		printf("\n account no : %d",actno);
		printf("\n amount deposited ");
	}
	else
	{
		printf("\n amount to be deposited should be greater than 0");
	}
	return bal;
}
int withdrawl(int actno,int amount,int bal )
{
	if(amount <= bal)
	{
		bal=bal-amount;
		printf("\n account no : %d",actno);
		printf("\n amount withdrawl ");
	}
	else
	{
		printf("\n no withdrawl due to insuffient ");
	}
	return bal;
}
void showbalance(int actno,int bal)
{
	printf("\n account no : %d",actno);
	printf("\n balance amount is : %d",bal);
}
