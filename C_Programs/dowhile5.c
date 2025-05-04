int main(){
	int i=1,sum,marks;
	float per;
	while(i<=5){
		printf("enter the marks [%d]",i);
		scanf("%d",&marks);
		sum=sum+marks;
		i++;
	}
	printf("\nthe 5 marks sum =%d ",sum);
	per=(float) (sum/500.0f) * 100.0f;
	printf("\n percentage = %.2f",per);
	
}
