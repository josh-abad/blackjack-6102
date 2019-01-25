package blackjack;

import playingcards.Card;
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
                view.enableOption("Deal");
            } else {
                view.displayMessage(Message.minimumBet(Model.MINIMUM_BET));
            }

            view.updateStats(model.playerChips(), model.playerBet());
            view.updateChips(model.playerChips(), Model.chips());
        }

        private final int value;
    }

    public class HitAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Card card = model.drawCard();
            model.playerHit(card);
            model.updateRunningCount(card.getRank());
            view.updateDeckCount(model.deckCount());
            view.updateTrueCount(model.getTrueCount());
            view.displayMessage(Message.hit(card));
            view.updatePlayerHandValue(model.playerHandValue(),
                                       model.playerHasSoftHand());
            view.updatePlayerCards(model.playerCardNames());
            view.disableChoice("Surrender");
            if (model.shoeIsEmpty()) {
                view.displayMessage(Message.deckIsEmpty());
                view.disableChoice("Hit");
                view.disableChoice("Double Down");
                view.disableOption("Hint");
            }
            if (model.wentOver()) {
                view.disableChoice("Hit");
                view.disableChoice("Double Down");
                view.disableOption("Hint");
            } 
        }
    }

    public class StandAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.disableAllChoices();
            view.disableOption("Hint");
            view.revealHoleCard(model.holeCard().toString());

            model.dealerTurn();
            view.updateDeckCount(model.deckCount());
            view.updateTrueCount(model.getTrueCount());

            view.updatePlayerCards(model.playerCardNames());
            view.updateDealerCards(model.dealerCardNames());

            view.updatePlayerHandValue(model.playerHandValue(),
                                       model.playerHasSoftHand());
            view.updateDealerHandValue(model.dealerHandValue(),
                                       model.dealerHasSoftHand());

            String message;
            if (model.isTie()) {
                view.displayMessage(Message.tie());
                model.returnBet();
            } else if (model.playerWon()) {
                if (model.playerHasBlackjack()) {
                    message = Message.playerBlackjack(model.playerBet());
                    model.givePayout(Payout.BLACKJACK);
                } else {
                    message = Message.playerWon(model.playerBet());
                    model.givePayout(Payout.REGULAR);
                }
                view.displayMessage(message);
            } else if (model.playerLost()) {
                message = (model.dealerHasBlackjack()) ?
                        Message.dealerBlackjack(model.playerBet()) :
                        Message.playerLost(model.playerBet());
                view.displayMessage(message);
                model.resetBet();
            } else {
                view.displayMessage(Message.bothOver());
            }

            if (model.outOfChips()) {
                view.displayMessage(Message.outOfChips());
            } else {
                view.enableOption("Next Hand");
            }
        
            view.updateStats(model.playerChips(), model.playerBet());
        }
    }

    public class DoubleDownAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            model.doubleBet();
            Card card = model.drawCard();
            model.playerHit(card);
            model.updateRunningCount(card.getRank());
            view.updateTrueCount(model.getTrueCount());

            view.displayMessage(Message.doubleDown(model.playerBet(), card));
            view.updatePlayerHandValue(model.playerHandValue(),
                                       model.playerHasSoftHand());
        
            view.updatePlayerCards(model.playerCardNames());
        
            view.updateStats(model.playerChips(), model.playerBet());
            view.updateDeckCount(model.deckCount());
            view.disableChoice("Hit");
            view.disableChoice("Double Down");
            view.disableChoice("Surrender");
            view.disableOption("Hint");
        }
    }

    public class SurrenderAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.displayMessage(Message.surrender(model.playerBet()));
            model.givePayout(Payout.HALF);
            model.resetBet();
            view.revealHoleCard(model.holeCard().toString());
            model.updateRunningCount(model.holeCard().getRank());
            view.updateTrueCount(model.getTrueCount());
            view.updateDealerHandValue(model.dealerHandValue(),
                                       model.dealerHasSoftHand());
            view.updateStats(model.playerChips(), model.playerBet());
            view.disableAllChoices();
            view.enableOption("Next Hand");
            view.disableOption("Hint");
        }
    }

    public class DealAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.disableAllChips();
            view.enableAllChoices();
            view.enableOption("Hint");
            view.disableOption("Deal");

            if (!model.canDoubleDown()) {
                view.disableChoice("Double Down");
            }
        
            for (int i = 0; i < 2; i++) {
                Card playerCard = model.drawCard();
                Card dealerCard = model.drawCard();
                model.playerHit(playerCard);
                model.updateRunningCount(playerCard.getRank());
                model.dealerHit(dealerCard);
                if (i != 0) {
                    model.updateRunningCount(dealerCard.getRank());
                }
            }
            view.updateTrueCount(model.getTrueCount());


            view.displayMessage(Message.deal(model.initialCards()[0],
                                             model.initialCards()[1]));

            view.updatePlayerHandValue(model.playerHandValue(),
                                       model.playerHasSoftHand());
            view.updateDealerHandValue(model.dealerFrontCard());

            view.updatePlayerCards(model.playerCardNames());
            view.updateDealerCards(model.dealerCardNames());
            view.hideHoleCard();
            view.updateDeckCount(model.deckCount());
        }
    }

    public class HintAction implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            BasicStrategy.Action action = model.basicStrategy();
            String hint = null;
            switch (action) {
                case DH:
                    if (!view.isChoiceEnabled("Double Down")) {
                        hint = BasicStrategy.Action.H.toString();
                    }   break;
                case DS:
                    if (!view.isChoiceEnabled("Double Down")) {
                        hint = BasicStrategy.Action.S.toString();
                    }   break;
                case RH:
                    if (!view.isChoiceEnabled("Surrender")) {
                        hint = BasicStrategy.Action.H.toString();
                    }   break;
                case RS:
                    if (!view.isChoiceEnabled("Surrender")) {
                        hint = BasicStrategy.Action.S.toString();
                    }   break;  
                default:
                    break;
            }
            if (hint == null) {
                hint = action.toString();
            }
            view.displayMessage("Hint: " + hint);
        }
    }

    public class NextHandAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.displayMessage(Message.nextHand());
            view.clearCards();

            if (!model.shoeIsSufficient()) {
                model.shuffleDeck();
                model.resetRunningCount();
                view.displayMessage("Deck is reshuffled.");
            }

            view.resetHandValue();
            view.disableOption("Next Hand");
            view.disableOption("Hint");
            model.resetHand();

            view.disableAllChoices();
            if (model.betIsSufficient()) {
                view.enableOption("Deal");
            } else {
                view.disableOption("Deal");
            }

            view.enableAllChips();
            view.updateChips(model.playerChips(), Model.chips());
            view.updateStats(model.playerChips(), model.playerBet());
            view.updateDeckCount(model.deckCount());
        }
    }

    public class QuitGameAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private void initController() {
        view.initHitActionListener(new HitAction());
        view.initStandActionListener(new StandAction());
        view.initDoubleDownActionListener(new DoubleDownAction());
        view.initSurrenderActionListener(new SurrenderAction());

        view.initDealActionListener(new DealAction());
        view.initHintActionListener(new HintAction());
        view.initNextHandActionListener(new NextHandAction());
        view.initQuitGameActionListener(new QuitGameAction());

        view.getBetOptions().forEach((betOption) -> {
            betOption.addActionListener(
                    new BetAction(Integer.parseInt(betOption.getText()))
            );
        });
    }

    private void initView() {
        view.initBetOptions(Model.chips());
        view.initPlayOptions(Model.choices());
        view.initHandOptions(Model.options());

        view.resetHandValue();
        view.clearCards();
        view.updateStats(model.playerChips(), model.playerBet());
        view.updateTrueCount(model.getTrueCount());
        view.updateDeckCount(model.deckCount());

        view.disableAllChoices();
        if (model.betIsSufficient()) {
            view.enableOption("Deal");
        } else {
            view.disableOption("Deal");
        }
        view.disableOption("Next Hand");
        view.disableOption("Hint");
    }
    
    private final Model model;
    private final View view;
}