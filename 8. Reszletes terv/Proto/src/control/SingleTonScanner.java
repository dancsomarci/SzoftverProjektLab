package control;

import java.util.Scanner;

/**
 * Burkoló osztály a Scanner körő, hogy singleton osztályként lehessen használni.
 */
public class SingleTonScanner {
    /**
     * Letiltott ctor.
     */
    private SingleTonScanner(){}

    /**
     * Egyetlen Scanner példány referenciája.
     */
    private static Scanner instance = null;

    /**
     * Ezzel a függvénnyel lehet elkérni a példány referenciáját.
     * @return A példány referenciája.
     */
    public static Scanner Create(){
        if (instance == null){
            instance = new Scanner(System.in);
        }
        return instance;
    }
}
