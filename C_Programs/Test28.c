int main()
{
   int no1=0,no2=1,no3,i,n;
   printf("Input number to  display : ");
   scanf("%d",&n);
   printf("% 5d % 5d", no1,no2);
 
  for(i=3;i<=n;i++)
   {
     no3=no1+no2;
     printf("% 5d",no3);
     no1=no2;
     no2=no3;
   }
   printf("\n");
}

