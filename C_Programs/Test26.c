int main(){
	int no=7,i,t=0;
	for(i=2;i<no;i++){
		if(no%i==0){
			t=t+1;
		}
	}
	if(t>=1){
		printf("not prime");
	}else{
		t=0;
		printf("prime");
	}

}
