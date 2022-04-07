package model;


import model.agents.*;
import model.codes.*;
import model.equipments.*;
import model.map.*;
import model.strategy.*;
import control.Tester;

import java.util.ArrayList;
import java.util.Random;

/**
 *  A virologusert felelo osztaly. Ezeket az objektumokat
 *  fogjak tudni iranyitani a jatekosok a jatek soran es
 *  kulonbozo interakciokat vegrehajtani veluk.
 */
public class Virologist
{
	public void bark(){
		System.out.println("Virologist: " + name);
		System.out.println("\tNumber of actions left: " + actionCount);
		System.out.println("\tAmino acid: " + aminoAcid);
		System.out.println("\tNucleotide: " + nucleotide);
		System.out.println("\tEquipment: " + nucleotide);
		for (Equipment e: equipments
			 ) {
			System.out.println("\t\t" + e.toString()); //id in equipment
		}
		System.out.println("\tGenetic codes: " + nucleotide);
		for (GeneticCode c: codes
		) {
			System.out.println("\t\t" + c.toString()); //id in equipment
		}
	}

	private String name;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	private IAttackStr attackStr;

	public void SetAttackStr(IAttackStr a){
		attackStr = a;
	}

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

	public Field getField(){
		return field;
	}

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

	public ArrayList<GeneticCode> getGeneticCodes(){
		return codes;
	}

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

	public void Kill(){
		game.RemoveVirologist(this);
	}

	public void Attack(Virologist v){

	}

	public void TargetedWith(Virologist who, Agent a){

	}

	public void RemoveEquipment(Equipment e){
		equipments.remove(e);
	}

	/**
	 * A virologus random mozgasaert felel, ezt egy bizonyos agens valthatja ki.
	 */
	public void Move()
	{
		if (Tester.getUserInput("Van meg akcioja hatra a virologusnak? (Y/N) ", "Y")) {
			ArrayList<Field> fields = field.GetNeighbours();
			if (fields.size() == 0) {
				Random random = new Random();
				int r = random.nextInt(fields.size());
				field.RemoveVirologist(this);
				fields.get(r).AddVirologist(this);
			}
		}
	}

	/**
	 *  Ez a fuggveny kezdemenyezi a tartozkodasi mezorol valo elmozdulast,
	 *  meghivja IMoveStr-t ami elvegzi a tobbi fuggveny hivast a strategianak megfeleloen.
	 *
	 * @param field  Megadjuk azt a mezot amire elszeretnenk mozditani a virologust
	 */
	public void Move(Field field)
	{
		if (Tester.getUserInput("Van meg akcioja hatra a virologusnak? (Y/N) ", "Y")) {
			moveStr.Move(this, this.field, field);
		}
	}

	/**
	 * Beallitja a fuggveny a parameterul kapott mezot, a tartozkodasi mezore
	 *
	 * @param f Az a mezo amire eppen atleptunk
	 */
	public void SetField(Field f)
	{
		field = f;
	}

	/**
	 * A fuggveny egy veletlenszeru felszereles eldobasaert felel.
	 */
	public void Drop()
	{
		if (Tester.getUserInput("Van meg akcioja hatra a virologusnak? (Y/N) ", "Y")) {
			if (equipments.size() > 0) {
				dropStr.Drop(this, field, equipments.remove(new Random().nextInt(equipments.size())));
			}
		}
	}

	/**
	 * A fuggveny a parameterul kapott virologustol valo aminosav lootolast kezdemenyezi,
	 * meghivja ILootStr fuggvenyet ami elvegzi a megfelelo fuggveny hivasokat.
	 *
	 * @param v A kivalaszott virologus akitol szeretnenk lootolni
	 */
	public void LootAminoAcidFrom(Virologist v)
	{
		if (Tester.getUserInput("Van meg akcioja hatra a virologusnak? (Y/N) ", "Y")) {
			lootStr.LootAmino(this, v);
		}
	}

	/**
	 * A fuggveny a parameterul kapott virologustol valo aminosav lootolast kezdemenyezi,
	 * meghivja ILootStr fuggvenyet ami elvegzi a megfelelo fuggveny hivasokat.
	 *
	 * @param v @param v A kivalaszott virologus akitol szeretnenk lootolni
	 */
	public void LootNucleotideFrom(Virologist v)
	{
		if (Tester.getUserInput("Van meg akcioja hatra a virologusnak? (Y/N) ", "Y")) {
			lootStr.LootNucleotide(this, v);
		}
	}

