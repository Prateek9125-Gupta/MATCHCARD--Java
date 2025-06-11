import javax.swing.*;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.IOException;
import java.net.URL;

public class MatchCards {
    class Card {
        String cardName;
        ImageIcon cardImageIcon;

        Card(String cardName, ImageIcon cardImageIcon) {
            this.cardName = cardName;
            this.cardImageIcon = cardImageIcon;
        }

        public String toString() {
            return cardName;
        }
    }

    String[] cardList = {
        "darkness", "double", "fairy", "fighting", "fire",
        "grass", "lightning", "metal", "psychic", "water"
    };

    int rows = 4, columns = 5, cardWidth = 90, cardHeight = 128;
    int boardWidth = columns * cardWidth;
    int boardHeight = rows * cardHeight;

    ArrayList<Card> cardSet;
    ImageIcon cardBackImageIcon;
    ArrayList<JButton> board;
    Timer hideCardTimer;

    JFrame frame = new JFrame("Pokemon Match Cards");
    JLabel textLabel = new JLabel();
    JLabel playerLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JPanel restartGamePanel = new JPanel();
    JButton restartButton = new JButton();

    boolean gameReady = false;
    JButton card1Selected = null;
    JButton card2Selected = null;

    int errorCount = 0;
    int playerTurn = 1;
    int player1Score = 0, player2Score = 0;

    public MatchCards() {
        setupCards();
        shuffleCards();

        frame.setLayout(new BorderLayout());
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Errors: 0");

        playerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        playerLabel.setHorizontalAlignment(JLabel.CENTER);
        playerLabel.setText("Player 1's Turn | P1: 0 P2: 0");

        textPanel.setLayout(new GridLayout(2, 1));
        textPanel.add(textLabel);
        textPanel.add(playerLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        board = new ArrayList<>();
        boardPanel.setLayout(new GridLayout(rows, columns));

        for (int i = 0; i < cardSet.size(); i++) {
            JButton tile = new JButton();
            tile.setPreferredSize(new Dimension(cardWidth, cardHeight));
            tile.setIcon(cardSet.get(i).cardImageIcon);
            tile.setFocusable(false);

            tile.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!gameReady) return;
                    JButton clicked = (JButton) e.getSource();
                    if (clicked.getIcon() == cardBackImageIcon) {
                        playSound("flipcard.wav");
                        if (card1Selected == null) {
                            card1Selected = clicked;
                            int index = board.indexOf(card1Selected);
                            card1Selected.setIcon(cardSet.get(index).cardImageIcon);
                        } else if (card2Selected == null) {
                            card2Selected = clicked;
                            int index = board.indexOf(card2Selected);
                            card2Selected.setIcon(cardSet.get(index).cardImageIcon);

                            if (card1Selected.getIcon().equals(card2Selected.getIcon())) {
                                playSound("correct.wav");
                                if (playerTurn == 1) player1Score++;
                                else player2Score++;
                                updateLabels();
                                card1Selected = null;
                                card2Selected = null;
                                checkGameOver();
                            } else {
                                playSound("error.wav");
                                errorCount++;
                                updateLabels();
                                hideCardTimer.start();
                            }
                        }
                    }
                }
            });

            board.add(tile);
            boardPanel.add(tile);
        }
        frame.add(boardPanel);

        restartButton.setText("Restart Game");
        restartButton.setEnabled(false);
        restartButton.addActionListener(e -> restartGame());
        restartGamePanel.add(restartButton);
        frame.add(restartGamePanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);

        hideCardTimer = new Timer(1500, e -> hideCards());
        hideCardTimer.setRepeats(false);
        hideCardTimer.start();
    }

    void setupCards() {
        cardSet = new ArrayList<>();
        for (String cardName : cardList) {
            Image img = new ImageIcon(getClass().getResource("/img/" + cardName + ".jpg")).getImage();
            ImageIcon icon = new ImageIcon(img.getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH));
            cardSet.add(new Card(cardName, icon));
        }
        cardSet.addAll(new ArrayList<>(cardSet));

        Image backImg = new ImageIcon(getClass().getResource("/img/back.jpg")).getImage();
        cardBackImageIcon = new ImageIcon(backImg.getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH));
    }

    void shuffleCards() {
        for (int i = 0; i < cardSet.size(); i++) {
            int j = (int) (Math.random() * cardSet.size());
            Card temp = cardSet.get(i);
            cardSet.set(i, cardSet.get(j));
            cardSet.set(j, temp);
        }
    }

    void hideCards() {
        if (card1Selected != null && card2Selected != null) {
            card1Selected.setIcon(cardBackImageIcon);
            card2Selected.setIcon(cardBackImageIcon);
            card1Selected = null;
            card2Selected = null;
            playerTurn = (playerTurn == 1) ? 2 : 1;
            updateLabels();
        } else {
            for (JButton button : board) button.setIcon(cardBackImageIcon);
            gameReady = true;
            restartButton.setEnabled(true);
        }
    }

    void updateLabels() {
        textLabel.setText("Errors: " + errorCount);
        playerLabel.setText("Player " + playerTurn + "'s Turn | P1: " + player1Score + " P2: " + player2Score);
    }

    void restartGame() {
        shuffleCards();
        for (int i = 0; i < board.size(); i++) {
            board.get(i).setIcon(cardSet.get(i).cardImageIcon);
        }
        card1Selected = null;
        card2Selected = null;
        errorCount = 0;
        playerTurn = 1;
        player1Score = 0;
        player2Score = 0;
        gameReady = false;
        updateLabels();
        hideCardTimer.start();
    }

    void checkGameOver() {
        int totalPairs = cardList.length;
        if (player1Score + player2Score == totalPairs) {
            String winner = (player1Score > player2Score) ? "Player 1 Wins!" :
                            (player2Score > player1Score) ? "Player 2 Wins!" : "It's a Draw!";
            JOptionPane.showMessageDialog(frame, "Game Over!\n" + winner +
                "\nScores:\nPlayer 1: " + player1Score + "\nPlayer 2: " + player2Score);
        }
    }

    public void playSound(String fileName) {
        try {
            URL url = getClass().getResource("/sounds/" + fileName);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MatchCards());
    }
}
