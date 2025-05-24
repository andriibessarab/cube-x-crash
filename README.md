# 🟦 🟦 Cube X Crash

<img src="src/assets/gifs/gameplay.gif" alt="Gameplay preview of Cube X Crash">  

**Cube X Crash** is a Java game I developed as my final project for ICS4U.

## Technical
This game was built using Java and Swing _(yes, Swing—not exactly the go-to for modern game development, but hey, sometimes you work with what you must)_, relying solely on Java's built-in libraries. The game uses a custom abstract class called `GameObject` to manage all in-game objects. It features a `ScreenManager` class that handles the game loop and all screen transitions. Game data is stored in a `.properties` file, which the game reads.

## Basic Instructions

- Launch a stack of balls to break blocks.
- After each launch, blocks move downward.
- As the game progresses, blocks require more damage to break, increasing the challenge.

## Block Types

| Image                                                                                     | Block Type       | Description                                              |
|-------------------------------------------------------------------------------------------|------------------|----------------------------------------------------------|
| <img src="src/assets/game/square_brick.png" width="50" alt="Normal square brick block">   | **Normal Block** | A standard block that requires damage to be destroyed.   |
| <img src="src/assets/game/triangle_brick_1.png" width="50" alt="Triangular half-block">   | **Half Block**   | A triangular block that requires damage to be destroyed. |
| <img src="src/assets/game/extra_ball.gif" width="50" alt="Temporary extra ball power-up"> | **Temp Ball**    | Grants an additional ball for this round only.           |
| <img src="src/assets/game/coin.gif" width="50" alt="Coin collectible power-up">           | **Coin**         | Can be collected and used to purchase power-ups.         |

## Power-ups

| Image                                                                                                 | Power-up                 | Description                                                   |
|-------------------------------------------------------------------------------------------------------|--------------------------|---------------------------------------------------------------|
| <img src="src/assets/game/permanent_ball_upgrade.png" width="50" alt="Permanent extra ball power-up"> | **Extra Permanent Ball** | Grants an additional ball permanently.                        |
| <img src="src/assets/game/break_bottom_row_upgrade.png" width="50" alt="Break bottom row power-up">   | **Break Bottom Row**     | Clears all blocks in the lowest occupied row.                 |
| <img src="src/assets/game/clear_grid_upgrade.png" width="50" alt="Clear grid power-up">               | **Clear Grid**           | Removes all blocks and repopulates the top row with new ones. |

---

**Note:** This game has only been tested on the device it was developed on. Apologies for any unforeseen compatibility issues. 😃
