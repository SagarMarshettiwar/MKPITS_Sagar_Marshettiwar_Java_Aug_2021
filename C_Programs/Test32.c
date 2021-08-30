int main(){
    int num,r,sum=0,i;

    printf("Input a number: ");
    scanf("%d",&num);

    for(i=num;num!=0;num=num/10){
         r=num % 10;
         sum=sum*10+r;
    }
    if(i==sum)
         printf("%d is a palindrome number.\n",i);
    else
         printf("%d is not a palindrome number.\n",i);

}

