int main(){
	int i,j,t;
	for(i=1;i<=20;i++){
		t=0;
		for(j=2;j<=i-1;j++){
			if(i%j==0){
				t=t+1;
			}
		}
		if(t==0){
			printf("%d\n",i);
		}
	}
}
