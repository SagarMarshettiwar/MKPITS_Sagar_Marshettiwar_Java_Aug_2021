//`1- create a union data with 3 member (int i, float j, char n[10])
union data {
	int i;
	float j;
	char name[10];
}d1;
int main()
{
	d1.i=20;
	printf("\n value of i = %d",d1.i);
	d1.j=20.2f;
	printf("\n value of j = %f",d1.j);
	strcpy(d1.name,"amit");
	printf("\n value of name = %s",d1.name);
	printf("\n now value of i = %d",d1.i); // it will print garbage value

}
