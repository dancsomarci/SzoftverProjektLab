package model.strategy;


import model.Virologist;

/**
 * A default zsákmányolási stratégia, engedélyezi a zsákmányolás kezdeményezését.
 */
public class DefLoot implements ILootStr
{
	/**
	 * Amniosav zsákmányolást kezdeményez v a target virológus felé.
	 * @param v A zsákmányoló virológus.
	 * @param target A kizsákmányolandó virológus.
	 */
	@Override
	public void LootAmino(Virologist v, Virologist target)
	{
		v.DecreaseActions();
		target.StealAminoAcid(v);
	}

	/**
	 * Nukleotid zsákmányolást kezdeményez v a target virológus felé.
	 * @param v A zsákmányoló virológus.
	 * @param target A kizsákmányolandó virológus.
	 */
	@Override
	public void LootNucleotide(Virologist v, Virologist target)
	{
		v.DecreaseActions();
		target.StealNukleotid(v);
	}

	/**
	 * Védőfelszerelés zsákmányolást kezdeményez v a target virológus felé.
	 * @param v A zsákmányoló virológus.
	 * @param target A kizsákmányolandó virológus.
	 */
	@Override
	public void LootEquipment(Virologist v, Virologist target)
	{
		v.DecreaseActions();
		target.StealEquipment(v);
	}
}
