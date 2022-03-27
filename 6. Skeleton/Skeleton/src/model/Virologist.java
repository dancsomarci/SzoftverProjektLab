package model;


import model.agents.*;
import model.codes.*;
import model.equipments.*;
import model.map.*;
import model.strategy.*;
import test.Tester;

import java.util.ArrayList;
import java.util.Random;

/**
 *  A virologusert felelo osztaly. Ezeket az objektumokat
 *  fogjak tudni iranyitani a jatekosok a jatek soran es
 *  kulonbozo interakciokat vegrehajtani veluk.
 */
public class Virologist
{
	/**
	 *  Azt a mennyiseget tarolja, hogy mennyi lepest tud vegre hajtani a korben a virologus.
	 * */
	private int actionCount;

	/**
	 *  A virologus altal birtokolt aminosav mennyiseget tarolja.
	 * */
	private int aminoAcid;

	/**
	 *  A virologus altal birtokolt nukleotid mennyiseget tarolja.
	 * */
	private int nucleotide;

	/**
	 *  A virologus altal maximum birtokolhato nukleotid es aminosav mennyiseget rogziti.
	 * */
	private int limit;


	/**
	 *  Azt a mezot tarolja ahol eppen a virologus tartozkodik.
	 * */
	private Field field;

	/**
	 * A Game osztaly peldanya ami a korok vezerlesehez szukseges
	 */
	private Game game;

	/**
	 *  Megtanult agensek listaja
	 */
	private ArrayList<Agent> agents;

	/**
	 * Az eddigi megtanult genetikai kodok listaja
	 */
	private ArrayList<GeneticCode> codes;

	/**
	 * A birtokolt felszerelesek listaja
	 */
	private ArrayList<Equipment> equipments;

	/**
	 * A virologus mozgasi startegiajat tarolja.
	 */
	private IMoveStr moveStr;

	/**
	 * A virologus tanulasi startegiajat tarolja.
	 */
	private ILearnStr learnStr;

	/**
	 * Erre a virologusra felkenendo agensekkel kapcsolatos strategiat tarolja
	 */
	private IInjectedStr injectedStr;

	/**
	 * A virologus targy eldobasi strategiajat tarolja.
	 */
	private IDropStr dropStr;

	/**
	 * A virologus lootolasi strategiajat tarolja.
	 */
	private ILootStr lootStr;

	/**
	 * A virologus targyfelveteli startegiajat tarolja.
	 */
	private IEquipStr equipStr;

	/**
	 * A virologus anyag gyujtesi strategiajat tarolja.
	 */
	private ICollectStr collectStr;

	/**
	 * A virologus agensfelkenesi startegiajat tarolja.
	 */
	private IInjectStr injectStr;

	/**
	 * A virologus kirablasara vonatkozo startegiajat tarolja.
	 */
	private ILootedStr lootedStr;

