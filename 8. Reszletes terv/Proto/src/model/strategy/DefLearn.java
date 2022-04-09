package model.strategy;


import model.Virologist;
import model.map.Field;

/**
 * Alapértelmezett tanulási stratégia, ami által a virológus megtanulja a mezőn lévő genetikai kódot, ha van.
 */
public class DefLearn implements ILearnStr
{
	/**
	 * A stratégia alkalmazásakor hívott metódus.
	 * Megtanulja a virológus a mezőn lévő genetikai kódot, ha van ott olyan.
	 * @param v Tanuló virológus
	 * @param f Virológus mezője
	 */
	@Override
	public void Learn(Virologist v, Field f)
	{
		f.LearnGeneticCode(v);
		v.DecreaseActions();
	}
}
