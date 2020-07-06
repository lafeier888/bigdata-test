

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {
    public static void main(String[] args) {
        String regex = "abc\\\td";
        System.out.println(regex);
        Pattern pattern = Pattern.compile(regex);
        String  str = "abc\tdef";
        System.out.println(str);
        Matcher matcher = pattern.matcher(str);
        System.out.println(matcher.find()) ;
        new String();

    }
}
