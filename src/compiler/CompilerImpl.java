package compiler;

import app.AppCore;
import divider.Divider;
import divider.DividerImpl;
import divider.Query;

import java.util.Collections;
import java.util.List;

public class CompilerImpl implements Compiler{

    private Query query;
    private List<Query> parts;
    private Divider divider = new DividerImpl();

    @Override
    public String makeSQLQuery(String s){
        try {
            parts = divider.devide1(s);
            Collections.sort(parts);
            String funName;
            String out = "";
            String alias = "";
            String aliasName = "";
            String flag = "";
            String flagName = "";

            String select = parts.get(0).getFunctionName();
            if (select.equals("Select")) {
                for (Query q : parts) {
                    if ((q.getFunctionName().equals("Avg") || q.getFunctionName().equals("Count")) || (q.getFunctionName().equals("Max") || q.getFunctionName().equals("Min"))) {
                        if (q.getArguments().length == 2) {
                            alias = q.getArguments()[0];
                            aliasName = q.getArguments()[1];
                            System.out.println(alias + aliasName);
                        }else if(q.getArguments().length == 1){
                            flag = q.getFunctionName();
                            flagName = q.getArguments()[0];
                        }
                    }
                }
                out += "SELECT" + " ";
                int sizearg = parts.get(0).getArguments().length;
                for (int i = 0; i < sizearg; i++) {

                    if(!(flag.isEmpty())){
                        if(parts.get(0).getArguments()[i].equals(flagName)){
                            if(sizearg == 1) {
                                out += flag.toLowerCase() + "(" + flagName + ")" + " ";
                                flag = "";
                            }else{
                                out += flag.toLowerCase() + "(" + flagName + ")" + ",";
                                flag = "";
                            }
                        }
                    }else {
                        if (parts.get(0).getArguments()[i].equalsIgnoreCase(alias)) {
                            out += parts.get(0).getArguments()[i] + " " + "AS" + " " + aliasName;
                        } else {
                            out += parts.get(0).getArguments()[i];
                        }
                        if (!(i == (parts.get(0).getArguments().length) - 1)) {
                            out += ",";
                        }
                    }
                }
                out += " ";
            } else {
                out += "SELECT " + "*" + " ";
            }
            for (Query part : parts) {

                funName = part.getFunctionName();
                //1.Upit nad tabelom
                if (funName.equalsIgnoreCase("query")) {
                    part.setFunctionName("FROM");
                    out += part.getFunctionName() + " " + part.getArguments()[0] + " ";
                }
                //3.Sortiranje
                if (funName.equalsIgnoreCase("orderby")) {
                    part.setFunctionName("ORDER BY");
                    out += part.getFunctionName() + " " + part.getArguments()[0] + " ";
                }
                if (funName.equalsIgnoreCase("orderbydesc")) {
                    part.setFunctionName("ORDER BY");
                    out += part.getFunctionName() + " " + part.getArguments()[0] + " " + "DESC";
                }
                if (funName.equalsIgnoreCase("orderbyasc")) {
                    part.setFunctionName("ORDER BY");
                    out += part.getFunctionName() + " " + part.getArguments()[0] + " " + "ASC";
                }
                //4.Filtriranje
                if (funName.equalsIgnoreCase("where")) {
                    part.setFunctionName("WHERE");
                    if (part.getArguments()[2].startsWith("\'")) {
                        out += part.getFunctionName() + " " + part.getArguments()[0] + " " + part.getArguments()[1] + " " + part.getArguments()[2] + " ";
                    } else {
                        out += part.getFunctionName() + " " + part.getArguments()[0] + " " + part.getArguments()[1] + " " + Integer.valueOf(part.getArguments()[2]) + " ";
                    }
                }
                if (funName.equalsIgnoreCase("orwhere")) {
                    part.setFunctionName("OR");
                    if (part.getArguments()[2].startsWith("\'")) {
                        out += part.getFunctionName() + " " + part.getArguments()[0] + " " + part.getArguments()[1] + " " + part.getArguments()[2] + " ";
                    } else {
                        out += part.getFunctionName() + " " + part.getArguments()[0] + " " + part.getArguments()[1] + " " + Integer.valueOf(part.getArguments()[2]) + " ";
                    }
                }
                if (funName.equalsIgnoreCase("andwhere")) {
                    part.setFunctionName("AND");
                    if (part.getArguments()[2].startsWith("\'")) {
                        out += part.getFunctionName() + " " + part.getArguments()[0] + " " + part.getArguments()[1] + " " + part.getArguments()[2] + " ";
                    } else {
                        out += part.getFunctionName() + " " + part.getArguments()[0] + " " + part.getArguments()[1] + " " + Integer.valueOf(part.getArguments()[2]) + " ";
                    }
                }
                if (funName.equalsIgnoreCase("wherebetween")) {
                    part.setFunctionName("WHERE");
                    out += part.getFunctionName() + " " + part.getArguments()[0] + " " + "BETWEEN" + " " + Integer.valueOf(part.getArguments()[1]) + " " + "AND" + " " + Integer.valueOf(part.getArguments()[2]) + " ";
                }
                if (funName.equalsIgnoreCase("wherein")) {
                    part.setFunctionName("WHERE");
                    out += part.getFunctionName() + " " + part.getArguments()[0] + " " + "IN" + " " + "(";
                    for (int j = 1; j < part.getArguments().length; j++) {
                        out += Integer.valueOf(part.getArguments()[j]);
                        if (!(j == (part.getArguments().length) - 1)) {
                            out += " " + ",";
                        }
                    }
                    out += ")";
                }
                //5.Spajanje tabela
                if (funName.equalsIgnoreCase("join")) {
                    part.setFunctionName("JOIN");
                    out += part.getFunctionName() + " " + part.getArguments()[0] + " ";
                }
                if (funName.equalsIgnoreCase("on")) {
                    part.setFunctionName("ON");
                    String column_name1 = part.getArguments()[0].split("[.]")[1];
                    out += part.getFunctionName() + " " + "(" ;
                    //+ part.getArguments().toString() + ") ";
                    for(int i = 0; i<part.getArguments().length; i++){
                        out += " " + part.getArguments()[i];
                    }
                    out += " " + ")" + " ";
                }
                //6.Stringovne operacije (where department_name like 'S%')
                if (funName.equalsIgnoreCase("whereendswith")) {
                    String n = part.getArguments()[1].replaceAll("\"", "").replaceAll(" ", "");
                    part.setFunctionName("WHERE");
                    out += part.getFunctionName() + " " + part.getArguments()[0] + " " + "like" + " " + "'" + "%" + n + "'";
                }
                if (funName.equalsIgnoreCase("wherestartswith")) {
                    String n = part.getArguments()[1].replaceAll("\"", "").replaceAll(" ", "");
                    ;
                    part.setFunctionName("WHERE");
                    out += part.getFunctionName() + " " + part.getArguments()[0] + " " + "like" + " " + "'" + n + "%" + "'";
                }
                if (funName.equalsIgnoreCase("wherecontains")) {
                    String n = part.getArguments()[1].replaceAll("\"", "").replaceAll(" ", "");
                    ;
                    part.setFunctionName("WHERE");
                    out += part.getFunctionName() + " " + part.getArguments()[0] + " " + "like" + " " + "'" + "%" + n + "%" + "'";
                }
                //7.Funkcije agregacije
                if (funName.equalsIgnoreCase("groupby")) {
                    part.setFunctionName("GROUP BY");
                    out += part.getFunctionName() + " ";
                    for (int j = 0; j < part.getArguments().length; j++) {
                        out += part.getArguments()[j];
                        if (!(j == (part.getArguments().length) - 1)) {
                            out += " " + ",";
                        }

                    }
                    out += " ";
                }
                //having max(salary)>10000
                if (funName.equalsIgnoreCase("having")) {
                    part.setFunctionName("HAVING");
                    out += part.getFunctionName() + " " ;
                    for(Query q: parts){
                        if((q.getFunctionName().equals("Avg") || q.getFunctionName().equals("Count")) || (q.getFunctionName().equals("Max") || q.getFunctionName().equals("Min"))){
                            out += q.getFunctionName().toLowerCase() + "(" + q.getArguments()[0] + ")" + " ";
                        }
                    }
                    out += part.getArguments()[1] + " " + Integer.valueOf(part.getArguments()[2]) + " ";
                }
                if (funName.equalsIgnoreCase("andhaving")) {
                    part.setFunctionName("HAVING");
                    out += part.getFunctionName() + " " + part.getArguments()[0] + " " + "AND" + " " + part.getArguments()[1] + " " + part.getArguments()[2] + " ";
                }
                if (funName.equalsIgnoreCase("orhaving")) {
                    part.setFunctionName("HAVING");
                    out += part.getFunctionName() + " " + part.getArguments()[0] + " " + "OR" + " " + part.getArguments()[1] + " " + part.getArguments()[2] + " ";
                }

                //OSTALI SU 8.PODUPITI.
                //out +=  part.getFunctionName() + " " + part.toString();
                //out +=  funName.toLowerCase() + " " + Arrays.toString(part.getArguments()) + " ";
            }
            divider.remove();
            System.out.println(out);
            return out;
        }catch (Exception e){
            System.out.println("pogresna sintaksa");
            return null;
        }
    }

}
