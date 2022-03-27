package model.strategy;


import model.Virologist;
import model.map.Field;
import test.Tester;

/**
 * Sikertelen genetikai kód tanulásért felelős stratégia
 */
public class NoLearn implements ILearnStr
{
	/**
	 * Tanulás stratégia létrehozása
	 */
	public NoLearn(){
		Tester.ctrMethodStart(new Object(){}.getClass().getEnclosingConstructor());
	}

	/**
	 * Genetikai kód tanulás
	 * @param v Tanuló virológus
	 * @param f Virológus mezője
	 */
	public void Learn(Virologist v, Field f)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
}
