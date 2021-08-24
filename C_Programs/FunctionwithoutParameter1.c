// wap to create a function sum to accept 2 no. 
//and display the addition of 2 no.
void sum(){
	int n1,n2,result;
	printf("enter 2 no.");
	scanf("%d%d",&n1,&n2);
	result=n1+n2;
	printf("\n addtion of 2 no. =%d",result);
}
int main() {
	//calling the function
	sum();
	printf("\n bye from main function");
}
