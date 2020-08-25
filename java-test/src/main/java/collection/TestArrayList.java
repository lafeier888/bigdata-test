package collection;

import java.util.ArrayList;
import java.util.function.Consumer;

public class TestArrayList {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<Integer>() {
            {
                add(1);
                add(2);
                add(3);
                add(4);
            }

            public void forEach(Consumer<? super Integer> action, boolean falg) {

            }
        };


        list.forEach((x) -> {
            System.out.println(x);
        });
    }
}
