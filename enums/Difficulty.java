package enums;

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
        for (Difficulty dif : values()) {
            if (dif.getNum() == num) {
                return dif;
            }
        }
        throw new WrongParam("Некорректное значение: ");
    }
}
