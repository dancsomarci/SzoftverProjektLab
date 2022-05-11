package model;

import view.Observer;
import java.util.ArrayList;

/**
 * Egy megfigyelt osztály, melynek változásaira fel lehet iratkozniuk Observereknek
 */
public class Subject {
    /**
     * Observerek - megfigyelők listája
     */
    ArrayList<Observer> observers = new ArrayList<>();

    /**
     * Egy megfigyelő regisztrálása
     * @param observer a hozzáadandó megfigyelő
     */
    public void attach(Observer observer){
        if (!observers.contains(observer)){
            observers.add(observer);
        }
    };

    /**
     * Egy megfigyelő leválasztása
     * @param observer az eltávolítandó megfigyelő
     */
    public void detach(Observer observer){
        observers.remove(observer);
    };

    /**
     * Az összes regisztrált megfigyelő értesítése
     */
    public void notifyAllObservers(){
        for (Observer o : observers) {
            o.update();
        }
    }
}
