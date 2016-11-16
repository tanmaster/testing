import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Tan on 10.03.2016.
 * This is a small program that can be used to break Many-Time-Pads
 * The usage is pretty straight forward, just follow the console instructions.
 * <p>
 * Two example Ciphers:
 * <p>
 * 3823646A0E0B4A0818063128042543113014353C0316386F252415482006212F0A113D594A0202730D23106116131B3F120C2E42180D1751280B2E024C22324500372D3D2E1D693123253B3E1E0427032419370A742A27221E3D280768
 * 3A242623151A0F1B4456262C10331100311F376E110C741B2C2F030D21026D03130D7916196A06734723433218170076190F6A260D1706196E3C3B03093F740C08723539244A1C2822382D2E522A2C012C0A3D1576456F301A213E0368
 * <p>
 * Plain texts (I don't know which is which!)):
 * <p>
 * On, he appears cheerful and relaxed. He has avoided the fate of fellow whistleblower Chelsea.
 * Minister, referring to Theresa May as a "a sort of Darth Vader in the United Kingdom", whose.
 */
public class OneTimePad {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String[] cipherStrings = new String[2];

        //Reading two ciphers
        System.out.println("Enter Two Ciphers in Hex, separated by one Enter: ");
        cipherStrings[0] = in.nextLine();
        cipherStrings[1] = in.nextLine();


        /*
         * Initialize the arrays with half the size of one of the Strings because 2 Characters in the string represent
         * one value
         */
        int[] numberOne = new int[cipherStrings[0].length() / 2];
        int[] numberTwo = new int[cipherStrings[0].length() / 2];
        int[] numberOneXORnumberTwo = new int[cipherStrings[0].length() / 2];

        /*
         * XOR'ing the two input strings. I tried the other ones too, these were just the last ones i tried.
         */
        for (int i = 0; i < cipherStrings[0].length() / 2; i++) {
            numberOne[i] = Integer.parseInt(cipherStrings[0].substring(2 * i, 2 * i + 2), 16);
            numberTwo[i] = Integer.parseInt(cipherStrings[1].substring(2 * i, 2 * i + 2), 16);
            numberOneXORnumberTwo[i] = numberOne[i] ^ numberTwo[i];
        }


        String messageOne = "", messageTwo = "", word;



        //Creating empty messages
        for (int i = 0; i < cipherStrings[0].length() / 2; i++) {
            messageOne += "_";
            messageTwo += "_";
        }

        boolean done = false, flag = false;

        while (true) {

            if (!done) {
                for (int i = 0; i < messageOne.length(); i++) {
                    if (messageOne.charAt(i) == '_') break;
                    if (i == messageOne.length() - 1) done = flag = true;
                }
            }

            if (flag) {
                System.out.println("Looks like you are done, do you want to stop and try to retrieve the key? (Y | N)");
                word = in.nextLine();
                if (word.equalsIgnoreCase("y")) {
                    break;
                } else if (word.equalsIgnoreCase("n")) flag = false;
            }


            System.out.println("Enter a word you want to test on occurrence or enter \"keysPlease\" to try and get your keys: ");
            word = in.nextLine();
            if (word.equalsIgnoreCase("keysplease")) {
                break;
            }


            int[] intValuesOfWord = plainToIntArray(word);

            ArrayList<String> output = new ArrayList<>();
            for (int i = 0; i < numberOneXORnumberTwo.length - (intValuesOfWord.length - 1); i++) {
                output.add("");
                for (int j = 0; j < intValuesOfWord.length; j++) {
                    output.set(i, output.get(i) + (char) (numberOneXORnumberTwo[i + j] ^ intValuesOfWord[j]));

                }
            }

            for (int i = 0; i < output.size(); i++) {

                for (int j = 0; j < output.get(i).length(); j++) {
                    if (!Character.isLetter(output.get(i).charAt(j)) && output.get(i).charAt(j) != ' '
                            && output.get(i).charAt(j) != ',' && output.get(i).charAt(j) != '.'
                            && output.get(i).charAt(j) != '"' && output.get(i).charAt(j) != '?'
                            && output.get(i).charAt(j) != '!' && output.get(i).charAt(j) != '-' ) {
                        /*
                         * this if is bad because it breaks at Characters like commas, spaces or exclamation marks
                         * therefore not every possible solution is being highlighted
                         */
                        break;
                    }
                    if (j == output.get(i).length() - 1) {

                        //if the loop has not 'broken' so far we will highlight the current line
                        System.out.printf("******* ");
                    }
                }

                //print index and output
                System.out.println(i + ": " + output.get(i));
            }


            String index;
            do {
                System.out.println("Enter index of String you want to choose or \"none\"");
                index = in.nextLine();
            }
            while (index.equals("") || (!index.equals("none") && !isNumericAndInBounds(index, output.size())));

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



                 // Mix the current messages with the new found parts

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


        System.out.println("Two possible keys: ");
        System.out.println(xorStringsOfSameLength(cipherStrings[0], plainToHex(messageOne)));
        System.out.println(xorStringsOfSameLength(cipherStrings[1], plainToHex(messageOne)));


    }

    /**
     * XOR's two HEX strings of same length!!!
     */
    private static String xorStringsOfSameLength(String one, String two) {
        String result = "";
        String helper;
        for (int i = 0; i < one.length() / 2; i++) {
            helper = Integer.toHexString(Integer.parseInt(one.substring(2 * i, 2 * i + 2), 16) ^ Integer.parseInt(two.substring(2 * i, 2 * i + 2), 16));
            result += helper.length() == 1 ? "0" + helper : helper;
        }

        return result;

    }


    /**
     * One Character in String will result in two Hex Digits
     */
    private static String plainToHex(String string) {
        String result = "";

        for (int i = 0; i < string.length(); i++) {
            result += Integer.toHexString(string.charAt(i));
        }

        return result;
    }

    /**
     * Transforms a String to its ASCII values. Result array has same length as input String and contains a value
     * between 0 and 255 (I think), one for each Character in string.
     *
     * @param string input string
     * @return an array that contains the values of every Character of string
     */
    private static int[] plainToIntArray(String string) {

        int[] wordToInt = new int[string.length()];
        for (int i = 0; i < string.length(); i++) {
            wordToInt[i] = ((int) (string.charAt(i)));
        }

        return wordToInt;
    }


    /**
     * @param upperBound the upper bound the pared integer can be max.
     * @param str        the String that shall be checked if numeric and if in bounds
     * @return true if parsed integer is numeric and in bounds
     */
    private static boolean isNumericAndInBounds(String str, int upperBound) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }

        return Integer.parseInt(str) >= 0 && Integer.parseInt(str) < upperBound;
    }

}
