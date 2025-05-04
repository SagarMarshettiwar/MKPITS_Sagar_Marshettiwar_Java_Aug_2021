void Total(int marks){
	int per;
	per=(marks*100)/300;
	printf("\nyour percentage is = %d",per);
	Grade(per);
}
void Grade(int percent){
	if(percent>=75){
		printf("\nDistinction");
	}else if(percent>=60 && percent<75){
		printf("\nfirst class");
	}else{
		printf("\nfail");
	}
}
int main(){
	int i,marks,tot=0;
	for(i=1;i<=3;i++){
		printf("enter 3 marks [%d]",i);
		scanf("%d",&marks);
		tot=tot+marks;
	}
	printf("\nTotal of 3 = %d",tot);
	Total(tot);
}
