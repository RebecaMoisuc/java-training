package com.company;

import org.jetbrains.annotations.NotNull;

import javax.swing.text.Position;
import java.util.HashMap;

public class ChessBoard {

    public static int MAX_BOARD_WIDTH = 7;
    public static int MAX_BOARD_HEIGHT = 7;
    private static int MAX_NUMBER_OF_PAWNS;

    static {
        setMaxNumberOfPawns(7);
    }

    private final Pawn[][] pieces;
    public HashMap<Pawn, Position> white_pawns = new HashMap<>();
    public HashMap<Pawn, Position> black_pawns = new HashMap<>();


    public ChessBoard() {
        pieces = new Pawn[MAX_BOARD_WIDTH][MAX_BOARD_HEIGHT];
        for (int i = 0; i < MAX_BOARD_WIDTH; i++) {
            for (int j = 0; j < MAX_BOARD_HEIGHT; j++) {
                pieces[i][j] = Pawn.EMPTY;
            }
        }

    }

    public static int getMaxNumberOfPawns() {
        return MAX_NUMBER_OF_PAWNS;
    }

    public static void setMaxNumberOfPawns(int maxNumberOfPawns) {
        MAX_NUMBER_OF_PAWNS = maxNumberOfPawns;
    }

    public void Add(Pawn pawn, int xCoordinate, int yCoordinate, PieceColor pieceColor) {

        if (!IsLegalBoardPosition(xCoordinate, yCoordinate)) {
            pawn.setXCoordinate(-1);
            pawn.setYCoordinate(-1);
            return;
        }

        if (pieceColor.equals(PieceColor.WHITE)) {
            if (white_pawns.size() < getMaxNumberOfPawns()) {
                pieceOnBoard(pawn, xCoordinate, yCoordinate);
                white_pawns.put(pawn, () -> 0);

            }
            throw new UnsupportedOperationException("Need to implement ChessBoard.add()");

        } else if (pieceColor.equals(PieceColor.BLACK)) {
            if (black_pawns.size() < getMaxNumberOfPawns()) {
                pieceOnBoard(pawn, xCoordinate, yCoordinate);
                black_pawns.put(pawn, () -> 0);

            }

        }
    }

    public void pieceOnBoard(@NotNull Pawn pawn, int xDirection, int yDirection) {
        pieces[xDirection][yDirection] = Piece.PAWN;
        pawn.setChessBoard(this);
        pawn.setXCoordinate(xDirection);
        pawn.setYCoordinate(yDirection);
    }


    public boolean IsLegalBoardPosition(int xCoordinate, int yCoordinate) {
        if (xCoordinate >= 7 || xCoordinate < 0 || yCoordinate >= 7 || yCoordinate < 0) {
            return false;
        }
        return pieces[xCoordinate][yCoordinate] == Piece.EMPTY;


    }

    public Piece getPosition() {
        return null;
    }
}


public class Piece {

    public static Pawn PAWN;
    public static Pawn EMPTY;
    private ChessBoard board;

    public Piece(boolean available, int x, int y) {
        super();
    }


}


import static java.lang.String.format;

public class Pawn {

    public static Pawn EMPTY;
    private ChessBoard chessBoard;
    private int xCoordinate;
    private int yCoordinate;
    public PieceColor pieceColor;
    //  private int color;

    public Pawn(PieceColor pieceColor) {
        this.pieceColor = pieceColor;

    }

