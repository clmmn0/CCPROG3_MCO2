# CCPROG3_MCO2

A Java-based single-player game that combines the elements of Player vs. Environment (PvE) and board games. The game has two modes: exploration and encounter. For the player to win, they must successfully traverse the game area. Encounters are triggered when the player stops or passes through certain sections of the game area. The player must defeat non-playable characters (NPCs) with the help of their companions. The player loses if all their three companions are defeated.

**Game Initialization**
- The player chooses three out of four posisble companions for the game. The possible companions are the following:
  - Kirin: fire elemental creatures that have the special skill, Blaze, which allows it to burn an entire column of obstruction from where the player is standing.
  - Yume: cloud-like creatures that have the special skill, Float, which allows it to move one unobstructed square diagonally.
  - Yuki: dinosaur-like creatures of the earth element that have the special skill, Giant Steps, which allows it to move two unobstructed steps for a single cost.
  - Same: water creatures that have the special skill, Splash, which allows it to destroy an entire row of obstruction from where the player is standing.
- The special skill of each companion can only be used once in a game walkthrough and only one skill can be used per turn.

**Game Elements**
1. `S`: start square of the game which is found at the top left corner of the grid.
2. `E`: exit square of the game which is found at the bottom right corner of the grid.
3. `X`: obstructions which indicate that a player cannot walk through that square. A total of 30 obstructions are placed on the grid.
4. `K`, `M`, `U`, `A`: if a player falls on a square with this element, then an encounter with an NPC will be triggered. K refers to a triggered encounter with a Kirin, M for Yumi, U for Yuki, and A for Same. A total of 12 encounters are placed on the grid.
5. `H`: if a player falls or passes through a square with this element, then a healing totem is obtained which heals all the player's companions. A total of 5 healing totems are placed on the grid.

**Game Mode: Exploration**
- The game will be played in a 10x10 grid wherein each square represents a spot that the player can walk on. The placement of game elements on the grid changes in every game.
- Game elements will only be shown on the screen once the player selects a certain square. 
- The player can only move up, down, left, or right, unless a special skill was used.
- To determine the number of steps to be taken, the player will roll a six-sided die.
- If the player reaches the exit, then the player wins and the game ends.

**Game Mode: Encounter**
- An encounter is a 1-on-1 battle between the player's companion and an NPC.
- At the start of every encounter, the player chooses one of its companions to participate in the battle and start attacking first.
- All companions have starting Hit Points (HP) of 50.
- Attacks can inflict damage within the range of 0-10 which is determined randomly by the game.
- An attack on a companion's strength will have additional 20% damage while an attack on a weakness will yield 20% less damage.
- The strengths and weaknesses of the companions are indicated below:
  - Kirin - S: Yume - W: Same
  - Yume - S: Yuki - W: Kirin
  - Yuki - S: Same - W: Yume
  - Same - S: Kirin - W: Yuki
- An encounter ends once a companion from either side is defeated.
- Defeated player's companions cannot be called after its defeat, unless a healing totem was obtained by the player.
- If all player's companions are defeated, then the player loses and the game ends.


