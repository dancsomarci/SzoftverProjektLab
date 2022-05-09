package view;

import model.codes.GeneticCode;
import model.map.Laboratory;

//TODO comment

public class DrawableLaboratory extends Laboratory implements Drawable{
    /**
     * Genetikai kód hozzáadása a mezőhöz
     *
     * @param c hozzáadandó genetikai kód
     */
    public DrawableLaboratory(GeneticCode c) {
        super(c);
    }

    @Override
    public String getTexture() {
        return "textures/Laboratory.png";
    }
}
