package model.map;



import model.Virologist;
import model.codes.GeneticCode;
import control.Tester;

/**
 * Olyan mező, amelyen genetikai kód tanulható
 */
public class Laboratory extends Field
{
	private GeneticCode code;

	/**
	 * Genetikai kód hozzáadása a mezőhöz
	 * @param c hozzáadandó genetikai kód
	 */
	public Laboratory(GeneticCode c){
		Tester.ctrMethodStart(new Object(){}.getClass().getEnclosingConstructor());

		code = c;
	}

	/**
	 * Genetikai kód tanuása a mezőn
	 * @param v tanuló virológus
	 */
	public void LearnGeneticCode(Virologist v) {
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		v.AddGeneticCode(code);

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
}
