package compiler;

import java.util.ArrayList;
import java.util.List;

public class CompilerImpl implements Compiler{

    @Override
    public void compile(String s) {
        System.out.println(s);
        String a;
        String query = s;
        String[] varCheck;
        String[] functions;
        String[] words;
        String[] finalWords;
        String temp = "";
        ArrayList<String> input = new ArrayList<>();

        varCheck = query.split("new ");
        //System.out.println(varCheck[varCheck.length-1]);

        functions = varCheck[varCheck.length-1].split("\\).");
        //System.out.println(rightSide);
        //System.out.println(functions[functions.length-1]);

        for(int i=0; i<functions.length; i++){
            System.out.println(functions[i]);

        }
        //System.out.println(temp);

        //words = temp.split("\"");
        //for(int i=0; i<words.length; i++){
            //if(words[i].charAt(i) == '('){

            //}
            //words[i].replaceAll("\\(\\)"," ");
            //(String)words[i].split("\(");
           //System.out.println(words[i]);
//           a = words[i];
//           a.replaceAll("()"," ");
//            System.out.println(a);
//           temp = words[i];
//           if(words[i].equals("(")){
//                words[i].replace("("," ");
//            }else if(words[i].equals(")")){
//                words[i].replace(")"," ");
//            }
//            System.out.println(words[i]);
 //       }
        //a.replaceAll("()"," ");
         //a.replaceAll("()","");


//        finalWords = temp.split("");
//        for (int i = 0 ; i<finalWords.length;i++){
//            System.out.println(finalWords[i]);
//        }




    }
}
