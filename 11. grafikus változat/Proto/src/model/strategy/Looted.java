package model.strategy;


import model.Virologist;
import model.equipments.Equipment;

/**
 *  Virológusra irányuló kifosztásért felelős stratégia, egedélyezi a kifosztást
 */
public class Looted implements ILootedStr
{

	/**
	 * Sikeres kifosztásért felelős stratégia létrehozása
	 */
	public Looted(){
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
		e.Disable(from);
		from.RemoveEquipment(e);
		v.AddEquipment(e);
	}

	/**
	 * Aminosavra irányuló kifosztás
	 * @param v kifosztó virológus
	 * @param from kifosztandó virológus
	 */
	@Override
	public void LootedForAminoAcid(Virologist v, Virologist from)
	{
		try {
			from.RemoveAminoAcid(1);
			v.AddAminoAcid(1);
		} catch (Exception e) {
			//Nem volt mit elvenni
		}
	}

	/**
	 * Nukleotidra irányuló kifosztás
	 * @param v kifosztó virológus
	 * @param from kifosztandó virológus
	 */
	@Override
	public void LootedForNukleotide(Virologist v, Virologist from)
	{
		try{
			from.RemoveNucleotide(1);
			v.AddNucleotide(1);
		}catch (Exception e){
			//Nem volt mit elvenni
		}
	}
}
