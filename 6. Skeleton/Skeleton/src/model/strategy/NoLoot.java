package model.strategy;


import model.Virologist;
import control.Tester;

/**
 * Zsákmányolási stratégia, ami nem engedélyez zsákmányolást.
 */
public class NoLoot implements ILootStr
{
	/**
	 * Default ctor, csak a kiíratás miatt.
	 */
	public NoLoot(){
		Tester.ctrMethodStart(new Object(){}.getClass().getEnclosingConstructor());
	}

	/**
	 * Nem Engedélyez Aminosav zsákmányolást.
	 * @param v A zsákmányoló virológus.
	 * @param target A kizsákmányolandó virológus.
	 */
	@Override
	public void LootAmino(Virologist v, Virologist target)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Nem Engedélyez Nukleotid zsákmányolást.
	 * @param v A zsákmányoló virológus.
	 * @param target A kizsákmányolandó virológus.
	 */
	@Override
	public void LootNucleotide(Virologist v, Virologist target)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Nem Engedélyez Védőfelszerelés zsákmányolást.
	 * @param v A zsákmányoló virológus.
	 * @param target A kizsákmányolandó virológus.
	 */
	@Override
	public void LootEquipment(Virologist v, Virologist target)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
}
