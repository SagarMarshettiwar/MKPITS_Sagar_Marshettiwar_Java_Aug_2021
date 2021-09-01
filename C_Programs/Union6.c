//7 create a union customer with member custid(int) , custname(char array) , mobileno(double)
union customer
{
	int custid;
	char custname[20];
	double mobileno;
};
int main()
{
	union customer c1;
	printf("enter customer id");
	scanf("%d",&c1.custid);
	printf("\n customer id = %d",c1.custid);
	printf("\nenter customer name");
	scanf("%s",&c1.custname);
	printf("\n customer name = %s",c1.custname);
	printf("\nenter customer mobile no");	
	scanf("%lf",&c1.mobileno);
	printf("\n mobileno = %.0lf",c1.mobileno);
	printf("\n customer name = %s",c1.custname); // will not print anything

}
