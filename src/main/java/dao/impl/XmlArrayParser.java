package dao.impl;

import entity.TreeNode;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class XmlArrayParser {
    String tagPattern;
    TreeNode root = null;
    TreeNode current = null;
    LinkedList<String> tags = new LinkedList<>();
    boolean isComment;
    String remainder = "";

    public void parseArray(String[] array) throws XmlParseException{
        for (String element : array) {
            if (Pattern.matches("<!--", element)) {
                isComment = true;
            } else if (Pattern.matches("-->", element)) {
                isComment = false;
            } else if (element.isEmpty() || isComment) {
                continue;
            } else if (Pattern.matches("<[^<>]+>", element)) {
                if (Pattern.matches("</[^<>\\s]+>", element)) {//if it's a good closing tag
                    String name = element.substring(2, element.length()-1);
                    if (!name.equalsIgnoreCase(tags.getLast())) {
                    throw new XmlParseException();
                    }
                    tags.removeLast();
                    TreeNode parent = current.getParent();
                    if (parent != null){current = parent;}

                } else if (!Pattern.matches(tagPattern, element)) { //if it's not a good opening tag either
                    throw new XmlParseException();
                } else {
                    String[] properties = element.substring(1, element.length() - 1).split("\\s");
                    TreeNode node = new TreeNode();
                    if (current != null) {
                        current.addChild(node);
                    }
                    current = node;

                    current.setName(properties[0]);
                    tags.add(properties[0]);
                    for (int i = 1; i < properties.length; i++) {
                        String[] parts = properties[i].split("=", 2);
                        current.addProperty(parts[0], parts[1]);
                    }
                }
            }

        }
    }
}
