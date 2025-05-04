//2- create a enum account having members saving,current and loan
enum Accounttype { Saving=1,Current,Loan };
int main()
{
	enum Accounttype act;
	act=Current;
	printf("your account type is = %d",act);
}
