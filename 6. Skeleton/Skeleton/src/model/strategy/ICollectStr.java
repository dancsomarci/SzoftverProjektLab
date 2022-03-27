package model.strategy;


import model.Virologist;
import model.map.Field;

/**
 * Anyag gyüjtésért felelős stratégia
 */
public interface ICollectStr
{
	/**
	 * Anyag gyüjtés
	 * @param v gyüjtő virológus
	 * @param f a mező, amelyen gyüjtődik az anyag
	 */
	void Collect(Virologist v, Field f);
}
