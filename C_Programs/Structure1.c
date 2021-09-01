struct student{
	int no;
	char name[10];
	char course[10];
	char address[20];
	double mobileno;
};
int main(){
	struct student s1;
	s1.no=1;
	printf("\n Student no = %d",s1.no);
	strcpy(s1.name,"sagar");
	printf("\n name = %s",s1.name);
	strcpy(s1.course,"java");
	printf("\n course = %s",s1.course);
	strcpy(s1.address,"ayyappa nagar");
	printf("\n Address = %s",s1.address);
	s1.mobileno=9823169745;
	printf("\n mobile no = %.0lf",s1.mobileno);
}
