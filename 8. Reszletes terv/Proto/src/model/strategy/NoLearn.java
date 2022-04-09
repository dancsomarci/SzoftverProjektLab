package model.strategy;


import model.Virologist;
import model.map.Field;

/**
 * Sikertelen genetikai kód tanulásért felelős stratégia
 */
public class NoLearn implements ILearnStr
{
	/**
	 * Genetikai kód tanulás
	 * @param v Tanuló virológus
	 * @param f Virológus mezője
	 */
	@Override
	public void Learn(Virologist v, Field f)
	{
	}
}
