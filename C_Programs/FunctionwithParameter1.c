// wap to create a function square with one int parameter to accept a number and calculate and display
//the square of that no.
void square(int num1){
	int square;
	square=num1 * num1;
	printf("\n square of a no. =%d",square);
}
int main() {
//calling the function square and passing number to function parameter
	int n1;
	printf("enter number");
	scanf("%d",&n1);
	square(n1);
	printf("\n bye from main function");
}
