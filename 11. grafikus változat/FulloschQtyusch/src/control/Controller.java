package control;

import model.Game;

import model.Virologist;
import model.codes.GeneticCode;
import model.map.*;
import model.Subject;
import view.Window;
//TODO comment
/**
 * Prototípus külvilággal való kommunikációjáért felelős osztály.
 * Megvalósítja a dokumentációban leírt bemeneti nyelv funkcióit, valamint közvetít a modell és a felhasználó(k) között.
 */
public class Controller extends Subject {

    /**
     * Az aktuálisan játszott játék.
     */
    private final Game game;
    private final Window window;
    private String actionMessage;

    public Controller(Game game) {
        this.game = game;
        window = new Window(this, game);
        attach(window);
        Virologist first = game.GetCurrentPlayer();
        first.attach(window);
        actionMessage = "";
    }

    /**
     * Virológus megtámadása
     * @param v Megtámadott virológus
     */
    public void attack(Virologist v){
        Virologist currentPlayer = game.GetCurrentPlayer();
        actionMessage = currentPlayer.getName() + " trying to attack" + v.getName() + "...";
    }

    /**
     * Virológus mozgatása
     * @param f Cél mező
     */
    public void move(Field f){
        Virologist currentPlayer = game.GetCurrentPlayer();
        currentPlayer.Move(f);
        actionMessage = currentPlayer.getName() + " trying to move to " + f.getName() + "...";
    }

    /**
     * Virológus felszerelésének eldobása
     */
    public void drop(){
        Virologist currentPlayer = game.GetCurrentPlayer();
        currentPlayer.EndTurn();
        actionMessage = currentPlayer.getName() + " trying to drop an equipmnet...";
    }

    /**
     * Virológus aminosav lopása
     * @param v Célpont virológus
     */
    public void lootAminoFrom(Virologist v){
        Virologist currentPlayer = game.GetCurrentPlayer();
        currentPlayer.LootAminoAcidFrom(v);
        actionMessage = currentPlayer.getName() + " trying to loot amino acid form " + v.getName() + "...";
    }

    /**
     * Virológus nukleotid lopása
     * @param v Célpont virológus
     */
    public void lootNucleoFrom(Virologist v){
        Virologist currentPlayer = game.GetCurrentPlayer();
        currentPlayer.LootNucleotideFrom(v);
        actionMessage = currentPlayer.getName() + " trying to nucleotide acid form " + v.getName() + "...";
    }

    /**
     * Virológus felszerelés lopása
     * @param v Célpont virológus
     */
    public void lootEquipmentFrom(Virologist v){
        Virologist currentPlayer = game.GetCurrentPlayer();
        currentPlayer.LootEquipmentFrom(v);
        actionMessage = currentPlayer.getName() + " trying to equipment acid form " + v.getName() + "...";
    }

    /**
     * Virológus anyag gyűjtése
     */
    public void collect(){
        Virologist currentPlayer = game.GetCurrentPlayer();
        currentPlayer.Collect();
        actionMessage = currentPlayer.getName() + " trying to collect material...";
    }

    /**
     * Virológus genetikai kód tanulása
     */
    public void learn(){
        Virologist currentPlayer = game.GetCurrentPlayer();
        currentPlayer.Learn();
        actionMessage = currentPlayer.getName() + " trying to learn a genetic code...";
    }

    /**
     * Virológus felszerelés felvétele
     */
    public void equip(){
        Virologist currentPlayer = game.GetCurrentPlayer();
        currentPlayer.Equip();
        actionMessage = currentPlayer.getName() + " trying to equip an equipment...";
    }

    /**
     * Virológus injektálása
     * @param v Célpont virológus
     * @param code Injektáláshoz szükséges genetikai kód
     */
    public void inject(Virologist v, GeneticCode code){
        Virologist currentPlayer = game.GetCurrentPlayer();
        currentPlayer.Inject(v, code);
        actionMessage = currentPlayer.getName() + " trying to inject " + v.getName() + " with " + code.getName() + "...";
    }

    /**
     * Virológus körének vége
     */
    public void endTurn(){
        Virologist currentPlayer = game.GetCurrentPlayer();
        currentPlayer.EndTurn();
        currentPlayer.detach(window);
        currentPlayer = game.GetCurrentPlayer();
        currentPlayer.attach(window);

    }

    /**
     * Állapot visszacsatolás
     * @return Cselekvés üzenete
     */
    public String getActionMessage(){
        return actionMessage;
    }
}

