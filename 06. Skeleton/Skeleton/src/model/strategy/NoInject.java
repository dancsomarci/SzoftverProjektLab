package model.strategy;


import model.Virologist;
import model.codes.GeneticCode;
import test.Tester;

/**
 * Az a felkenés stratégia, ami nem engedélyezi a felkenést.
 */
public class NoInject implements IInjectStr
{
	/**
	 * Default ctor, csak a kiíratás miatt.
	 */
	public NoInject(){
		Tester.ctrMethodStart(new Object(){}.getClass().getEnclosingConstructor());
	}

	/**
	 * A felkenést nem engedélyezi v-nek a stratégia, így nem csinál semmit.
	 * @param v A virológus aki a kenést akarja végezni.
	 * @param target A célpont akit fel akar kenni.
	 * @param gc A genetikai kód, ami a felkenendő ágenst gyártja.
	 */
	@Override
	public void Inject(Virologist v, Virologist target, GeneticCode gc) {
		Tester.methodStart(new Object() {
		}.getClass().getEnclosingMethod());
		Tester.methodEnd(new Object() {
		}.getClass().getEnclosingMethod());
	}
}
