package model.strategy;



import model.Virologist;
import model.equipments.Equipment;
import model.map.Field;

/**
 * Védőfelszerelés eldobásáért felelős stratégia
 */
public interface IDropStr
{
	/**
	 * Védőfelszerelés elddobása
	 * @param v Dobó virológus
	 * @param f Virológus mezője
	 * @param e Eldobandó felszerelés
	 */
	void Drop(Virologist v, Field f, Equipment e);
}
