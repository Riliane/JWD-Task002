package entity;

import java.util.*;

public class TreeNode {
    static final String UNIT_OF_SPACING = "    ";
    private String name;
    Map<String, String> properties = new HashMap<String, String>();
    private String value;
    private TreeNode parent;
    List<TreeNode> children = new ArrayList<TreeNode>();

    public TreeNode(){}

    public void addChild (TreeNode node){
        node.setParent(this);
        children.add(node);
    }
    public void addProperty (String key, String val){
        properties.put(key, val);
    }
    public void print(String spacing){
        System.out.print(spacing + name);
        if (!properties.isEmpty()){
            System.out.print(" (");
            Set<String> keys = properties.keySet();
            for (String key : keys) {
                System.out.print(key + " = " + properties.get(key) + "; ");
            }
            System.out.print(")");
        }
        System.out.println(":");
        if (!value.isEmpty()){
            System.out.println(spacing + UNIT_OF_SPACING + value);
        }
        for (TreeNode child : children){
            child.print(spacing + UNIT_OF_SPACING);
        }
    }
    public void print(){print("");}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }
}
