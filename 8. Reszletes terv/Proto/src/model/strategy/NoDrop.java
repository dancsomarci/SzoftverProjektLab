package model.strategy;


import model.Virologist;
import model.equipments.Equipment;
import model.map.Field;

/**
 * Eldobási stratégia, amely megakadályozza, hogy a virológus eldobjon egy felszerelést.
 */
public class NoDrop implements IDropStr
{
	/**
	 * Védőfelszerelés eldobása
	 * @param v Dobó virológus
	 * @param f Virológus mezője
	 * @param e Eldobandó felszerelés
	 */
	@Override
	public void Drop(Virologist v, Field f, Equipment e)
	{
		v.DecreaseActions();
	}
}
