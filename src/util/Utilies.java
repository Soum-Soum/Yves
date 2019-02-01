package util;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

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

    public static LinkedList<Integer> intToVerticalString(Integer n){
        LinkedList<Integer> tab = new LinkedList<>();
        while(n%10!=n){
            tab.add(0,n%10);
            n = n/10;
        }
        tab.add(0,n%10);;
        return tab;
    }


    public static void main(String args[]){
        int a = 123456;
        System.out.print(Utilies.intToVerticalString(a));
    }
}
