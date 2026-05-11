package client.src.enums;

import java.util.Arrays;

import client.src.exceptions.WrongParam;

public enum Color {
    YELLOW(1),
    ORANGE(2),
    WHITE(3);


    private final int num;

    Color(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }
    public static Color getVal(int num) throws WrongParam{
        return Arrays.stream(values())
            .filter(col -> col.getNum() == num)
            .findFirst()
            .orElseThrow(() -> new WrongParam("Некорректное значение: "));
    }
}