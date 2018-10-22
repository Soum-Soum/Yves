package util;

public class Utilies {
    public static double round3(double v){
        double a = Math.round(v*1000);
        double b = a/1000;
        return b;
    }

    public static double round2(double v){
        double a = Math.round(v*100);
        double b = a/100;
        return b;
    }

}
