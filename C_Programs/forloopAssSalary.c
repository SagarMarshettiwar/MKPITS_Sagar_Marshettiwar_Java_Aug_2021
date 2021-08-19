int main(){
	int salary,i=1,ts=0;
	float avg;
	while(i<=10){
		printf("\nenter the salary of [%d] ",i);
		scanf("%d",&salary);
		ts=ts+salary;
		i++;
	}
	printf("Total Salary=%d ",ts);
	avg=(float)ts/10.0f;
	printf("\naverage age sal = %.2f",avg);
}
