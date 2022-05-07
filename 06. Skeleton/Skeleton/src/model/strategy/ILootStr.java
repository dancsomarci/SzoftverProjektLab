package model.strategy;


import model.Virologist;

/**
 * A virológus Aminosav, Nukleotid, védőfelszerelés zsákmányolási stratégiját megtesítendő osztályok interface-e.
 */
public interface ILootStr
{
	/**
	 * Aminosav zsákmányolás metódusa.
	 * @param v A zsákmányoló virológus.
	 * @param target A kizsákmányolandó virológus.
	 */
	void LootAmino(Virologist v, Virologist target);

	/**
	 * Nukleotid zsákmányolás metódusa.
	 * @param v A zsákmányoló virológus.
	 * @param target A kizsákmányolandó virológus.
	 */
	void LootNucleotide(Virologist v, Virologist target);

	/**
	 * Védőfelszerelés zsákmányolás metódusa.
	 * @param v A zsákmányoló virológus.
	 * @param target A kizsákmányolandó virológus.
	 */
	void LootEquipment(Virologist v, Virologist target);
}
