package com.gomspace.simulator.domain;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class represents a white and black grid that allows flipping square colors.
 *
 * The default grid initial state is all squares white.
 * Black square are stored by its position, represented by a vector.
 */
@Slf4j
@AllArgsConstructor
public class WhiteBlackGrid {

    private final Map<String, Vector> blackSquares;

    /**
     * Constructor for initial default state (all squares white).
     */
    public WhiteBlackGrid() {
        this.blackSquares = new HashMap<>();
    }

    /**
     * Checks if the square in the given position is black.
     *
     * @param position square position
     * @return true if the square is black
     */
    public boolean isSquareBlack(Vector position) {
        return blackSquares.containsKey(position.toString());
    }

    /**
     * Flips the square color in the given position.
     *
     * @param position square position
     */
    public void flipSquareColor(Vector position) {
        if (isSquareBlack(position)) {
            blackSquares.remove(position.toString());
        } else {
            blackSquares.put(position.toString(), position);
        }
    }

    /**
     * Draws the grid square by square and returns them as a character matrix.
     * White squares are represented by the minus (-) character.
     * Black squares are represented by the hash (#) character.
     * The grid draw limits are defined by smallest and greatest black square coordinates,
     * adding one unit of padding in each direction.
     *
     * Example of a grid drawing output:
     * -----------
     * ---##------
     * --#--#-----
     * -#---##-#--
     * -#-###-#-#-
     * --#------#-
     * -----#--#--
     * ------##---
     * -----------
     *
     * @return a matrix of characters representing the grid.
     */
    public char[][] draw() {
        final Collection<Vector> blacks = blackSquares.values();
        final List<Integer> xValues = blacks.stream().map(Vector::getX).collect(Collectors.toList());
        final List<Integer> yValues = blacks.stream().map(Vector::getY).collect(Collectors.toList());
        final int smallestX = xValues.stream().min(Integer::compare).get() - 1;
        final int greatestX = xValues.stream().max(Integer::compare).get() + 1;
        final int smallestY = yValues.stream().min(Integer::compare).get() - 1;
        final int greatestY = yValues.stream().max(Integer::compare).get() + 1;
        log.debug(String.format("[Draw] Grid corners - Top left: (%d, %d) - Bottom right: (%d, %d)", smallestX, greatestY, greatestX, smallestY));

        final int rows = Math.abs(smallestY) + Math.abs(greatestY) + 1;
        final int rowOffset = Math.abs(greatestY);
        final int columns = Math.abs(smallestX) + Math.abs(greatestX) + 1;
        final int columnOffset = Math.abs(smallestX);
        final char[][] result = new char[rows][];
        char[] row;

        for (int y = greatestY; y >= smallestY; y--) {
            row = new char[columns];
            for (int x = smallestX; x <= greatestX; x++) {
                char value = isSquareBlack(new Vector(x, y)) ? '#' : '-';
                row[x + columnOffset] = value;
            }
            result[rowOffset - y] = row;
        }
        return result;
    }

}
