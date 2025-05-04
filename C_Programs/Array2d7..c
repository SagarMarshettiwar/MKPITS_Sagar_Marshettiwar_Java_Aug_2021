//9 - wap of multiplication of matrices
int main()
{
	int arr1[3][3] = { {1,2,3} , {4,5,6}, {7,8,9} };
	int arr2[3][3] = { {11,12,13} , {14,15,16}, {17,18,19} };
	int arr3[3][3];
	int r,c;
	for(r=0;r<3;r++) {
		for(c=0;c<3;c++)
		{
			arr3[r][c] = arr1[r][c] * arr2[r][c];
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
