package model.map;



import model.Virologist;
import model.equipments.Equipment;

import java.util.ArrayList;

/**
 * Egy sima mező, amely tárolja az esetleg rajta lévő virológusokat, felszereléseket
 */
public class Field
{
	/**
	 * Szomszédos mezők
	 */
	protected ArrayList<Field> neighbours;
	/**
	 * Mezőn tartózkodó virológusok
	 */
	protected ArrayList<Virologist> virologists;
	/**
	 * Mezőn található felszerelések
	 */
	protected ArrayList<Equipment> equipments;

	/**
	 * Listázza a mezőt, felszereléseket, virológusokat és a szomszédos mezőket
	 */
	public void bark(){
		System.out.println(this.getClass().getSimpleName() + ": " + name);
		System.out.println("\tEquipments:");
		for (Equipment e: equipments) {
			System.out.println("\t\t" + e.getName());
		}
		System.out.println("\tVirologists:");
		for (Virologist v: virologists) {
			System.out.println("\t\t" + v.getName());
		}
		System.out.println("\tNeighbours:");
		for (Field f: neighbours) {
			System.out.println("\t\t" + f.getName());
		}
	}

	/**
	 * Mező neve
	 */
	protected String name;

	/**
	 * Beállítja a mező nevét
	 * @param name név
	 */
	public void setName(String name){
		this.name = name;
	}

	/**
	 * Megadja a mező nevét
	 * @return név
	 */
	public String getName(){
		return name;
	}

	/**
	 * Létrehozza a tárolókat
	 */
	public Field(){
		neighbours = new ArrayList<>();
		virologists = new ArrayList<>();
		equipments = new ArrayList<>();
	}

	/**
	 * Megadja a körülötte lévő mezőket
	 * @return szomszéd mezők
	 */
	public ArrayList<Field> GetNeighbours() {
		return neighbours;
	}

	/**
	 * Megadja a mezőn tartózkodó virológusokat
	 * @return virológusok
	 */
	public ArrayList<Virologist> GetVirologists(){
		return virologists;
	}

	/**
	 * Egy új szomszéddal bővíti a mezőt
	 * @param f új szomszéd
	 */
	public void AddNeighbour(Field f) {
		if(!neighbours.contains(f))
			neighbours.add(f);
	}

	/**
	 * A mezőn az anyagok tönkre tételét szimbolizálja,
	 * de nem csinál semmit alapból, hiszen csak a Warehouse-on található anyag
	 */
	public void DestroyMaterial(){
	}

	/**
	 * Virológus elhelyezése a mezőn
	 * @param v elehelyezendő virológus
	 */
	public void AddVirologist(Virologist v) {
		virologists.add(v);
		v.SetField(this);
	}

	/**
	 * Eltávolítja a virológust a mezőről
	 * @param v eltávolítandó virológus
	 */
	public void RemoveVirologist(Virologist v) {
		virologists.remove(v);
	}

	/**
	 * Eldobja az adott felszerelést a mezőre
	 * @param e eldobandó felszerelés
	 */
	public void Drop(Equipment e) {
		equipments.add(e);
	}

	/**
	 * Genetikai kód tanulásáért felelős
	 * @param v tanuló virológus
	 */
	public void LearnGeneticCode(Virologist v) {
	}

	/**
	 * Anyag gyüjtésért felelős
	 * @param v gyüjtő virológus
	 */
	public void CollectMaterial(Virologist v) {
	}

	/**
	 * Felszerelés felvétele adott virológussal
	 * @param v felszedő virológus
	 */
	public void PickUpEquipment(Virologist v) {
		if (equipments.size()>0) {
			Equipment equipment = equipments.remove(equipments.size()-1);
			//equipment.Apply(v); berakva AddEquipment-be
			v.AddEquipment(equipment);
			//equipment.ApplyStrategy(v); berakva AddEquipment-be
		}
	}
}
