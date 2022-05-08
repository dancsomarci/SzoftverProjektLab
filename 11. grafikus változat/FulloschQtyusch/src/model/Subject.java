package model;

import view.Observer;
import java.util.ArrayList;

public class Subject {
    ArrayList<Observer> observers = new ArrayList<>();

    public void attach(Observer observer){
        if (!observers.contains(observer)){
            observers.add(observer);
        }
    };

    public void detach(Observer observer){
        observers.remove(observer);
    };

    public void notifyAllObservers(){
        for (Observer o : observers) {
            o.update();
        }
    }
}
