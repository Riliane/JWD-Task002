package dao.impl;

import dao.XmlDao;
import entity.TreeNode;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlDaoImpl implements XmlDao{
    final String SEPARATING_PATTERN = "<[^<>]+>|[^<>]+"; //this pattern finds tags in <> and chunks of text in between
    final String TAG_PATTERN = "<[^<>=\"\\s]+(\\s+[^<>=\"\\s]+\\s*=\\s*\"[^<>\"]+\")*>";
    final String COMMENT_PATTERN = "<!--.*-->";//what to do with comments with line breaks??
    @Override
    public TreeNode parseXML(String filename){
        TreeNode root = new TreeNode();
        TreeNode current = root;
        List<String> tags = new LinkedList<>();
        BufferedReader in = null;
        try {
            File file = new File(filename);
            if (!file.exists()) {
                throw new FileNotFoundException();
            } //put this in services
            in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            StringSplitter splitter = new StringSplitter(SEPARATING_PATTERN, "", "");

            String fileString = in.readLine();
            String[] tokens = splitter.split(fileString);
            //now parse each token



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


        return null;
    }
}