	/**
	 * A fuggveny a parameterul kapott virologustol valo aminosav lootolast kezdemenyezi,
	 * meghivja ILootStr fuggvenyet ami elvegzi a megfelelo fuggveny hivasokat.
	 *
	 * @param v A kivalaszott virologus akitol szeretnenk lootolni
	 */
	public void LootEquipmentFrom(Virologist v)
	{
		if (Tester.getUserInput("Van meg akcioja hatra a virologusnak? (Y/N) ", "Y")) {
			if (Tester.getUserInput("Van hely a virologusnal vedofelszerlesnek? (Y/N) ", "Y")){
				lootStr.LootEquipment(this, v);
			}
		}
	}

	/**
	 * Anyag felvetelt kezdemenyez az adott mezon, a strategia kezeli a megfelelo fuggveny hivasokat
	 */
	public void Collect()
	{

		if (Tester.getUserInput("Van meg akcioja hatra a virologusnak? (Y/N) ", "Y")) {
			collectStr.Collect(this, field);
		}

	}

	/**
	 * Az adott mezon levo genetikai kod megtanulasat kezdemenyezi, ehhez meghivja a learnStr-t,
	 * ami elvegzi az allapotnak megfelelo fuggveny hivasokat.
	 */
	public void Learn()
	{

		if (Tester.getUserInput("Van meg akcioja hatra a virologusnak? (Y/N) ", "Y")) {
			learnStr.Learn(this, field);
		}

	}

	/**
	 * Ez a fuggveny kezdemenyezi egy mezon levo felszereles felvetelet.
	 */
	public void Equip()
	{

		if (Tester.getUserInput("Van meg akcioja hatra a virologusnak? (Y/N) ", "Y")) {
			equipStr.Equip(this, field);
		}

	}

	/**
	 * uj agenst helyezunk a taroloba
	 * @param a agens amit eltarolunk
	 */
	public void AddAgent(Agent a)
	{
		agents.add(a);
	}

	/**
	 * Kivesz egy agenst a tarolobol
	 * @param a agens amit kiakarunk venni
	 */
	public void RemoveAgent(Agent a)
	{
		agents.remove(a);
	}

	/**
	 * A parameterben szereplo felszerelessel boviti, az eppen birtokolt felszereleseket sikeres lefutas eseten.
	 * @param e A felszerelest amit felvettunk
	 */
	public void AddEquipment(Equipment e)
	{
		if (equipments.size() < 3)
			equipments.add(e);
	}

	/**
	 * Az equipment getter fuggvenye.
	 * @return Vissza ter a birtokolt felszerelesekkel, ha nincs akkor kivetelt dob
	 */
	public Equipment GetEquipment()
	{
		if (equipments.size() == 0)
			throw new IndexOutOfBoundsException("ures a felszereles tarolo");
		Equipment e = equipments.remove(equipments.size()-1);

		return e;
	}

	/**
	 * A megtanult genetikai kodok listajat boviti
	 * @param code Az uj kod
	 */
	public void AddGeneticCode(GeneticCode code)
	{
		codes.add(code);
	}

	/**
	 * egy masik virologus megkenese egy agenssel.
	 * @param v a masik virologus
	 * @param code az agens letrehozasahoz szukseges genetikai kod
	 */
	public void Inject(Virologist v, GeneticCode code)
	{

		if (Tester.getUserInput("Van meg akcioja hatra a virologusnak? (Y/N) ", "Y"))
		{
			injectStr.Inject(this, v, code);
		}

	}

	/**
	 * Adott virologust lehet ezzel fuggvennyel  megcelozni egy agensfelkenessel.
	 * @param a Az az agens amit szeretnenk kenni
	 */
	public void TargetedWith(Agent a)
	{
		injectedStr.Injected(this, a);
	}

	/**
	 * Kezdemenyezi aminosav levonasat a virologustol ami a strategianak megfeleloen tortenik
	 * @param self a virologus akitol levonjuk az aminosav mennyiseget
	 */
	public void StealAminoAcid(Virologist self)
	{

		lootedStr.LootedForAminoAcid(this, self);
	}

	/**
	 * Kezdemenyezi nukleotid levonasat a virologustol ami a strategianak megfeleloen tortenik
	 * @param self a virologus akitol levonjuk a nukleotid mennyiseget
	 */
	public void StealNukleotid(Virologist self)
	{
		lootedStr.LootedForNukleotide(self, this);
	}

	/**
	 * Veletlenszeruen kivalaszt egy felszerelest a meglevok kozul
	 * @param self a virologus aki elszeretne tulajdonitani a felszerelest
	 */
	public void StealEquipment(Virologist self)
	{
		if (equipments.size() > 0) {
			Random random = new Random();
			int r = random.nextInt(equipments.size());
			Equipment e = equipments.get(r);
			lootedStr.LootedForEquipment(this, self, e);
		}
	}

	/**
	 * A torli az eddig megtanult genetikai kodokat.
	 */
	public void RemoveGeneticCodes()
	{
		codes.clear();
	}

	/**
	 * Kiveszi a tarolobol az osszes agenst.
	 */
	public void RemoveAgents()
	{
		agents.clear();
	}

