package model.strategy;


import model.Virologist;
import model.map.Field;
import test.Tester;

public class DefMove implements IMoveStr
{
	public DefMove(){
		Tester.ctrMethodStart(new Object(){}.getClass().getEnclosingConstructor());
	}

	public void Move(Virologist v, Field from, Field to)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		from.RemoveVirologist(v);
		to.AddVirologist(v);
		v.DecreaseActions();

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
}
