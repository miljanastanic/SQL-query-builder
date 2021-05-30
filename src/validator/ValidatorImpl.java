package validator;

import divider.Divider;
import divider.DividerImpl;
import divider.Query;
import gui.table.MainFrame;

import java.util.ArrayList;
import java.util.List;

public class ValidatorImpl implements Validator {

    private List<Rule> pravila = new ArrayList<>();
    private String ime;
    private Divider divider = new DividerImpl();
    private List<Query> queries;
    private String help = "";
    @Override
    public boolean valid(String s) {
        queries = divider.devide1(s);
        pravila.add(new Rule("Pravilo1","Nakon Join-a mora ici On.") {
            @Override
            public boolean check() {
                ime = s;
                System.out.println(ime);
                if(ime.contains("Join")){
                    if(ime.contains("On")) {
                        return true;
                    }else{
                        return false;
                    }
                }
                return true;
            }
        });
        pravila.add(new Rule("Pravilo2","sve što je selektovano a nije pod funkcijom agregacije, mora ući u group by.") {
            @Override
            public boolean check() {
                if(ime.contains("Select") && ime.contains("GroupBy") && ((ime.contains("Min") || ime.contains("Max")) || (ime.contains("Avg") || ime.contains("Count")))){
                    for (Query q:queries) {
                        if(q.getFunctionName().equals("Select")){
                            help += getHelp(q.getArguments());
                        }
                        if((q.getFunctionName().equals("Avg") || q.getFunctionName().equals("Min")) || (q.getFunctionName().equals("Max") || q.getFunctionName().equals("Count"))){
                            if(help.equalsIgnoreCase(getHelp(q.getArguments()))){
                                help = "";
                                return false;
                            }
                        }

                    }
                    for (Query q:queries) {
                        if(q.getFunctionName().equals("GroupBy")){
                            if(help.equals(q.getArguments().toString())){
                                help = "";
                                return true;
                            }
                        }
                    }
                }

                return true;
            }
        });
        pravila.add(new Rule("Pravilo3", "Samo ono što je pod funkcijom agregacije može da se nađe u having") {
            @Override
            public boolean check() {
                if(ime.contains("Having") && ((ime.contains("Min") || ime.contains("Max")) || (ime.contains("Avg") || ime.contains("Count")))){
                    for (Query q:queries) {
                        if(q.getFunctionName().equals("Having")){
                            help += getHelp(q.getArguments());
                        }
                        if((q.getFunctionName().equals("Avg") || q.getFunctionName().equals("Min")) || (q.getFunctionName().equals("Max") || q.getFunctionName().equals("Count"))){
                            if(help.equalsIgnoreCase(getHelp(q.getArguments()))){
                                return false;
                            }
                        }

                    }
                }
                return true;
            }
        });
        pravila.add(new Rule("Pravilo4", "On mora da trazi iste kolone i operator je =") {
            @Override
            public boolean check() {
                for(Query q: queries){
                    if(q.getFunctionName().equalsIgnoreCase("On")){
                        String column_name1 = q.getArguments()[0].split("[.]")[1];
                        String column_name2 = q.getArguments()[2].split("[.]")[1];
                        String operator = q.getArguments()[1];

                        if(operator.equals("=") && column_name1.equals(column_name2)){
                            return true;
                        }
                        return false;
                    }
                }
                return true;
            }
        });

        for (Rule pravilo:pravila) {
            if(!pravilo.check())
                return false;

        }
        divider.remove();
        return true;
    }

    public String getHelp(String[] arguments){
        String s = "";
        for(int i=0 ; i<arguments.length; i++){
            s += arguments[i] + " ";
        }
        return s;
    }

}
