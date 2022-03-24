package model.strategy;


import model.Virologist;
import model.codes.GeneticCode;

public interface IInjectStr
{
	void Inject(Virologist v, Virologist target, GeneticCode gc);

}
