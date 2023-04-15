package com.niu;

import static java.lang.Math.*;

/**
 * @author niushuanglong
 * @date 2022/11/11 22:59:03
 * @description
 */
public class love {
    public static void main(String[] args) throws InterruptedException {
        double x,y,a;
        char s[]=new char[]{'I',' ','l','o','v','e',' ','y','o','u','!'};
        int index=0;
        for(y=2.3f;y>-2.1f;y-=0.06f){
            index=0;
            for(x=-2.1f;x<=2.1f;x+=0.025f){
                double result=x*x+pow((5.0*y/4.0-sqrt(abs(x))),2);
                if(result<=1){
                    System.out.print((s[index]));
                    index=(index+1)%11;
                }
                else{
                    System.out.print(' ');
                }
            }
            Thread.sleep(100);
            System.out.println(" ");
    }
    }
}
