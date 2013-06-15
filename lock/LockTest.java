package lock;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class LockTest {
    static Integer orange = 2;

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, InterruptedException, InstantiationException {
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get(null);

        Field integerField = LockTest.class.getDeclaredField("orange");
        System.out.println(integerField.getInt(orange));
        long integerOffFiled = unsafe.objectFieldOffset(integerField);

//        System.out.println("Vorher "+a);
//        System.out.println(unsafe.compareAndSwapInt(a, integerOffFiled, 1, 2));
//        System.out.println("Nachher "+a);


    }

}