package model.strategy;

import model.Virologist;
import model.map.Field;

/**
 * Genetikai kód sikertelen megtanulásáért felelős stratégia.
 */
public class NoLearn implements ILearnStr {

	/**
	 * Genetikai kód sikertelen megtanulását implementáló függvény.
	 * @param v Tanuló virológus
	 * @param f Virológus mezője
	 */
	@Override
	public void Learn(Virologist v, Field f) {
		v.DecreaseActions();
	}

}
