package sudoku.computationlogic;

import sudoku.constants.GameState;
import sudoku.constants.Rows;
import sudoku.problemdomain.SudokuGame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static sudoku.problemdomain.SudokuGame.GRID_BOUNDARY;

public class GameLogic {

    public static SudokuGame getNewGame() {
        return new SudokuGame(
                GameState.NEW,
                GameGenerator.getNewGameGrid()
        );
    }

    public static GameState checkForCompletion(int[][] grid) {
        if (sudokuIsInvalid(grid)) return GameState.ACTIVE;
        if(tilesAreNotFilled(grid)) return GameState.ACTIVE;
        return GameState.COMPLETE;
    }

    protected static boolean sudokuIsInvalid(int[][] grid) {
        return (
            rowsAreInvalid(grid)
            || columnsAreInvalid(grid)
            || squaresAreInvalid(grid)
        );
    }

    private static boolean squaresAreInvalid(int[][] grid) {
        return (
            rowOfSquareIsInvalid(Rows.TOP, grid)
            || rowOfSquareIsInvalid(Rows.MIDDLE, grid)
            || rowOfSquareIsInvalid(Rows.BOTTOM, grid)
        );
    }

    private static boolean rowOfSquareIsInvalid(Rows value, int[][] grid) {
        return switch (value) {
            case TOP -> (
                    squareIsInvalid(0, 0, grid)
                            || squareIsInvalid(0, 3, grid)
                            || squareIsInvalid(0, 6, grid)
            );
            case MIDDLE -> (
                    squareIsInvalid(3, 0, grid)
                            || squareIsInvalid(3, 3, grid)
                            || squareIsInvalid(3, 6, grid)
            );
            case BOTTOM -> (
                    squareIsInvalid(6, 0, grid)
                            || squareIsInvalid(6, 3, grid)
                            || squareIsInvalid(6, 6, grid)
            );
            default -> false;
        };
    }

    private static boolean squareIsInvalid(int xIndexStart, int yIndexStart, int[][] grid) {
        List<Integer> square = new ArrayList<>();

        for (int yIndex = yIndexStart; yIndex < yIndexStart + 3; yIndex++) {
            for (int xIndex = xIndexStart; xIndex < xIndexStart + 3; xIndex++) {
                square.add(grid[xIndex][yIndex]);
            }
        }
        return collectionHasRepeats(square);
    }

    private static boolean rowsAreInvalid(int[][] grid) {
        for (int xIndex = 0; xIndex < GRID_BOUNDARY; xIndex++) {
            List<Integer> row = new ArrayList<>();
            for (int yIndex = 0; yIndex < GRID_BOUNDARY; yIndex++) {
                row.add(grid[xIndex][yIndex]);
            }
            if (collectionHasRepeats(row)) return true;
        }
        return false;
    }

    private static boolean columnsAreInvalid(int[][] grid) {
        for (int yIndex = 0; yIndex < GRID_BOUNDARY; yIndex++) {
            List<Integer> column = new ArrayList<>();
            for (int xIndex = 0; xIndex < GRID_BOUNDARY; xIndex++) {
                column.add(grid[xIndex][yIndex]);
            }
            if (collectionHasRepeats(column)) return true;
        }
        return false;
    }

    private static boolean collectionHasRepeats(List<Integer> collection) {
        for (int index = 1; index <= GRID_BOUNDARY; index++) {
            if (Collections.frequency(collection, index) > 1) return true;
        }

        return false;
    }

    private static boolean tilesAreNotFilled(int[][] grid) {
        for (int xIndex = 0; xIndex < GRID_BOUNDARY; xIndex++) {
            for (int yIndex = 0; yIndex < GRID_BOUNDARY; yIndex++) {
                if (grid[xIndex][yIndex] == 0) return true;
            }
        }

        return false;
    }


}
