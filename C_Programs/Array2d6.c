//8 - wap of additon of 2 matrices ( ie create an arr1 of 3 rows and 3 cols , create another array arr2 of 3
//rows and 3 cols , then create array arr3 to to addtion of arr1 and arr2.)
int main()
{
	int arr1[3][3] = { {1,2,3} , {4,5,6}, {7,8,9} };
	int arr2[3][3] = { {11,12,13} , {14,15,16}, {17,18,19} };
	int arr3[3][3];
	int r,c;
	for(r=0;r<3;r++) {
		for(c=0;c<3;c++)
		{
			arr3[r][c] = arr1[r][c] + arr2[r][c];
		}
	}
//displaying the arr3 values
	for(r=0;r<3;r++)
	{
		for(c=0;c<3;c++)
		{
			printf("\t%d",arr3[r][c]);
		}
		printf("\n");
	}
}
