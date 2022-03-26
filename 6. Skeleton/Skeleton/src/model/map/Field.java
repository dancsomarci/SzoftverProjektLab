package model.map;



import model.Virologist;
import model.equipments.Equipment;
import test.Tester;

import java.util.ArrayList;

/**
 * Egy sima mező, amely tárolja az esetleg rajta lévő virológusokat, felszereléseket
 */
public class Field
{

	protected ArrayList<Field> neighbours;
	protected ArrayList<Virologist> virologists;
	protected ArrayList<Equipment> equipments;

	/**
	 * Létrehozza a tárolókat
	 */
	public Field(){
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		neighbours = new ArrayList();
		virologists = new ArrayList();
		equipments = new ArrayList();

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Megadja a körülötte lévő mezőket
	 * @return szomszéd mezők
	 */
	public ArrayList<Field> GetNeighbours() {
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());

		return neighbours;
	}

	/**
	 * Egy új szomszéddal bővíti a mezőt
	 * @param f új szomszéd
	 */
	public void AddNeighbour(Field f) {
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		neighbours.add(f);

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Virológus elhelyezése a mezőn
	 * @param v elehelyezendő virológus
	 */
	public void AddVirologist(Virologist v) {
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		virologists.add(v);
		v.SetField(this);
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Eltávolítja a virológust a mezőről
	 * @param v eltávolítandó virológus
	 */
	public void RemoveVirologist(Virologist v) {
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		virologists.remove(v);

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Megadja az adott sorszámú virológust a mezőn
	 * @param virologist virológus sorszáma
	 * @throws IndexOutOfBoundsException csak egy virológus tartózkodik a mezőn
	 * @return adott sorszámú virológus
	 */
	public Virologist GetVirologist(int virologist) {
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		if (this.virologists.size() < 2) {
			throw new IndexOutOfBoundsException("There is no other virologist on this field");
		}

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());

		return this.virologists.get(virologist%this.virologists.size());
	}

	/**
	 * Eldobja az adott felszerelést a mezőre
	 * @param e eldobandó felszerelés
	 */
	public void Drop(Equipment e) {
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		equipments.add(e);

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Genetikai kód tanulásáért felelős
	 * @param v tanuló virológus
	 */
	public void LearnGeneticCode(Virologist v) {
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Anyag gyüjtésért felelős
	 * @param v gyüjtő virológus
	 */
	public void CollectMaterial(Virologist v) {
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Felszerelés felvétele adott virológussal
	 * @param v felszedő virológus
	 */
	public void PickUpEquipment(Virologist v) {
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		if (equipments.size()>0) {
			Equipment equipment = equipments.remove(equipments.size()-1);
			equipment.Apply(v);
			v.AddEquipment(equipment);
		}

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
}
