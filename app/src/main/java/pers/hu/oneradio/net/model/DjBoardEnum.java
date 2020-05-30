package pers.hu.oneradio.net.model;

import java.util.Random;

public enum DjBoardEnum {
    //TOP暂时没有
    RCD,HOT,TODAY;

    private static Random random = new Random();
    public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    public static DjBoardEnum getRandomDjEnum(){
        return randomEnum(DjBoardEnum.class);
    }
}
