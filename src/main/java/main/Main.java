package main;

import dao.DAOFactory;
import dao.XmlDao;
import dao.impl.XmlParseException;
import entity.TreeNode;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.print("Enter file path:");
        Scanner sc=new Scanner(System.in);
        String path = sc.nextLine();
        DAOFactory factory=DAOFactory.getInstance();
        XmlDao dao = factory.getXmlDao();
        try {
            TreeNode tree = dao.parseXML(path);
            if (tree != null) {tree.print();}
            else {System.out.println("Something went wrong.");}
        }
        catch (XmlParseException e){
            System.out.print("The file is badly formatted.");
        }

    }
}
