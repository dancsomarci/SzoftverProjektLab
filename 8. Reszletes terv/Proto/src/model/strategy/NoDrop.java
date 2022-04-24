package model.strategy;

import model.Virologist;
import model.equipments.Equipment;
import model.map.Field;

/**
 * Felszerelés sikertelen eldobásáért felelős stratégia.
 */
public class NoDrop implements IDropStr {

	/**
	 * Felszerelés sikertelen eldobását implementáló függvény.
	 * @param v Dobó virológus
	 * @param f Virológus mezője
	 * @param e Eldobandó felszerelés
	 */
	@Override
	public void Drop(Virologist v, Field f, Equipment e) {
		v.DecreaseActions();
	}

}
