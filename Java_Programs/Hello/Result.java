/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hello;

/**
 *
 * @author SAGAR
 */
public class Result {
    int phy,chem,bio;
    int total;
    float per;
    String grade;
public int CalculateTotal(int p,int c,int b){
    phy=p;
    chem=c;
    bio=b;
    total=phy+chem+bio;
    return total;
}
public float calculatePercentage()
{
    per=(float) (total / 300.0f) * 100.0f;
    return per;
}
public String calculateGrade()
{
    if(per >= 75)
    grade="Distinction";
    else if (per >= 60 && per < 75)
    grade="first";
    else
    grade="fail";
    return grade;
}
}
