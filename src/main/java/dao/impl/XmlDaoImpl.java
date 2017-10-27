package dao.impl;

import dao.XmlDao;
import entity.TreeNode;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlDaoImpl implements XmlDao{
    final String SEPARATING_PATTERN = "<[^<>]+>|<!--|-->"; //this pattern finds tags in <> and comment openers and closers
    final String TAG_PATTERN = "<[^<>=\"\\s]+(\\s+[^<>=\"\\s]+\\s*=\\s*\"[^<>\"]+\")*>";
    @Override
    public TreeNode parseXML(String filename) throws XmlParseException{
        BufferedReader in = null;
        TreeNode root = null;
        try {
            File file = new File(filename);
            if (!file.exists()) {
                throw new FileNotFoundException();
            } //put this in services
            in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            StringSplitter splitter = new StringSplitter(SEPARATING_PATTERN, "", "");
            XmlArrayParser parser = new XmlArrayParser();

            String fileString = in.readLine();
            while (fileString != null) {
                String[] tokens = splitter.split(fileString);
                parser.parseArray(tokens);
                fileString = in.readLine();
            }
            root = parser.getParsedTree();


        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e){
            System.out.println("File error");
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    System.out.println("File error");
                }
            }
        }


        return root;
    }
}
