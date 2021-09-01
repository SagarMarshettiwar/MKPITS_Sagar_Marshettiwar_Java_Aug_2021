struct employee{
	int empno;
	char empname[10];
	float designation;
	float Salary;
};
int main(){
	struct employee e1;
	e1.empno=1;
	printf("\n employee no = %d",e1.empno);
	strcpy(e1.empname,"sagar");
	printf("\n employee name = %s",e1.empname);
	e1.designation=15000.0f;
	printf("\n designation = %.2f",e1.designation);
	e1.Salary=10000.0;
	printf("\n salary = %.2f",e1.Salary);	
}
