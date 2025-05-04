#include <stdio.h>
enum months{jan=1, feb, march, april, may, june, july, august, september, october, november,
december};
int main()
{
// printing the values of months
	for(int i=jan;i<=december;i++)
	{
		printf("%d, ",i);
	}
	return 0;
}

