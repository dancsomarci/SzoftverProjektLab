package model.strategy;


import model.Virologist;
import model.codes.GeneticCode;

/**
 * Azt a stratégiát reprezentálja, mikor a Virológus rá akar kenni egy paraméterül kapott genetikai kód által reprezentált ágenst a másikra.
 */
public interface IInjectStr
{
	/**
	 * A stratégia alkalmazásához hívandó metódus
	 * @param v A virológus aki a kenést akarja végezni.
	 * @param target A célpont akit fel akar kenni.
	 * @param gc A genetikai kód, ami a felkenendő ágenst gyártja.
	 */
	void Inject(Virologist v, Virologist target, GeneticCode gc);
}
