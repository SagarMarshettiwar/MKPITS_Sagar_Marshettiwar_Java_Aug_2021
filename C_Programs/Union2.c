//3- example to check memory allocation in union
union data {
	int i;
	float j;
	char name[20];
};
int main()
{
	union data d1;
	printf("\n memory occupied by data = %d bytes",sizeof(d1));
	d1.i=20;
	printf("\n value of i = %d",d1.i);
	d1.j=20.2f;
	printf("\n value of j = %f",d1.j);
	strcpy(d1.name,"amit");
	printf("\n value of name = %s",d1.name);
	printf("\n now value of j = %f",d1.j); // it will print garbage value
}
