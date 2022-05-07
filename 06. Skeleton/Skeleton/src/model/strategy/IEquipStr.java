package model.strategy;


import model.Virologist;
import model.map.Field;

/**
 * A virológus védőfelszerelés felvételéért felelős stratégia.
 */
public interface IEquipStr
{
	/**
	 * A stratégia alkalmazásakor hívandó metódus.
	 * @param v A felvételt végző virológus.
	 * @param f Erről a mezőről próbálkozik védőfelszerelés felvételével v.
	 */
	void Equip(Virologist v, Field f);
}
