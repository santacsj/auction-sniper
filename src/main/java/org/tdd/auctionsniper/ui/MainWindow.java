package org.tdd.auctionsniper.ui;

import javax.swing.JFrame;

import org.tdd.auctionsniper.Main;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {

    public MainWindow() {
        super("Auction Sniper");
        setName(Main.MAIN_WINDOW_NAME);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

}
