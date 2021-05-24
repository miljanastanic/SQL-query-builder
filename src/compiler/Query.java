package compiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Query {

    private String functionName;
    private String[] arguments;
    private static List<Query> allPartsOfQuery = new ArrayList<>();

    public Query(String functionName, String[] arguments) {
        this.functionName = functionName;
        this.arguments = arguments;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String[] getArguments() {
        return arguments;
    }

    public void queryDivide(){
        int size = arguments.length;

        for (int i=0; i< arguments.length; i++){
            if(arguments[i].endsWith("\"") || arguments[i].startsWith("\"")){
                String temp = arguments[i];
                arguments[i] = temp.replace("\"", "");
            }
        }
        if(arguments[size-1].endsWith(")")){
            String temp = arguments[size-1];
            arguments[size-1] = temp.replace(")", "");
        }
        allPartsOfQuery.add(this);
    }

    @Override
    public String toString() {
        String s = "";
        for(int i=0 ; i<arguments.length; i++){
            s += arguments[i] + " ";
        }
        return s;
    }

    public static List<Query> getAllPartsOfQuery() {
        return allPartsOfQuery;
    }

    public static void removeAllPartsOfQuery() {
        allPartsOfQuery.clear();
    }

    public void setArguments(String argument) {
        this.arguments[arguments.length] = argument;
    }
}
