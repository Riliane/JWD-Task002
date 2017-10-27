package dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringSplitter {
    String separatingRegex;
    String tagRegex;
    String commentRegex;
    Pattern separatingPattern;

    StringSplitter(String general, String tag, String comment){
        separatingRegex = general;
        tagRegex = tag;
        commentRegex = comment;
        separatingPattern = Pattern.compile(separatingRegex);
    }

    public String[] split (String str){

        Matcher m = separatingPattern.matcher(str);
        List<String> tokens = new ArrayList<>();
        while(m.find())
        {
            String token = m.group( 0 );
            tokens.add(token);
        }
        int length = tokens.size();
        for (int i = 0; i < length; i++){
            if (Pattern.matches(commentRegex, tokens.get(i))){ //if it's a comment
                tokens.remove(i);
                i--;
                length--;
            }
            else if (tokens.get(i).charAt(0) == '<' && !Pattern.matches(tagRegex, tokens.get(i))){ //if it's a tag but a badly formatted one
                //throw exception
            }
        }
        return (String[])tokens.toArray();
    }
}
