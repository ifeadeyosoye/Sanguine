package sanguine.strategies;

import sanguine.model.SanguineCard;

/**
 * this record will represent a coordinate pair. row is the row in which the player can go in.
 * col is the column.
 */
public record Coordinates(int row, int col, SanguineCard card) {
}
