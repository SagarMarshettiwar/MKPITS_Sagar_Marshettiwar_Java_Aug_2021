package com.example.practice;
class Cont {
    String a;
    String b;
    String c;
    String d;

    public Cont(String a, String b, String c, String d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    @Override
    public String toString() {
        return "Cont{" +
                "a='" + a + '\'' +
                ", b='" + b + '\'' +
                ", c='" + c + '\'' +
                ", d='" + d + '\'' +
                '}';
    }
}

public class Conste extends Cont{
        String e;
        String f;
        String g;
        public Conste(String a, String b, String c, String d,String e,String f,String g) {
            super(a, b, c, d);
            this.e=e;
            this.f=f;
            this.g=g;
        }
        @Override
        public String toString() {
            return "Const{" +
                    "e='" + e + '\'' +
                    ", f='" + f + '\'' +
                    ", g='" + g + '\'' +
                    '}';
        }
    public static void main(String[] args) {
        Cont cont=new Cont("a","b","c","d");
        cont.toString();
        Conste conste=new Conste("a","b","c","d","e","f","g");
        conste.toString();

    }
}
