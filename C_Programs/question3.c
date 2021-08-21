int main(){
	int maths,physics,chemistry,total1,total2;
	printf("enter maths marks\n ");
	scanf("%d",&maths);
	printf("enter physics marks\n");
	scanf("%d",&physics);
	printf("enter chemistry marks\n");
	scanf("%d",&chemistry);
	total1=maths+physics+chemistry;
	printf("the total of PCM =%d\n",total1);
	total2=maths+physics;
	printf("the total of PM =%d\n",total2);
	if(total1>=190 || total2>=140){
		printf("eligible to admission");
	}else{
		printf("not-eligible to admission");
	}
}
