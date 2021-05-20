package compiler;

import java.util.ArrayList;
import java.util.List;

public class CompilerImpl implements Compiler{

    @Override
    public void compile(String s) {
        System.out.println(s);
        String query = s;
        String[] varCheck;
        String[] functions;
        String[] words;
        String[] finalWords;
        String temp = "";
        ArrayList<String> input = new ArrayList<>();

        varCheck = query.split(" ");
        //System.out.println(varCheck[varCheck.length-1]);

        functions = varCheck[varCheck.length-1].split("[.]");
        //System.out.println(rightSide);
        //System.out.println(functions[functions.length-1]);

        for(int i=0; i<functions.length; i++){
            temp += functions[i];
        }
        System.out.println(temp);

        words = temp.split("\"");
        for(int i=0; i<words.length; i++){
           System.out.println(words[i]);
        }

    }
}
