package model.strategy;


import model.Virologist;
import model.equipments.Equipment;
import model.map.Field;
import test.Tester;

/**
 * Sikertelen védőfelszerelés eldobásért felelős stratégia
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
	public void Drop(Virologist v, Field f, Equipment e)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
}
