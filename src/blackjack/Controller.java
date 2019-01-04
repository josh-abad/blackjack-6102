package blackjack;

import blackjack.design.Format;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        view.initPlayOptions(model.PLAY_OPTIONS);
        view.initHandOptions(model.HAND_OPTIONS);
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

        view.getBetOptions().forEach((betOption) -> {
            betOption.addActionListener(
                    new BetAction(
                            Integer.parseInt(betOption.getText())
                    )
            );
        });
    }

    public class BetAction implements ActionListener {

        private final int value;

        public BetAction(int value) {
            this.value = value;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            model.bet(value);

            if (model.betIsSufficient()) {
                view.clearMessage();
                view.enableHandOption("Deal");
            } else {
                view.displayMessage(
                        "You need to bet a minimum of 25 Chips to play."
                );
            }

            view.updateStats(model.getChips(), model.getBet());
            view.updateBetOptions(model.getChips(), model.BET_VALUES);
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
            if (model.wentOver()) {
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

            String message;
            String bet = Format.currency(model.getBet());
            if (model.isTie()) {
                view.displayMessage("You push for a tie.");
                model.returnBet();
            } else if (model.won()) {
                if (model.playerHasBlackjack()) {
                    message = "You got Blackjack! and won " 
                            + Format.currency(model.getBet() * 1.5) + " Chips!";
                    model.givePayout(Payout.BLACKJACK);
                } else {
                    message = "You won " + bet + " Chips!";
                    model.givePayout(Payout.REGULAR);
                }
                view.displayMessage(message, Color.GREEN);
            } else if (model.lost()) {
                message = "You lose " + bet + " Chips.";
                if (model.dealerHasBlackjack()) {
                    message = "The Dealer got Blackjack. " + message;
                }
                view.displayMessage(message, Color.RED);
                model.resetBet();
            } else {
                view.displayMessage("You both went over.", Color.RED);
            }

            if (model.outOfChips()) {
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
            String chips = Format.currency(model.getBet() / 2);
            view.displayMessage(
                    "You surrendered and got back " + chips + " Chips."
            );
            model.givePayout(Payout.HALF);
            model.resetBet();
            view.revealHoleCard(model.getHoleCard());
            view.updateDealerHandValue(model.getDealerHandValue());
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

            if (!model.canDoubleDown()) {
                view.disablePlayOption("Double Down");
            }
        
            for (int i = 0; i < 2; i++) {
                Card playerCard = model.drawCard();
                Card dealerCard = model.drawCard();
                model.playerHit(playerCard);
                model.dealerHit(dealerCard);
            }

            view.displayMessage(
                    "Your first two cards are " + model.getPlayerHand().get(0) 
                    + " and " + model.getPlayerHand().get(1) + "."
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
            model.resetHand();

            view.disableAllPlayOptions();
            if (!model.canDoubleDown()) {
                view.disablePlayOption("Double Down");
            }

            view.enableAllBetOptions();
            view.updateBetOptions(model.getChips(), model.BET_VALUES);
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