package org.tdd.auctionsniper;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.SwingUtilities;

import org.tdd.auctionsniper.ui.MainWindow;
import org.tdd.auctionsniper.ui.SnipersTableModel;
import org.tdd.auctionsniper.xmpp.XMPPAuctionHouse;

public class Main {

    public class SwingThreadSniperListener implements SniperListener {

        private final SniperListener listener;

        public SwingThreadSniperListener(SniperListener listener) {
            this.listener = listener;
        }

        @Override
        public void sniperStateChanged(SniperSnapshot newSnapshot) {
            SwingUtilities.invokeLater(() -> listener.sniperStateChanged(newSnapshot));
        }

    }

    private static final int ARG_HOSTNAME = 0;
    private static final int ARG_USERNAME = 1;
    private static final int ARG_PASSWORD = 2;

    public static final String AUCTION_RESOURCE = "Auction";
    public static final String ITEM_ID_AS_LOGIN = "auction-%s";
    public static final String MAIN_WINDOW_NAME = "Auction Sniper Main";

    public static void main(String... args) throws Exception {
        Main main = new Main();
        XMPPAuctionHouse auctionHouse = XMPPAuctionHouse.connect(args[ARG_HOSTNAME],
                args[ARG_USERNAME], args[ARG_PASSWORD]);
        main.disconnectWhenUICloses(auctionHouse);
        main.addUserRequestListenerFor(auctionHouse);
    }

    private final SnipersTableModel snipers = new SnipersTableModel();
    private MainWindow ui;
    private Set<Auction> notToBeGCd;

    public Main() throws Exception {
        this.notToBeGCd = new HashSet<Auction>();
        SwingUtilities.invokeAndWait(() -> ui = new MainWindow(snipers));
    }

    private void disconnectWhenUICloses(AuctionHouse auctionHouse) {
        ui.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                auctionHouse.disconnect();
            }
        });
    }

    private void addUserRequestListenerFor(AuctionHouse auctionHouse) {
        ui.addUserRequestListener(itemId -> {
            snipers.addSniper(SniperSnapshot.joining(itemId));
            Auction auction = auctionHouse.auctionFor(itemId);
            notToBeGCd.add(auction);
            auction.addAuctionEventListener(new AuctionSniper(itemId, auction,
                    new SwingThreadSniperListener(snipers)));
            auction.join();
        });
    }
}
