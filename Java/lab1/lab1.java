import java.util.*;

public class lab1{
    public static long[] f = new long[(24-6)/2+1];
    public static double[] x = new double[14];

    public static double wij(int i,int j){
        if(f[i]==20){
                    return Math.cos(
                            Math.tan(
                            Math.atan(
                                (x[j]+0.5)/13
                                )
                                )
                                );
                    }
        else if (f[i]==8 || f[i]==10 || f[i]==12 || f[i]==14 || f[i]==24){
                    return  Math.sin(
                            Math.exp(
                            Math.pow(x[j], x[j]-1)
                            )
                            );
        }else{
                    return Math.cos(
                            Math.log(
                            Math.abs(
                            (2)/(Math.sin(x[j])-0.5)
                            )
                            )
                            );
                }
    }

    public static void main(String[] args){

        // 1. Создание массива f[i]

        for(int i=0;i<f.length;i++){
            f[i] = 6+2*i;
        }

        // 2. Создание массива x[i]
        
        Random random = new Random();

        for (int i = 0; i < x.length; i++) {
            
            x[i] = random.nextDouble(13)-6;
            
        }

        // 3. Создание w 

        double[][] w = new double[10][14];
        
        for(int i = 0; i < w.length; i++) {
            for(int j = 0; j < w[i].length; j++) {
                w[i][j] = wij(i, j);
            } 
            
        }

        

        for(int i = 0; i < w.length; i++) {
            
            for(int j = 0; j < w[i].length; j++) {
                System.out.printf(" %1$-5.2f |", w[i][j]);
            }
            if(i!=(w.length-1))  System.out.print("\n");
            
        }
        
    }
}