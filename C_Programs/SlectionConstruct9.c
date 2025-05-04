int main(){
	int salary;
	printf("enter the salary");
	scanf("%d",&salary);
	if(salary>10000){
		printf("you get the bonus of 1000 rs");
	}else if(salary>5000 && salary<10000){
		printf("you get the bonus of 500 rs");
	}else if(salary<5000){
		printf("you get the bonus of 100 rs");
	}
	return 0;
}
