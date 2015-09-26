package org.tdd.auctionsniper;

public enum SniperState {
    JOINING {

        @Override
        public SniperState whenAuctionCLosed() {
            return LOST;
        }

    },
    BIDDING {

        @Override
        public SniperState whenAuctionCLosed() {
            return LOST;
        }

    },
    WINNING {

        @Override
        public SniperState whenAuctionCLosed() {
            return WON;
        }

    },
    LOST, WON;

    public SniperState whenAuctionCLosed() {
        throw new Defect("Auction is already closed");
    }
}
