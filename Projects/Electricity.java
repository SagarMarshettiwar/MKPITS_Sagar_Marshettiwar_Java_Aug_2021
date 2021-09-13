import java.util.Scanner;
public class Electricity {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        System.out.println("WELCOME TO ELECTRICITY PAY-HOUSE");
        System.out.println("Enter the TOTAL USAGE OF UNITS");
        double Units=s.nextDouble();
        double Charges=0,VehicleCost=1.38;
        if(Units>0 && Units<=100){
            Charges=Units*3.44*VehicleCost;
        }else if(Units>101 && Units<=300){
            Charges=Units*7.34*VehicleCost;
        }else if(Units>301 && Units<=500){
            Charges=Units*10.36*VehicleCost;
        }else if(Units>501 && Units<=1000){
            Charges=Units*11.82*VehicleCost;
        }else if(Units>1000){
            Charges=Units*11.82*VehicleCost;
        }
        System.out.println("YOUR CHARGES + VEHICLE COST = "+Charges);
        double TAX=Charges*(0.16);
        System.out.println("Including 16% TAX "+TAX);
        System.out.println("TOTAL ELECTRICITY BILL + Mandatary Cost 200rs ="+(Charges+TAX+200));
     }
}
