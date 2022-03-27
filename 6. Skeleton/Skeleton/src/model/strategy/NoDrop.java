package model.strategy;


import model.Virologist;
import model.equipments.Equipment;
import test.Tester;

public class NoDrop implements IDropStr
{
	public  NoDrop(){
		Tester.ctrMethodStart(new Object(){}.getClass().getEnclosingConstructor());
	}

	public void Drop(Virologist v, Equipment e)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
}
