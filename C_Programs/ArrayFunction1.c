//2 -repeat the above program using for loop to display array elements
int main()
{
	int arr[3]={1,2,3} ;
	display(arr);
	display(arr);
}
void display(int arr[]) 
{
	int i;
	for(i=0;i<3;i++)
	{
		printf("\n arr[%d] =%d",i,arr[i]);
	}
}
