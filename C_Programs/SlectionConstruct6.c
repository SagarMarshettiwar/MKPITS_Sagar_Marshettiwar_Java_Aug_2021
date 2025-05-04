#include<stdio.h>
int main() {
	int s1,s2,s3,s4,s5,total;
	float percentage;

	printf("enter 5 subject marks.");
	scanf("%d%d%d%d%d",&s1,&s2,&s3,&s4,&s5);
	total = s1+s2+s3+s4+s5;
	printf("\n total marks = %d",total );
	percentage = (float) (total / 500.0f) * 100.0f;
	printf("\n percentage = %f", percentage);
	if(percentage >= 75 ){
		printf("\n grade = A");
	}else if(percentage >= 60 && percentage < 75) {
		printf("\n grade = B ");
	}else if (percentage >= 45 && percentage <60 ) {
		printf("\n grade = C");
	}
	else
	{
	printf ("\n fail");
	}
	return 0;
}
