//12 - create a structure array of 5 elements of structure book .
struct book{
	int bookid;
	char bname[10];
};
void display(struct book b2);
#include<Stdio.h>
int main(){
	struct book b1[5];
	int i=1;
	while(i<=5){
		printf("\nenter book id :");
		scanf("%d",&b1[i].bookid);
		printf("enter book name :");
		scanf("%s",&b1[i].bname);
		display(b1[i]);
		i++;
	}
	printf("\n");
}
void display(struct book b2){
		printf("\n bookid = %d",b2.bookid);
		printf("\n book name = %s",b2.bname);
}
