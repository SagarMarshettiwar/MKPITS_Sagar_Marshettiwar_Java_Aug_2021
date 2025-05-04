int main(){
	int rollno,total,marks,i;
	char name[10];
	float per;
	printf("enter yout rollno\n");
	scanf("%d",&rollno);
	printf("enter your name\n");
	scanf("%s",&name);
	//printf("enter three subjects marks");
	//scanf("%d%d%d",&n1,&n2,&n3);
	//total=n1+n2+n3;
	while(i<=3){
		printf("enter three subjects marks [%d]",i);
		scanf("%d",&marks);
		total=total+marks;
		i++;
	}
	printf("your name is =%s\n",name);
	printf("your roll no is =%d\n",rollno);
	printf("total marks of three subjects %d\n ",total);
	per=(total*100)/300;
	printf("percentage = %.2f",per);
	if(per>=75){
		printf("\ndistinction");
	}else if(per<75 && per>=60){
		printf("\nfirst class");
	}else{
		printf("\nfail !");
	}
}
