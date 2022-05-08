package control;

import model.Game;

import java.lang.reflect.Method;
import java.util.*;

import model.Virologist;
import model.agents.Agent;
import model.codes.GeneticCode;
import model.equipments.Equipment;
import model.map.*;
import model.Subject;
import view.Window;

/**
 * Prototípus külvilággal való kommunikációjáért felelős osztály.
 * Megvalósítja a dokumentációban leírt bemeneti nyelv funkcióit, valamint közvetít a modell és a felhasználó(k) között.
 */
public class Controller extends Subject {

    /**
     * Az aktuálisan játszott játék.
     */
    private Game game;
    private Window window;
    private String actionMessage;

    public Controller(Game game) {
        game = game;
        window = new Window(this, game);
        actionMessage = "";
    }

    public void attack(Virologist v){
        Virologist currentPlayer = game.GetCurrentPlayer();

    }

    public void move(Field f){
        Virologist currentPlayer = game.GetCurrentPlayer();
        currentPlayer.Move(f);
        actionMessage = currentPlayer.getName() + " trying to move to " + f.getName() + "...";
    }

    public void drop(){
        Virologist currentPlayer = game.GetCurrentPlayer();
        currentPlayer.EndTurn();
        actionMessage = currentPlayer.getName() + " trying to drop an equipmnet...";
    }

    public void lootAminoFrom(Virologist v){
        Virologist currentPlayer = game.GetCurrentPlayer();
        currentPlayer.LootAminoAcidFrom(v);
        actionMessage = currentPlayer.getName() + " trying to loot amino acid form " + v.getName() + "...";
    }

    public void lootNucleoFrom(Virologist v){
        Virologist currentPlayer = game.GetCurrentPlayer();
        currentPlayer.LootNucleotideFrom(v);
        actionMessage = currentPlayer.getName() + " trying to nucleotide acid form " + v.getName() + "...";
    }

    public void lootEquipmentFrom(Virologist v){
        Virologist currentPlayer = game.GetCurrentPlayer();
        currentPlayer.LootEquipmentFrom(v);
        actionMessage = currentPlayer.getName() + " trying to equipment acid form " + v.getName() + "...";
    }

    public void collect(){
        Virologist currentPlayer = game.GetCurrentPlayer();
        currentPlayer.Collect();
        actionMessage = currentPlayer.getName() + " trying to collect material...";
    }

    public void learn(){
        Virologist currentPlayer = game.GetCurrentPlayer();
        currentPlayer.Learn();
        actionMessage = currentPlayer.getName() + " trying to learn a genetic code...";
    }

    public void equip(){
        Virologist currentPlayer = game.GetCurrentPlayer();
        currentPlayer.Equip();
        actionMessage = currentPlayer.getName() + " trying to equip an equipment...";
    }

    public void inject(Virologist v, GeneticCode code){
        Virologist currentPlayer = game.GetCurrentPlayer();
        currentPlayer.Inject(v, code);
        actionMessage = currentPlayer.getName() + " trying to inject " + v.getName() + " with " + code.getName() + "...";
    }

    public void endTurn(){
        Virologist currentPlayer = game.GetCurrentPlayer();
        currentPlayer.EndTurn();
    }

    public String getActionMessage(){
        return actionMessage;
    }
}

