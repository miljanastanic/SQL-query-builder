package compiler;

import java.util.Collections;
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

        query = new Query(probica1[0], probica2, priorityMethod(probica1[0]));
        query.queryDivide();
    }

    @Override
    public String makeSQLQuery(){
        parts = query.getAllPartsOfQuery();
        Collections.sort(parts);
        String funName;
        String out = "";

        String select =parts.get(0).getFunctionName();
        if(select.equals("Select")){
            out += "SELECT" + " ";
            for(int i=0; i<parts.get(0).getArguments().length; i++){
                        out+=parts.get(0).getArguments()[i];
                        if(!(i == (parts.get(0).getArguments().length)-1)){
                            out+=",";
                        }
                    }
                    out+=" ";
        }else{
            out += "SELECT " + "*" + " ";
        }
        for (Query part: parts) {

            funName = part.getFunctionName();
            //1.Upit nad tabelom
            if(funName.equalsIgnoreCase("query")){
                System.out.println("ovo radi 1");
                part.setFunctionName("FROM");
                out +=  part.getFunctionName() + " " + part.toString();
            }
            //3.Sortiranje
            if(funName.equalsIgnoreCase("orderby")){
                part.setFunctionName("ORDER BY");
                out +=  part.getFunctionName() + " " + part.toString() ;
            }
            if(funName.equalsIgnoreCase("orderbydesc")){
                part.setFunctionName("ORDER BY");
                out +=  part.getFunctionName() + " " + part.toString() + "DESC";
            }
            //4.Filtriranje
            if(funName.equalsIgnoreCase("where")){
                part.setFunctionName("WHERE");
                out +=  part.getFunctionName() + " " + part.toString();
            }
            if(funName.equalsIgnoreCase("orwhere")){
                part.setFunctionName("OR");
                out +=  part.getFunctionName() + " " + part.getArguments()[0] + " " + part.getArguments()[1] + " " + part.getArguments()[2];
            }
            if(funName.equalsIgnoreCase("andwhere")){
                part.setFunctionName("AND");
                out +=  part.getFunctionName() + " " + part.getArguments()[0] + " " + part.getArguments()[1] + " " + part.getArguments()[2];
            }
            if(funName.equalsIgnoreCase("wherebetween")){
                part.setFunctionName("BETWEEN");
                out +=  part.getFunctionName() + " " + part.getArguments()[0] +  " "  + part.getArguments()[1]+ " " + "AND" + " " + part.getArguments()[2] ;
            }
            if(funName.equalsIgnoreCase("wherein")){
                part.setFunctionName("WHERE");
                out +=  part.getFunctionName() + " "+ part.getArguments()[0] + " " +"IN"+ " " + "(";
                for(int j=1; j < part.getArguments().length; j++){
                    out+=part.getArguments()[j];
                    if(!(j == (part.getArguments().length)-1)){
                        out+=" " + ",";
                    }
                }
                out+=")" ;
            }
            //5.Spajanje tabela
            if(funName.equalsIgnoreCase("join")){
                part.setFunctionName("JOIN");
                out+= part.getFunctionName() + " " + part.getArguments()[0] + " ";
            }
            if(funName.equalsIgnoreCase("on")){
                part.setFunctionName("USING");
                out+= part.getFunctionName() + " ";

                String column_name1 = part.getArguments()[0].split("[.]")[1];
                String column_name2 = part.getArguments()[2].split("[.]")[1];
                String operator = part.getArguments()[1];

                if(operator.equals("=") && column_name1.equals(column_name2)){
                    out+= "(" + column_name1 + ") " ;
                }
            }
            //6.Stringovne operacije (where department_name like 'S%')
            if(funName.equalsIgnoreCase("whereendswith")) {
                part.setFunctionName("WHERE");
                out += part.getFunctionName() + " " + part.getArguments()[0] + " " + "like" + " " + "'" + "%" + part.getArguments()[1] + "'" ;
            }
            if(funName.equalsIgnoreCase("wherestartswith")) {
                part.setFunctionName("WHERE");
                out += part.getFunctionName() + " " + part.getArguments()[0] + " " + "like" + " " + "'" + part.getArguments()[1] + "%" + "'" ;
            }
            if(funName.equalsIgnoreCase("wherecontains")) {
                part.setFunctionName("WHERE");
                out += part.getFunctionName() + " " + part.getArguments()[0] + " " + "like" + " " + "'"+ "%" + part.getArguments()[1] + "%" + "'" ;
            }
            //7.Funkcije agregacije
            if(funName.equalsIgnoreCase("avg")){
                part.setFunctionName("avg");
                out += part.getFunctionName() + "(" + part.getArguments()[0] + ")" ;
            }
            if(funName.equalsIgnoreCase("count")){
                part.setFunctionName("count");
                out += part.getFunctionName() + "(" + part.getArguments()[0] + ")" ;
            }
            if(funName.equalsIgnoreCase("min")){
                part.setFunctionName("min");
                out += part.getFunctionName() + "(" + part.getArguments()[0] + ")" ;
            }
            if(funName.equalsIgnoreCase("max")){
                part.setFunctionName("max");
                out += part.getFunctionName() + "(" + part.getArguments()[0] + ")" ;
            }
            if(funName.equalsIgnoreCase("groupby")){
                part.setFunctionName("GROUP BY");
                out += part.getFunctionName() + " ";
                for(int j=0; j < part.getArguments().length; j++){
                    out+=part.getArguments()[j];
                    if(!(j == (part.getArguments().length)-1)){
                        out+= " " + ",";
                    }

                }
                out+=" ";
            }
            if(funName.equalsIgnoreCase("having")){
                part.setFunctionName("HAVING");
                out +=  part.getFunctionName() + " " + part.getArguments()[0] + " " + part.getArguments()[1]+ " " + part.getArguments()[2] ;
            }
            if(funName.equalsIgnoreCase("andhaving")){
                part.setFunctionName("HAVING");
                out +=  part.getFunctionName() + " " + part.getArguments()[0] + " " + "AND" + " " + part.getArguments()[1] + " " + part.getArguments()[2];
            }
            if(funName.equalsIgnoreCase("orhaving")){
                part.setFunctionName("HAVING");
                out +=  part.getFunctionName() + " " + part.getArguments()[0] + " " + "OR" + " " + part.getArguments()[1] + " " + part.getArguments()[2];
            }
            //OSTALI SU 8.PODUPITI.
            //out +=  part.getFunctionName() + " " + part.toString();
            //out +=  funName.toLowerCase() + " " + Arrays.toString(part.getArguments()) + " ";
        }
        query.removeAllPartsOfQuery();
        //sortFuntions(out);
        return out;
    }
    public int priorityMethod(String s){
        if(s.contains("Select")){
            return 1;
        }
        if(s.contains("Query")){
            return 2;
        }
        if(s.contains("Join")){
            return 3;
        }
        if(s.contains("On")){
            return 4;
        }
        if(s.contains("Where")){
            return 5;
        }
        if(s.contains("GroupBy")){
            return 6;
        }
        if(s.contains("Having")){
            return 7;
        }
        if(s.contains("OrderBy")){
            return 8;
        }
        return 9;
    }
}
