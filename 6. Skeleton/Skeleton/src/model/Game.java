package model;

import model.codes.GeneticCode;
import model.map.Field;

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

	public ArrayList<Virologist> getVirologists() {
		return virologists;
	}

	public boolean randOn = true; //by default, parancs kell a kikapcsoláshoz (+jelszó)

	/**
	 * A játékban szereplő virpológusok
	 */
	private ArrayList<Virologist> virologists;

	private int currentPlayer = 0;

	/**
	 * Game osztály konstruktora, inicializálja a tagváltozók listáit
	 */
	public Game(){
		NewGame();
	}

	/**
	 * Elindít egy új játékot, inicializálja a pályát.
	 */
	public void NewGame() {
		fields = new ArrayList<>();
		codes = new ArrayList<>();
		virologists = new ArrayList<>();
	}

	/**
	 * Átadja az irányítást a sorrendben a következő játékosnak, az aktuálisnak lezárja a körét.
	 * @param codes a megismert genetikai kódok száma
	 */
	public void NextPlayer(int codes){
		// A VIROLÓGUSNÁL KELL MEGKÉRDEZNI, HOGY MEGVAN-E AZ ÖSSZES KÓDJA
		// EZ ALAPJÁN KELL MEGFELELŐEN PARAMÉTEREZNI MAJD A FÜGGVÉNYHÍVÁST
		if(codes == this.codes.size())
			EndGame();
		else {
			for (Virologist v: virologists) {
				v.Update();
			}
			currentPlayer++;
			if (currentPlayer == virologists.size()) currentPlayer = 0;
		}
	}

	public Virologist GetCurrentPlayer(){
		return virologists.get(currentPlayer);
	}

	/**
	 * Befejezi a játékot és kihírdeti a nyertest
	 */
	public void EndGame()
	{
		System.out.println("YoureWinner");
		System.exit(0);
	}

	/**
	 * Hozzáad egy virológust a játékhoz
	 * @param v a hozzáadandó virológus
	 */
	public void AddVirologist(Virologist v)
	{
		virologists.add(v);
		v.SetGame(this);
	}

	/**
	 * Hozzáad egy új genetikai kódot a játékhoz
	 * @param gc hozzáadandó genetikai kód
	 */
	public void AddGeneticCode(GeneticCode gc){
		codes.add(gc);
	}

	/**
	 * Hozzáad egy mezőt a játékhoz
	 * @param f hozzáadandó mező
	 */
	public void AddField(Field f){
		fields.add(f);
	}

	public List<Field> GetFields(){return fields;}

	public void RemoveVirologist(Virologist virologist) {
		virologists.remove(virologist);
	}
}
