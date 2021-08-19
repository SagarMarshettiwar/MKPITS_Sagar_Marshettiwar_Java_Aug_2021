int main() {
	int marks,total,i;
	float per;
	for(i=1;i<=5;i++)
	{
	printf("enter marks");
	scanf("%d",&marks);
	total=total + marks;
	}
	printf("\n total marks = %d",total);
	per=(float) (total / 500.0f) * 100.0f;
	printf("\n per = %.2f",per);
	if(per >= 75){
		printf("\n grade=distinction");
	}
	else if(per >= 60 && per <75) {
		printf("\n grade=first");
	}
	else
	{
		printf("\n grade = fail");
	}
}

