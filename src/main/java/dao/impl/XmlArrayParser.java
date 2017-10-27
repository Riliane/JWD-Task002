package dao.impl;

import entity.TreeNode;

import java.util.LinkedList;
import java.util.regex.Pattern;

public class XmlArrayParser {
    final String TAG_PATTERN = "<[^<>=\"\\s]+(\\s+[^<>=\"\\s]+\\s*=\\s*\"[^<>\"]+\")*>";
    TreeNode current = null;
    LinkedList<String> tags = new LinkedList<>();
    boolean isComment = false;

    public void parseArray(String[] array) throws XmlParseException {
        for (String element : array) {
            if (Pattern.matches("<!--", element)) { //opening comment
                isComment = true;
            } else if (Pattern.matches("-->", element)) { //closing comment
                isComment = false;
            } else if (Pattern.matches("\\s*", element) || isComment ||Pattern.matches("<!--.*-->", element)) { //empty or all space split result, anything inside a comment or single line comment
                continue;
            } else if (Pattern.matches("<[^<>]+>", element)) {//any tag
                if (Pattern.matches("</[^<>\\s]+>", element)) {//a good closing tag
                    String name = element.substring(2, element.length() - 1);
                    if (!name.equalsIgnoreCase(tags.getLast())) {
                        throw new XmlParseException();
                    }
                    tags.removeLast();
                    TreeNode parent = current.getParent();
                    if (parent != null) {
                        current = parent;
                    }

                } else if (!Pattern.matches(TAG_PATTERN, element)) { //not a good closing tag but not a good opening tag either
                    throw new XmlParseException();
                } else { //good opening tag
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
            } else { //text
                current.setValue(current.getValue().concat(element));
            }
        }
    }
    public TreeNode getParsedTree() throws XmlParseException{
        if (!tags.isEmpty()) {
            throw new XmlParseException();
        }
        return current;
    }
}
