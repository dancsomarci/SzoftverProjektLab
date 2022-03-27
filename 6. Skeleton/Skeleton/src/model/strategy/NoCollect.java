package model.strategy;

import model.Virologist;
import model.map.Field;
import test.Tester;

public class NoCollect implements ICollectStr
{
	public NoCollect(){
		Tester.ctrMethodStart(new Object(){}.getClass().getEnclosingConstructor());
	}

	@Override
	public void Collect(Virologist v, Field f) {
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
}
