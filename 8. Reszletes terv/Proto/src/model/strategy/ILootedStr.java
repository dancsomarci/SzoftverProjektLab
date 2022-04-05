package model.strategy;


import model.Virologist;
import model.equipments.Equipment;

/**
 * A virológusra irányuló kifosztásért felelős stratégia
 */
public interface ILootedStr
{
	/**
	 * Felszerelésre irányuló kifosztás
	 * @param v kifosztó virológus
	 * @param from kifosztandó virológus
	 * @param e kifosztandó felszerelés
	 */
	void LootedForEquipment(Virologist v, Virologist from, Equipment e);

	/**
	 * Aminosavra irányuló kifosztás
	 * @param v kifosztó virológus
	 * @param from kifosztandó virológus
	 */
	void LootedForAminoAcid(Virologist v, Virologist from);

	/**
	 * Nukleotidra irányuló kifosztás
	 * @param v kifosztó virológus
	 * @param from kifosztandó virológus
	 */
	void LootedForNukleotide(Virologist v, Virologist from);
}
