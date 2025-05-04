char sayhello(char Word[10]){
	printf("your name = %s",Word);
}
int main(){
	char name[10];
	printf("enter the name =");
	scanf("%s",&name);
	sayhello(name);
}
