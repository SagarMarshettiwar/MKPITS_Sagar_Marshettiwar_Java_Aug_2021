//10 wap to pass rno(int) and name (string) to function to display it.
#include<stdio.h>
int main() {
	int rno=11;
	char name[]="mkpits";
	display(rno,name);
}
void display(int r,char *ch)
{
	printf("\n rno =%d",r);
	printf("\nname = %s",ch);
}
