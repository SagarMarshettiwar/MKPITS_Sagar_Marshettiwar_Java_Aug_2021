void check(int num){
	if(num%2==0){
		printf("no is even");
	}else{
		printf("no is odd");
	}
}
int main(){
	int no;
	printf("enter the no ");
	scanf("%d",&no);
	check(no);
}
