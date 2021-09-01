struct product{
	int id;
	char name[10];
	float price;
};
int main(){
	struct product p1;
	p1.id=1;
	printf("\n Student no = %d",p1.id);
	strcpy(p1.name,"sugar");
	printf("\n name = %s",p1.name);
	p1.price=12.2f;
	printf("\n course = %.2f",p1.price);
}
