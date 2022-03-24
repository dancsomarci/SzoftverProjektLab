package model;

import model.codes.GeneticCode;
import model.map.Field;
import test.Tester;

import java.io.Console;
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
	 * Elindít egy új játékot, inicializálja a pályát és a virológusokat.
	 */
	public void NewGame()
	{

	}

	/**
	 * Átadja az irányítást a sorrendben a következő játékosnak, az aktuálisnak lezárja a körét.
	 * @param codes a megismert genetikai kódok száma
	 */
	public void NextPlayer(int codes)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		if(codes == this.codes.size())
			EndGame();
		else {
			for (Virologist v: virologists) {
				v.Update();
			}
		}

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
	
	public void EndGame()
	{
	}
	
	public void AddVirologist(Virologist v)
	{

	}
}
