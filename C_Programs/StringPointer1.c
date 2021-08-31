//7 example to access the string via the pointer.
#include<stdio.h>
void main ()
{
	char s[11] = "mkpits";
	char *p = s; // pointer p is pointing to string s.
	printf("%s",p); // the string mkpits is printed if we print p.
}
