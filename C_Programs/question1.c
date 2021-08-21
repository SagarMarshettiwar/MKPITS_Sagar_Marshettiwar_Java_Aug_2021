int main(){
	int year;
	printf("enter the year to find");
	scanf("%d",&year);
	if(year%4==0){
		if(year%100==0){
			if(year%400==0){
				printf("leap year");
			}else{
				printf("not a leap year");
			}
		}else{
			printf("leap year");
		}
	}else{
		printf("not a leap year");
	}
}
