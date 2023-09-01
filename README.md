# Pong
Welcome to Pong! This is a classic arcade game where players control paddles to hit a ball back and forth. The objective is to score points by making the ball pass the opponent's paddle.

## Features

- Single Player vs. CPU: You can choose to play against a computer opponent with two difficulty modes: easy and hard.
- Multiplayer: Play against a friend on the same computer, with one player using the mouse and the other using the keyboard.

# Pong Game Setup and Execution

## Introduction

This section will guide you through configuring and running the Pong game with your desired game mode.

## Installation

> Clone this repository on your computer:

```shell
$ git clone https://github.com/DelicaTessa/Java-Pong
```

## Game Modes

### Single Player vs. CPU

- To play against the CPU, follow these steps:
  - Open your Integrated Development Environment (IDE).
  - Locate the `Game.java` file.
  - Look for line 11, which reads:
    ```java
    Pong content = new Pong(Player.MOUSE, Player.KEYBOARD);
    ```
  - Replace the first parameter with either `CPU.EASY` or `CPU.HARD` depending on your desired CPU difficulty level.
  - For the second parameter, choose either `Player.MOUSE` or `Player.KEYBOARD` based on whether you want to control your paddle using the mouse or keyboard.

### Multiplayer

- To play a multiplayer game, follow these steps:
  - Open your Integrated Development Environment (IDE).
  - Locate the `Game.java` file.
  - Look for line 11, which reads:
    ```java
    Pong content = new Pong(Player.MOUSE, Player.KEYBOARD);
    ```
  - Set the first parameter to `Player.MOUSE`.
  - Set the second parameter to `Player.KEYBOARD`.

## Compiling and Running

- After configuring the game mode, follow these steps to compile and run the game:

1. **Compile the Game**:

   - In your IDE, make sure all source files, including `Game.java`, are saved.
   - Open your terminal or command prompt.
   - Navigate to the directory containing the game source files.
   - Use the `javac` command to compile the game:

     ```shell
     javac *.java
     ```

   - This command will compile the Java source code.

2. **Run the Game**:

   - After successful compilation, run the game using the `java` command:

     ```shell
     java Main
     ```

   - The game will start based on the game mode configuration you specified.

## Enjoy the Game!

- Have fun playing Pong with your preferred game mode setup!
