package model.strategy;

import model.Virologist;
import model.equipments.Equipment;
import model.map.Field;

/**
 * Védőfelszerelés eldobásáért felelős stratégiát reprezentáló interfész.
 */
public interface IDropStr {

	/**
	 * Egy felszerelés eldobásakor meghívott, az interakciót reprezentáló függvény.
	 * @param v Dobó virológus
	 * @param f Virológus mezője
	 * @param e Eldobandó felszerelés
	 */
	void Drop(Virologist v, Field f, Equipment e);

}
