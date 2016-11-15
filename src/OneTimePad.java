import com.sun.deploy.util.StringUtils;
import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;

import javax.imageio.IIOException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.NavigableMap;
import java.util.Scanner;
import java.util.UnknownFormatConversionException;

/**
 * Created by Tan on 10.03.2016.
 */
public class OneTimePad {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String[] cipherStrings = new String[2];

        /**
         * Reading two ciphers
         * */
        System.out.println("Enter Two Ciphers in Hex: ");
        cipherStrings[0] = in.nextLine();
        cipherStrings[1] = in.nextLine();

        /**
         * Initialize the arrays with half the size of one of the Strings because 2 Characters in the string represent
         * one value
         */
        int[] numberOne = new int[cipherStrings[0].length() / 2];
        int[] numberTwo = new int[cipherStrings[0].length() / 2];
        int[] numberOneXORnumberTwo = new int[cipherStrings[0].length() / 2];

        /**
         * XOR'ing the two input strings. I tried the other ones too, these were just the last ones i tried.
         * */
        for (int i = 0; i < cipherStrings[0].length() / 2; i++) {
            numberOne[i] = Integer.parseInt(cipherStrings[0].substring(2 * i, 2 * i + 2), 16);
            numberTwo[i] = Integer.parseInt(cipherStrings[1].substring(2 * i, 2 * i + 2), 16);
            numberOneXORnumberTwo[i] = numberOne[i] ^ numberTwo[i];
            //System.out.printf(" " + numberOneXORnumberTwo[i]);
        }


        String messageOne = "", messageTwo = "", word = "";


        for (int i = 0; i < cipherStrings[0].length() / 2; i++) {
            messageOne += "_";
            messageTwo += "_";
        }


        while (true) {

            System.out.println("Enter a word: ");
            word = in.nextLine();

            int[] wordToInt = new int[word.length()];
            for (int i = 0; i < word.length(); i++) {
                wordToInt[i] = ((int) (word.charAt(i)));
            }

            ArrayList<String> output = new ArrayList<>();
            for (int i = 0; i < numberOneXORnumberTwo.length - (wordToInt.length - 1); i++) {
                output.add("");
                for (int j = 0; j < wordToInt.length; j++) {
                    output.set(i, output.get(i) + (char) (numberOneXORnumberTwo[i + j] ^ wordToInt[j]));

                }
            }

            for (int i = 0; i < output.size(); i++) {

                for (int j = 0; j < output.get(i).length(); j++) {
                    if (!Character.isLetter(output.get(i).charAt(j))) {
                        /**
                         * this if is bad because it breaks at Characters like commas, spaces or exclamation marks
                         * therefore not every possible solution is being highlighted
                         * */
                        break;
                    }
                    if (j == output.get(i).length() - 1) {
                        /**
                         * if the loop has not 'broken' so far we will highlight the current line
                         * */
                        System.out.printf("******* ");
                    }
                }

                /**
                 * print index and output
                 * */
                System.out.println(i + ": " + output.get(i));
            }


            String index;
            do {
                System.out.println("Enter index of String you want to choose or \"none\"");
                index = in.nextLine();
            }
            while (!index.equals("none") && !isNumericAndInBounds(index, output.size()));

            if (!index.equals("none")) {

                String input;
                Boolean m = false, k = false;
                do {
                    System.out.println("Is \"" + word + "\" part of message one or part of message two? ( O | T )");
                    input = in.nextLine();
                    if (input.equalsIgnoreCase("messageOne") || input.equalsIgnoreCase("o")) {
                        m = true;
                    } else if (input.equalsIgnoreCase("messageTwo") || input.equalsIgnoreCase("t")) {
                        k = true;
                    }

                } while (!m && !k);

                System.out.println(index);


                /**
                 * Mix the current messages with the new found parts
                 * */
                if (k) {
                    messageOne = messageOne.substring(0, Integer.valueOf(index)) + output.get(Integer.valueOf(index)) +
                            messageOne.substring(Integer.valueOf(index) + output.get(Integer.valueOf(index)).length());
                    messageTwo = messageTwo.substring(0, Integer.valueOf(index)) + word + messageTwo.substring(Integer.valueOf(index) + word.length());
                } else if (m) {
                    messageTwo = messageTwo.substring(0, Integer.valueOf(index)) + output.get(Integer.valueOf(index)) +
                            messageTwo.substring(Integer.valueOf(index) + output.get(Integer.valueOf(index)).length());
                    messageOne = messageOne.substring(0, Integer.valueOf(index)) + word + messageOne.substring(Integer.valueOf(index) + word.length());
                }

            }


            System.out.println("messageOne: " + messageOne);
            System.out.println("messageTwo: " + messageTwo);

        }


    }


    private static boolean isNumericAndInBounds(String str, int upperBound) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }

        return Integer.parseInt(str) >= 0 && Integer.parseInt(str) < upperBound;
    }

}
