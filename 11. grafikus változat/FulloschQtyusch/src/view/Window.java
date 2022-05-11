package view;

import control.Controller;
import model.Game;
import model.Virologist;
import model.codes.GeneticCode;
import model.equipments.Equipment;
import model.map.Field;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


//TODO comment
public class Window extends Observer{


    /**
     * Az utolsó üzenet tartalma
     */
    String msgText;

    private JProgressBar aminoBar;
    private JProgressBar nucleoBar;

    private JLabel aminoLabel;
    private JLabel nucleoLabel;
    private JLabel turnCounter;
    /**
     * Az üzenetbuborék
     */
    private JLabel actionBubble;
    /**
     * Az üzenetbuborék szövege
     */
    private JTextArea actionBubbleText;
    private ArrayList<JButton> equipmentButtons;

    private JLabel backGround;
    private final JFrame frame;

    private final Controller controller;
    private final Game game;

    public Window(Controller controller, Game game){

        this.controller = controller;
        this.game = game;

        frame = new JFrame("Vilagtalan virologusok vilaga");
        JMenuBar mainMenu = new JMenuBar();
        JMenu actions = new JMenu("Actions");
        mainMenu.add(actions);
        frame.setJMenuBar(mainMenu);

        JMenu attack= new JMenu("attack");
        actions.add(attack);
        attack.addMenuListener(new ViewMenuListener(()->{
            attack.removeAll();
           Virologist v = game.GetCurrentPlayer();
            for (Virologist vir : v.getField().GetVirologists() ) {
                JMenuItem item = new JMenuItem(vir.getName());
                item.addActionListener((e)->controller.attack(vir));
                attack.add(item);
            }
        }));

        JMenu move= new JMenu("move");
        actions.add(move);
        move.addMenuListener(new ViewMenuListener(()->{
            move.removeAll();
            Virologist v = game.GetCurrentPlayer();
            for (Field field : v.getField().GetNeighbours() ) {
                JMenuItem item = new JMenuItem(field.getName());
                item.addActionListener((e)->controller.move(field));
                move.add(item);
            }
        }));

        JMenuItem drop = new JMenuItem("drop");
        actions.add(drop);
        drop.addActionListener((e)->controller.drop());

        JMenu lootAminoFrom = new JMenu("lootAminoFrom");
        actions.add(lootAminoFrom);
        lootAminoFrom.addMenuListener(new ViewMenuListener(()->{
            lootAminoFrom.removeAll();
            Virologist v = game.GetCurrentPlayer();
            for (Virologist vir : v.getField().GetVirologists() ) {
                if (!vir.equals(v)){
                    JMenuItem item = new JMenuItem(vir.getName());
                    item.addActionListener((e)->controller.lootAminoFrom(vir));
                    lootAminoFrom.add(item);
                }
            }
        }));

        JMenu lootNucleoFrom = new JMenu("lootNucleoFrom");
        actions.add(lootNucleoFrom);
        lootNucleoFrom.addMenuListener(new ViewMenuListener(()->{
            lootNucleoFrom.removeAll();
            Virologist v = game.GetCurrentPlayer();
            for (Virologist vir : v.getField().GetVirologists() ) {
                if (!vir.equals(v)){
                    JMenuItem item = new JMenuItem(vir.getName());
                    item.addActionListener((e)->controller.lootNucleoFrom(vir));
                    lootNucleoFrom.add(item);
                }
            }
        }));

        JMenu lootEquipmentFrom = new JMenu("lootEquipmentFrom");
        actions.add(lootEquipmentFrom);
        lootEquipmentFrom.addMenuListener(new ViewMenuListener(()->{
            lootEquipmentFrom.removeAll();
            Virologist v = game.GetCurrentPlayer();
            for (Virologist vir : v.getField().GetVirologists() ) {
                if (!vir.equals(v)){
                    JMenuItem item = new JMenuItem(vir.getName());
                    item.addActionListener((e)->controller.lootEquipmentFrom(vir));
                    lootEquipmentFrom.add(item);
                }
            }
        }));

        JMenuItem collect=new JMenuItem("collect");
        actions.add(collect);
        collect.addActionListener((e)->controller.collect());

        JMenuItem learn=new JMenuItem("learn");
        actions.add(learn);
        learn.addActionListener((e)->controller.learn());

        JMenuItem equip=new JMenuItem("equip");
        actions.add(equip);
        equip.addActionListener((e)->controller.equip());

        JMenu inject = new JMenu("inject");
        actions.add(inject);
        inject.addMenuListener(new ViewMenuListener(()->{
            inject.removeAll();
            Virologist v = game.GetCurrentPlayer();
            for (Virologist vir : v.getField().GetVirologists() ) {
                JMenu virMenu = new JMenu(vir.getName());
                virMenu.addMenuListener(new ViewMenuListener(()->{
                    virMenu.removeAll();
                    for (GeneticCode code : v.getGeneticCodes()){
                        JMenuItem codeItem = new JMenuItem(code.getName());
                        codeItem.addActionListener((e)->controller.inject(vir, code));
                        virMenu.add(codeItem);
                    }
                }));
                inject.add(virMenu);
            }
        }));

        JMenuItem endTurn=new JMenuItem("endTurn");
        actions.add(endTurn);
        endTurn.addActionListener((e) -> controller.endTurn());

        drawInterface();

        frame.setSize(600,600);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setVisible(true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        update();
    }

    @Override
    public void update(){
        Virologist player = game.GetCurrentPlayer();

    //AKCIÓSZÁMLÁLÓ FRISSÍTÉSE
        turnCounter.setText(player.getActionCount() + " / 3");

    //ÁLLAPOTSÁVOK FRISSÍTÉSE
        nucleoBar.setValue(player.GetNucleotide());
        nucleoBar.setMaximum(player.GetMaterialLimit());
        nucleoLabel.setText( String.valueOf(player.GetNucleotide()));
        aminoBar.setValue(player.GetAminoAcid());
        aminoBar.setMaximum(player.GetMaterialLimit());
        aminoLabel.setText(String.valueOf(player.GetAminoAcid()));

    //FELSZERELÉSEK FRISSÍTÉSE
        ArrayList<Equipment> equipment = player.GetEquipments();
        Image equipmentSlotIcon = null;
        for (int i = 0; i < 3; i++){
            if (equipment.size() > i) {
                Drawable drawableEquipment = (Drawable) equipment.get(i);
                try {
                    equipmentSlotIcon = ImageIO.read(getClass().getResourceAsStream(drawableEquipment.getTexture()));
                    equipmentSlotIcon = equipmentSlotIcon.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                } catch (IOException ignored) { }
            } else {
                try {
                    equipmentSlotIcon = ImageIO.read(getClass().getResourceAsStream("/textures/itemSlot.png"));
                    equipmentSlotIcon = equipmentSlotIcon.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                } catch (IOException ignored) {}
            }
            assert equipmentSlotIcon != null;
            equipmentButtons.get(i).setIcon( new ImageIcon(equipmentSlotIcon));
        }

    //SZÖVEGBUBORÉK FRISSÍTÉSE
        if(!controller.getActionMessage().equals(""))
            msgText = player.getName()+": "+ controller.getActionMessage();

        if(msgText.equals("")){
            actionBubble.setVisible(false);
            actionBubbleText.setVisible(false);
        }
        else {

            actionBubbleText.setText(msgText);
            actionBubble.setVisible(true);
            actionBubbleText.setVisible(true);
        }

    //HÁTTÉR FRISSÍTÉSE
        Drawable drawableField = (Drawable) player.getField();
        Image backGroundIMG;
        try {
            backGroundIMG = ImageIO.read(getClass().getResourceAsStream(drawableField.getTexture()));
            backGroundIMG = backGroundIMG.getScaledInstance(600, 600, Image.SCALE_SMOOTH);
            backGround.setIcon(new ImageIcon(backGroundIMG));
            backGround.setBounds(0, 0, 600, 600);
        } catch (IOException ignored) { }
    }

    public void drawInterface() {

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 600, 600);

    //KÖRVÉGE GOMB BEÁLLÍTÁSA
        Image endButtonIcon = null;
        try {
            endButtonIcon = ImageIO.read(getClass().getResourceAsStream("/textures/endButton.png"));
            endButtonIcon = endButtonIcon.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert endButtonIcon != null;
        JButton endButton = new JButton(new ImageIcon(endButtonIcon));
        endButton.addActionListener((e)-> controller.endTurn());
        endButton.setBorder(null);
        endButton.setContentAreaFilled(false);
        endButton.setBorderPainted(false);
        endButton.setBackground(Color.BLACK);
        endButton.setOpaque(false);
        endButton.setBounds(480, 450, 70, 70);

    //ANYAGSÁVOK BEÁLLÍTÁSA
        nucleoBar = new JProgressBar();
        nucleoBar.setBounds(215, 460, 170, 25);
        nucleoBar.setMaximum(20);
        nucleoBar.setMinimum(0);
        nucleoLabel = new JLabel("min");
        nucleoLabel.setBounds(215, 465, 170, 15);
        nucleoLabel.setHorizontalAlignment(JLabel.CENTER);
        nucleoLabel.setFont(new Font("sans-serif", Font.BOLD, 13));

        aminoBar = new JProgressBar();
        aminoBar.setBounds(215, 495, 170, 25);
        aminoBar.setMaximum(20);
        aminoBar.setMinimum(0);
        aminoLabel = new JLabel("min");
        aminoLabel.setBounds(215, 500, 170, 15);
        aminoLabel.setHorizontalAlignment(JLabel.CENTER);
        aminoLabel.setFont(new Font("sans-serif", Font.BOLD, 13));

    //FELSZERELÉSEK BEÁLLÍTÁSA
        Image equipmentSlotIcon = null;
        try {
            equipmentSlotIcon = ImageIO.read(getClass().getResourceAsStream("/textures/itemSlot.png"));
            equipmentSlotIcon = equipmentSlotIcon.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        equipmentButtons = new ArrayList<>(3);
        int y = 340;
        for (int i = 0; i < 3; i++){
            JButton eq;
            assert equipmentSlotIcon != null;
            eq = new JButton(new ImageIcon(equipmentSlotIcon));
            eq.setBounds(15, y, 50, 50);
            eq.setFocusPainted(false);
            eq.setBorderPainted(false);
            eq.setContentAreaFilled(false);
            eq.setBorder(null);
            equipmentButtons.add(eq);
            y += 60;
        }

    //KAKCIÓSZÁMLÁLÓ BEÁLLÍTÁSA
        turnCounter = new JLabel("3 / 3");
        turnCounter.setFont(new Font("sans-serif", Font.BOLD, 48));
        turnCounter.setForeground(Color.white);
        turnCounter.setBounds(480, 25, 160, 50);

    //SZÖVEGBUBORÉK BEÁKKÍTÁSA
        Image actionBubbleIMG;
        ImageIcon actionBubbleIcon;
        try{
            actionBubbleIMG = ImageIO.read(getClass().getResourceAsStream("/textures/bubble.png"));
            actionBubbleIMG = actionBubbleIMG.getScaledInstance(190, 88, Image.SCALE_SMOOTH);
            actionBubbleIcon = new ImageIcon(actionBubbleIMG);
            actionBubble = new JLabel(actionBubbleIcon);
            actionBubble.setBounds(280,225,190,88);
        } catch (IOException e) {
            e.printStackTrace();
        }

        msgText = "kiskúgya kunya gúgyuszka kiskufya\n" +
                "kiskugya kútya sulya lislyuta\n" +
                "kizskugka kutja kútja kiskuhya\n" +
                "gizsgugya    kuta    kutyna    kiskuxya\n" +
                "gizskutya    kislyutya    kutnya    kiskuya\n" +
                "kiskutius    gidzsigegizsgutya    kitsikugya    kitsigugya\n" +
                "hutya        kúgyugya    lizya      kűgyja\n" +
                "kutsus         kogya    gidzsgudja    nyuta\n" +
                "gizsgúgya    kutttya    qgya        pimbull \n" +
                "gűtya     kizsgutya    kutgya       kugyja\n" +
                "kugyuzs    qúhggyah    qkútgyikah    gútjya\n" +
                "guggya    gizsgyugya    kúdtja    gizskugya\n" +

                "kuhya    kúja    kudgya     tutya\n"+
                "gúgyah    kugyha    qutja    kislutya\n" +
                "kutsa    outya    kuyua    lizya\n" +
                "lutyw    litya    kitya    lutxa\n" +
                "kuxta    gidzsigegugyus    kuzxa    kikúgyka\n" +

                "gútyja    kutxa    kigya    gugyuska\n" +
                "gisguya      kuxgy    kurya    gogya\n" +
                "kisgugytkya    jutya    kufya    gugklya\n" +
                "kiskulya       gizsgugyuzsga     kucsa    kiskytya\n" +
                "kismulya    guty    gizsgutyi    kiskhtya\n" +
                "kuva         vau    kizskugya    kiskjtya\n" +
                "qutya     kúgyka    kiskutja    kugyus\n" +
                "qugya      kuty    kulyuska    gutus \n" +
                "gisgúgya    lugya    kuxa    tugya\n" +
                "qúgya      gűgya    gúgyika    kutga\n" +

                "kuja    rót valter (rottweiler)    gi gutga    kisgutya\n" +

                "kulya    kugta    kiekutya    kutuska\n" +
                "kucus    mutya    kiwkutya    nyutya\n" +
                "gizsigutygyja      gizs gugya    kiqkutya    kis kuta\n" +
                "kukia     gízsgúgya    kikugya    kiskutyu\n" +
                "gyutya        qtya    kikutya    kutyuli\n" +
                "gutya     gigygugya    kiskurya     mutyuli\n" +
                "zuka       qkutya    kizskutga    kizskuja\n" +
                "guta    qtyja     gisgutgya    kúgyús\n" +
                "zutyi      guka    gizsgutya    kúgya\n" +
                "könya      kuttya    tutus    kumgya\n" +
                "kölya        putya    tuta    lutya\n" +
                "bidbulgugya    giszkutya    tyutyu    kúlya\n" +

                "qtja     kizsgútya    tyutyus    gugyuzsga\n" +
                "köjök    kis kugya    kizsgugya    kuthya    gútyja\n" +
                "gugyus    gugyusga    kutyhus    gudgya\n" +
                "gizsgutga    kuya    gizsgutya    gizsgutya\n" +
                "bidbugugya    kisgugya    gutyna    gizsgutyus\n" +
                "kizskutja    kucuka    kismutya    ulya\n" +
                "gudja      kuszus    kutyulimutyuli    qty\n" +
                "kuzya     kutyha     kugdlya           gútyja\n" +
                "kissqutya    kűgyka    kudglya    gútygya\n" +
                "kissqtya    qügya    dutya    kunyus \n" +
                "kiss kutya    kis kutyuss    kuyga    kúnya\n" +
                "kiskógya          kuggya     gi guya    ksigugyq\n" +
                "kitzsikutynyuzska    kucs    kuryz    gizsgyutya\n" +
                "kislutyuy         giskunya    gizs kugyúgya    kisgucsa\n" +
                "kisgógya      giskugyulimugyuli    gyutyulimugyuli    kurgya\n" +
                "gizs gudja    gisgugya    gisguya    kurtya\n" +
                "guttya         qutgya     quttya     kis lutyuj \n" +
                "glutya       gulytjya    kisluytuj     discsucsa\n" +
                "kiskzóuyta     kutjda    katya        lutyiluty\n" +
                "kutyika     kis kutsus    kútxa     kutxuzs\n" +
                "kuyly    kuyla    kiskunyus        gugyja\n" +
                "kúfka    kúdka    kissgugyuska    kisskugyus\n" +
                "kütya    gidzsigutya    gunyus     kisgunyus\n" +
                "qs qtya    gugyuli-mugyuli          kizskzgya    kútdja\n" +
                "krudja       krugyja    gizsguggya    kiskukia\n" +
                "kutyulu         kislutuy    kisgugyja    gutyja\n" +
                "kizs zsutyila    gúgyulka-mugyulka        kizsgudzsuka     kisgudzsus\n" +
                "kigy gyuka      kúqggyuzska    kusugyulj    qizs qtya\n" +
                "kufa        gúdzsus-mudzsus    kizs zslutya    qúdzsa\n" +
                "qugyka     gudzsuska    qkútgya    kutguzska\n" +
                "kiskugy a     dicsakbuksi    qugyulimugyuli    tyutya\n" +
                "kisgutju         kisgyúgya    kigyugya         kisgugy";

        actionBubbleText = new JTextArea("Hello vak virologus!\n"+msgText);
        actionBubbleText.setBackground(Color.white);
        actionBubbleText.setBorder(BorderFactory.createEmptyBorder());
        actionBubbleText.setBounds(290,235, 170,50);
        actionBubbleText.setEditable(false);
        actionBubbleText.setFont(new Font("sans-serif", Font.BOLD, 12));
        actionBubbleText.setColumns(28);
        actionBubbleText.setLineWrap(true);

    //HÁTTÉRKÉP BEÁLLÍTÁSA
        Image backGroundIMG;
        ImageIcon backGroundIcon;
        backGround = new JLabel();
        try {
            backGroundIMG = ImageIO.read(getClass().getResourceAsStream("/textures/Field.png"));
            backGroundIMG = backGroundIMG.getScaledInstance(600, 600, Image.SCALE_SMOOTH);
            backGroundIcon = new ImageIcon(backGroundIMG);
            backGround = new JLabel(backGroundIcon);
            backGround.setBounds(0, 0, 600, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }

    //ELRENDEZÉS BEÁLLÍTÁSA
        layeredPane.add(backGround, Integer.valueOf(0));
        layeredPane.add(actionBubble, Integer.valueOf(1));
        layeredPane.add(endButton, Integer.valueOf(1));
        layeredPane.add(aminoBar, Integer.valueOf(1));
        layeredPane.add(nucleoBar, Integer.valueOf(1));
        layeredPane.add(equipmentButtons.get(0), Integer.valueOf(1));
        layeredPane.add(equipmentButtons.get(1), Integer.valueOf(1));
        layeredPane.add(equipmentButtons.get(2), Integer.valueOf(1));
        layeredPane.add(turnCounter, Integer.valueOf(1));
        layeredPane.add(actionBubbleText, Integer.valueOf(2));
        layeredPane.add(nucleoLabel, Integer.valueOf(2));
        layeredPane.add(aminoLabel, Integer.valueOf(2));
        frame.add(layeredPane);
    }

}