	/**
	 * Az osztaly konstruktora, beallitja az alapertelmezett strategiakat.
	 */
	public Virologist(){
		Tester.ctrMethodStart(new Object(){}.getClass().getEnclosingConstructor());

		equipments = new ArrayList<>(3);
		codes = new ArrayList<>();
		agents = new ArrayList<>();

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
	 * Beallitja a jatek osztaly peldanyanak referenciajat
	 * @param g a beallitando objektum
	 */
	public void AddGame(Game g){
		game = g;
	}

	/**
	 * A virologus random mozgasaert felel, ezt egy bizonyos agens valthatja ki.
	 */
	public void Move()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		if (Tester.getUserInput("Van meg akcioja hatra a virologusnak? (Y/N) ", "Y")) {
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
	 *  Ez a fuggveny kezdemenyezi a tartozkodasi mezorol valo elmozdulast,
	 *  meghivja IMoveStr-t ami elvegzi a tobbi fuggveny hivast a strategianak megfeleloen.
	 *
	 * @param field  Megadjuk azt a mezot amire elszeretnenk mozditani a virologust
	 */
	public void Move(Field field)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		if (Tester.getUserInput("Van meg akcioja hatra a virologusnak? (Y/N) ", "Y")) {
			moveStr.Move(this, this.field, field);
		}
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Beallitja a fuggveny a parameterul kapott mezot, a tartozkodasi mezore
	 *
	 * @param f Az a mezo amire eppen atleptunk
	 */
	public void SetField(Field f)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		field = f;
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * A fuggveny egy veletlenszeru felszereles eldobasaert felel.
	 */
	public void Drop()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		if (Tester.getUserInput("Van meg akcioja hatra a virologusnak? (Y/N) ", "Y")) {
			if (equipments.size() > 0) {
				dropStr.Drop(this, field, equipments.remove(new Random().nextInt(equipments.size())));
			}
		}
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * A fuggveny a parameterul kapott virologustol valo aminosav lootolast kezdemenyezi,
	 * meghivja ILootStr fuggvenyet ami elvegzi a megfelelo fuggveny hivasokat.
	 *
	 * @param v A kivalaszott virologus akitol szeretnenk lootolni
	 */
	public void LootAminoAcidFrom(Virologist v)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		if (Tester.getUserInput("Van meg akcioja hatra a virologusnak? (Y/N) ", "Y")) {
			lootStr.LootAmino(this, v);
		}
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * A fuggveny a parameterul kapott virologustol valo aminosav lootolast kezdemenyezi,
	 * meghivja ILootStr fuggvenyet ami elvegzi a megfelelo fuggveny hivasokat.
	 *
	 * @param v @param v A kivalaszott virologus akitol szeretnenk lootolni
	 */
	public void LootNucleotideFrom(Virologist v)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		if (Tester.getUserInput("Van meg akcioja hatra a virologusnak? (Y/N) ", "Y")) {
			lootStr.LootNucleotide(this, v);
		}
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * A fuggveny a parameterul kapott virologustol valo aminosav lootolast kezdemenyezi,
	 * meghivja ILootStr fuggvenyet ami elvegzi a megfelelo fuggveny hivasokat.
	 *
	 * @param v A kivalaszott virologus akitol szeretnenk lootolni
	 */
	public void LootEquipmentFrom(Virologist v)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		if (Tester.getUserInput("Van meg akcioja hatra a virologusnak? (Y/N) ", "Y")) {
			if (Tester.getUserInput("Van hely a virologusnal vedofelszerlesnek? (Y/N) ", "Y")){
				lootStr.LootEquipment(this, v);
			}
		}

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Anyag felvetelt kezdemenyez az adott mezon, a strategia kezeli a megfelelo fuggveny hivasokat
	 */
	public void Collect()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		if (Tester.getUserInput("Van meg akcioja hatra a virologusnak? (Y/N) ", "Y")) {
			collectStr.Collect(this, field);
		}

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Az adott mezon levo genetikai kod megtanulasat kezdemenyezi, ehhez meghivja a learnStr-t,
	 * ami elvegzi az allapotnak megfelelo fuggveny hivasokat.
	 */
	public void Learn()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		if (Tester.getUserInput("Van meg akcioja hatra a virologusnak? (Y/N) ", "Y")) {
			learnStr.Learn(this, field);
		}

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Ez a fuggveny kezdemenyezi egy mezon levo felszereles felvetelet.
	 */
	public void Equip()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		if (Tester.getUserInput("Van meg akcioja hatra a virologusnak? (Y/N) ", "Y")) {
			equipStr.Equip(this, field);
		}

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * uj agenst helyezunk a taroloba
	 * @param a agens amit eltarolunk
	 */
	public void AddAgent(Agent a)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		agents.add(a);
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Kivesz egy agenst a tarolobol
	 * @param a agens amit kiakarunk venni
	 */
	public void RemoveAgent(Agent a)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		agents.remove(a);
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * A parameterben szereplo felszerelessel boviti, az eppen birtokolt felszereleseket sikeres lefutas eseten.
	 * @param e A felszerelest amit felvettunk
	 */
	public void AddEquipment(Equipment e)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		if (equipments.size() < 3)
			equipments.add(e);
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Az equipment getter fuggvenye.
	 * @return Vissza ter a birtokolt felszerelesekkel, ha nincs akkor kivetelt dob
	 */
	public Equipment GetEquipment()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		if (equipments.size() == 0)
			throw new IndexOutOfBoundsException("ures a felszereles tarolo");
		Equipment e = equipments.remove(equipments.size()-1);

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
		return e;
	}

	/**
	 * A megtanult genetikai kodok listajat boviti
	 * @param code Az uj kod
	 */
	public void AddGeneticCode(GeneticCode code)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		codes.add(code);
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * egy masik virologus megkenese egy agenssel.
	 * @param v a masik virologus
	 * @param code az agens letrehozasahoz szukseges genetikai kod
	 */
	public void Inject(Virologist v, GeneticCode code)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		if (Tester.getUserInput("Van meg akcioja hatra a virologusnak? (Y/N) ", "Y"))
		{
			injectStr.Inject(this, v, code);
		}

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Adott virologust lehet ezzel fuggvennyel  megcelozni egy agensfelkenessel.
	 * @param a Az az agens amit szeretnenk kenni
	 */
	public void TargetedWith(Agent a)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		injectedStr.Injected(this, a);
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Kezdemenyezi aminosav levonasat a virologustol ami a strategianak megfeleloen tortenik
	 * @param self a virologus akitol levonjuk az aminosav mennyiseget
	 */
	public void StealAminoAcid(Virologist self)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		lootedStr.LootedForAminoAcid(this, self);
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Kezdemenyezi nukleotid levonasat a virologustol ami a strategianak megfeleloen tortenik
	 * @param self a virologus akitol levonjuk a nukleotid mennyiseget
	 */
	public void StealNukleotid(Virologist self)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		lootedStr.LootedForNukleotide(self, this);
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Veletlenszeruen kivalaszt egy felszerelest a meglevok kozul
	 * @param self a virologus aki elszeretne tulajdonitani a felszerelest
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
	 * A torli az eddig megtanult genetikai kodokat.
	 */
	public void RemoveGeneticCodes()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		codes.clear();
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Kiveszi a tarolobol az osszes agenst.
	 */
	public void RemoveAgents()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		agents.clear();
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Drekementalja a koben vegrehajthato interakciok szamat
	 */
	public void DecreaseActions()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		if (actionCount > 0)
		actionCount--;
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Tovabb adja a kort(atadja a lepesi jogot) a soron kovetkezo virologusnak
	 */
	public void EndTurn()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		game.NextPlayer(codes.size());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * A parameter mertekevel noveli a birtokolt aminosav mennyiseget
	 * @param delta a mennyiseg amivel novelunk
	 */
	public void AddAminoAcid(int delta)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		aminoAcid+= delta;
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * A parameter mertekevel noveli a birtokolt nukleotid mennyiseget
	 * @param delta a mennyiseg amivel novelunk
	 */
	public void AddNucleotide(int delta)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		nucleotide+= delta;
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * A parameter mertekevel csokkenti a birtokolt nukleotid mennyiseget
	 * @param delta a mennyiseg amivel csokkentunk
	 * @throws Exception ha nincs megfelelo mennyiseg akkor kivetelt dobunk
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
	 * A parameter mertekevel csokkenti a birtokolt aminosav mennyiseget
	 * @param delta a mennyiseg amivel csokkentunk
	 * @throws Exception ha nincs megfelelo mennyiseg akkor kivetelt dobunk
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
	 * noveli a birtokolhato anyag(aminosav es nukleotid) mennyiseget
	 * @param delta noveles merteke
	 */
	public void IncreaseLimit(int delta)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		limit += delta;
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Csokkenti a birtokolhato anyag(aminosav es nukleotid) mennyiseget
	 * @param delta csokkentes merteke
	 */
	public void DecreaseLimit(int delta)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		limit -= delta;
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Az aminosav mezo gettere
	 * @return A birtokolt aminosav mennyiseg
	 */
	public int GetAminoAcid()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
		return aminoAcid;
	}

