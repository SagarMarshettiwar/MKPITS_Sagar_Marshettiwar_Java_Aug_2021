 #include<stdio.h>
int main()
{
	int customerid,unit;
	float amountcharge,surchargeamount,netamount;
	char name[10];
	printf("enter customer id :");
	scanf("%d",&customerid);
	printf("enter customer name :");
	scanf("%s",&name);
	printf("enter unit consumed by customer :");
	scanf("%d",&unit);
	
	
	if(unit<=199)
	{
		amountcharge=unit*1.20f;
	}
	else if(unit>=200 && unit<400)
	{
		amountcharge=unit*1.50f;
	}
	else if(unit>=400 && unit<600)
	{
		amountcharge=unit*1.80f;
	}
	else if(unit>=600 )
	{
		amountcharge=unit*2.00;
	}
	else
	{
		printf("\ninvalid unit");
	}
	if(amountcharge <= 100)
	{
		amountcharge=100.0f;
	}
	printf("\namount charge =%f",amountcharge);
	
	if(amountcharge >=400)
	{
	
surchargeamount=(float)(15/100.0f)*amountcharge;
	printf("\nsurcharge amount =%f",surchargeamount);
}
	netamount=amountcharge+surchargeamount;
	printf("\nnet amount paid by the customer =%f",netamount);
	return 0;
}

