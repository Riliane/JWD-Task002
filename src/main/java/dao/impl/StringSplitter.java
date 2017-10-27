package dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringSplitter {
    final static String SEPARATING_PATTERN = "<[^<>]+>|<!--|-->"; //this pattern finds tags in <> and comment openers and closers
    Pattern separatingPattern;

    StringSplitter(){
        separatingPattern = Pattern.compile(SEPARATING_PATTERN);
    }

    public String[] split (String str){

        Matcher m = separatingPattern.matcher(str);
        List<String> tokens = new ArrayList<>();
        int lastPosition = 0;
        while(m.find())
        {
            String token = m.group( 0 );
            String before = str.substring(lastPosition, m.start());
            tokens.add(before);
            tokens.add(token);
            lastPosition = m.end();
        }
        return tokens.toArray(new String[0]);
    }
}
