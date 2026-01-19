# Sanguine: Storm of Crimson
## Table of Contents
- Overview
- Quick Start
- Key Components
- Code Base Organization
- Gameplay Overview
- Card Mechanics & Influence
- Changes for Part 2
- Visual View
- AI Strategies
- Extensibility & Future Work
- Testing

## Overview 
**Sanguine: Storm of Crimson** is a turn-based, two-player strategy card game in Java. Players compete for board control by placing cards with influence grids that affect pawns on a configurable board.
This project implements:
- MVC architecture: clean separation of model, view, and controller
- Textual and graphical views (using Java Swing)
- Configurable decks and boards
- AI strategies for computer players
- JUnit testing with mocked models

## Quick Start
Compile and run the game:
  javac -d out src/main/java/sanguine/SanguineGame.java
  java -cp out sanguine.SanguineGame docs/deck.config

Alternatively, run SanguineGame.main() in an IDE. The game reads a deck configuration file for both players.

## Key Components
**Model**
- ReadonlySanguineModel: exposes observation methods only (board contents, player hands, legality checks, scores).
- SanguineModel: extends Readonly interface with mutator methods (play card, pass, setup game).
- Board, Cells, Pawns, Cards, Players: core data structures.

**View**
- TextView: prints the board, hands, pawns, and row scores.
- SwingView: graphical view using Java Swing, including:
- Highlightable cards and cells
- Mouse and keyboard event handling
- Resizable window
- Mirrored influence grids for Blue player
- Controller
- StubController handles mouse and key events, printing interactions to System.out.
- Full controller

## Code Base Organization
Sanguine/
├── docs/                 # Decks and configs
├── images/               # View screenshots
├── src/
│   └── main/java/sanguine/
│       ├── model/
│       ├── view/
│       ├── controller/
│       ├── strategies/
│       └── SanguineGame.java
└── tests/                # JUnit tests

## Gameplay Overview
- **Setup:** players draw hands from their decks and place pawns on starting columns.
- **Turns:** players draw a card, then play a card or pass.
- **Card Placement:** must place on a legal cell (checked via model).
- **Influence Application:** modifies pawn ownership on adjacent cells.
- **Scoring:** row-based; player with higher row score wins points.

## Card Mechanics & Influence
- Cards have cost, value, 5x5 influence grid.
- **Influence rules:**
    - Empty cell → add pawn
    - Owned pawn → increment (max 3)
    - Opponent pawn → convert
    - Cards → unaffected
  - Blue player influence grids are mirrored horizontally.
 
## Visual View
- **Swing-based GUI**
- Highlights selected cards and cells
- Mouse clicks on hand/cards or cells are detected and printed to console
- Keyboard input handles confirm or pass actions
- Resizable window, with proper scaling for board and hand
- Screenshots included in images/ folder:
  1. Start of game
  2. Red player selects a card and cell
  3. Blue player selects a card on their turn
  4. Mid-game, multiple cards played
 
## AI Strategies
- **Strategy 1: Fill First** – play the first available card and location.
- **Strategy 2: Maximize Row-Score** – choose a card and location that ensures the current player exceeds the opponent’s row score (top-down row order).
- Both strategies pass if no valid move exists.
- **Strategy 3: Control Board** – maximize cell ownership
- **Strategy 4: Minimax** opponent blocking – reduce opponent’s optimal moves
Strategies are tested with mocked models (strategy-transcript-first.txt and strategy-transcript-score.txt) to ensure correct decision-making and tie-breaking.

## Extensibility & Future Work
- GUI can be extended with animations or more polished graphics.
- AI strategies can be stacked or combined for smarter players.
- Support for variable board sizes, hand sizes, and deck compositions.

**Testing**
- Unit tests: cards, pawns, cells, decks, board scoring, and legality checks
- Strategy tests: mock models simulate board states to validate moves
- View tests: screenshots verify GUI correctness (manual visual inspection)

## Authors
Ifeoluwa Adeyosoye 
Kadin Solpon 


