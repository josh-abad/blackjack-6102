package blackjack;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class Controller {
    
    private final Model model;
    private final View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        initView();
        initController();
    }

    public void run() {
        view.displayFrame();
    }

    private void initView() {
        view.initBetOptions(model.BET_VALUES);
        view.updateCards(model.getPlayerHand(), model.getDealerHand());

        view.updateStats(model.getChips(), model.getBet());

        view.disableAllPlayOptions();
        view.disableHandOption("Deal");
        view.disableHandOption("Next Hand");
    }

    private void initController() {
        view.initHitActionListener(new HitAction());
        view.initStandActionListener(new StandAction());
        view.initDoubleDownActionListener(new DoubleDownAction());
        view.initSurrenderActionListener(new SurrenderAction());

        view.initDealActionListener(new DealAction());
        view.initNextHandActionListener(new NextHandAction());
        view.initQuitGameActionListener(new QuitGameAction());

        for (JButton betOption : view.getBetOptions()) {
            betOption.addActionListener(
                    new BetAction(
                            Integer.parseInt(betOption.getText())
                    )
            );
        }
    }

    public class BetAction implements ActionListener {

        private final int value;

        public BetAction(int betValue) {
            this.value = betValue;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (model.betIsEmpty()) {
                model.setBet(value);
            } else {
                model.addBet(value);
            }

            if (model.betSufficient()) {
                view.clearMessage();
                view.enableHandOption("Deal");
            } else {
                view.displayMessage(
                        "You need to bet a minimum of 25 Chips to play."
                );
            }

            view.updateStats(model.getChips(), model.getBet());
        }
    }

    public class HitAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Card card = model.drawCard();
            model.playerHit(card);
            view.displayMessage("You draw " + card + ".");
            view.updatePlayerHandValue(model.getPlayerHandValue());
            view.updateCards(model.getPlayerHand(), model.getDealerHand());
            view.hideHoleCard(model.getHoleCard());
            if (model.playerWentOver()) {
                view.disablePlayOption("Hit");
                view.disablePlayOption("Double Down");
                view.disablePlayOption("Surrender");
            }
        }
    }

    public class StandAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.disableAllPlayOptions();
            view.revealHoleCard(model.getHoleCard());

            model.dealerTurn();

            view.updateCards(model.getPlayerHand(), model.getDealerHand());

            view.updatePlayerHandValue(model.getPlayerHandValue());
            view.updateDealerHandValue(model.getDealerHandValue());

            if (model.isTie()) {
                view.displayMessage("You push for a tie.");
                model.returnBet();
            } else if (model.playerWon()) {
                if (model.getPlayer().hasBlackjack()) {
                    view.displayMessage(
                            "You got Blackjack and won "
                            + Format.currency(model.getBet() * 1.5)
                            + " Chips!", 
                            Color.GREEN
                    );
                    model.givePayout(Payout.BLACKJACK);
                } else {
                    view.displayMessage(
                            "You won " 
                            + Format.currency(model.getBet()) 
                            + " Chips!", 
                            Color.GREEN
                    );
                    model.givePayout(Payout.REGULAR);
                }
            } else if (model.dealerWon()) {
                if (model.getDealer().hasBlackjack()) {
                    view.displayMessage(
                            "The Dealer got Blackjack and you lose "
                            + Format.currency(model.getBet())
                            + " Chips.",
                            Color.RED
                    );
                } else {
                    view.displayMessage(
                            "You lose " 
                            + Format.currency(model.getBet()) 
                            + " Chips.", 
                            Color.RED
                    );
                }
                model.resetBet();
            } else {
                view.displayMessage("You both went over.", Color.RED);
            }

            if (model.playerIsOutOfChips()) {
                view.displayMessage("You're out of chips.", Color.red);
            } else {
                view.enableHandOption("Next Hand");
            }
        
            view.updateStats(model.getChips(), model.getBet());
        }
    }

    public class DoubleDownAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            model.doubleBet();
            Card card = model.drawCard();
            model.playerHit(card);

            view.displayMessage(
                    "You double your bet to " 
                    + Format.currency(model.getBet()) 
                    + " Chips and draw " 
                    + card + "."
            );
            view.updatePlayerHandValue(model.getPlayerHandValue());
        
            view.updateCards(model.getPlayerHand(), model.getDealerHand());
            view.hideHoleCard(model.getHoleCard());
        
            view.updateStats(model.getChips(), model.getBet());
            view.disablePlayOption("Hit");
            view.disablePlayOption("Double Down");
            view.disablePlayOption("Surrender");
        }
    }

    public class SurrenderAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.displayMessage(
                    "You surrendered and got back " 
                    + Format.currency(model.getBet() / 2) 
                    + " Chips."
            );
            model.givePayout(Payout.HALF);
            model.resetBet();
            view.updateStats(model.getChips(), model.getBet());
            view.disableAllPlayOptions();
            view.enableHandOption("Next Hand");
        }
    }

    public class DealAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.disableAllBetOptions();
            view.enableAllPlayOptions();
            view.disableHandOption("Deal");
            view.displayTableBorder();

            model.shuffleDeck();

            if (!model.playerCanDoubleDown()) {
                view.disablePlayOption("Double Down");
            }
        
            for (int i = 0; i < 2; i++) {
                Card playerCard = model.drawCard();
                Card dealerCard = model.drawCard();
                model.playerHit(playerCard);
                model.dealerHit(dealerCard);
            }

            view.displayMessage(
                    "Your first two cards are " 
                    + model.getPlayerHand().get(0) 
                    + " and " 
                    + model.getPlayerHand().get(1) + "."
            );

            view.updatePlayerHandValue(model.getPlayerHandValue());

            view.updateCards(model.getPlayerHand(), model.getDealerHand());
            view.hideHoleCard(model.getHoleCard());
        }
    }

    public class NextHandAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.displayMessage("Place a bet.");
            view.clearCards();

            view.clearHandValue();
            view.clearTableBorder();

            view.disableHandOption("Next Hand");
            model.getPlayer().resetHand(model.getDeck());
            model.getDealer().resetHand(model.getDeck());

            view.disableAllPlayOptions();
            if (!model.playerCanDoubleDown()) {
                view.disablePlayOption("Double Down");
            }

            view.enableAllBetOptions();
            view.updateBetOptions(model.getChips());
            view.updateStats(model.getChips(), model.getBet());
        }
    }

    public class QuitGameAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO: display a pop-up indicating whether player won or lost
            System.exit(0);
        }
    }
}