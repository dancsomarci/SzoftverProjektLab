package model;


import model.agents.*;
import model.codes.*;
import model.equipments.*;
import model.map.*;
import model.strategy.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

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
		System.out.println("\tCurrently on Field named: " + field.getName());
		System.out.println("\tAmino acid: " + aminoAcid + " / maximum: "+ limit);
		System.out.println("\tNucleotide: " + nucleotide+ " / maximum: "+ limit);
		System.out.println("\tEquipments:");
		for (Equipment e: equipments) {
			System.out.println("\t\t" + e.getName()); // később dekorátor tervezési minta lesz
		}
		System.out.println("\tGenetic codes:");
		for (GeneticCode c: codes) {
			System.out.println("\t\t" + c.getName());
		}
		System.out.println("\tAgents: (+ttl)");
		for (Agent a: agents) {
			System.out.println("\t\t" + a.getName() + " " + a.getTimeToLive());
		}
	}

	public Game GetContext(){return game;} //randomitás kikapcsolása stb..

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

	private int maxNumberOfItems;

	public void SetActionCount(int count){actionCount = count;}

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
		equipments = new ArrayList<>(maxNumberOfItems);
		codes = new ArrayList<>();
		agents = new ArrayList<>();

		//from docs
		maxNumberOfItems = 3;
		limit = 20;
		aminoAcid = 0;
		nucleotide = 0;
		actionCount = 3;

		Reset(); //sets the default strategies
	}

	/**
	 * Beallitja a jatek osztaly peldanyanak referenciajat
	 * @param g a beallitando objektum
	 */
	public void SetGame(Game g){
		game = g;
	}

	public void Kill(){
		field.RemoveVirologist(this);
		game.RemoveVirologist(this);
	}

	public void Attack(Virologist v){
		if (actionCount > 0){
			attackStr.Attack(this, v);
		}
	}

	public void TargetedWith(Virologist who, Agent a){
		injectedStr.Injected(who, this, a);
	}

	public void RemoveEquipment(Equipment e){
		equipments.remove(e);
	}

	/**
	 * A virologus random mozgasaert felel, ezt egy bizonyos agens valthatja ki.
	 */
	public void Move()
	{
		if (actionCount > 0) {
			ArrayList<Field> fields = field.GetNeighbours();
			if (game.randOn){
				if (fields.size() != 0) {
					Random random = new Random();
					Move(fields.get(random.nextInt(fields.size())));
				}
			}else{
				if (fields.size() > 0){
//					for (int i = 0; i < fields.size(); i++){
//						System.out.println(i + " - " + fields.get(i).getName());
//					}
//					Scanner sc = new Scanner(System.in); //nem szabad bezárni!
//					Field target = fields.get(sc.nextInt());
//					System.out.println(name + " tries moving from " + field.getName() + " to " + target.getName());
				Field target = fields.get(0);
				Move(target);
				}
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
		if (actionCount > 0) {
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
	 * A fuggveny az utolsónak felvett equipment eldobásáért felel
	 */
	public void Drop()
	{
		if (actionCount > 0 && equipments.size() > 0) {
			dropStr.Drop(this, field, equipments.remove(equipments.size()-1));
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
		if (actionCount > 0) {
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
		if (actionCount > 0) {
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
		if (actionCount > 0 && equipments.size() < maxNumberOfItems) {
			lootStr.LootEquipment(this, v);
		}
	}

	/**
	 * Anyag felvetelt kezdemenyez az adott mezon, a strategia kezeli a megfelelo fuggveny hivasokat
	 */
	public void Collect()
	{
		if (actionCount > 0) {
			collectStr.Collect(this, field);
		}
	}

	/**
	 * Az adott mezon levo genetikai kod megtanulasat kezdemenyezi, ehhez meghivja a learnStr-t,
	 * ami elvegzi az allapotnak megfelelo fuggveny hivasokat.
	 */
	public void Learn()
	{
		if (actionCount > 0) {
			learnStr.Learn(this, field);
		}
	}

	/**
	 * Ez a fuggveny kezdemenyezi egy mezon levo felszereles felvetelet.
	 */
	public void Equip()
	{
		if (actionCount > 0) {
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
		if (equipments.size() < maxNumberOfItems)
			equipments.add(e);
		e.Apply(this);
		e.ApplyStrategy(this);
	}

	/**
	 * Az equipment getter fuggvenye.
	 * @return Vissza ter a birtokolt felszerelesekkel, ha nincs akkor kivetelt dob
	 */
	public Equipment GetEquipment() throws IndexOutOfBoundsException
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

		if (actionCount > 0)
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
		lootedStr.LootedForAminoAcid(self, this);
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
			if(game.randOn) {
				Random random = new Random();
				int r = random.nextInt(equipments.size());
				Equipment e = equipments.get(r);
				lootedStr.LootedForEquipment(self, this, e);
			}
			else{
				lootedStr.LootedForEquipment(self,this, equipments.get(0));
			}
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
		actionCount = 3;
		game.NextPlayer(codes.size());
	}

	/**
	 * A parameter mertekevel noveli a birtokolt aminosav mennyiseget
	 * @param delta a mennyiseg amivel novelunk
	 */
	public void AddAminoAcid(int delta)
	{
		aminoAcid+= delta;
		if (aminoAcid > limit) aminoAcid = limit;
	}

	/**
	 * A parameter mertekevel noveli a birtokolt nukleotid mennyiseget
	 * @param delta a mennyiseg amivel novelunk
	 */
	public void AddNucleotide(int delta)
	{
		nucleotide+= delta;
		if (nucleotide > limit) nucleotide = limit;
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
		for(int i = 0; i < agents.size();i++){
			agents.get(i).Update(this);
		}
	}

	/**
	 * Vissza strategiakat a default állípotba.
	 */
	public void Reset()
	{
		lootedStr = new DefLooted();
		injectedStr = new DefInjected();
		collectStr = new DefCollect();
		equipStr = new DefEquip();
		lootStr = new DefLoot();
		dropStr = new DefDrop();
		injectStr = new DefInject();
		learnStr = new DefLearn();
		moveStr = new DefMove();
		attackStr = new DefAttack();

		for (Agent a:agents
		) {
			a.ApplyStrategy(this);
		}
		for (Equipment e :
				equipments) {
			e.ApplyStrategy(this);
		}
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
