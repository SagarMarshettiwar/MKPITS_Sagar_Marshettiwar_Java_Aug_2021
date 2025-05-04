//5 -example of union by acception values from the user.
union data {
	int i;
	float j;
	char name[20];
};
int main()
{
	union data d1;
	printf("enter value of i(int value)");
	scanf("%d",&d1.i);
	printf("\n value of i = %d",d1.i);
	printf("\nenter value of j(float value)");	
	scanf("%f",&d1.j);
	printf("\n value of j = %f",d1.j);
	printf("\nenter value of name");
	scanf("%s",&d1.name);
	//printf("\n value of i = %d",d1.i);
	//printf("\n value of j = %f",d1.j);
	printf("\n value of name = %s",d1.name);
}
