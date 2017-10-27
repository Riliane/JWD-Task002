package dao;

import dao.impl.XmlParseException;
import entity.TreeNode;

public interface XmlDao {
    TreeNode parseXML(String filename) throws XmlParseException;
}
