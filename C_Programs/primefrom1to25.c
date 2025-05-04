int main(){
	int i=1,j,t;
	while(i<=25)
	{
		t=0;
		j=2;
		while(j<=i-1)
		{
			if(i%j==0)
			{
				t=t+1;
			}
		j++;
		}
		if(t==0)
		{
		printf("%d\n",i);
		}
		//else
		//{
		//	t=0;
		//}
		i++;	
	}
}
