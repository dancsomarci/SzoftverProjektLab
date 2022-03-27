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
 *  A virológusért felelő osztály. Ezeket az objektumokat
 *  fogják tudni irányítani a játékosok a játék során és
 *  különbőző interakciókat végrehajtani velük.
 */
public class Virologist
{
	/**
	 *  Azt a mennyiséget tárolja, hogy mennyi lépést tud végre hajtani a körben a virológus.
	 * */
	private int actionCount;

	/**
	 *  A virológus által birtokolt aminosav mennyiségét tárolja.
	 * */
	private int aminoAcid;

	/**
	 *  A virológus által birtokolt nukleotid mennyiségét tárolja.
	 * */
	private int nucleotide;

	/**
	 *  A virológus által maximum birtokolható nukleotid és aminosav mennyiségét rögzíti.
	 * */
	private int limit;


	/**
	 *  Azt a mezőt tárolja ahol éppen a virológus tartózkodik.
	 * */
	private Field field;

	/**
	 * A Game osztály példánya ami a körök vezérlésehez szükséges
	 */
	private Game game;

	/**
	 *  Megtanult ágensek listája
	 */
	private ArrayList<Agent> agents;

	/**
	 * Az eddigi megtanult genetikai kódok listája
	 */
	private ArrayList<GeneticCode> codes;

	/**
	 * A birtokolt felszerelések listája
	 */
	private ArrayList<Equipment> equipments;

	/**
	 * A virológus mozgási startégiáját tárolja.
	 */
	private IMoveStr moveStr;

	/**
	 * A virológus tanulási startégiáját tárolja.
	 */
	private ILearnStr learnStr;

	/**
	 * Erre a virológusra felkenendő ágensekkel kapcsolatos stratégiát tárolja
	 */
	private IInjectedStr injectedStr;

	/**
	 * A virológus tárgy eldobási stratégiáját tárolja.
	 */
	private IDropStr dropStr;

	/**
	 * A virológus lootolási stratégiáját tárolja.
	 */
	private ILootStr lootStr;

	/**
	 * A virológus tárgyfelvételi startégiáját tárolja.
	 */
	private IEquipStr equipStr;

	/**
	 * A virológus anyag gyűjtési stratégiáját tárolja.
	 */
	private ICollectStr collectStr;

	/**
	 * A virológus ágensfelkenési startégiáját tárolja.
	 */
	private IInjectStr injectStr;

	/**
	 * A virológus kirablására vonatkozó startégiáját tárolja.
	 */
	private ILootedStr lootedStr;

	/**
	 * Az osztály konstruktora, beállítja az alapértelmezett stratégiákat.
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
	 * A virologus random mozgásáért felel, ezt egy bizonyos ágens válthatja ki.
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
	 *  Ez a függvény kezdeményezi a tartózkodási mezőről való elmozdulást,
	 *  meghívja IMoveStr-t ami elvégzi a többi függvény hívást a stratégiának megfelelően.
	 *
	 * @param field  Megadjuk azt a mezőt amire elszeretnénk mozdítani a virológust
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
	 * Beállítja a függvény a paraméterül kapott mezőt, a tartózkodási mezőre
	 *
	 * @param f Az a mező amire éppen átléptünk
	 */
	public void SetField(Field f)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		field = f;
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * A függvény egy véletlenszerű felszerelés eldobásáért felel.
	 */
	public void Drop()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		if (Tester.getUserInput("Van még akciója hátra a virológusnak? (Y/N) ", "Y")) {
			if (equipments.size() > 0) {
				dropStr.Drop(this, field, equipments.remove(new Random().nextInt(equipments.size())));
			}
		}
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * A függvény a paraméterül kapott virológustól való aminosav lootolást kezdeményezi,
	 * meghívja ILootStr függvényét ami elvégzi a megfelelő függvény hívásokat.
	 *
	 * @param v A kiválaszott virológus akitől szeretnénk lootolni
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
	 * A függvény a paraméterül kapott virológustól való aminosav lootolást kezdeményezi,
	 * meghívja ILootStr függvényét ami elvégzi a megfelelő függvény hívásokat.
	 *
	 * @param v @param v A kiválaszott virológus akitől szeretnénk lootolni
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
	 * A függvény a paraméterül kapott virológustól való aminosav lootolást kezdeményezi,
	 * meghívja ILootStr függvényét ami elvégzi a megfelelő függvény hívásokat.
	 *
	 * @param v A kiválaszott virológus akitől szeretnénk lootolni
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
	 * Anyag felvételt kezdeményez az adott mezőn, a stratégia kezeli a megfelelő függvény hívásokat
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
	 * Az adott mezőn lévő genetikai kód megtanulását kezdeményezi, ehhez meghívja a learnStr-t,
	 * ami elvégzi az állapotnak megfelelő függvény hívásokat.
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
	 * Ez a függvény kezdeményezi egy mezőn lévő felszerelés felvételét.
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
	 * Új ágenst helyezünk a tárolóba
	 * @param a Ágens amit eltárolunk
	 */
	public void AddAgent(Agent a)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		agents.add(a);
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Kivesz egy ágenst a tárolóból
	 * @param a Ágens amit kiakarunk venni
	 */
	public void RemoveAgent(Agent a)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		agents.remove(a);
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * A paraméterben szereplő felszereléssel bővíti, az éppen birtokolt felszereléseket sikeres lefutás esetén.
	 * @param e A felszerelést amit felvettünk
	 */
	public void AddEquipment(Equipment e)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		if (equipments.size() < 3)
			equipments.add(e);
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Az equipment getter függvénye.
	 * @return Vissza tér a birtokolt felszerelésekkel, ha nincs akkor kivételt dob
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