	/**
	 * A nulceotide mezo gettere
	 * @return A birtokolt nukleotid mennyisege
	 */
	public int GetNucleotide()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
		return nucleotide;
	}

	/**
	 * Frissiti a virologus strategiait
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
	 * Vissza strategiakat az alapallapotukba. Azt ertjuk alapnak ami a konstruktorban van.
	 */
	public void Reset()
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());

		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Beallitja a virologus IDropStr-jet
	 * @param d beallitando strategia
	 */
	public void SetDropStr(IDropStr d)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		dropStr = d;
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Beallitja a virologus IMoveStr-jet
	 * @param m beallitando strategia
	 */
	public void SetMoveStr(IMoveStr m)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		moveStr = m;
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Beallitja a virologus ILearnnStr-jet
	 * @param l beallitando strategia
	 */
	public void SetLearnStr(ILearnStr l)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		learnStr = l;
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Beallitja a virologus ILootStr-jet
	 * @param l beallitando strategia
	 */
	public void SetLootStr(ILootStr l)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		lootStr = l;
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Beallitja a virologus IInject-jet
	 * @param i beallitando strategia
	 */
	public void SetInjectStr(IInjectStr i)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		injectStr = i;
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Beallitja a virologus IInjected-jet
	 * @param i beallitando strategia
	 */
	public void SetInjectedStr(IInjectedStr i)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		injectedStr = i;
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Beallitja a virologus IEquipStr-jet
	 * @param e beallitando strategia
	 */
	public void SetEquipStr(IEquipStr e)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		equipStr = e;
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Beallitja a virologus ICollectStr-jet
	 * @param c beallitando strategia
	 */
	public void SetCollectStr(ICollectStr c)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		collectStr= c;
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

	/**
	 * Beallitja a virologus ILootedStr-jet
	 * @param l beallitando strategia
	 */
	public void SetLootedStr(ILootedStr l)
	{
		Tester.methodStart(new Object(){}.getClass().getEnclosingMethod());
		lootedStr =l;
		Tester.methodEnd(new Object(){}.getClass().getEnclosingMethod());
	}

}
