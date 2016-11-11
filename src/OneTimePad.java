import java.math.BigInteger;

/**
 * Created by Tan on 10.03.2016.
 */
public class OneTimePad {
    public static void main(String[] args) {

        String[] strings = new String[]{"242C212E463D04061F123127563E02167801353C030C2622212E51097213382C1E1D3A57192F15250C21066F57311C33560A2B0F1C041B16204A39081921304514372234281C2C662A6C2E3F00152D0A394E3E11321D6F21002120462D04033C243970"
                , "021E6825084E3D0C0C18313A12371A5E7806393A0D43242325240248340C3F6E210036190F6A063D0162302F1812103318493E0D4C0F1D18204A33094C2C74010F21222432192029256C292C060437182A1C360B741F2626522F6D100B0C0F254B"
                , "23226828034E0F1F0D18743A1A3F0E1F3D037E6E2D06742B2139121A3B01282A520031124A7857625362133312161D3213073E0B0D0952032F093F470D3E741008223334220F2D2325382D2E52432C016B1A370A391A6F28146E390E07481925333F70"
                , "3528242303180F490118786B563E06522B10392A4B43761B2C2302483B106D2F5217361A1A26022B45310A350204003F190764423E100102270B7A0E1F6D3A0A12722C28610B3B232A6C272C52072A0C3E1D7C581D1D6F2E016E2309164807334B"
                , "2E2829384A4E190648013C3056320C5231056F6E473431232866510126433A2107183D5704251373072743351F0054301F1B39164C111B1C2B4A1347042C22004626203A240469276B3E213919412300394E2117390C3B2F1B202A462B62"
                , "3F243B6A0B0B070601042769142317523010236E0B0C206F292B150D720E382D1A542905052D153616314D61230D112413492B10094513056E063F061F3974110E20243461082629203F682B100E301B6B063B15740621670626284615091371612A1468"
                , "0439293E031D51491A1720211324431B2C51382F170E276F302214057C416D1D1C1B2E130F24473211361120141107761A063E114C0A14512D0534141C242604052B6125290F263422293B645224241D271772173A456F2F176E3A071162"
                , "32392023050F0605115A743E1E330D522F1470220A0C3F6F253E511C3A066D3C17072C1B1E394B730C36433212001925561D22071F0052062B183F470228370015212023384A3D2E22222F395E4131072E1D3758230C3D2252382412030460"
                , "3F286823154E0B0A0B03272C12760C1478073921090220262A2D511C3A066D0B01043018042B00364503003557041A32560F2B01091652103A4A36020D3E204555626128240B3B356B25266A18002C03654E01083108242E1C296D090C62"
                , "3823646A0E0B4A0818063128042543113014353C0316386F252415482006212F0A113D594A0202730D23106116131B3F120C2E42180D1751280B2E024C22324500372D3D2E1D693123253B3E1E0427032419370A742A27221E3D280768"
        };

        String[][] xoredStrings = new String[strings.length][strings.length];

        BigInteger[] bigIntegers = new BigInteger[strings.length];
        BigInteger[][] xored = new BigInteger[strings.length][strings.length];
        BigInteger[][] firstSix = new BigInteger[strings.length][strings.length];
        BigInteger[][] results = new BigInteger[strings.length][strings.length];
        String[][] stringResult = new String[strings.length][strings.length];

        int[][] xoredInts = new int[strings.length][strings.length];
        String the = "The ";
        the = toHex(the);

        for (int i = 0; i < the.length(); i++) {
            if (the.charAt(i) != '0') {
                the = the.substring(i, the.length());
                break;
            }
        }

        //System.out.println(the);

        //System.out.printf((char) 116 + "" + (char) 104 + "" + (char) 101);
        //System.out.println();


        for (int i = 0; i < strings.length; i++) {
            bigIntegers[i] = new BigInteger(strings[i], 16);
        }


        for (int i = 0; i < strings.length; i++) {
            for (int j = 0; j < strings.length; j++) {
                xored[j][i] = bigIntegers[i].xor(bigIntegers[j]);
                //         System.out.println(xored[j][i]);
            }
        }

        for (int i = 0; i < xored.length; i++) {
            for (int j = 0; j < xored[i].length; j++) {
                firstSix[i][j] = xored[i][j].toString().length() > 1 ? new BigInteger(xored[i][j].toString(16).substring(0, the.length()), 16) : new BigInteger("0");
                //System.out.println(firstSix[i][j]);
                //       System.out.println(firstSix[i][j].toString(16));
            }
        }


        for (int i = 0; i < xored.length; i++) {
            for (int j = 0; j < xored[i].length; j++) {
                xoredStrings[i][j] = xored[i][j].toString(16);
                //          System.out.println(xoredStrings[i][j]);
            }
        }


        for (int i = 0; i < stringResult.length; i++) {
            for (int j = 0; j < stringResult[i].length; j++) {
                stringResult[i][j] = "";
            }
        }

/*
        for (int i = 0; i < 8; i++) {
            the += the;
        }


        for (int i = 0; i < xored.length; i++) {
            for (int j = 0; j < xored[i].length; j++) {
                String helper = the.substring(0, xored[i][j].toString(16).length());
                 results[i][j] = xored[i][j].xor(new BigInteger(helper, 16));
                System.out.println(results[i][j].toString(16));
            }
        }

*/

        for (int i = 0; i < xored.length; i++) {
            for (int j = 0; j < xored[i].length; j++) {



                BigInteger a = xored[i][j];
                if (a.toString(16).length() < the.length()){
                    break;
                }

                String[] helper = new String[(a.toString(16).length() - (the.length() - 1) - 1)];
                String zeros = "";
                int f = the.length();

                for (int k = 0; k < a.toString(16).length() - f; k++) {

                    helper[k] = a.xor(new BigInteger(the, 16)).toString(16);
                    the += "0";
                    //System.out.println(helper[i]);
                }
                checker(hexToString(helper), f);

            }

        }




        /*






        for (int i = 0; i < stringResult.length; i++) {
            for (int j = 0; j < stringResult[i].length; j++) {
                System.out.println(stringResult[i][j]);
            }
        }
*/



/*
        for (int i = 0; i < firstSix.length; i++) {
            for (int j = 0; j < firstSix[i].length; j++) {
                xoredInts[i][j] = firstSix[i][j] ^ the;

                while (true) {
                    if (p >= String.valueOf(xoredInts[i][j]).length() - 1) {
                        System.out.printf(""+(char) (Integer.parseInt(String.valueOf(xoredInts[i][j]).substring(o, p + 1))));

                        break;
                    }

                    if ((Integer.valueOf(String.valueOf(xoredInts[i][j]).substring(o, p)) < 256 && Integer.valueOf(String.valueOf(xoredInts[i][j]).substring(o, p + 1)) > 256)
                            || (Integer.valueOf(String.valueOf(xoredInts[i][j]).substring(o, p)) < 256 && p == String.valueOf(xoredInts[i][j]).length()) ) {

                        System.out.print((char) (Integer.parseInt(String.valueOf(xoredInts[i][j]).substring(o, p))));
                        o = p;
                        p = p < String.valueOf(xoredInts[i][j]).length() - 1 ? p + 1 : p;

                    } else {
                        p = p < String.valueOf(xoredInts[i][j]).length() - 1 ? p + 1 : p;

                    }

                }
                p = 1;
                o = 0;
                System.out.println();
            }
        }
*/

    }

    public static String[] hexToString(String[] arr) {


        String[] result = new String[arr.length];


        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length(); j += 2) {
                //System.out.print(arr[i].substring(j, j + 2));
                result[i] += (char) Integer.parseInt(arr[i].substring(j, j + 2), 16);
            }
        }

        return result;
    }

    public static void checker (String[] arr, int i){
        for (int j = 0; j < arr.length; j++) {
            for (int k = 0; k < arr[j].length(); k++) {
                for (int l = k; l < k + i; l++) {
                    if (l >= arr[j].length() - 1){
                        break;
                    }
                    if (!Character.isLetter(arr[j].charAt(l))){
                        break;
                    }
                    if (l == k + i - 1){
                        System.out.println(arr[j]);
                    }
                }
            }
        }

    }

    public static String toHex(String arg) {
        return String.format("%040x", new BigInteger(1, arg.getBytes(/*YOUR_CHARSET?*/)));
    }
}
