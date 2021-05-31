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
    private boolean flag;
    private Query select;
    private Query alias;
    private Query groupby;

    @Override
    public boolean valid(String s) {
        if(pravila.isEmpty() == false) {
            removeAllp();
        }
        System.out.println(s + "validator 1");
        queries = divider.devide1(s);
        pravila.add(new Rule("Pravilo1","Nakon Join-a mora ici On.") {
            @Override
            public boolean check() {
                ime = s;
               System.out.println(s + "validator2");
                if(s.contains("Join")){
                    if(s.contains("On")) {
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
                select = queries.get(0);
                flag = false;
                if(ime.contains("Select") && (ime.contains("Min") || ime.contains("Max")) || (ime.contains("Avg") || ime.contains("Count"))){
                    flag = true;
                }
                if(flag){
                    for (Query q: queries) {
                        if(q.getFunctionName().equals("GroupBy")){
                            groupby = q;
                        }
                        if(q.getFunctionName().equalsIgnoreCase("Avg") || q.getFunctionName().equalsIgnoreCase("Min") || q.getFunctionName().equalsIgnoreCase("Max") || q.getFunctionName().equalsIgnoreCase("Count")){
                            alias = q;
                        }
                    }
                    if(select.getArguments().length == alias.getArguments().length){
                        if(getHelp(select.getArguments()).equals(getHelp(select.getArguments()))){
                            return true;
                        }
                    }
                    for(int i = 0; i<select.getArguments().length; i++){
                        if((getHelp(select.getArguments()).contains(getHelp(alias.getArguments())))){
                            if(!ime.contains("GroupBy")){
                                return false;
                            }
                        }
                    }
                    for(int i = 0; i<select.getArguments().length; i++){
                        if(!getHelp(groupby.getArguments()).contains(select.getArguments()[i])){
                            if(!(getHelp(alias.getArguments())).contains(select.getArguments()[i])) {
                                return false;
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
        pravila.add(new Rule("Pravilo5", "Bez obzira što je alias opcioni za agregatne funkcije, mora biti postavljen ukoliko se filtrira sa HAVING.") {
            @Override
            public boolean check() {
                for(Query q: queries){
                    if(ime.contains("Having") && (q.getFunctionName().equalsIgnoreCase("Avg") || q.getFunctionName().equalsIgnoreCase("Min") || q.getFunctionName().equalsIgnoreCase("Max") || q.getFunctionName().equalsIgnoreCase("Count"))){
                        if(q.getArguments().length == 1){
                            return false;
                        }else{
                            return true;
                        }
                    }
                }
                return true;
            }
        });


        for (Rule pravilo:pravila) {
            if(!pravilo.check()) {

                return false;
            }

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
    public void removeAllp() {
       // if (!pravila.isEmpty()) {
            pravila.clear();
        //}
    }

}