	/**
	 * Drekementalja a koben vegrehajthato interakciok szamat
	 */
	public void DecreaseActions()
	{
		if (actionCount > 0)
		actionCount--;
	}

	/**
	 * Tovabb adja a kort(atadja a lepesi jogot) a soron kovetkezo virologusnak
	 */
	public void EndTurn()
	{

		game.NextPlayer(codes.size());

	}

	/**
	 * A parameter mertekevel noveli a birtokolt aminosav mennyiseget
	 * @param delta a mennyiseg amivel novelunk
	 */
	public void AddAminoAcid(int delta)
	{
		aminoAcid+= delta;
	}

	/**
	 * A parameter mertekevel noveli a birtokolt nukleotid mennyiseget
	 * @param delta a mennyiseg amivel novelunk
	 */
	public void AddNucleotide(int delta)
	{
		nucleotide+= delta;
	}

	/**
	 * A parameter mertekevel csokkenti a birtokolt nukleotid mennyiseget
	 * @param delta a mennyiseg amivel csokkentunk
	 * @throws Exception ha nincs megfelelo mennyiseg akkor kivetelt dobunk
	 */
	public void RemoveNucleotide(int delta) throws Exception
	{

		if(delta > nucleotide){
			throw new Exception("I don't have such many nucleotide!");
		}
		nucleotide -= delta;

	}

	/**
	 * A parameter mertekevel csokkenti a birtokolt aminosav mennyiseget
	 * @param delta a mennyiseg amivel csokkentunk
	 * @throws Exception ha nincs megfelelo mennyiseg akkor kivetelt dobunk
	 */
	public void RemoveAminoAcid(int delta) throws Exception
	{

		if(delta > aminoAcid){
			throw new Exception("I don't have such many amino acid!");
		}
		aminoAcid -= delta;

	}

	/**
	 * noveli a birtokolhato anyag(aminosav es nukleotid) mennyiseget
	 * @param delta noveles merteke
	 */
	public void IncreaseLimit(int delta)
	{
		limit += delta;
	}

	/**
	 * Csokkenti a birtokolhato anyag(aminosav es nukleotid) mennyiseget
	 * @param delta csokkentes merteke
	 */
	public void DecreaseLimit(int delta)
	{
		limit -= delta;
		if (limit < 0) limit = 0;
	}

	/**
	 * Az aminosav mezo gettere
	 * @return A birtokolt aminosav mennyiseg
	 */
	public int GetAminoAcid()
	{
		return aminoAcid;
	}

	/**
	 * A nulceotide mezo gettere
	 * @return A birtokolt nukleotid mennyisege
	 */
	public int GetNucleotide()
	{
		return nucleotide;
	}

	/**
	 * Frissiti a virologus strategiait
	 */
	public void Update()
	{
		for(Agent a: agents){
			a.Update(this);
		}
	}

	/**
	 * Vissza strategiakat az alapallapotukba. Azt ertjuk alapnak ami a konstruktorban van.
	 */
	public void Reset()
	{

	}

	/**
	 * Beallitja a virologus IDropStr-jet
	 * @param d beallitando strategia
	 */
	public void SetDropStr(IDropStr d)
	{
		dropStr = d;
	}

	/**
	 * Beallitja a virologus IMoveStr-jet
	 * @param m beallitando strategia
	 */
	public void SetMoveStr(IMoveStr m)
	{
		moveStr = m;
	}

	/**
	 * Beallitja a virologus ILearnnStr-jet
	 * @param l beallitando strategia
	 */
	public void SetLearnStr(ILearnStr l)
	{
		learnStr = l;
	}

	/**
	 * Beallitja a virologus ILootStr-jet
	 * @param l beallitando strategia
	 */
	public void SetLootStr(ILootStr l)
	{
		lootStr = l;
	}

	/**
	 * Beallitja a virologus IInject-jet
	 * @param i beallitando strategia
	 */
	public void SetInjectStr(IInjectStr i)
	{
		injectStr = i;
	}

	/**
	 * Beallitja a virologus IInjected-jet
	 * @param i beallitando strategia
	 */
	public void SetInjectedStr(IInjectedStr i)
	{
		injectedStr = i;
	}

	/**
	 * Beallitja a virologus IEquipStr-jet
	 * @param e beallitando strategia
	 */
	public void SetEquipStr(IEquipStr e)
	{
		equipStr = e;
	}

	/**
	 * Beallitja a virologus ICollectStr-jet
	 * @param c beallitando strategia
	 */
	public void SetCollectStr(ICollectStr c)
	{
		collectStr= c;
	}

	/**
	 * Beallitja a virologus ILootedStr-jet
	 * @param l beallitando strategia
	 */
	public void SetLootedStr(ILootedStr l)
	{
		lootedStr =l;
	}

}
