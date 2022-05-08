package view;

import control.Controller;
import model.Game;
import model.Virologist;
import model.codes.GeneticCode;
import model.map.Field;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.border.Border;

public class Window extends Observer{



    private JMenuBar menuBar;
    private JButton endButton;
    private JProgressBar aminoBar;
    private JProgressBar nucleoBar;
    private JLabel aminoLabel;
    private JLabel nucleoLabel;
    private JLabel turnCounter;
    private JLabel actionBubble;

    Controller controller;
    Game game;

    public Window(){
        JFrame f= new JFrame("Vak virológusok földje");
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
        endButton.setContentAreaFilled(true);
        endButton.setBorder(null);
        endButton.setOpaque(false);
        endButton.setBorderPainted(false);
        endButton.setBackground(Color.GREEN);
        endButton.setBounds(450, 450, 70, 70);
        endButton.setBorder(new RoundedBorder(35));

        aminoBar = new JProgressBar();

        layeredPane.add(endButton, Integer.valueOf(0));
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

    }

    private static class RoundedBorder implements Border {

        private int radius;


        RoundedBorder(int radius) {
            this.radius = radius;
        }


        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
        }


        public boolean isBorderOpaque() {
            return true;
        }


        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width-1, height-1, radius, radius);
        }
    }

}
