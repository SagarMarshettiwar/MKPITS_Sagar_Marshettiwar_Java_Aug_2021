//6 wap to create a variable num and store value 5 in it. then create a pointer ptr and store the address of
//num variable and the print the value of num variable using pointer.
#include<stdio.h>
int main() {
	int num=5;
	int *ptr;
	ptr=&num;
	printf("value in num =%d",*ptr);
}
