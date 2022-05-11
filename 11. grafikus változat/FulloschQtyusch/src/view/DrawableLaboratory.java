package view;

import model.codes.GeneticCode;
import model.map.Laboratory;

/**
 * A laboratóriumnak a képernyőre kirajzolható változata.
 */
public class DrawableLaboratory extends Laboratory implements Drawable{

    /**
     * Genetikai kód hozzáadása a mezőhöz
     *
     * @param c hozzáadandó genetikai kód
     */
    public DrawableLaboratory(GeneticCode c) {
        super(c);
    }

    /**
     * Visszatér a laboratórium textúrájának az elérési útjával.
     *
     * @return Az elérési út
     */
    @Override
    public String getTexture() {
        return "/textures/Laboratory.png";
    }

}
