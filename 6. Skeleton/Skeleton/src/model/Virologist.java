package model;


import model.agents.*;
import model.codes.*;
import model.equipments.*;
import model.map.*;
import model.strategy.*;
import test.Tester;

import java.util.Scanner;

public class Virologist
{
	private int actionCount;
	private int aminoAcid;
	private int nucleotide;
	private int maxAminoAcid;
	private int maxNucleotide;
	private Field field;
	private Agent agents;
	private GeneticCode codes;
	private Equipment equipments;
	private IMoveStr moveStr;
	private ILearnStr learnStr;
	private IInjectedStr injectedStr;
	private IDropStr dropStr;
	private ILootStr lootStr;
	private IEquipStr equipStr;
	private ICollectStr collectStr;
	private IInjectStr injectStr;
	private ILootedStr lootedStr;

	public Virologist(){
		Tester.ctrMethodStart(new Object(){}.getClass().getEnclosingMethod());

		//TODO be kell állítani a def str-eket meg a valtozokat
	}

	public void Move()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
	
	public void Move(Field field)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
	
	public void SetField(Field f)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
	
	public void Drop()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
	
	public void LootAminoAcidFrom(Virologist v)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
	
	public void LootNucleotideFrom(Virologist v)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
	
	public void LootEquipmentFrom(Virologist v)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		System.out.print("Van még akciója hátra a virológusnak? (Y/N) ");
		Scanner sc = new Scanner(System.in);
		String ans = sc.nextLine();

		if(ans.equals("Y")) {
			System.out.print("Tele van a virológus táskája felszerelésekkel  -3 a max-?  (Y/N) ");
			String ans2 = sc.nextLine();

			if(ans2.equals("N"))
				lootStr.LootEquipment(this, v);
		}

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
	
	public void Collect()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		System.out.print("Van még akciója hátra a virológusnak? (Y/N) ");
		Scanner sc = new Scanner(System.in);
		String ans = sc.nextLine();

		if(ans.equals("Y")){

		}

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
	
	public void Learn()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		System.out.print("Van még akciója hátra a virológusnak? (Y/N) ");
		Scanner sc = new Scanner(System.in);
		String ans = sc.nextLine();

		if(ans.equals("Y")){

		}

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
	
	public void Equip()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		System.out.print("Van még akciója hátra a virológusnak? (Y/N) ");
		Scanner sc = new Scanner(System.in);
		String ans = sc.nextLine();

		if(ans.equals("Y")){

		}

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
	
	public void AddAgent(Agent a)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
	
	public void RemoveAgent(Agent a)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
	
	public void AddEquipment(Equipment e)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
	
	public Equipment GetEquipment()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
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

		System.out.print("Van még akciója hátra a virológusnak? (Y/N) ");
		Scanner sc = new Scanner(System.in);
		String ans = sc.nextLine();

		if(ans.equals("Y"))
			injectStr.Inject(this, v, code);

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
	
	public boolean TargetedWith(Agent a)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
	
	public void StealAminoAcid(Virologist self)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
	
	public void StealNukleotid(Virologist self)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
	
	public void StealEquipment(Virologist self)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
	
	public void RemoveGeneticCodes()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
	
	public void RemoveAgents()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
	
	public void DecreaseActions()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
	
	public void EndTurn()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
	
	public void AddAminoAcid(int delta)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
	
	public void AddNucleotide(int delta)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
	
	public void RemoveNucleotide(int delta) throws Exception
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
	
	public void RemoveAminoAcid(int delta) throws Exception
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
	
	public void IncreaseLimit(int delta)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
	
	public void DecreaseLimit(int delta)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
	
	public int GetAminoAcid()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
	
	public int GetNucleotide()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
	
	public void Update()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	//azert lehetne private, mert a block-nak nem kell meghivnia, eleg a removeagents-et,
	// ami meg majd meghivja a restetet, de meg azert ne hivja meg a restet,
	// mert a szekvencian sem abrazoltam, hogy meghivna a restet
	public void Reset()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
	
	public void SetDropStr(IDropStr d)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
	
	public void SetMoveStr(IMoveStr m)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
	
	public void SetLearnStr(ILearnStr l)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
	
	public void SetLootStr(ILootStr l)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
	
	public void SetInjectStr(IInjectStr i)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
	
	public void SetInjectedStr(IInjectedStr l)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
	
	public void SetEquipStr(IEquipStr e)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
	
	public void SetCollectStr(ICollectStr c)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
	
	public void SetLootedStr(ILootedStr l)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}
}
