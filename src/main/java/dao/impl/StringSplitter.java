package dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringSplitter {
    String separatingRegex;
    Pattern separatingPattern;

    StringSplitter(String general, String tag, String comment){
        separatingRegex = general;
        separatingPattern = Pattern.compile(separatingRegex);
    }

    public String[] split (String str){

        Matcher m = separatingPattern.matcher(str);
        List<String> tokens = new ArrayList<>();
        int lastPosition = 0;
        while(m.find())
        {
            String token = m.group( 0 );
            String before = str.substring(lastPosition, m.start()-1);
            tokens.add(before);
            tokens.add(token);
            lastPosition = m.end()+1;
        }
        /*int length = tokens.size();
        for (int i = 0; i < length; i++){
            if (tokens.get(i).charAt(0) == '<' && !Pattern.matches(tagRegex, tokens.get(i))){ //if it's a tag but a badly formatted one
                //throw exception
            }
        }*/
        return (String[])tokens.toArray();
    }
}
