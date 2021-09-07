//2 wap to check whether amount to be deposited or withdrawl
int main() {
	int actno,amount;
	int bal=1000;
	printf("enter account no");
	scanf("%d",&actno);
	printf("enter deposit");
	scanf("%d",&amount);
//calling the function deposit
	deposit(actno,amount,bal);
//calling the function withdrawl
	withdrawl(actno,amount,bal);
//calling the function showbalance
	showbalance(actno, bal);
}
//function return nothing
void deposit(int actno,int amount,int bal )
{
	if(amount > 0 )
	{
		bal=bal+amount;
		printf("\n account no : %d",actno);
		printf("\n amount deposited , balance amount is : %d",bal);
	}
	else
	{
		printf("\n amount to be deposited should be greater than 0");	
	}

}
void withdrawl(int actno,int amount,int bal )
{
	if(amount <= bal)
	{
		bal=bal-amount;
		printf("\n account no : %d",actno);
		printf("\n amount withdrawl , balance amount is : %d",bal);
	}
	else
	{
		printf("\n no withdrawl due to insuffient ");
	}
}
void showbalance(int actno,int bal)
{
	printf("\n account no : %d",actno);
	printf("\n balance amount is : %d",bal);
}
