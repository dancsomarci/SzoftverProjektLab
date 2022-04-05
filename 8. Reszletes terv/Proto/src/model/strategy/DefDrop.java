package model.strategy;



import model.Virologist;
import model.equipments.Equipment;
import model.map.Field;
import test.Tester;

/**
 * Alapértelmezett eldobási stratégia, ami által a virológus eldob egy felszerelést.
 */
public class DefDrop implements IDropStr
{
	/**
	 * Alapértelmezett konstruktor
	 */
	public DefDrop(){
		Tester.ctrMethodStart(new Object(){}.getClass().getEnclosingConstructor());
	}

	/**
	 * A stratégia alkalmazásakor hívott metódus.
	 * Eldobja a virológus a megadott felszerlést, majd csökkenti az akciói számát eggyel.
	 * @param v A virológus, aki el próbál dobni egy felszerelést.
	 * @param e A felszerelés, amit el próbál dobni.
	 * @param f a virológus aktuális mezeje
	 */
	@Override
	public void Drop(Virologist v, Field f, Equipment e)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		e.Disable(v);
		f.Drop(e);
		v.Reset();
		v.DecreaseActions();

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
}
