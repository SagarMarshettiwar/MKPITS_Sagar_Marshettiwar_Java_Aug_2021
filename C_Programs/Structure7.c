struct student{
	int no;
	char name[10];
	char course[10];
	char address[20];
	double mobileno;
};
int main(){
	struct student s1;
	printf(" Student no " );
	scanf("%d",&s1.no);
	printf(" name = ");
	scanf("%s",&s1.name);
	printf(" course = ");
	scanf("%s",&s1.course);
	printf(" Address = ");
	scanf("%s",&s1.address);
	printf(" mobile no = ");
	scanf("%lf",&s1.mobileno);
	printf("\n Student no = %d",s1.no);
	printf("\n name = %s",s1.name);
	printf("\n course = %s",s1.course);
	printf("\n Address = %s",s1.address);
	printf("\n mobile no = %.0lf",s1.mobileno);
	
}
