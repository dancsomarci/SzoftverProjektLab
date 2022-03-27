package model.strategy;


import model.Virologist;
import model.map.Field;
import test.Tester;

/**
 * Sikeres genetikai kód tanulásért felelős stratégia
 */
public class DefLearn implements ILearnStr
{
	/**
	 * Sikeres tanulás stratégia létrehozása
	 */
	public DefLearn(){
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

		f.LearnGeneticCode(v);
		v.DecreaseActions();

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
}
