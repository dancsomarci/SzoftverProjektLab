package model.strategy;


import model.Virologist;
import model.equipments.Equipment;
import model.map.Field;
import control.Tester;

/**
 * Eldobási stratégia, amely megakadályozza, hogy a virológus eldobjon egy felszerelést.
 */
public class NoDrop implements IDropStr
{
	/**
	 * Sikertelen eldobás stratégia létrehozása
	 */
	public  NoDrop(){
		Tester.ctrMethodStart(new Object(){}.getClass().getEnclosingConstructor());
	}

	/**
	 * Védőfelszerelés eldobása
	 * @param v Dobó virológus
	 * @param f Virológus mezője
	 * @param e Eldobandó felszerelés
	 */
	@Override
	public void Drop(Virologist v, Field f, Equipment e)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
}
