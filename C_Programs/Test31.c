int revers(int num)
{
    int rev = 0;
    while (num > 0) {
        rev = rev * 10 + num % 10;
        num = num / 10;
    }
    return rev;
}
 

int main()
{
    int num = 123;
    printf("Reverse of no. is %d", revers(num));
}
