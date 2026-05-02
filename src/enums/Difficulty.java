package enums;

import java.util.Arrays;

import exceptions.WrongParam;

public enum Difficulty {
    HARD(1),
    VERY_HARD(2),
    INSANE(3);

    private final int num;

    Difficulty(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }
    public static Difficulty getVal(int num) throws WrongParam{
        return Arrays.stream(values())
            .filter(dif -> dif.getNum() == num)
            .findFirst()
            .orElseThrow(() -> new WrongParam("Некорректное значение: "));
    }
}
