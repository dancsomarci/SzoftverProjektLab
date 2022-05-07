package model.strategy;


import model.Virologist;

/**
 * Zsákmányolási stratégia, ami nem engedélyez zsákmányolást.
 */
public class NoLoot implements ILootStr
{
	/**
	 * Nem Engedélyez Aminosav zsákmányolást.
	 * @param v A zsákmányoló virológus.
	 * @param target A kizsákmányolandó virológus.
	 */
	@Override
	public void LootAmino(Virologist v, Virologist target)
	{
		v.DecreaseActions();
	}

	/**
	 * Nem Engedélyez Nukleotid zsákmányolást.
	 * @param v A zsákmányoló virológus.
	 * @param target A kizsákmányolandó virológus.
	 */
	@Override
	public void LootNucleotide(Virologist v, Virologist target)
	{
		v.DecreaseActions();
	}

	/**
	 * Nem Engedélyez Védőfelszerelés zsákmányolást.
	 * @param v A zsákmányoló virológus.
	 * @param target A kizsákmányolandó virológus.
	 */
	@Override
	public void LootEquipment(Virologist v, Virologist target)
	{
		v.DecreaseActions();
	}
}
