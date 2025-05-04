#include<stdio.h>
// declaring a function prototype
float calculatearea();
//creating a main function
int main() {
	float result;
	result=calculatearea();
	printf("\n area = %f",result);
}
float calculatearea()
{
	int radius;
	float area;
	printf("enter radius");
	scanf("%d",&radius);
	area = 3.14f * radius * radius;
	return area;
}
