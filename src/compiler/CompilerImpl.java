package compiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CompilerImpl implements Compiler{

    private Query query;
    private String sqlupit = "";
    private List<Query> parts;
    private List<String> a = new ArrayList<>();
    private List<String> baka = new ArrayList<>();

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
        //System.out.println(newQuery);
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
                part.setFunctionName("2FROM");
                out +=  part.getFunctionName() + " " + part.toString() + ".";
            }
            //2.Projekcija
            if(funName.equalsIgnoreCase("select")){
                if(part.getArguments().length == 0){
                    part.setFunctionName("1SELECT *");
                    out+=funName + ".";
                }else{
                    part.setFunctionName("1SELECT");
                    out += part.getFunctionName() + " ";
                    for(int i=0; i<part.getArguments().length; i++){
                        out+=part.getArguments()[i];
                        if(!(i == (part.getArguments().length)-1)){
                            out+=",";
                        }
                    }
                    out+=".";
                }

            }
            //3.Sortiranje
            if(funName.equalsIgnoreCase("orderby")){
                part.setFunctionName("8ORDER BY");
                out +=  part.getFunctionName() + " " + part.toString() + ".";
            }
            if(funName.equalsIgnoreCase("orderbydesc")){
                part.setFunctionName("8ORDER BY");
                out +=  part.getFunctionName() + " " + part.toString() + "DESC" + ".";
            }
            //4.Filtriranje
            if(funName.equalsIgnoreCase("where")){
                part.setFunctionName("5WHERE");
                out +=  part.getFunctionName() + " " + part.toString() + ".";
            }
            if(funName.equalsIgnoreCase("orwhere")){
                part.setFunctionName("5WHERE");
                out +=  part.getFunctionName() + " " + part.getArguments()[0] + " " + "OR" + " "+ part.getArguments()[1] + " " + part.getArguments()[2]+ ".";
            }
            if(funName.equalsIgnoreCase("andwhere")){
                part.setFunctionName("5WHERE");
                out +=  part.getFunctionName() + " " + part.getArguments()[0] + " " + "AND" + " " + part.getArguments()[1] + " " + part.getArguments()[2]+ ".";
            }
            if(funName.equalsIgnoreCase("wherebetween")){
                part.setFunctionName("5WHERE");
                out +=  part.getFunctionName() + " " + part.getArguments()[0] + " "  + "BETWEEN" + " "  + part.getArguments()[1]+ " " + "AND" + " " + part.getArguments()[2] + ".";
            }
            if(funName.equalsIgnoreCase("wherein")){
                part.setFunctionName("5WHERE");
                out +=  part.getFunctionName() + " "+ part.getArguments()[0] + " " +"IN"+ " " + "(";
                for(int j=1; j < part.getArguments().length; j++){
                    out+=part.getArguments()[j];
                    if(!(j == (part.getArguments().length)-1)){
                        out+=" " + ",";
                    }
                }
                out+=")" + ".";
            }
            //5.Spajanje tabela
            if(funName.equalsIgnoreCase("join")){
                part.setFunctionName("3JOIN");
                out+= part.getFunctionName() + " " + part.getArguments()[0] + ".";
            }
            if(funName.equalsIgnoreCase("on")){
                part.setFunctionName("4USING");
                out+= part.getFunctionName() + " ";

                String column_name1 = part.getArguments()[0].split("[.]")[1];
                String column_name2 = part.getArguments()[2].split("[.]")[1];
                String operator = part.getArguments()[1];

                if(operator.equals("=") && column_name1.equals(column_name2)){
                    out+= "(" + column_name1 + ")" + ".";
                }
            }
            //6.Stringovne operacije (where department_name like 'S%')
            if(funName.equalsIgnoreCase("whereendswith")) {
                part.setFunctionName("5WHERE");
                out += part.getFunctionName() + " " + part.getArguments()[0] + " " + "like" + " " + "'" + "%" + part.getArguments()[1] + "'" + ".";
            }
            if(funName.equalsIgnoreCase("wherestartswith")) {
                part.setFunctionName("5WHERE");
                out += part.getFunctionName() + " " + part.getArguments()[0] + " " + "like" + " " + "'" + part.getArguments()[1] + "%" + "'" + ".";
            }
            if(funName.equalsIgnoreCase("wherecontains")) {
                part.setFunctionName("5WHERE");
                out += part.getFunctionName() + " " + part.getArguments()[0] + " " + "like" + " " + "'"+ "%" + part.getArguments()[1] + "%" + "'" + ".";
            }
            //7.Funkcije agregacije
            if(funName.equalsIgnoreCase("avg")){
                part.setFunctionName("avg");
                out += part.getFunctionName() + "(" + part.getArguments()[0] + ")" + ".";
            }
            if(funName.equalsIgnoreCase("count")){
                part.setFunctionName("count");
                out += part.getFunctionName() + "(" + part.getArguments()[0] + ")" + ".";
            }
            if(funName.equalsIgnoreCase("min")){
                part.setFunctionName("min");
                out += part.getFunctionName() + "(" + part.getArguments()[0] + ")" + ".";
            }
            if(funName.equalsIgnoreCase("max")){
                part.setFunctionName("max");
                out += part.getFunctionName() + "(" + part.getArguments()[0] + ")" + ".";
            }
            if(funName.equalsIgnoreCase("groupby")){
                part.setFunctionName("6GROUP BY");
                out += part.getFunctionName() + " ";
                for(int j=0; j < part.getArguments().length; j++){
                    out+=part.getArguments()[j];
                    if(!(j == (part.getArguments().length)-1)){
                        out+= " " + ",";
                    }

                }
                out+=".";
            }
            if(funName.equalsIgnoreCase("having")){
                part.setFunctionName("7HAVING");
                out +=  part.getFunctionName() + " " + part.getArguments()[0] + " " + part.getArguments()[1]+ " " + part.getArguments()[2] + ".";
            }
            if(funName.equalsIgnoreCase("andhaving")){
                part.setFunctionName("7HAVING");
                out +=  part.getFunctionName() + " " + part.getArguments()[0] + " " + "AND" + " " + part.getArguments()[1] + " " + part.getArguments()[2]+ ".";
            }
            if(funName.equalsIgnoreCase("orhaving")){
                part.setFunctionName("7HAVING");
                out +=  part.getFunctionName() + " " + part.getArguments()[0] + " " + "OR" + " " + part.getArguments()[1] + " " + part.getArguments()[2]+ ".";
            }
            //OSTALI SU 8.PODUPITI.
            //out +=  part.getFunctionName() + " " + part.toString();
            //out +=  funName.toLowerCase() + " " + Arrays.toString(part.getArguments()) + " ";
        }
        query.removeAllPartsOfQuery();
        sortFuntions(out);
        return out;
    }
    public void sortFuntions(String s){
        String[] help = s.split("[.]");
        int size = help.length;
        for (int i = 0; i < size ; i++){
            a.add(help[i]);
        }
        Collections.sort(a);
        for (String g: a) {
            if(g.startsWith("1") ){
                String temp;
                temp = g.replace("1","");
                baka.add(temp);
            }
            else if(g.startsWith("2") ){
                String temp;
                temp = g.replace("2","");
                baka.add(temp);
            }
            else if(g.startsWith("3") ){
                String temp;
                temp = g.replace("3","");
                baka.add(temp);
            }
            else if(g.startsWith("4") ){
                String temp;
                temp = g.replace("4","");
                baka.add(temp);
            }
           else if(g.startsWith("5") ){
                String temp;
                temp = g.replace("5","");
                baka.add(temp);
            }
          else  if(g.startsWith("6") ){
                String temp;
                temp = g.replace("6","");
                baka.add(temp);
            }
           else if(g.startsWith("7") ){
                String temp;
                temp = g.replace("7","");
                baka.add(temp);
            }else
                baka.add(g);
        }
        System.out.println(a);
        System.out.println(baka);
        for (String s1: baka) {
            sqlupit += s1 + " ";
        }

        System.out.println(sqlupit);

    }

}
