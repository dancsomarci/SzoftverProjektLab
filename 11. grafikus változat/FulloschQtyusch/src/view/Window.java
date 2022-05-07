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

    public Window(Controller controller, Game game){

    }

    public void update(){

    }

}
