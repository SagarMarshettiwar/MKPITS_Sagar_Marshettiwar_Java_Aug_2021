//13 - create a structure array of 10 element of struture product.
struct product{
	int productid;
	char pname[10];
};
void display(struct product p2);
#include<Stdio.h>
int main(){
	struct product p1[10];
	int i=1;
	while(i<=10){
		printf("\nenter product id :");
		scanf("%d",&p1[i].productid);
		printf("enter product name :");
		scanf("%s",&p1[i].pname);
		display(p1[i]);
		i++;
	}
	printf("\n");
}
void display(struct product p2){
		printf("\n productid = %d",p2.productid);
		printf("\n product name = %s",p2.pname);
}
