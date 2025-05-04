//6 wap to accept empno,firstname , lastname, designation and salary of the employee and display it.
#include<stdio.h>
int main() {
	int empno;
	char firstname[20] ;
	char lastname[20];
	char designation[20];
	float salary;
	printf("enter empno");
	scanf("%d",&empno);
	printf("enter first name");
	fflush(stdin);
	gets(firstname); // gets function is used to accept a string from the user
	printf("enter last name");
	fflush(stdin);
	gets(lastname); // gets function is used to accept a string from the user
	fflush(stdin);
	printf("enter designation");
	gets(designation); // gets function is used to accept a string from the user
	printf("enter salary");
	scanf("%f",&salary);
	printf("\n employee details are :") ;
	printf("\nempno =%d",empno);
	printf("\nfirst name =%s",firstname);
	printf("\nlast name =%s",lastname);
	printf("\ndesignation =%s",designation);
	printf("\n0salary =%f",salary);

}
