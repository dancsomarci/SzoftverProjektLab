package model.strategy;


import model.Virologist;
import model.map.Field;
import test.Tester;

public class DefLearn implements ILearnStr
{
	public DefLearn(){
		Tester.ctrMethodStart(new Object(){}.getClass().getEnclosingConstructor());
	}

	public void Learn(Virologist v, Field f)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		f.LearnGeneticCode(v);
		v.DecreaseActions();

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
}
