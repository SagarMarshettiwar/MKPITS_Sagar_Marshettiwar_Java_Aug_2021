//wap to create function calarea with one int parameter to accept radius
//calculate and display area and circ of circle.
void Calarea(int radius){
	float pi=3.14;
	int area;
	int circ;
	area=2*pi*radius;
	printf("area of circle %d",area);
	circ=pi*radius*radius;
	printf("\ncircumferance of circle %d",circ);
}
int main(){
	int r;
	printf("enter radius");
	scanf("%d",&r);
	Calarea(r);
}
