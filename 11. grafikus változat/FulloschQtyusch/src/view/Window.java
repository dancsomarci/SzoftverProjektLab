package view;

import control.Controller;
import model.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Window extends Observer{



    private JMenuBar menuBar;
    private JButton endButton;
    private JProgressBar aminoBar;
    private JProgressBar nucleoBar;
    private JLabel aminoLabel;
    private JLabel nucleoLabel;
    private JLabel turnCounter;
    private JLabel actionBubble;
    private JButton equipment1;
    private JButton equipment2;
    private JButton equipment3;
    private JLabel backGround;

    Controller controller;
    Game game;

    public Window(){
        JFrame f= new JFrame("Vilagtalan virologusok vilaga");
        JMenuBar mainMenu = new JMenuBar();
        JMenu actions = new JMenu("Actions");
        mainMenu.add(actions);
        f.setJMenuBar(mainMenu);

        JMenu attack= new JMenu("attack");
        actions.add(attack);
        attack.addMenuListener(new ViewMenuListener(()->{
//            Virologist v = game.GetCurrentPlayer();
//            for (Virologist vir : v.getField().GetVirologists() ) {
//                JMenuItem item = new JMenuItem(vir.getName());
//                item.addActionListener((e)->controller.attack(vir));
//                attack.add(item);
//            }
        }));

        JMenu move= new JMenu("move");
        actions.add(move);
        move.addMenuListener(new ViewMenuListener(()->{
//            Virologist v = game.GetCurrentPlayer();
//            for (Field field : v.getField().GetNeighbours() ) {
//                JMenuItem item = new JMenuItem(field.getName());
//                item.addActionListener((e)->controller.move(field));
//                attack.add(item);
//            }
        }));

        JMenuItem drop = new JMenuItem("drop");
        actions.add(drop);
        drop.addActionListener((e)->controller.drop());

        JMenu lootAminoFrom = new JMenu("lootAminoFrom");
        actions.add(lootAminoFrom);
        lootAminoFrom.addMenuListener(new ViewMenuListener(()->{
//            Virologist v = game.GetCurrentPlayer();
//            for (Virologist vir : v.getField().GetVirologists() ) {
//                if (!vir.equals(v)){
//                    JMenuItem item = new JMenuItem(vir.getName());
//                    item.addActionListener((e)->controller.lootAminoFrom(vir));
//                    attack.add(item);
//                }
//            }
        }));

        JMenu lootNucleoFrom = new JMenu("lootNucleoFrom");
        actions.add(lootNucleoFrom);
        lootNucleoFrom.addMenuListener(new ViewMenuListener(()->{
//            Virologist v = game.GetCurrentPlayer();
//            for (Virologist vir : v.getField().GetVirologists() ) {
//                if (!vir.equals(v)){
//                    JMenuItem item = new JMenuItem(vir.getName());
//                    item.addActionListener((e)->controller.lootNucleoFrom(vir));
//                    attack.add(item);
//                }
//            }
        }));

        JMenu lootEquipmentFrom = new JMenu("lootEquipmentFrom");
        actions.add(lootEquipmentFrom);
        lootEquipmentFrom.addMenuListener(new ViewMenuListener(()->{
//            Virologist v = game.GetCurrentPlayer();
//            for (Virologist vir : v.getField().GetVirologists() ) {
//                if (!vir.equals(v)){
//                    JMenuItem item = new JMenuItem(vir.getName());
//                    item.addActionListener((e)->controller.lootEquipmentFrom(vir));
//                    attack.add(item);
//                }
//            }
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
//            Virologist v = game.GetCurrentPlayer();
//            for (Virologist vir : v.getField().GetVirologists() ) {
//                JMenu virMenu = new JMenu(vir.getName());
//                virMenu.addMenuListener(new ViewMenuListener(()->{
//                    for (GeneticCode code : v.getGeneticCodes()){
//                        JMenuItem codeItem = new JMenuItem(code.getName());
//                        codeItem.addActionListener((e)->controller.inject(vir, code));
//                        virMenu.add(codeItem);
//                    }
//                }));
//                attack.add(virMenu);
//            }
        }));

        JMenuItem endTurn=new JMenuItem("endTurn");
        actions.add(endTurn);
        endTurn.addActionListener((e) -> controller.endTurn());

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 600, 600);

        /*
    private JProgressBar aminoBar;
    private JProgressBar nucleoBar;
    private JLabel aminoLabel;
    private JLabel nucleoLabel;
    private JLabel turnCounter;
    private JLabel actionBubble;
         */
        Image endButtonIcon = null;
        try {
            endButtonIcon = ImageIO.read(new File("textures/endButton.png"));
            endButtonIcon = endButtonIcon.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        endButton = new JButton(new ImageIcon(endButtonIcon));
        endButton.setBorder(null);
        endButton.setContentAreaFilled(false);
        endButton.setBorderPainted(false);
        endButton.setBackground(Color.BLACK);
        endButton.setOpaque(false);
        endButton.setBounds(480, 450, 70, 70);


        nucleoBar = new JProgressBar();
        nucleoBar.setBounds(215, 460, 170, 25);
        aminoBar = new JProgressBar();
        aminoBar.setBounds(215, 495, 170, 25);

        Image equipmentSlotIcon = null;
        try {
            equipmentSlotIcon = ImageIO.read(new File("textures/itemSlot.png"));
            equipmentSlotIcon = equipmentSlotIcon.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        equipment1 = new JButton(new ImageIcon(equipmentSlotIcon));
        equipment1.setBounds(15, 340, 50, 50);
        equipment1.setFocusPainted(false);
        equipment1.setBorderPainted(false);
        equipment2 = new JButton(new ImageIcon(equipmentSlotIcon));
        equipment2.setBounds(15, 400, 50, 50);
        equipment2.setFocusPainted(false);
        equipment2.setBorderPainted(false);
        equipment3 = new JButton(new ImageIcon(equipmentSlotIcon));
        equipment3.setBounds(15, 460, 50, 50);
        equipment3.setFocusPainted(false);
        equipment3.setBorderPainted(false);

        turnCounter = new JLabel("3 / 3");
        turnCounter.setFont(new Font("Serif", Font.BOLD, 48));
        turnCounter.setForeground(Color.white);
        turnCounter.setBounds(490, 25, 160, 50);


        Image backGroundIMG;
        ImageIcon backGroundIcon;
        backGround = new JLabel();
        try {
            backGroundIMG = ImageIO.read(new File("textures/Warehouse.png"));
            backGroundIMG = backGroundIMG.getScaledInstance(600, 600, Image.SCALE_SMOOTH);
            backGroundIcon = new ImageIcon(backGroundIMG);
            backGround = new JLabel(backGroundIcon);
            backGround.setBounds(0, 0, 600, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }
        layeredPane.add(backGround, Integer.valueOf(0));
        layeredPane.add(endButton, Integer.valueOf(1));
        layeredPane.add(aminoBar, Integer.valueOf(1));
        layeredPane.add(nucleoBar, Integer.valueOf(1));
        layeredPane.add(equipment1, Integer.valueOf(1));
        layeredPane.add(equipment2, Integer.valueOf(1));
        layeredPane.add(equipment3, Integer.valueOf(1));
        layeredPane.add(turnCounter, Integer.valueOf(1));
        f.add(layeredPane);
        f.setSize(600,600);
        f.setResizable(false);
        f.setLayout(null);
        f.setVisible(true);
    }

    public static void main(String[] args){
        new Window();
    }

    public Window(Controller controller, Game game){
        this.controller = controller;
        this.game = game;
    }

    @Override
    public void update(){
        game.GetCurrentPlayer();


    }
}
