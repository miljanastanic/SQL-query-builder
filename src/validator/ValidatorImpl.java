package validator;

import divider.Divider;
import divider.Query;
import gui.table.MainFrame;

import java.util.ArrayList;
import java.util.List;

public class ValidatorImpl implements Validator {

    private List<Rule> pravila = new ArrayList<>();
    private String ime;
    private Divider divider;
    private List<Query> queries;
    @Override
    public boolean valid(String s) {
        //queries = divider.devide1(s);
        pravila.add(new Rule("Pravilo1","aa") {
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
//        pravila.add(new Rule("Pravilo2","a") {
//            @Override
//            public boolean check() {
//
//
//                return false;
//            }
//        });

        for (Rule pravilo:pravila) {
            if(!pravilo.check())
                return false;

        }
        return true;
    }

}
