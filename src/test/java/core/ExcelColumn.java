package core;

import lombok.Getter;

public class ExcelColumn {

    @Getter static private String positionLetter="";
    @Getter static private Integer positionRow = 0;

    private ExcelColumn() {}

    public static int toNumber(String name) {
        int number = 0;
        for (int i = 0; i < name.length(); i++) {
            number = number * 26 + (name.charAt(i) - ('A' - 1));
        }
        return number;
    }

    public static String toName(int number) {
        StringBuilder sb = new StringBuilder();
        while (number-- > 0) {
            sb.append((char)('A' + (number % 26)));
            number /= 26;
        }
        return sb.reverse().toString();
    }


    public static void processPosition(String posi) {
        String letter = "";
        String number = "";
        for(int i = 0; i < posi.length(); i++) {
            char c = posi.charAt(i);
            if(c >= '0' && c <= '9') number+= c;
            else letter+=c;
        }

        positionLetter = letter;
        positionRow = Integer.valueOf(number);
    }
}
