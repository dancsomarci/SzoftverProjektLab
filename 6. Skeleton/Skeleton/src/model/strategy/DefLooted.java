package model.strategy;


import model.Virologist;
import model.equipments.Equipment;
import test.Tester;

/**
 * Alapértelmezett virológusra irányuló kifosztásért felelős stratégia, blokkolja a kifosztást
 */
public class DefLooted implements ILootedStr
{

	/**
	 * Nem sikeres kifosztásért felelős stratégia létrehozása
	 */
	public DefLooted(){
		Tester.ctrMethodStart(new Object(){}.getClass().getEnclosingConstructor());
	}

	/**
	 * Felszerelésre irányuló kifosztás
	 * @param v kifosztó virológus
	 * @param from kifosztandó virológus
	 * @param e kifosztandó felszerelés
	 */
	@Override
	public void LootedForEquipment(Virologist v, Virologist from, Equipment e) {
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Aminosavra irányuló kifosztás
	 * @param v kifosztó virológus
	 * @param from kifosztandó virológus
	 */
	@Override
	public void LootedForAminoAcid(Virologist v, Virologist from) {
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Nukleotidra irányuló kifosztás
	 * @param v kifosztó virológus
	 * @param from kifosztandó virológus
	 */
	@Override
	public void LootedForNukleotide(Virologist v, Virologist from) {
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
}
