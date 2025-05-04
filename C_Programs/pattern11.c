int main()
{
	int r,c;  
	for(r=1;r<=3;r++)  
	{
		for(c=1;c<=r;c++) 
		{
			 if(c==1) 
			 {
			 	printf("\t%d",c);   
			 				
			 }
			 else if(c==2)
			 {
			 	printf("\t%d",c+1); 
			 }
			 else if(c==3)
			 {
			 	printf("\t%d",c+2);
			 }
								
		}
		printf("\n");
	}
}
