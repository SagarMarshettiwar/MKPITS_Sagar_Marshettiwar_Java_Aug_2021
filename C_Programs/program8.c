int main()
{
	int eng,hin,mar,math,sci,avg;
	printf("enter the eng marks ");
	scanf("%d" ,&eng);
	printf("enter the hin marks ");
	scanf("%d" ,&hin);
	printf("enter the mar marks ");
	scanf("%d" ,&mar);
	printf("enter the math marks ");
	scanf("%d" ,&math);
	printf("enter the sci marks ");
	scanf("%d" ,&sci);
	avg=eng+hin+mar+math+sci/5;
	printf("the average is %d ",avg);

}
