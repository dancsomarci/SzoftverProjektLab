package model.strategy;

import model.Virologist;
import model.map.Field;

/**
 * Sikertelen anyag gyüjtésért felelős stratégia
 */
public class NoCollect implements ICollectStr
{
	/**
	 * Sikertelen anyag gyüjtés
	 * @param v Gyüjtő virológus
	 * @param f A mező, amelyen gyüjtődik az anyag
	 */
	@Override
	public void Collect(Virologist v, Field f) {
	}
}
