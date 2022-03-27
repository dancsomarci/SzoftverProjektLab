package model;


import model.agents.*;
import model.codes.*;
import model.equipments.*;
import model.map.*;
import model.strategy.*;
import test.Tester;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * TODO
 */
public class Virologist
{
	/**
	 * TODO
	 */
	private int actionCount;

	/**
	 * TODO
	 */
	private int aminoAcid;

	/**
	 * TODO
	 */
	private int nucleotide;

	/**
	 * TODO
	 */
	private int limit;


	/**
	 * TODO
	 */
	private Field field;


	/**
	 * TODO
	 */
	private ArrayList<Agent> agents;

	/**
	 * TODO
	 */
	private ArrayList<GeneticCode> codes;

	/**
	 * TODO
	 */
	private ArrayList<Equipment> equipments;

	/**
	 * TODO
	 */
	private IMoveStr moveStr;

	/**
	 * TODO
	 */
	private ILearnStr learnStr;

	/**
	 * TODO
	 */
	private IInjectedStr injectedStr;

	/**
	 * TODO
	 */
	private IDropStr dropStr;

	/**
	 * TODO
	 */
	private ILootStr lootStr;

	/**
	 * TODO
	 */
	private IEquipStr equipStr;

	/**
	 * TODO
	 */
	private ICollectStr collectStr;

	/**
	 * TODO
	 */
	private IInjectStr injectStr;

	/**
	 * TODO
	 */
	private ILootedStr lootedStr;

	/**
	 * TODO
	 */
	public Virologist(){
		Tester.ctrMethodStart(new Object(){}.getClass().getEnclosingConstructor());

		lootedStr = new DefLooted();
		injectedStr = new DefInjected();
		collectStr = new DefCollect();
		equipStr = new DefEquip();
		lootStr = new DefLoot();
		dropStr = new DefDrop();
		injectStr = new DefInject();
		learnStr = new DefLearn();
		moveStr = new DefMove();
	}

