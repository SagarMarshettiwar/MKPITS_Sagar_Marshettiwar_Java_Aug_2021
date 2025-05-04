Prime(int num){
	int i,t=0;
	for(i=2;i<num;i++){
		if(num%i==0){
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
int main(){
	int no;
	printf("enter the no ");
	scanf("%d",&no);
	Prime(no);
}
