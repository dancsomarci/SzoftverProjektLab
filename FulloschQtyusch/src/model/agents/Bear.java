package model.agents;

import model.Virologist;
import model.strategy.BearMove;

/**
 * Medvevírus, mely automatikusan megfertőzi az ezzel már megfertőzött játékossal egy mezőn álló többi játékost.
 * Illetve ha az illető lépni akarna, akkor azt random irányba fogja megtenni.
 */
public class Bear extends Agent {
    /**
     * Konstruktor, mely beállítja a vírus élettartamát végtelenre -melyet -1 -gyel jelölünk-.
     */
    public Bear(){
        super(-1);
    }

    /**
     * Konstruktor, mely beállítja a vírus élettartamát tetszőleges értékre.
     * @param ttl a beállítandó élettartam.
     */
    public Bear(int ttl) {
        super(ttl);
    }

    /**
     * @return az ágens hátralévő hatásideje szöveg típusként, így kiírható a végtelen.
     */
    @Override
    public String getTimeToLive(){return "infinity";}

    /**
     * A vírus beállítja a célzott virológus mozgási stratégiáját a megfelelőre, így fejti majd ki a hatását.
     * @param v a célzott virológus.
     */
    @Override
    public void ApplyStrategy(Virologist v){
        v.SetMoveStr(new BearMove());
    }

    /**
     * Nem csinál semmit, ez a jelentőssége, mivel a többi ágens esetén csökkenteni kellene a hátralévő élettartamát.
     * @param v a tulajdonos virológus.
     */
    @Override
    public void Update(Virologist v){ //Sosem évül el
    }
}
