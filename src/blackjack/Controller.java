package blackjack;

import playingcards.Card;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        initController();
        initView();
    }

    public void run() {
        view.displayFrame();
    }

    public class PlayAction implements ActionListener {

        @Override 
        public void actionPerformed(ActionEvent e) {
            model.loadSettings(view.getSettings());
            view.displayTable();
            view.resetHandValue();
            view.clearCards();
            view.updateStats(model.playerChips(), model.playerBet());
            view.updateTrueCount(model.getTrueCount());
            view.updateDeckCount(model.deckCount());
            view.displayMessage(Message.welcome());

            view.enableAllChips();
            view.updateChips(model.playerChips(), Model.chips());
            view.disableAllChoices();

            if (model.betIsSufficient()) {
                view.enableButton("Deal");
            } else {
                view.disableButton("Deal");
            }
            view.disableButton("Next Hand");
            view.disableButton("Hint");
        }
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
                view.enableButton("Deal");
            } else {
                view.displayMessage(Message.minimumBet(model.minimumBet()));
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
            view.disableButton("Surrender");
            if (model.shoeIsEmpty()) {
                view.displayMessage(Message.deckIsEmpty());
                view.disableButton("Hit");
                view.disableButton("Double Down");
                view.disableButton("Hint");
            }
            if (model.wentOver()) {
                view.disableButton("Hit");
                view.disableButton("Double Down");
                view.disableButton("Hint");
            } 
        }
    }

    public class StandAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.disableAllChoices();
            view.disableButton("Hint");
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
                view.enableButton("Next Hand");
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
            view.disableButton("Hit");
            view.disableButton("Double Down");
            view.disableButton("Surrender");
            view.disableButton("Hint");
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
            view.enableButton("Next Hand");
            view.disableButton("Hint");
        }
    }

    public class DealAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.disableAllChips();
            view.enableAllChoices();
            view.enableButton("Hint");
            view.disableButton("Deal");

            if (!model.canDoubleDown()) {
                view.disableButton("Double Down");
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
            view.displayMessage(hint, "/images/hint.png");
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
            view.disableButton("Next Hand");
            view.disableButton("Hint");
            model.resetHand();

            view.disableAllChoices();
            if (model.betIsSufficient()) {
                view.enableButton("Deal");
            } else {
                view.disableButton("Deal");
            }

            view.enableAllChips();
            view.updateChips(model.playerChips(), Model.chips());
            view.updateStats(model.playerChips(), model.playerBet());
            view.updateDeckCount(model.deckCount());
        }
    }

    public class NewGameAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (model.outOfChips() ||
                    view.prompt(Message.newGame(), "New Game")) {
                initView();
                model.restartGame();
            }
        }
    }

    public class QuitGameAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (model.outOfChips() ||
                    view.prompt(Message.quit(), "Quit Game")) {
                System.exit(0);
            }
        }
    }

    private void initController() {
        view.initBetOptions(Model.chips());
        view.initPlayOptions(Model.choices());
        view.initHandOptions(Model.options());

        view.initButtonActionListener("Play", new PlayAction());

        view.initButtonActionListener("Hit", new HitAction());
        view.initButtonActionListener("Stand", new StandAction());
        view.initButtonActionListener("Double Down", new DoubleDownAction());
        view.initButtonActionListener("Surrender", new SurrenderAction());

        view.initButtonActionListener("Deal", new DealAction());
        view.initButtonActionListener("Hint", new HintAction());
        view.initButtonActionListener("Next Hand", new NextHandAction());
        view.initButtonActionListener("New Game", new NewGameAction());
        view.initButtonActionListener("Quit Game", new QuitGameAction());

        view.getBetOptions().forEach((betOption) -> {
            int value = Integer.parseInt(betOption.getText());
            betOption.addActionListener(new BetAction(value));
        });
    }

    private void initView() {
        view.displaySettings();
    }
    
    private final Model model;
    private final View view;
}