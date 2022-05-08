package model;

import view.Observer;
import java.util.ArrayList;

/**
 * Egy megfigyelt osztály, melynek változásaira fel lehet iratkozniuk Observereknek
 */
public class Subject {
    /**
     * Observerek - megfigyelõk listája
     */
    ArrayList<Observer> observers = new ArrayList<>();

    /**
     * Egy megfigyelõ regisztrálása
     * @param observer a hozzáadandó megfigyelõ
     */
    public void attach(Observer observer){
        if (!observers.contains(observer)){
            observers.add(observer);
        }
    };

    /**
     * Egy megfigyelõ leválasztása
     * @param observer az eltávolítandó megfigyelõ
     */
    public void detach(Observer observer){
        observers.remove(observer);
    };

    /**
     * Az összes regisztrált megfigyelõ értesítése
     */
    public void notifyAllObservers(){
        for (Observer o : observers) {
            o.update();
        }
    }
}
