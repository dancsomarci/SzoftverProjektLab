package model.strategy;


import model.Virologist;
import model.equipments.Equipment;
import test.Tester;

/**
 *  Virológusra irányuló kifosztásért felelős stratégia, egedélyezi a kifosztást
 */
public class Looted implements ILootedStr
{

	/**
	 * Sikeres kifosztásért felelős stratégia létrehozása
	 */
	public Looted(){
		Tester.ctrMethodStart(new Object(){}.getClass().getEnclosingConstructor());
	}

	/**
	 * Felszerelésre irányuló kifosztás
	 * @param v kifosztó virológus
	 * @param from kifosztandó virológus
	 * @param e kifosztandó felszerelés
	 */
	@Override
	public void LootedForEquipment(Virologist v, Virologist from, Equipment e)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		e.Disable(from);
		v.AddEquipment(e);

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Aminosavra irányuló kifosztás
	 * @param v kifosztó virológus
	 * @param from kifosztandó virológus
	 */
	@Override
	public void LootedForAminoAcid(Virologist v, Virologist from)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		try {
			from.RemoveAminoAcid(1);
			v.AddAminoAcid(1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Nukleotidra irányuló kifosztás
	 * @param v kifosztó virológus
	 * @param from kifosztandó virológus
	 */
	@Override
	public void LootedForNukleotide(Virologist v, Virologist from)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		try{
			from.RemoveNucleotide(1);
			v.AddNucleotide(1);
		}catch (Exception e){
			e.printStackTrace();
		}

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
}
