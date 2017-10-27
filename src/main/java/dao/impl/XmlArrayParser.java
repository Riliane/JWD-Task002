package dao.impl;

import entity.TreeNode;

import java.util.LinkedList;
import java.util.regex.Pattern;

public class XmlArrayParser {
    private static final String ANY_TAG_PATTERN = "<[^<>]+>";
    private static final String OPENING_TAG_PATTERN = "<[^<>=\"\\s]+(\\s+[^<>=\"\\s]+\\s*=\\s*\"[^<>\"]+\")*>";
    private static final String CLOSING_TAG_PATTERN = "</[^<>\\s]+>";
    private static final String OPEN_COMMENT_PATTERN = "<!--";
    private static final String CLOSE_COMMENT_PATTERN = "-->";
    private static final String LINE_COMMENT_PATTERN = "<!--.*-->";
    private static final String DECLARATION_PATTERN = "<\\?.*\\?>";
    private TreeNode current = null;
    private LinkedList<String> tags = new LinkedList<>();
    private boolean isComment = false;

    public void parseArray(String[] array) throws XmlParseException {
        for (String element : array) {
            if (Pattern.matches(OPEN_COMMENT_PATTERN, element)) { //opening comment
                isComment = true;
            } else if (Pattern.matches(CLOSE_COMMENT_PATTERN, element)) { //closing comment
                isComment = false;
            } else if (isComment || skipped(element)) { //empty or all space split result, anything inside a comment or single line comment
                continue;
            } else if (Pattern.matches(ANY_TAG_PATTERN, element)) {//any tag
                if (Pattern.matches(CLOSING_TAG_PATTERN, element)) {//a good closing tag
                    String name = element.substring(2, element.length() - 1);
                    closeTagByName(name);

                } else if (!Pattern.matches(OPENING_TAG_PATTERN, element)) { //not a good closing tag but not a good opening tag either
                    throw new XmlParseException();
                } else { //good opening tag
                    String[] properties = element.substring(1, element.length() - 1).split("\\s");
                    openTagByNameProperties(properties);
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
    private boolean skipped(String element){//comments, XML version declaration and
        if (Pattern.matches("\\s*", element) || Pattern.matches(LINE_COMMENT_PATTERN, element) || Pattern.matches(DECLARATION_PATTERN, element)){
            return true;
        }
        else return false;
    }
    private void openTagByNameProperties(String[] properties){
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
    private void closeTagByName (String name) throws XmlParseException{
        if (!name.equalsIgnoreCase(tags.getLast())) {
            throw new XmlParseException();
        }
        tags.removeLast();
        TreeNode parent = current.getParent();
        if (parent != null) {
            current = parent;
        }
    }
}
