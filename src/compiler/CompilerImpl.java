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

            //1.Upit nad tabelom
            if(funName.equalsIgnoreCase("query")){
                System.out.println("ovo radi 1");
                part.setFunctionName("FROM");
                out +=  part.getFunctionName() + " " + part.toString() + ".";
            }
            //2.Projekcija
            if(funName.equalsIgnoreCase("select")){
                if(part.getArguments().length == 0){
                    part.setFunctionName("SELECT *");
                }else{
                    part.setFunctionName("SELECT");
                }
                out +=  part.getFunctionName() + " " + part.toString() + ".";
            }
            //3.Sortiranje
            if(funName.equalsIgnoreCase("orderby")){
                part.setFunctionName("ORDER BY");
                out +=  part.getFunctionName() + " " + part.toString() + ".";
            }
            if(funName.equalsIgnoreCase("orderbydesc")){
                part.setFunctionName("ORDER BY");
                out +=  part.getFunctionName() + " " + part.toString() + "DESC" + ".";
            }
            //4.Filtriranje
            if(funName.equalsIgnoreCase("where")){
                out +=  part.getFunctionName() + " " + part.toString() + ".";
            }
            if(funName.equalsIgnoreCase("orwhere")){
                part.setFunctionName("WHERE");
                part.setFunctionName("WHERE");
                out +=  part.getFunctionName() + " " + part.getArguments()[0] + " " + "OR" + " "+ part.getArguments()[1] + " " + part.getArguments()[2]+ ".";
            }
            if(funName.equalsIgnoreCase("andwhere")){
                part.setFunctionName("WHERE");
                out +=  part.getFunctionName() + " " + part.getArguments()[0] + " " + "AND" + " " + part.getArguments()[1] + " " + part.getArguments()[2]+ ".";
            }
            if(funName.equalsIgnoreCase("wherebetween")){
                part.setFunctionName("WHERE");
                out +=  part.getFunctionName() + " " + part.getArguments()[0] + " "  + "BETWEEN" + " "  + part.getArguments()[1]+ " " + "AND" + " " + part.getArguments()[2] + ".";
            }
            if(funName.equalsIgnoreCase("wherein")){
                part.setFunctionName("WHERE");
                out +=  part.getFunctionName() + " "+ part.getArguments()[0] + " " +"IN"+ " " + "(";
                for(int j=1; j < part.getArguments().length; j++){
                    out+=part.getArguments()[j];
                    if(!(j == (part.getArguments().length)-1)){
                        out+=",";
                    }
                }
                out+=")" + ".";
            }
            //6.Stringovne operacije (where department_name like 'S%')
            if(funName.equalsIgnoreCase("whereendswith")) {
                part.setFunctionName("WHERE");
                out += part.getFunctionName() + " " + part.getArguments()[0] + " " + "like" + " " + "'" + "%" + part.getArguments()[1] + "'" + ".";
            }
            if(funName.equalsIgnoreCase("wherestartswith")) {
                part.setFunctionName("WHERE");
                out += part.getFunctionName() + " " + part.getArguments()[0] + " " + "like" + " " + "'" + part.getArguments()[1] + "%" + "'" + ".";
            }
            if(funName.equalsIgnoreCase("wherecontains")) {
                part.setFunctionName("WHERE");
                out += part.getFunctionName() + " " + part.getArguments()[0] + " " + "like" + " " + "'"+ "%" + part.getArguments()[1] + "%" + "'" + ".";
            }
            //7.Funkcije agregacije
            if(funName.equalsIgnoreCase("avg")){
                part.setFunctionName("avg");
                out += part.getFunctionName() + "(" + part.getArguments() + ")" + ".";
            }
            if(funName.equalsIgnoreCase("count")){
                part.setFunctionName("count");
                out += part.getFunctionName() + "(" + part.getArguments() + ")" + ".";
            }
            if(funName.equalsIgnoreCase("min")){
                part.setFunctionName("min");
                out += part.getFunctionName() + "(" + part.getArguments() + ")" + ".";
            }
            if(funName.equalsIgnoreCase("max")){
                part.setFunctionName("max");
                out += part.getFunctionName() + "(" + part.getArguments() + ")" + ".";
            }
            if(funName.equalsIgnoreCase("groupby")){
                part.setFunctionName("GROUP BY");
                out += part.getFunctionName() + " ";
                for(int j=1; j < part.getArguments().length; j++){
                    out+=part.getArguments()[j];
                    if(!(j == (part.getArguments().length)-1)){
                        out+= " " + ",";
                    }
                    out+=".";
                }
            }
            if(funName.equalsIgnoreCase("having")){
                part.setFunctionName("HAVING");
                out +=  part.getFunctionName() + " " + part.getArguments()[0] + " " + part.getArguments()[1]+ " " + part.getArguments()[2] + ".";
            }
            if(funName.equalsIgnoreCase("andhaving")){
                part.setFunctionName("HAVING");
                out +=  part.getFunctionName() + " " + part.getArguments()[0] + " " + "AND" + " " + part.getArguments()[1] + " " + part.getArguments()[2]+ ".";
            }
            if(funName.equalsIgnoreCase("orhaving")){
                part.setFunctionName("HAVING");
                out +=  part.getFunctionName() + " " + part.getArguments()[0] + " " + "OR" + " " + part.getArguments()[1] + " " + part.getArguments()[2]+ ".";
            }
            //OSTALI SU 8.PODUPITI i 5.
            //out +=  part.getFunctionName() + " " + part.toString();
            //out +=  funName.toLowerCase() + " " + Arrays.toString(part.getArguments()) + " ";
        }

        query.removeAllPartsOfQuery();
        return out;
    }

}
