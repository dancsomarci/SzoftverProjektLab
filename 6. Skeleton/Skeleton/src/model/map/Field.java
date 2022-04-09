package model.map;



import model.Virologist;
import model.equipments.Equipment;

import java.util.ArrayList;

/**
 * Egy sima mező, amely tárolja az esetleg rajta lévő virológusokat, felszereléseket
 */
public class Field
{
	protected ArrayList<Field> neighbours;
	protected ArrayList<Virologist> virologists;
	protected ArrayList<Equipment> equipments;

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

	private String name;

	public void setName(String name){
		this.name = name;
	}
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

	public ArrayList<Virologist> GetVirologists(){
		return virologists;
	}

	/**
	 * Egy új szomszéddal bővíti a mezőt
	 * @param f új szomszéd
	 */
	public void AddNeighbour(Field f) {
		neighbours.add(f);
	}

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
	 * Megadja az adott sorszámú virológust a mezőn
	 * @param virologist virológus sorszáma
	 * @throws IndexOutOfBoundsException csak egy virológus tartózkodik a mezőn
	 * @return adott sorszámú virológus

	public Virologist GetVirologist(int virologist) {
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		if (this.virologists.size() < 2) {
			throw new IndexOutOfBoundsException("There is no other virologist on this field");
		}

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());

		return this.virologists.get(virologist%this.virologists.size());
	}*/

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
