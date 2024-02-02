package Random;

import java.util.Random;

public class Randonnumber {
	public static void main(String[] args) {
		String s="";
		Random random=new Random();
		for(int i=0 ;s.length()<10;i++) {
			int rn=random.nextInt(10);
			if(!s.contains(rn+"")){
				s=s+rn;
				if(i==0)
					 System.out.println(rn);
	             else if(i==1)
	            	 System.out.println(rn);
	             else if(i==2)
	            	 System.out.println(rn);
	             else if(i==3)
	            	 System.out.println(rn);
	             else if(i==4)
	            	 System.out.println(rn);
	             else if(i==5)
	            	 System.out.println(rn);
	             else if(i==6)
	            	 System.out.println(rn);
	             else if(i==7)
	            	 System.out.println(rn);
	             else if(i==8)
	            	 System.out.println(rn);
	             else if(i==9)
	            	 System.out.println(rn);
				
			}else { 
				i--;
			}
		}
		System.out.println(s);
	}	
}
