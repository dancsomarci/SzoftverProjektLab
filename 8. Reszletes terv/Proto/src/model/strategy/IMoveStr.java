package model.strategy;



import model.Virologist;
import model.map.Field;

/**
 * Mozgásért felelős stratégia
 */
public interface IMoveStr
{
	/**
	 * Mozgás
	 * @param v Mozgó virológus
	 * @param from Virológus aktuális mezője
	 * @param to Új mező, amelyre lépni szeretne
	 */
	void Move(Virologist v, Field from, Field to);
}
