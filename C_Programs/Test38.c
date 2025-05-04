int main(){
	char ename[10];
	float Salary,bonus,designation;
	float bonusCalc;
	printf("enter your name");
	scanf("%s",&ename);
	printf("\nenter salary");
	scanf("%f",&Salary);
	printf("\nenter Designation");
	scanf("%f",&designation);
	printf("\nenter Bonus");
	scanf("%f",&bonus);
	bonusCalc=Salary+bonus+designation;
	printf(" \nyour name =%s",ename);
	printf(" \nyour Salary =%.2f",Salary);
	printf(" \nyour Designation =%.2f",designation);
	printf(" \nyour Bonus =%.2f",bonus);
	printf("\n your total salary with bonus =%.2f",bonusCalc);
	
}
