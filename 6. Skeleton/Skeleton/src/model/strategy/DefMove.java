package model.strategy;


import model.Virologist;
import model.map.Field;
import test.Tester;

/**
 * Sikeres mozgásért felelős stratégia
 */
public class DefMove implements IMoveStr
{
	/**
	 * Sikeres mozgás stratégia létrehozása
	 */
	public DefMove(){
		Tester.ctrMethodStart(new Object(){}.getClass().getEnclosingConstructor());
	}

	/**
	 * Sikeres mozgás
	 * @param v Mozgó virológus
	 * @param from Virológus aktuális mezője
	 * @param to Új mező, amelyre lépni szeretne
	 */
	public void Move(Virologist v, Field from, Field to)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		from.RemoveVirologist(v);
		to.AddVirologist(v);
		v.DecreaseActions();

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
}
