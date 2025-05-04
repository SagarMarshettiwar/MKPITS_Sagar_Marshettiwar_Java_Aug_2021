//11- wap to create a structure array of 3 elements of structure student
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
	struct student stud[3]; //creating structure array of 3 elements
	int i=0;
	while(i<3 )
	{
		printf("\nenter rno ");
		scanf("%d",&stud[i].rno);
		printf("enter student name ");
		scanf("%s",&stud[i].name);
		printf("enter address ");
		fflush(stdin);
		gets(stud[i].address);
		printf("\nstudent[%d] details are",i+1);
		display(stud[i]); // passing structure to function
		i++;
	}
}
//creating function display with structure parameter to accept structure of type student
void display(struct student s2)
{
	printf("\n rno = %d",s2.rno);
	printf("\n student name = %s",s2.name);
	printf("\n address = %s",s2.address);
}
