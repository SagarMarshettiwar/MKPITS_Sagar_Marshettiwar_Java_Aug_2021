//2- wap to accept a string and count the no. of vowels in it .
int main() {
	char ch[10];
	int cnt=0;
	int i=0;
	printf("enter a string ");
	scanf("%s",&ch);
	while(i<10)
	{
		if(ch[i]=='a' || ch[i]=='e' || ch[i]=='i' || ch[i]=='o' || ch[i]=='u' )
		{
			cnt=cnt+1;
		}
		i++;
	}
	printf("\n no. of vowel in string =%d",cnt);
}
