/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment;

/**
 *
 * @author SAGAR
 */
public class DoWhile6 {
    public static void main(String[] args) {
        int i=1,j,t=0;
        do{
		j=2;
		while(j<=i-1){
			if(i%j==0){
				t=t+1;
			}
		j++;	
		}
		if(t==0){
			System.out.println(i);
		}else{
			t=0;
		}
		i++;
	}while(i<=25);
    }
}
