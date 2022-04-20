package control;

import java.util.Scanner;

public class SingleTonScanner {
    private SingleTonScanner(){}
    private static Scanner instance = null;
    public static Scanner Create(){
        if (instance == null){
            instance = new Scanner(System.in);
        }
        return instance;
    }
}
