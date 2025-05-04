//wap to accept account no. and amount and initial_bal=1000 and create function deposit, withdrawl and
//show_balance .
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
	bal=bal+amount;
	printf("\n account no : %d",actno);
	printf("\n amount deposited , balance amount is : %d",bal);
}
void withdrawl(int actno,int amount,int bal )
{
	bal=bal-amount;
	printf("\n account no : %d",actno);
	printf("\n amount withdrawl , balance amount is : %d",bal);
}
void showbalance(int actno,int bal)
{
	printf("\n account no : %d",actno);
	printf("\n balance amount is : %d",bal);
}
