import org.jetbrains.annotations.Contract;

import java.util.Scanner;

/**
 * Created by tanyucel on 05.12.16.
 */
public class VierGewinnt {

    private static boolean finished = false;

    public static void main(String[] args) {
        int x = 6;
        int y = 7;
        int[][] field = new int[x][y];

        Scanner scanner = new Scanner(System.in);
        System.out.println();


        //System.out.println("Player one's turn");
        //System.out.println("Player two's turn");

        while (!finished) {
            drawField(field);
            System.out.println("Player one's turn: enter a number between 0 and 6");
            int p1select = Integer.valueOf(scanner.nextLine());
            while (!inBounds(field, p1select)  || isRowFull(field, p1select)) {
                System.err.println("enter a number between 0 and 6");
                p1select = Integer.valueOf(scanner.nextLine());
            }
            field = addStone(field, p1select, 1);

            if (hasWon(field, 1)) {
                drawField(field);
                System.out.println("CONGRATULATIONS, PLAYER 1 WON!");
                break;
            }
            drawField(field);

            System.out.println("Player two's turn: enter a number between 0 and 6");
            int p2select = Integer.valueOf(scanner.nextLine());
            while (!inBounds(field, p2select) || isRowFull(field, p2select)) {
                System.err.println("enter a number between 0 and 6");
                p2select = Integer.valueOf(scanner.nextLine());

            }
            field = addStone(field, p2select, 2);

            if (hasWon(field, 2)) {
                drawField(field);
                System.out.println("CONGRATULATIONS, PLAYER 2 WON!");
                break;
            }


        }
    }


    private static void drawField(int[][] field) {
        for (int i = field.length - 1; i >= 0; i--) {
            for (int j = 0; j < field[i].length; j++) {
                switch (field[i][j]) {
                    case 0:
                        System.out.printf("| ");
                        break;
                    case 1:
                        System.out.printf("|X");
                        break;
                    case 2:
                        System.out.printf("|O");
                        break;
                    default:
                        System.err.println("Array value not between 0 and 2");
                }
            }
            System.out.println("|");

        }

    }

    private static int[][] addStone(int[][] field, int i, int player) {
        int j = field.length - 1;
        if (!isRowFull(field, i)) {
            while (j >= 1 && field[j - 1][i] == 0) {
                j--;
            }
            field[j][i] = player;
        } else System.err.println("this column is already full");

        return field;
    }

    // returns true if integer is in bounds (x axis)
    private static boolean inBounds(int[][] field, int i) {
        return i >= 0 && i <= field.length;
    }

    @Contract(pure = true)
    private static boolean isRowFull(int[][] field, int i) {
        return field[field.length - 1][i] != 0;
    }

    private static boolean hasWon(int[][] field, int player) {
        // TODO: 05.12.16 doesnt work if 4th element is top row
        boolean wonUp = true, wonDown = true, wonLeft = true, wonRight = true;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] == player) {
                    for (int k = 0; k < 4; k++) {
                        wonUp &= (i + k) < field.length && field[i + k][j] == player;
                        wonDown &= (i - k) >= 0 && field[i - k][j] == player;
                        wonRight &= (j + k) < field[i].length && field[i][j + k] == player;
                        wonLeft &= (j - k) >= 0 && field[i][j - k] == player;

                    }
                    if (wonDown || wonLeft || wonRight || wonUp){
                        return true;
                    }

                }
            }
        }
        return false;
    }

}
