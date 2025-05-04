int main() {
	int marks,sum,i=1;
	float per;
	do{
		printf("enter marks[%d] ",i);
		scanf("%d",&marks); 
		sum=sum+marks; 
		i++;
	}while(i<=5);
	printf("\n sum of 5 subject marks = %d",sum);
	per=(float) (sum/500.0f) * 100.0f;
	printf("\n percentage = %.2f",per);
}
