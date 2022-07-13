import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter two numbers in format: {source base} {target base} (To quit type /exit)");
        String bases = scanner.nextLine();
        while (!bases.equals("/exit")) {
            String[] basesArr = bases.split(" ");
            try {
                terminal(basesArr[0], basesArr[1], scanner);
            } catch (Exception e) {
                System.out.println("Not a number!");
            }
            System.out.println("Enter two numbers in format: {source base} {target base} (To quit type /exit)");
            bases = scanner.nextLine();
        }
    }

    public static String decimalToRestInts(String n, int target) {
        String[] strings = n.split("\\.");
        BigInteger result = new BigInteger(strings[0]);
        BigInteger[] arr = new BigInteger[1000];
        int i = 0;
        BigInteger bigTarget = BigInteger.valueOf(target);
        while (!result.equals(BigInteger.valueOf(0))) {
            BigInteger t = result.mod(bigTarget);
            arr[i] = t;
            result = result.divide(bigTarget);
            i++;
        }
        StringBuilder sb = new StringBuilder();
        for (int j = i - 1; j >= 0; j--) {
            sb.append(arr[j]);
        }
        if (sb.length() == 0) {
            sb.append(0);
        }
        if (strings.length != 1) {
            sb.append(".");
            strings[1] = "0." + strings[1];
            BigDecimal result2 = new BigDecimal(strings[1]);
            BigDecimal bigTargetDec = new BigDecimal(target);
            String[] arr2 = new String[20];
            int k = 0;
            while (!result2.equals(BigDecimal.ZERO) && k < 20) {
                result2 = result2.multiply(bigTargetDec);
                BigDecimal integ;
                integ = result2.setScale(0, RoundingMode.FLOOR);
                arr2[k] = integ.toString();
                result2 = result2.subtract(integ);
                k++;
            }
            for (int j = 0; j < k; j++) {
                sb.append(arr2[j]);
            }
        }
        return sb.toString();
    }

    public static String decimalToRestChar(String num, int target) {
        if (num.equals("0")) {
            return num;
        }
        String[] strings = num.split("\\.");
        BigInteger n = new BigInteger(strings[0]);
        char[] hexadecimalNum = new char[1000];
        int i = 0;
        BigInteger bigTarget = BigInteger.valueOf(target);
        while (n.compareTo(BigInteger.valueOf(0)) != 0) {
            BigInteger t = n.mod(bigTarget);
            if (t.compareTo(BigInteger.valueOf(10)) < 0) {
                hexadecimalNum[i] = (char) (t.intValue() + 48);
            } else {
                hexadecimalNum[i] = (char) (t.intValue() + 55);
            }
            n = n.divide(bigTarget);
            i++;
        }
        StringBuilder sb = new StringBuilder();
        for (int j = i - 1; j >= 0; j--) {
            sb.append(hexadecimalNum[j]);
        }
        if (sb.length() == 0) {
            sb.append(0);
        }
        if (strings.length != 1) {
            sb.append(".");
            strings[1] = "0." + strings[1];
            BigDecimal result2 = new BigDecimal(strings[1]);
            BigDecimal bigTargetDec = new BigDecimal(target);
            char[] arr2 = new char[20];
            int k = 0;
            while (!result2.equals(BigDecimal.ZERO) && k < 20) {
                result2 = result2.multiply(bigTargetDec);
                BigDecimal integ;
                integ = result2.setScale(0, RoundingMode.FLOOR);
                if (integ.compareTo(BigDecimal.valueOf(10)) < 0) {
                    arr2[k] = (char) (integ.intValue() + 48);
                } else {
                    arr2[k] = (char) (integ.intValue() + 55);
                }
                result2 = result2.subtract(integ);
                k++;
            }
            for (int j = 0; j < k; j++) {
                sb.append(arr2[j]);
            }
        }
        return sb.toString().toLowerCase();
    }

    public static String restIntToDecimal(String str, int source) {
        String[] strings = str.split("\\.");
        BigInteger n = new BigInteger(strings[0]);
        BigInteger decimal = BigInteger.valueOf(0);
        for (int i = 0; n.compareTo(BigInteger.valueOf(0)) > 0; i++) {
            BigInteger t = n.mod(BigInteger.valueOf(10));
            decimal = decimal.add(t.multiply(BigInteger.valueOf(source).pow(i)));
            n = n.divide(BigInteger.valueOf(10));
        }
        StringBuilder sb = new StringBuilder().append(decimal);
        if (strings.length > 1) {
            BigDecimal result = BigDecimal.ZERO;
            char[] chars = strings[1].toCharArray();
            int[] ints = new int[chars.length];
            for (int i = 0; i < chars.length; i++) {
                ints[i] = chars[i] - '0';
            }
            for (int i = 0; i < ints.length; i++) {
                if (ints[i] != 0) {
                    result = result.add(BigDecimal.valueOf(ints[i]).multiply(BigDecimal.valueOf(source).pow((i + 1) * -1, MathContext.DECIMAL128)));
                }
            }
            sb.append(".");
            if (result.compareTo(BigDecimal.valueOf(0)) != 0) {
                if (result.compareTo(BigDecimal.ONE) < 0) {
                    String res = result.toString().substring(2);
                    sb.append(res);
                } else {
                    sb.append(result);
                }
            } else {
                sb.append("0".repeat(6));
            }
        }
        return sb.toString();
    }

    public static String restCharToDecimal(String n, int source) {
        String digits = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        n = n.toUpperCase();
        BigInteger val = BigInteger.valueOf(0);
        String[] strings = n.split("\\.");
        for (int i = 0; i < strings[0].length(); i++)
        {
            char c = strings[0].charAt(i);
            int d = digits.indexOf(c);
            val = val.multiply(BigInteger.valueOf(source)).add(BigInteger.valueOf(d));
        }
        StringBuilder sb = new StringBuilder().append(val);
        if (strings.length > 1) {
            BigDecimal result = new BigDecimal(0);
            for (int i = 0; i < strings[1].length(); i++)
            {
                char c = strings[1].charAt(i);
                int d = digits.indexOf(c);
                if (d != 0) {
                    int pow = (i + 1) * -1;
                    result = result.add(BigDecimal.valueOf(d).multiply(BigDecimal.valueOf(source).pow(pow, MathContext.DECIMAL128)));
                }
            }
            sb.append(".");
            if (result.compareTo(BigDecimal.valueOf(0)) != 0) {
                if (result.compareTo(BigDecimal.ONE) < 0) {
                    String res = result.toString().substring(2);
                    sb.append(res);
                } else {
                    sb.append(result);
                }
            } else {
                sb.append("0".repeat(6));
            }
        }
        return sb.toString();
    }

    public static void terminal(String source, String target, Scanner scanner) {
        System.out.printf("Enter number in base %s to convert to base %s (To go back type /back)", source, target);
        System.out.println();
        String number = scanner.nextLine();
        if (!number.equals("/back")) {
            if (!source.equals("10")) {
                if (Integer.parseInt(source) < 11) {
                    number = restIntToDecimal(number, Integer.parseInt(source));
                } else {
                    number = restCharToDecimal(number, Integer.parseInt(source));
                }
            }
            if (!target.equals("10")) {
                if (Integer.parseInt(target) < 11) {
                    number = decimalToRestInts(number, Integer.parseInt(target));
                } else {
                    number = decimalToRestChar(number, Integer.parseInt(target));
                }
            }
            String[] strings = number.split("\\.");
            if (strings.length > 1) {
                System.out.println("Conversion result: " + strings[0] + "." + strings[1].substring(0, 5));
            } else {
                System.out.println("Conversion result: " + strings[0]);
            }
            terminal(source, target, scanner);
        }
    }
}
