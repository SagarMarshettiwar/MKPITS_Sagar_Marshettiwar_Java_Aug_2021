//15 -wap to create 3 functions
//1- caltotal , calpercentage and display grade
//accept 3 subject marks and display the result
//12 - wap to create a function result with 3 int parameters to accept 3 subject marks,
// calculate total, percentage and grade.
#include<stdio.h>
//function prototype
float calpercentage(int totalmarks );
void displaygrade(float per);
//creating a main function
int main() {
	int sub1,sub2,sub3,tot;
	float per=0.0f;
	printf("enter 3 subject marks");
	scanf("%d%d%d",&sub1,&sub2,&sub3);
	tot=caltotal(sub1,sub2,sub3);
	printf("\n total marks = %d",tot);
	per=calpercentage(tot);
	printf("\n percentage = %f", per);
	displaygrade(per);
}
int caltotal(int s1,int s2,int s3)
{
	int total=s1+s2+s3;
	return total;
}
float calpercentage(int totalmarks)
{
	float p;
	p=(float) (totalmarks / 300.0f ) * 100.0f;
	return p;
}
void displaygrade(float per)
{
// printf("\n per=%f",per);
if(per >= 75 )
	{
		printf("\n grade = distinction");
	}
	else if(per >= 60 && per < 75 )
	{	
		printf("\n grade = first");
	}
	else if(per >= 40 && per < 60 )
	{
		printf("\n grade = second division");
	}
	else
	{
		printf("\n grade=fail");
	}
}