	/**
	 * TODO
	 */
	public void Move()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		if (Tester.getUserInput("Van még akciója hátra a virológusnak? (Y/N) ", "Y")) {
			ArrayList<Field> fields = field.GetNeighbours();
			if (fields.size() == 0) {
				Random random = new Random();
				int r = random.nextInt(fields.size());
				field.RemoveVirologist(this);
				fields.get(r).AddVirologist(this);
			}
		}
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * TODO
	 * @param field
	 */
	public void Move(Field field)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		if (Tester.getUserInput("Van még akciója hátra a virológusnak? (Y/N) ", "Y")) {
			moveStr.Move(this, this.field, field);
		}
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * TODO
	 * @param f
	 */
	public void SetField(Field f)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		field = f;
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * TODO
	 */
	public void Drop()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		if (Tester.getUserInput("Van még akciója hátra a virológusnak? (Y/N) ", "Y")) {
			if (equipments.size() > 0) {
				dropStr.Drop(this, equipments.remove(new Random().nextInt(equipments.size())));
			}
		}
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * TODO
	 * @param v
	 */
	public void LootAminoAcidFrom(Virologist v)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		if (Tester.getUserInput("Van még akciója hátra a virológusnak? (Y/N) ", "Y")) {
			lootStr.LootAmino(this, v);
		}
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * TODO
	 * @param v
	 */
	public void LootNucleotideFrom(Virologist v)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		if (Tester.getUserInput("Van még akciója hátra a virológusnak? (Y/N) ", "Y")) {
			lootStr.LootNucleotide(this, v);
		}
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * TODO
	 * @param v
	 */
	public void LootEquipmentFrom(Virologist v)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		if (Tester.getUserInput("Van még akciója hátra a virológusnak? (Y/N) ", "Y")) {
			if (Tester.getUserInput("Van hely a virológusnál védőfelszerlésnek? (Y/N) ", "Y")){
				lootStr.LootEquipment(this, v);
			}
		}

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * TODO
	 */
	public void Collect()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		if (Tester.getUserInput("Van még akciója hátra a virológusnak? (Y/N) ", "Y")) {
			collectStr.Collect(this, field);
		}

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * TODO
	 */
	public void Learn()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		if (Tester.getUserInput("Van még akciója hátra a virológusnak? (Y/N) ", "Y")) {
			learnStr.Learn(this, field);
		}

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * TODO
	 */
	public void Equip()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		if (Tester.getUserInput("Van még akciója hátra a virológusnak? (Y/N) ", "Y")) {
			equipStr.Equip(this, field);
		}

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * TODO
	 * @param a
	 */
	public void AddAgent(Agent a)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		agents.add(a);
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * TODO
	 * @param a
	 */
	public void RemoveAgent(Agent a)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		agents.remove(a);
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * TODO
	 * @param e
	 */
	public void AddEquipment(Equipment e)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		if (equipments.size() < 3)
			equipments.add(e);
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * TODO
	 * @return
	 */
	public Equipment GetEquipment()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		if (equipments.size() == 0)
			throw new IndexOutOfBoundsException("Üres a felszerelés tároló");
		Equipment e = equipments.remove(equipments.size()-1);

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
		return e;
	}
	
	public void AddGeneticCode(GeneticCode code)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * egy másik virológus megkenése egy ágenssel.
	 * @param v a másik virológus
	 * @param code az ágens létrehozásához szükséges genetikai kód
	 */
	public void Inject(Virologist v, GeneticCode code)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		if (Tester.getUserInput("Van még akciója hátra a virológusnak? (Y/N) ", "Y"))
		{
			injectStr.Inject(this, v, code);
		}

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * TODO
	 * @param a
	 */
	public void TargetedWith(Agent a)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		injectedStr.Injected(this, a);
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * TODO
	 * @param self
	 */
	public void StealAminoAcid(Virologist self)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		lootedStr.LootedForAminoAcid(this, self);
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * TODO
	 * @param self
	 */
	public void StealNukleotid(Virologist self)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		lootedStr.LootedForNukleotide(this, self);
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * TODO
	 * @param self
	 */
	public void StealEquipment(Virologist self)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		if (equipments.size() > 0) {
			Random random = new Random();
			int r = random.nextInt(equipments.size());
			Equipment e = equipments.get(r);
			lootedStr.LootedForEquipment(this, self, e);
		}
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * TODO
	 */
	public void RemoveGeneticCodes()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		codes.clear();
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * TODO
	 */
	public void RemoveAgents()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		agents.clear();
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * TODO
	 */
	public void DecreaseActions()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		if (actionCount > 0)
		actionCount--;
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * TODO
	 */
	public void EndTurn()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * TODO
	 * @param delta
	 */
	public void AddAminoAcid(int delta)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * TODO
	 * @param delta
	 */
	public void AddNucleotide(int delta)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * TODO
	 * @param delta
	 * @throws Exception
	 */
	public void RemoveNucleotide(int delta) throws Exception
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * TODO
	 * @param delta
	 * @throws Exception
	 */
	public void RemoveAminoAcid(int delta) throws Exception
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * TODO
	 * @param delta
	 */
	public void IncreaseLimit(int delta)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * TODO
	 * @param delta
	 */
	public void DecreaseLimit(int delta)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * TODO
	 * @return
	 */
	public int GetAminoAcid()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
		return aminoAcid;
	}

	/**
	 * TODO
	 * @return
	 */
	public int GetNucleotide()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
		return nucleotide;
	}

	/**
	 * TODO
	 */
	public void Update()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		for(Agent a: agents){
			a.Update(this);
		}
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 *
	 */
	public void Reset()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * TODO
	 * @param d
	 */
	public void SetDropStr(IDropStr d)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		dropStr = d;
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * TODO
	 * @param m
	 */
	public void SetMoveStr(IMoveStr m)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		moveStr = m;
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * TODO
	 * @param l
	 */
	public void SetLearnStr(ILearnStr l)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		learnStr = l;
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * TODO
	 * @param l
	 */
	public void SetLootStr(ILootStr l)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		lootStr = l;
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * TODO
	 * @param i
	 */
	public void SetInjectStr(IInjectStr i)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		injectStr = i;
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * TODO
	 * @param i
	 */
	public void SetInjectedStr(IInjectedStr i)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		injectedStr = i;
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * TODO
	 * @param e
	 */
	public void SetEquipStr(IEquipStr e)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		equipStr = e;
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * TODO
	 * @param c
	 */
	public void SetCollectStr(ICollectStr c)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		collectStr= c;
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * TODO
	 * @param l
	 */
	public void SetLootedStr(ILootedStr l)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		lootedStr =l;
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * TODO
	 * @param code
	 */
	public void AddgeneticCode(GeneticCode code){
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		codes.add(code);
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

}
