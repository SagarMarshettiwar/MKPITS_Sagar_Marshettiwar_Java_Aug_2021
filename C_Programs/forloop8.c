int main() {
	int age,i,total;
	float average;
	for(i=1;i<=5;i++)
	{
	printf("enter age ");
	scanf("%d",&age);
	total=total + age;
	}
	average=(float)total/5.0f;
	printf("average age of student = %.2f",average);
}
