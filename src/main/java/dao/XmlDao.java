package dao;

import entity.TreeNode;

import java.io.IOException;

public interface XmlDao {
    public TreeNode parseXML(String filename);
}
