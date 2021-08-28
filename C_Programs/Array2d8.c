int main()
{
	int arr1[3][3] = { {1,2,3} , {4,5,6}, {7,8,9} };
	int arr2[3][3] ;	
	int r,c;
	for(r=0;r<3;r++) {
		for(c=0;c<3;c++)
		{
			arr2[c][r] = arr1[r][c];
		}
	}
	printf("\n arr1 \n");
	for(r=0;r<3;r++) {
		for(c=0;c<3;c++){
			printf("\t%d",arr1[r][c]);
		}
		printf("\n");
	}
	printf("\n arr2 (transpose ie interchanging of rows and col) \n");
	for(r=0;r<3;r++) {
		for(c=0;c<3;c++){
			printf("\t%d",arr2[r][c]);
		}
		printf("\n");
	}	
}
