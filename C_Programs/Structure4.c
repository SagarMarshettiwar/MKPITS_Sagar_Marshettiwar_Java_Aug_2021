//5 - create a structure book and create 2 structure variable b1 and b2
// create a structure book with members bookid, title, author and price.
struct book
{
	int bookid;
	char title[10];
	char author[10];
	float price;
	}b1,b2;
int main()
{
//creating a structure variable
//struct book b1; // b1 is structure variable
//assigning value to structure members
	b1.bookid=123;
	strcpy(b1.title,"oracle");
	strcpy(b1.author,"bill");
	b1.price=112.2f;
	//displaying the values stored in structure.
	printf("\n book 1 details");
	printf("\n bookid = %d",b1.bookid);
	printf("\n title = %s",b1.title);
	printf("\n author = %s",b1.author);
	printf("\n price = %f",b1.price);
	printf("\n");
	printf("\n book 2 details");
	b2.bookid=124;
	strcpy(b2.title,"java");
	strcpy(b2.author,"mahesh");
	b2.price=162.2f;
	//displaying the values stored in structure.
	printf("\n bookid = %d",b2.bookid);
	printf("\n title = %s",b2.title);
	printf("\n author = %s",b2.author);
	printf("\n price = %f",b2.price);
}
