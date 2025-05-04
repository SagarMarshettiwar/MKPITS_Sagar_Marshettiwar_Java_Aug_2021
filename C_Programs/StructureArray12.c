//15 - create a structure to product employee salary statement.
//create a structure student with members rno,name and address , pass this structure to function
//display to display structure values
struct employee
{
	int empno;
	char name[20];
	int salary;
};
//creating function declaration
void display(struct employee s1);
#include<stdio.h>
int main()
{
	struct employee emp[3]; //creating structure array of 3 elements
	int i=0;
	while(i<3 )
	{
		printf("\nenter empno ");
		scanf("%d",&emp[i].empno);
		printf("enter employee name ");
		scanf("%s",&emp[i].name);
		printf("enter salary ");
		scanf("%d",&emp[i].salary);
		printf("\n------------------employee[%d] details are-----------------",i+1);
		display(emp[i]); // passing structure to function
		printf("\n----------------------------------------------------------",i+1);
		i++;
	}
}
//creating function display with structure parameter to accept structure of type student
void display(struct employee s1)
{
float hra,da,totalsalary;
	printf("\n empno = %d",s1.empno);
	printf("\n emp name = %s",s1.name);
	hra=s1.salary * .15f;
	da=s1.salary * .35f;
	totalsalary=hra+da+s1.salary;
	printf("\n hra = %f",hra);
	printf("\n da = %f",da);
	printf("\n total salary = %f",totalsalary);
}
