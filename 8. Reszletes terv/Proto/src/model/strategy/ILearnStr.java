package model.strategy;


import model.Virologist;
import model.map.Field;

/**
 * Genetikai kód tanulásért felelős stratégia
 */
public interface ILearnStr
{
	/**
	 * Genetikai kód tanulás
	 * @param v Tanuló virológus
	 * @param f Virológus mezője
	 */
	void Learn(Virologist v, Field f);

}
