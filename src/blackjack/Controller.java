package blackjack;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        initView();
        initController();
    }

    public void run() {
        view.displayFrame();
    }

    public class BetAction implements ActionListener {

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
                view.displayMessage(Message.minimumBet(Model.MINIMUM_BET));
            }

            view.updateStats(model.getChips(), model.getBet());
            view.updateBetOptions(model.getChips(), Model.betValues());
        }

        private final int value;
    }

    public class HitAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Card card = model.drawCard();
            model.playerHit(card);
            view.displayMessage(Message.hit(card));
            view.updatePlayerHandValue(model.getPlayerHandValue(), model.isSoft());
            view.updatePlayerCards(model.getPlayerCardNames());
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
            view.revealHoleCard(model.getHoleCardName());

            model.dealerTurn();

            view.updatePlayerCards(model.getPlayerCardNames());
            view.updateDealerCards(model.getDealerCardNames());

            view.updatePlayerHandValue(model.getPlayerHandValue(), model.isSoft());
            view.updateDealerHandValue(model.getDealerHandValue());

            String message;
            if (model.isTie()) {
                view.displayMessage(Message.tie());
                model.returnBet();
            } else if (model.playerWon()) {
                if (model.playerHasBlackjack()) {
                    message = Message.playerBlackjack(model.getBet());
                    model.givePayout(Payout.BLACKJACK);
                } else {
                    message = Message.playerWon(model.getBet());
                    model.givePayout(Payout.REGULAR);
                }
                view.displayMessage(message, Color.GREEN);
            } else if (model.playerLost()) {
                if (model.dealerHasBlackjack()) {
                    message = Message.dealerBlackjack(model.getBet());
                } else {
                    message = Message.playerLost(model.getBet());
                }
                view.displayMessage(message, Color.RED);
                model.resetBet();
            } else {
                view.displayMessage(Message.bothOver(), Color.RED);
            }

            if (model.outOfChips()) {
                view.displayMessage(Message.outOfChips(), Color.RED);
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

            view.displayMessage(Message.doubleDown(model.getBet(), card));
            view.updatePlayerHandValue(model.getPlayerHandValue(), model.isSoft());
        
            view.updatePlayerCards(model.getPlayerCardNames());
        
            view.updateStats(model.getChips(), model.getBet());
            view.disablePlayOption("Hit");
            view.disablePlayOption("Double Down");
            view.disablePlayOption("Surrender");
        }
    }

    public class SurrenderAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.displayMessage(Message.surrender(model.getBet()));
            model.givePayout(Payout.HALF);
            model.resetBet();
            view.revealHoleCard(model.getHoleCardName());
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
                    Message.deal(model.getFirstCard(), model.getSecondCard())
            );

            view.updatePlayerHandValue(model.getPlayerHandValue(), model.isSoft());

            view.updatePlayerCards(model.getPlayerCardNames());
            view.updateDealerCards(model.getDealerCardNames());
            view.hideHoleCard(model.getHoleCardName());
        }
    }

    public class NextHandAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.displayMessage(Message.nextHand());
            view.clearCards();

            view.updateDealerHandValue(0);
            view.updatePlayerHandValue();
            view.disableHandOption("Next Hand");
            model.resetHand();

            view.disableAllPlayOptions();
            if (!model.canDoubleDown()) {
                view.disablePlayOption("Double Down");
            }
            if (model.betIsSufficient()) {
                view.enableHandOption("Deal");
            } else {
                view.disableHandOption("Deal");
            }

            view.enableAllBetOptions();
            view.updateBetOptions(model.getChips(), Model.betValues());
            view.updateStats(model.getChips(), model.getBet());
        }
    }

    public class QuitGameAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO: display a pop-up indicating whether player playerWon or playerLost
            System.exit(0);
        }
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

    private void initView() {
        view.initBetOptions(Model.betValues());
        view.initPlayOptions(Model.playOptions());
        view.initHandOptions(Model.handOptions());

        view.updateDealerHandValue(0);
        view.updatePlayerHandValue();
        view.clearCards();
        view.displayTableBorder();
        view.updateDealerCards(model.getDealerCardNames());
        view.updateStats(model.getChips(), model.getBet());

        view.disableAllPlayOptions();
        if (model.betIsSufficient()) {
            view.enableHandOption("Deal");
        } else {
            view.disableHandOption("Deal");
        }
        view.disableHandOption("Next Hand");
    }
    
    private final Model model;
    private final View view;
}