package model.strategy;


import model.Virologist;
import model.map.Field;
import control.Tester;

/**
 * Alapértelmezett tanulási stratégia, ami által a virológus megtanulja a mezőn lévő genetikai kódot, ha van.
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
	 * A stratégia alkalmazásakor hívott metódus.
	 * Megtanulja a virológus a mezőn lévő genetikai kódot, ha van ott olyan.
	 * @param v Tanuló virológus
	 * @param f Virológus mezője
	 */
	@Override
	public void Learn(Virologist v, Field f)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		f.LearnGeneticCode(v);
		v.DecreaseActions();

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
}
