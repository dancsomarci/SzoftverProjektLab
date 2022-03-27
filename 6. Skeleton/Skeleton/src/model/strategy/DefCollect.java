package model.strategy;


import model.Virologist;
import model.map.Field;
import test.Tester;

public class DefCollect implements ICollectStr
{
	public DefCollect(){
		Tester.ctrMethodStart(new Object(){}.getClass().getEnclosingConstructor());
	}

	@Override
	public void Collect(Virologist v, Field f) {
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		f.CollectMaterial(v);

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

}
