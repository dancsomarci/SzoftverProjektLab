package model.strategy;


import model.Virologist;
import model.map.Field;
import test.Tester;

public class NoLearn implements ILearnStr
{
	public NoLearn(){
		Tester.ctrMethodStart(new Object(){}.getClass().getEnclosingConstructor());
	}

	public void Learn(Virologist v, Field f)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
}
