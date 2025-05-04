//multiplication of row of arr1 and col of arr2 into arr3
//2 create a 2d array of 2 rows and 4 columns and store some value in it and display the values
int main()
{
	int arr1[3][3] = { {1,2,3} , {4,5,6}, {7,8,9} };
	int arr2[3][3] = { {1,2,3} , {4,5,6}, {7,8,9} };
	int arr3[3][3];
	int r,c;
	for(r=0;r<3;r++) {
		for(c=0;c<3;c++)
		{
			arr3[r][c]= arr1[r][c] * arr2[c][r];
		}
	}
	printf("\n arr1 \n");
	for(r=0;r<3;r++) {
		for(c=0;c<3;c++){
			printf("\t%d",arr1[r][c]);
		}	
		printf("\n");
	}
	printf("\n arr2 \n");
	for(r=0;r<3;r++) {
		for(c=0;c<3;c++){
			printf("\t%d",arr2[r][c]);
		}
		printf("\n");
	}
	printf("\n arr3 \n");
	for(r=0;r<3;r++) {
		for(c=0;c<3;c++){
			printf("\t%d",arr3[r][c]);
		}
	printf("\n");
	}
}
