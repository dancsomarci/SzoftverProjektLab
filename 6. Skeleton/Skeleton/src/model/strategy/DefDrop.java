package model.strategy;



import model.Virologist;
import model.equipments.Equipment;
import model.map.Field;
import test.Tester;

/**
 * Sikeres védőfelszerelés eldobásért felelős stratégia
 */
public class DefDrop implements IDropStr
{
	/**
	 * TODO
	 */
	public DefDrop(){
		Tester.ctrMethodStart(new Object(){}.getClass().getEnclosingConstructor());
	}

	public void Drop(Virologist v, Field f, Equipment e)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		e.Disable(v);
		f.Drop(e);
		v.Reset();
		v.DecreaseActions();

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
}
