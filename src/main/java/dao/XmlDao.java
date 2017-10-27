package dao;

import dao.impl.XmlParseException;
import entity.TreeNode;

public interface XmlDao {
    public TreeNode parseXML(String filename) throws XmlParseException;
}
