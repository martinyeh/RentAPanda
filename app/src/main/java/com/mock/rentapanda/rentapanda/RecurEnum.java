package com.mock.rentapanda.rentapanda;

/**
 * Created by martinyeh on 16/4/11.
 */
public enum RecurEnum {

    Once(0),
    Weekly(7),
    Every2weeks(14),
    Monthly(28);

    private int id; // Could be other data type besides int

    private RecurEnum(int id) {
        this.id = id;
    }

    public static RecurEnum fromId(int id) {
        for (RecurEnum type : RecurEnum.values()) {
            if (type.id == id) {
                return type;
            }
        }
        return null;
    }

}
