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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Az ablak, amiben az egész játék meg van jelenítve.
 */
public class Window extends Observer{
    /**
     * Az utolsó üzenet tartalma
     */
    String msgText;

    /**
     * Aminosav mennyiségét megjelenítő sáv.
     */
    private JProgressBar aminoBar;

    /**
     * Nukleotid mennyiségét megjelenítő sáv.
     */
    private JProgressBar nucleoBar;

    /**
     * Aminosav felirat.
     */
    private JLabel aminoLabel;

    /**
     * Nukleotid felirat.
     */
    private JLabel nucleoLabel;

    /**
     * Hátralévő akciók számát megjelenítő felirat.
     */
    private JLabel turnCounter;

    /**
     * Az üzenetbuborék
     */
    private JLabel actionBubble;

    /**
     * Az üzenetbuborék szövege
     */
    private JTextArea actionBubbleText;

    /**
     * Az üzenetbuborék szövege
     */
    private ArrayList<JButton> equipmentButtons;

    /**
     * A háttér
     */
    private JLabel backGround;

    /**
     * A keret, ami az ablakot reprezentálja
     */
    private final JFrame frame;

    /**
     * A külvilággal való kommunikációért felelős osztály példánya
     */
    private final Controller controller;

    /**
     * A játék kezeléséért felelős osztály
     */
    private final Game game;

    /**
     * Létrehoz egy ablakot a megadott kontroller és játék osztályokkal
     *
     * @param controller A kontroller
     * @param game A játék
     */
    public Window(Controller controller, Game game){
        this.controller = controller;
        this.game = game;

        //SETUP FRAME
        frame = new JFrame("Vilagtalan virologusok vilaga");

        //POPULATE FRAME
        drawInterface();

        frame.setSize(600,600);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setVisible(true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Az ablak frissítésekor meghívott függvény.
     * Újrarajzolja az egész ablakot, a modell (Game) aktuális állapota alapján.
     */
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
                    equipmentSlotIcon = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(drawableEquipment.getTexture())));
                    equipmentSlotIcon = equipmentSlotIcon.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                } catch (IOException ignored) { }
            } else {
                try {
                    equipmentSlotIcon = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/itemSlot.png")));
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
            backGroundIMG = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(drawableField.getTexture())));
            backGroundIMG = backGroundIMG.getScaledInstance(600, 600, Image.SCALE_SMOOTH);
            backGround.setIcon(new ImageIcon(backGroundIMG));
            backGround.setBounds(0, 0, 600, 600);
        } catch (IOException ignored) { }
    }

    /**
     * A felhasználói interfész kirajzolásakor meghívott függvény.
     */
    public void drawInterface() {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 600, 600);

        //SETUP MENUBAR
        JMenuBar mainMenu = new JMenuBar();
        JMenu actions = new JMenu("Actions");
        mainMenu.add(actions);

        //SETUP SUBMENUS
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

    //KÖRVÉGE GOMB BEÁLLÍTÁSA
        Image endButtonIcon = null;
        try {
            endButtonIcon = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/endButton.png")));
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
            equipmentSlotIcon = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/itemSlot.png")));
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

    //AKCIÓSZÁMLÁLÓ BEÁLLÍTÁSA
        turnCounter = new JLabel("3 / 3");
        turnCounter.setFont(new Font("sans-serif", Font.BOLD, 48));
        turnCounter.setForeground(Color.white);
        turnCounter.setBounds(480, 25, 160, 50);

    //SZÖVEGBUBORÉK BEÁKKÍTÁSA
        Image actionBubbleIMG;
        ImageIcon actionBubbleIcon;
        try{
            actionBubbleIMG = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/bubble.png")));
            actionBubbleIMG = actionBubbleIMG.getScaledInstance(190, 88, Image.SCALE_SMOOTH);
            actionBubbleIcon = new ImageIcon(actionBubbleIMG);
            actionBubble = new JLabel(actionBubbleIcon);
            actionBubble.setBounds(280,225,190,88);
        } catch (IOException e) {
            e.printStackTrace();
        }

        msgText = "Hello vak virologus!\nWelcome to the Jurassic Park\n";

        actionBubbleText = new JTextArea(msgText);
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
            backGroundIMG = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/Shelter.png")));
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
        layeredPane.add(actionBubbleText, Integer.valueOf(2));

        //START BUTTON
        Image startButtonIcon = null;
        try {
            startButtonIcon = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/startButton.png")));
            startButtonIcon = startButtonIcon.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert startButtonIcon != null;
        JButton startButton = new JButton(new ImageIcon(startButtonIcon));
        startButton.setBorder(null);
        startButton.setContentAreaFilled(false);
        startButton.setBorderPainted(false);
        startButton.setBackground(Color.BLACK);
        startButton.setOpaque(false);
        startButton.setBounds(400, 400, 150, 150);

        startButton.addActionListener((e)->{
            layeredPane.remove(startButton);

            frame.setJMenuBar(mainMenu);
            layeredPane.add(endButton, Integer.valueOf(1));
            layeredPane.add(aminoBar, Integer.valueOf(1));
            layeredPane.add(nucleoBar, Integer.valueOf(1));
            layeredPane.add(equipmentButtons.get(0), Integer.valueOf(1));
            layeredPane.add(equipmentButtons.get(1), Integer.valueOf(1));
            layeredPane.add(equipmentButtons.get(2), Integer.valueOf(1));
            layeredPane.add(turnCounter, Integer.valueOf(1));
            layeredPane.add(nucleoLabel, Integer.valueOf(2));
            layeredPane.add(aminoLabel, Integer.valueOf(2));

            update();
        });
        layeredPane.add(startButton, Integer.valueOf(2));

        frame.add(layeredPane);
    }
}
