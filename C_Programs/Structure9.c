//10 - repeat the above program by creating 2 structure variables stud1 and stud2
//create a structure student with members rno,name and address , pass this structure to function
//display to display structure values
struct student
{
	int rno;
	char name[20];
	char address[20];
};
//creating function declaration
void display(struct student s2);
#include<stdio.h>
int main()
{
	struct student stud1,stud2;
	printf("enter rno ");
	scanf("%d",&stud1.rno);
	printf("enter student name ");
	scanf("%s",&stud1.name);
	printf("enter address ");
	fflush(stdin);
	gets(stud1.address);
	printf("\nstudent1 details are");
	display(stud1); // passing structure to function

	//accepting values of second student
	printf("\nenter rno ");
	scanf("%d",&stud2.rno);
	printf("enter student name ");
	scanf("%s",&stud2.name);
	printf("enter address ");
	fflush(stdin);
	gets(stud2.address);
	printf("\nstudent2 details are");
	display(stud2); // passing structure to function
}
//creating function display with structure parameter to accept structure of type student
void display(struct student s2)
{
	printf("\n rno = %d",s1.rno);	
	printf("\n student name = %s",s1.name);
	printf("\n address = %s",s1.address);
}
