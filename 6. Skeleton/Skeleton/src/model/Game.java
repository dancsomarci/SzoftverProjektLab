package model;

import model.codes.GeneticCode;
import model.map.Field;
import test.Tester;

import java.util.ArrayList;
import java.util.List;

/**
 * Egy singleton osztály, ami a játék kezeléséért felelős.
 * A játékot indítja, és lezárja.
 * Számontartja a játékosokat, és köreiket.(átadja egyiktől a másiknak az irányítást)
 * Mivel a játkosok köreit kezeli, tehát gyakorlatilag az időegységeket,
 * így az ő feladata az ágensek hátralévő idejét csökkenti is.
 */
public class Game
{
	/**
	 * A pálya mezői
	 */
	private List<Field> fields;

	/**
	 * A játékban szereplő genetikai kódok
	 */
	private List<GeneticCode> codes;

	/**
	 * A játékban szereplő virpológusok
	 */
	private List<Virologist> virologists;

	/**
	 * Game osztály konstruktora, inicializálja a tagváltozók listáit
	 */
	public Game(){
		Tester.ctrMethodStart(new Object(){}.getClass().getEnclosingMethod());

		fields = new ArrayList<>();
		codes = new ArrayList<>();
		virologists = new ArrayList<>();
	}

	/**
	 * Elindít egy új játékot, inicializálja a pályát.
	 */
	public void NewGame() {
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Átadja az irányítást a sorrendben a következő játékosnak, az aktuálisnak lezárja a körét.
	 * @param codes a megismert genetikai kódok száma
	 */
	public void NextPlayer(int codes){

		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		// A VIROLÓGUSNÁL KELL MEGKÉRDEZNI, HOGY MEGVAN-E AZ ÖSSZES KÓDJA
		// EZ ALAPJÁN KELL MEGFELELŐEN PARAMÉTEREZNI MAJD A FÜGGVÉNYHÍVÁST
		if(codes == this.codes.size())
			EndGame();
		else {
			for (Virologist v: virologists) {
				v.Update();
			}
		}

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Befejezi a játékot és kihírdeti a nyertest
	 */
	public void EndGame()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Hozzáad egy virológust a játékhoz
	 * @param v a hozzáadandó virológus
	 */
	public void AddVirologist(Virologist v)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		virologists.add(v);
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Hozzáad egy új genetikai kódot a játékhoz
	 * @param gc hozzáadandó genetikai kód
	 */
	public void AddGeneticCode(GeneticCode gc){
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		codes.add(gc);
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Hozzáad egy mezőt a játékhoz
	 * @param f hozzáadandó mező
	 */
	public void AddField(Field f){
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		fields.add(f);
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
}
