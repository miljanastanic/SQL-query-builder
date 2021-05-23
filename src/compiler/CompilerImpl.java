package compiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompilerImpl implements Compiler{

    private Query query;
    private List<Query> parts;

    @Override
    public void compile(String s) {
        System.out.println(s);
        String query = s;
        String newQuery;
        String[] varCheck;
        String[] functions;

        varCheck = query.split("new ");

        functions = varCheck[varCheck.length-1].split("\\).");

        for(int i=0; i<functions.length; i++){
            devide(functions[i]);
        }
        newQuery = makeSQLQuery();
        System.out.println(newQuery);
    }

    @Override
    public void devide(String input){
        String[] probica1;
        String[] probica2;

        probica1 = input.split("\\(");
        probica2 = probica1[1].split("[,]");

        query = new Query(probica1[0], probica2);
        query.queryDivide();
    }

    @Override
    public String makeSQLQuery(){
        parts = query.getAllPartsOfQuery();
        String funName;
        String out = "";
        for (Query part: parts) {

           funName = part.getFunctionName();

           if(funName.equalsIgnoreCase("query")){
               System.out.println("ovo radi 1");
               part.setFunctionName("SELECT");
               funName = part.getFunctionName();
               System.out.println(funName);

               if (part.getArguments().length == 0){
                   out += "SELECT * FROM";
               }
               else{
                   //out += funName.toUpperCase() + part.toString() + "FROM";
               }
           }
           //out +=  funName.toLowerCase() + " " + part.toString();
            out +=  funName.toLowerCase() + " " + Arrays.toString(part.getArguments()) + " ";
        }
        query.removeAllPartsOfQuery();
        return out;
    }

}
