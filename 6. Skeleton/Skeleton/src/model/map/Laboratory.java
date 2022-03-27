package model.map;



import model.Virologist;
import model.agents.Agent;
import model.codes.GeneticCode;
import model.codes.StunCode;
import test.Tester;

/**
 *
 */
public class Laboratory extends Field
{
	private GeneticCode code;

	/**
	 * TODO
	 * @param c
	 */
	public Laboratory(GeneticCode c){
		Tester.ctrMethodStart(new Object(){}.getClass().getEnclosingConstructor());

		code = c;
	}

	/**
	 * TODO
	 * @param v tanuló virológus
	 */
	public void LearnGeneticCode(Virologist v) {
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		v.AddGeneticCode(code);

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
}
