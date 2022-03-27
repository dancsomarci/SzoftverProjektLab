package model.strategy;



import model.Virologist;
import model.equipments.Equipment;
import test.Tester;

public class DefDrop implements IDropStr
{
	/**
	 * TODO
	 */
	public DefDrop(){
		Tester.ctrMethodStart(new Object(){}.getClass().getEnclosingConstructor());
	}

	public void Drop(Virologist v, Equipment e)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		e.Disable(v);
		//v.getField().Drop(e);
		v.Reset();
		v.DecreaseActions();

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
}
