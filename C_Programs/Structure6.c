struct product{
	int id;
	char name[10];
	float price;
};
int main(){
	struct product p1;
	printf("enter product id: ");
	scanf("%d",&p1.id);
	printf("enter product name: ");
	scanf("%s",&p1.name);
	printf("enter product price: ");
	scanf("%f",&p1.price);
	printf("\nproduct id: %d",p1.id);
	printf("\nproduct name: %s",p1.name);
	printf("\nproduct price: %.2f",p1.price);
}
