//2 create a 2d array of 2 rows and 2 columns and store some value in it and display the values
int main()
{
	int arr[2][2];
	int row,col;
	for(row=0;row<2;row++)
	{
		for(col=0;col<2;col++) 
		{
			printf("enter number");
			scanf("%d",&arr[row][col]) ;
		}
	}
	//displaying the values of an array
	for(row=0;row<2;row++)
	{
		for(col=0;col<2;col++)
		{
			printf("\t%d",arr[row][col]) ; 
		}
		printf("\n");
	}
}
