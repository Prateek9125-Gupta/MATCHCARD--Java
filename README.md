# MATCHCARD--Java
This project is a graphical Pokemon-themed memory matching card game built with Java Swing, allowing two players to take turns flipping and matching cards on a 4x5 grid. It demonstrates key concepts of Java GUI development, object-oriented programming, image handling, and event-driven logic — making it a great beginner-to-intermediate level project for Java learners.

🧩 Features

✅ Interactive GUI using Java Swing (JFrame, JPanel, JButton, JLabel)

🎯 Two-player mode with automatic turn switching

🖼️ 10 unique Pokémon-themed card pairs with front/back images

🔊 Sound effects for flip, match, and mismatch actions

🏆 Score tracking for both players

❌ Error counter for mismatches

🔄 Restart button to reset the game anytime

📏 Game board size: 600x750 pixels

🛠️ Technologies Used

Java (JDK 8 or above)

Java AWT and Swing libraries

Java Audio System for sound playback

Image handling and resizing with ImageIcon

🚀 How to Run the Game

1. Compile the code:

javac -cp ./resources -d ./bin ./src/*.java

2. Run the game:
   
java -cp ./bin MatchCards

Alternatively, open it in IntelliJ IDEA or Eclipse, set the resources/ folder as the resource root, and run MatchCards.java.

📌 Gameplay Instructions

The game window opens with all 20 cards face up for a short time.

Once the game begins, players take turns clicking two cards.

If the selected cards match, the player scores a point.

If not, the cards flip back, and the turn switches.

The game ends when all pairs are matched.

A popup announces the winner or a draw and shows the final scores.

🧠 Concepts Demonstrated

Java Swing GUI design and layout management

Event handling with ActionListener

Custom classes for game components (Card objects)

Image scaling and loading from resources

Sound effect integration using Clip and AudioInputStream

Game logic: turn handling, score tracking, win detection

Object-oriented design and encapsulation

Pokemon-Match-Cards-Java/
├── src/

│   ├── MatchCards.java

│   └── App.java

├── resources/

│   ├── img/

│   │    ├── back.jpg

│   │    ├── darkness.jpg

│   │    ├── double.jpg

│   │    ├── fairy.jpg

│   │    ├── fighting.jpg

│   │    ├── fire.jpg

│   │    ├── grass.jpg

│   │    ├── lightning.jpg

│   │    ├── metal.jpg

│   │    ├── psychic.jpg

│   │    └── water.jpg

│   └── sounds/

│        ├── flipcard.wav

│        ├── correct.wav

│        └── error.wav

├── .gitignore

├── README.md


🖼️ Sample GUI Screenshot
![Screenshot 2025-06-11 105052](https://github.com/user-attachments/assets/5b981f1b-11f4-4483-a16e-c1ecc9f91800)

🖼️ Sample GUI Video

https://github.com/user-attachments/assets/deca1206-3582-4950-ad21-f47cb26a599d

https://github.com/user-attachments/assets/6b46550f-2062-45ed-af7f-fcb7b4e6b484


