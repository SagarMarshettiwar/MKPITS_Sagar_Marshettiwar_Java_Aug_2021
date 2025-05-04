int main()
{
	int basic_sal,hra,da,totalsalary;
	printf("enter the basic salary ");
	scanf("%d" ,&basic_sal);
	hra=25 % basic_sal;
	da=35 % basic_sal;
	totalsalary=hra+da+basic_sal;
	printf("the tsalary is %d ",totalsalary);

}
