int main(){
	int s,e,i,j,t;
	printf("enter start range ");
	scanf("%d",&s);
	printf("enter end range ");
	scanf("%d",&e);
	for(i=s;i<=e;i++){
		t=0;
		for(j=2;j<i;j++){
			if(i%j==0){
				t=t+1;
			}
		}
		if(t==0){
			printf("\n prime %d",i);
		}
	}	
}
