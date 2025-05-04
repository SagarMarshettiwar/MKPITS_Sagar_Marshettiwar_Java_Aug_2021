int main(){
	int days;
	printf("enter the number 1 to 7");
	scanf("%d",&days);
	switch(days){
		case 1:
			printf("\n mon");
			break;
		case 2:
			printf("\n tue");
			break;
		case 3:
			printf("\n wed");
			break;
		case 4:
			printf("\n thus");
			break;
		case 5:
			printf("\n fri");
			break;
		case 6:
			printf("\n sat");
			break;
		case 7:
			printf("\n sun");
			break;
		default :
			printf("\n error");
			break;	
	}
	return 0;
}
