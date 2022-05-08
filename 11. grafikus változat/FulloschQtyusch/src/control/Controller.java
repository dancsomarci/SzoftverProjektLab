package control;

import model.Game;
import model.Subject;
import model.Virologist;
import model.codes.GeneticCode;
import model.map.Field;
import view.Window;

public class Controller extends Subject{
    private String actionMessage;
    private Game game;
    private Window window;

    public Controller(Game game) {
        this.game = game;
        this.actionMessage = "";
        window = new Window(this, game);
    }

    public void attack(Virologist v) {
    }

    public void move(Field f) {
    }

    public void drop() {
    }

    public void lootAminoFrom(Virologist v) {
    }

    public void lootNucleoFrom(Virologist v) {
    }

    public void lootEquipmentFrom(Virologist v) {
    }

    public void collect() {
    }

    public void learn() {
    }

    public void equip() {
    }

    public void inject(Virologist v, GeneticCode code) {
    }

    public void endTurn() {
    }

    public String getActionMessage() {
        return actionMessage;
    }

    public void update() {
    }
}