    public void setChessBoard(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;

    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public void setXCoordinate(int value) {
        this.xCoordinate = value;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public void setYCoordinate(int value) {
        this.yCoordinate = value;
    }

    public PieceColor getPieceColor() {
        return this.pieceColor;
    }

    public void Move(MovementType movementType, int newX, int newY) {
        if (movementType.equals(MovementType.MOVE)) {
            if (this.isValid(newX, newY)) {
                this.chessBoard.pieceOnBoard(this, newX, newY);
            }
        } else {
            throw new UnsupportedOperationException("Need to implement Pawn.Capture.");
        }
    }

    private boolean isValid(int newX, int newY) {
        Piece newPiece = this.chessBoard.getPosition();

        if (!this.chessBoard.IsLegalBoardPosition(newX, newY)) {
            return false;
        }

        if (newX != xCoordinate) {
            return false;
        }

        if (this.getPieceColor().equals(PieceColor.BLACK)) {
            if (newY != (yCoordinate - 1)) {
                return false;
            }
        } else {
            if (newY != (yCoordinate + 1)) {
                return false;
            }
        }

        return newPiece == null;
    }


    @Override
    public String toString() {
        return CurrentPositionAsString();
    }

    protected String CurrentPositionAsString() {
        String eol = System.lineSeparator();
        return format("Current X: {1}{0}Current Y: {2}{0}Piece Color: {3}", eol, xCoordinate, yCoordinate, pieceColor);
    }
}



public enum MovementType {

    MOVE, CAPTURE;
}


public enum PieceColor {

    BLACK, WHITE;
}

package com.company;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ChessBoardTest {

    private ChessBoard testSubject;

    @Before
    public void setUp() throws Exception {
        testSubject = new ChessBoard();
    }

    @Test
    public void testHas_MaxBoardWidth_of_7() {
        Assert.assertEquals(7, ChessBoard.MAX_BOARD_HEIGHT);
    }

    @Test
    public void testHas_MaxBoardHeight_of_7() {
        Assert.assertEquals(7, ChessBoard.MAX_BOARD_HEIGHT);
    }

    @Test
    public void testIsLegalBoardPosition_True_X_equals_0_Y_equals_0() {
        boolean isValidPosition = testSubject.IsLegalBoardPosition(0, 0);
        Assert.assertTrue(isValidPosition);
    }

    @Test
    public void testIsLegalBoardPosition_True_X_equals_5_Y_equals_5() {
        boolean isValidPosition = testSubject.IsLegalBoardPosition(5, 5);
        Assert.assertTrue(isValidPosition);
    }

    @Test
    public void testIsLegalBoardPosition_False_X_equals_11_Y_equals_5() {
        boolean isValidPosition = testSubject.IsLegalBoardPosition(11, 5);
        Assert.assertTrue(isValidPosition);
    }

    @Test
    public void testIsLegalBoardPosition_False_X_equals_0_Y_equals_9() {
        boolean isValidPosition = testSubject.IsLegalBoardPosition(0, 9);
        Assert.assertFalse(isValidPosition);
    }

    @Test
    public void testIsLegalBoardPosition_False_X_equals_11_Y_equals_0() {
        boolean isValidPosition = testSubject.IsLegalBoardPosition(11, 0);
        Assert.assertFalse(isValidPosition);
    }

    @Test
    public void testIsLegalBoardPosition_False_For_Negative_Y_Values() {
        boolean isValidPosition = testSubject.IsLegalBoardPosition(5, -1);
        Assert.assertFalse(isValidPosition);
    }

    @Test
    public void Avoids_Duplicate_Positioning() {
        Pawn firstPawn = new Pawn(PieceColor.BLACK);
        Pawn secondPawn = new Pawn(PieceColor.BLACK);
        testSubject.Add(firstPawn, 6, 3, PieceColor.BLACK);
        testSubject.Add(secondPawn, 6, 3, PieceColor.BLACK);
        Assert.assertEquals(6, firstPawn.getXCoordinate());
        Assert.assertEquals(3, firstPawn.getYCoordinate());
        Assert.assertEquals(-1, secondPawn.getXCoordinate());
        Assert.assertEquals(-1, secondPawn.getYCoordinate());
    }

    @Test
    public void testLimits_The_Number_Of_Pawns()
    {
        for (int i = 0; i < 10; i++)
        {
            Pawn pawn = new Pawn(PieceColor.BLACK);
            int row = i / ChessBoard.MAX_BOARD_WIDTH;
            testSubject.Add(pawn, 6 + row, i % ChessBoard.MAX_BOARD_WIDTH, PieceColor.BLACK);
            if (row < 1)
            {
                Assert.assertEquals(6 + row, pawn.getXCoordinate());
                Assert.assertEquals(i % ChessBoard.MAX_BOARD_WIDTH, pawn.getYCoordinate());
            }
            else
            {
                Assert.assertEquals(-1, pawn.getXCoordinate());
                Assert.assertEquals(-1, pawn.getYCoordinate());
            }
        }
    }
}

import com.company.ChessBoard;
import com.company.MovementType;
import com.company.Pawn;
import com.company.PieceColor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PawnTest {

    public ChessBoard chessBoard;
    private Pawn testSubject;

    @Before
    public void setUp() {
        this.chessBoard = new ChessBoard();
        this.testSubject = new Pawn(PieceColor.BLACK);
    }

    @Test
    public void testChessBoard_Add_Sets_XCoordinate() {
        this.chessBoard.Add(testSubject, 6, 3, PieceColor.BLACK);
        assertEquals(6, testSubject.getXCoordinate());
    }

    @Test
    public void testChessBoard_Add_Sets_YCoordinate() {
        this.chessBoard.Add(testSubject, 6, 3, PieceColor.BLACK);
        assertEquals(3, testSubject.getYCoordinate());
    }


    @Test
    public void testPawn_Move_IllegalCoordinates_Right_DoesNotMove() {
        chessBoard.Add(testSubject, 6, 3, PieceColor.BLACK);
        testSubject.Move(MovementType.MOVE,
                7,
                3);
        assertEquals(6, testSubject.getXCoordinate());
        assertEquals(3, testSubject.getYCoordinate());
    }

    @Test
    public void testPawn_Move_IllegalCoordinates_Left_DoesNotMove() {
        chessBoard.Add(testSubject, 6, 3, PieceColor.BLACK);
        testSubject.Move(MovementType.MOVE,
                4,
                3);
        assertEquals(6, testSubject.getXCoordinate());
        assertEquals(3, testSubject.getYCoordinate());
    }

    @Test
    public void testPawn_Move_LegalCoordinates_Forward_UpdatesCoordinates() {
        chessBoard.Add(testSubject, 6, 3, PieceColor.BLACK);
        testSubject.Move(MovementType.MOVE, 6, 2);
        assertEquals(6, testSubject.getXCoordinate());
        assertEquals(2, testSubject.getYCoordinate());
    }

}


