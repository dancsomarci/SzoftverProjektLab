package view;

import control.Controller;
import model.Game;

import javax.swing.*;

public class Window extends Observer {

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
        JFrame f= new JFrame("Világtalan virológusok világa");
        JMenuBar mainMenu = new JMenuBar();
        JMenu actions = new JMenu("Actions");
        mainMenu.add(actions);
        f.setJMenuBar(mainMenu);



//        JMenu subMenu= new JMenu("items");
//        JMenuItem i1=new JMenuItem("Item 1");
//        JMenuItem i2=new JMenuItem("Item 2");
//        JMenuItem i3=new JMenuItem("Item 3");
//        JMenuItem i4=new JMenuItem("Item 4");
//        JMenuItem i5=new JMenuItem("Item 5");


        f.setSize(600,600);
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
