package view;

import model.codes.GeneticCode;
import model.map.InfectedLaboratory;

/**
 * A fertőzött laborítóriumnak a képernyőre kirajzolható változata.
 */
public class DrawableInfectedLaboratory extends InfectedLaboratory implements Drawable {

    /**
     * Genetikai kód hozzáadása a mezőhöz
     *
     * @param c hozzáadandó genetikai kód
     */
    public DrawableInfectedLaboratory(GeneticCode c) {
        super(c);
    }

    /**
     * Visszatér a fertőzött laborítórium textúrájának az elérési útjával.
     *
     * @return Az elérési út
     */
    @Override
    public String getTexture() {
        return "/textures/InfectedLaboratory.png";
    }

}
