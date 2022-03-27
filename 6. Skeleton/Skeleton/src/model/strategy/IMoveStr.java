package model.strategy;



import model.Virologist;
import model.map.Field;

public interface IMoveStr
{
	void Move(Virologist v, Field from, Field to);
}
