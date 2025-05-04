#include<stdio.h>
void Vowel(char character){
	if(character=='a' || character=='A' || character=='e' || character=='E' || character=='i' || character=='I' || character=='o' || character=='O' || character== 'u' || character=='U')
	{
		printf("character is vowel");
	}else{
		printf("character is not vowel");
	}	
}
int main(){
	char c;
	printf("enter the char to find it is vowel or not ");
	fflush(stdin);
	scanf("%c",&c);
	Vowel(c);
}
