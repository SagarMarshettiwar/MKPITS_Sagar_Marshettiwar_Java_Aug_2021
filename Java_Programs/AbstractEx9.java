class A1{
    void o(){
        System.out.println(" hi");
    }
}
abstract class B1 extends A1{
    abstract void s();
} 
public class AbstractEx9 extends B1 {
    void s(){
        System.out.println(" hello");
    }
    public static void main(String[] args) {
        B1 b=new AbstractEx9();
        b.s();
        b.o();
    }
}