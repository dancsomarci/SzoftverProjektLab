package model.strategy;



import model.Virologist;
import model.map.Field;
import test.Tester;

public class NoMove implements IMoveStr
{
	public NoMove(){
		Tester.ctrMethodStart(new Object(){}.getClass().getEnclosingConstructor());
	}

	public void Move(Virologist v, Field from, Field to)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
}
