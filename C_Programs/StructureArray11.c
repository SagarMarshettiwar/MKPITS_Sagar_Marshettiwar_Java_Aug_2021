//14 - create a structure array to produce result of student.
//create a structure student with members rno,name and address , pass this structure to function
//display to display structure values
struct student
{
	int rno;
	char name[20];
	int marks1;
	int marks2;
	int marks3;
};
//creating function declaration
void display(struct student s1);
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
		printf("enter marks1 ");
		scanf("%d",&stud[i].marks1);
		printf("enter marks2 ");
		scanf("%d",&stud[i].marks2);
		printf("enter marks3 ");
		scanf("%d",&stud[i].marks3);
		printf("\n------------------student[%d] details are-----------------",i+1);
		display(stud[i]); // passing structure to function
		printf("\n----------------------------------------------------------",i+1);
		i++;
	}
}
//creating function display with structure parameter to accept structure of type student
void display(struct student s1)
{
	int total;
	float per;
	printf("\n rno = %d",s1.rno);
	printf("\n student name = %s",s1.name);
	total=s1.marks1+s1.marks2+s1.marks3;
	printf("\n total marks = %d",total);
	per=(float)(total/300.0f) * 100.0f;
	printf("\n percentage = %f",per);
	if(per>=75 ) {
		printf("\n grade=distinction");
	}
	else if(per>=60 && per <75 ) {
		printf("\n grade=first");
	}
	else{
		printf("\n grade=fail");
	}
}
