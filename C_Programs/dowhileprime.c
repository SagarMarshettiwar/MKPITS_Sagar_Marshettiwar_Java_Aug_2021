//wap to print prime no. between 1 and 20 using do while loop.
int main(){
	int i=1,j,t;
	do{
		j=2;
		while(j<=i-1){
			if(i%j==0){
				t=t+1;
			}
		j++;	
		}
		if(t==0){
			printf("%d",i);
		}else{
			t=0;
		}
		i++;
	}while(i<=25);
}
