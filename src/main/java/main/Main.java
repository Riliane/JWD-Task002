package main;

import dao.impl.XmlDaoImpl;
import dao.impl.XmlParseException;
import entity.TreeNode;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.print("Enter file path:");
        Scanner sc=new Scanner(System.in);
        String path = sc.nextLine();
        XmlDaoImpl dao = new XmlDaoImpl();
        try {
            TreeNode tree = dao.parseXML(path);
            tree.print();
        }
        catch (XmlParseException e){
            System.out.print("The file is badly formatted.");
        }

    }
}