	/**
	 * A megtanult genetikai kódok listáját bővíti
	 * @param code Az új kód
	 */
	public void AddGeneticCode(GeneticCode code)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		codes.add(code);
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
	 * Kezdeményezi aminosav levonását a virológustól ami a stratégiának megfelelően történik
	 * @param self a virológus akitől levonjuk az aminosav mennyiséget
	 */
	public void StealAminoAcid(Virologist self)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		lootedStr.LootedForAminoAcid(this, self);
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Kezdeményezi nukleotid levonását a virológustól ami a stratégiának megfelelően történik
	 * @param self a virológus akitől levonjuk a nukleotid mennyiséget
	 */
	public void StealNukleotid(Virologist self)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		lootedStr.LootedForNukleotide(this, self);
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * TODO
	 * @param self a virológus aki elszeretné tulajdonítani a felszerelést
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
	 * A törli az eddig megtanult genetikai kódokat.
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
	 * Drekementálja a köben végrehajtható interakciók számát
	 */
	public void DecreaseActions()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		if (actionCount > 0)
		actionCount--;
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Tovább adja a kört(átadja a lépési jogot) a soron következő virológusnak
	 */
	public void EndTurn()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		game.NextPlayer(codes.size());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * A paraméter mértékével növeli a birtokolt aminosav mennyiséget
	 * @param delta a mennyiség amivel növelünk
	 */
	public void AddAminoAcid(int delta)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		aminoAcid+= delta;
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * A paraméter mértékével növeli a birtokolt nukleotid mennyiséget
	 * @param delta a mennyiség amivel növelünk
	 */
	public void AddNucleotide(int delta)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		nucleotide+= delta;
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * A paraméter mértékével csökkenti a birtokolt nukleotid mennyiséget
	 * @param delta a mennyiség amivel csökkentünk
	 * @throws Exception ha nincs megfelelő mennyiség akkor kivételt dobunk
	 */
	public void RemoveNucleotide(int delta) throws Exception
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		if(delta > nucleotide){
			throw new Exception("I don't have such many nucleotide!");
		}
		nucleotide -= delta;

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * A paraméter mértékével csökkenti a birtokolt aminosav mennyiséget
	 * @param delta a mennyiség amivel csökkentünk
	 * @throws Exception ha nincs megfelelő mennyiség akkor kivételt dobunk
	 */
	public void RemoveAminoAcid(int delta) throws Exception
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		if(delta > aminoAcid){
			throw new Exception("I don't have such many amino acid!");
		}
		aminoAcid -= delta;

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * növeli a birtokolható anyag(aminosav és nukleotid) mennyiséget
	 * @param delta növelés mértéke
	 */
	public void IncreaseLimit(int delta)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		limit += delta;
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Csökkenti a birtokolható anyag(aminosav és nukleotid) mennyiséget
	 * @param delta csökkentés mértéke
	 */
	public void DecreaseLimit(int delta)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		limit -= delta;
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Az aminosav mező gettere
	 * @return A birtokolt aminosav mennyiség
	 */
	public int GetAminoAcid()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
		return aminoAcid;
	}

	/**
	 * A nulceotide mező gettere
	 * @return A birtokolt nukleotid mennyisége
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
	 * Beállítja a virológus IDropStr-jét
	 * @param d beállítandó stratégia
	 */
	public void SetDropStr(IDropStr d)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		dropStr = d;
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Beállítja a virológus IMoveStr-jét
	 * @param m beállítandó stratégia
	 */
	public void SetMoveStr(IMoveStr m)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		moveStr = m;
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Beállítja a virológus ILearnnStr-jét
	 * @param l beállítandó stratégia
	 */
	public void SetLearnStr(ILearnStr l)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		learnStr = l;
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Beállítja a virológus ILootStr-jét
	 * @param l beállítandó stratégia
	 */
	public void SetLootStr(ILootStr l)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		lootStr = l;
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Beállítja a virológus IInject-jét
	 * @param i beállítandó stratégia
	 */
	public void SetInjectStr(IInjectStr i)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		injectStr = i;
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Beállítja a virológus IInjected-jét
	 * @param i beállítandó stratégia
	 */
	public void SetInjectedStr(IInjectedStr i)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		injectedStr = i;
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Beállítja a virológus IEquipStr-jét
	 * @param e beállítandó stratégia
	 */
	public void SetEquipStr(IEquipStr e)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		equipStr = e;
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Beállítja a virológus ICollectStr-jét
	 * @param c beállítandó stratégia
	 */
	public void SetCollectStr(ICollectStr c)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		collectStr= c;
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Beállítja a virológus ILootedStr-jét
	 * @param l beállítandó stratégia
	 */
	public void SetLootedStr(ILootedStr l)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		lootedStr =l;
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

}
