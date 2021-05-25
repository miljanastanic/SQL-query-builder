package validator;

import gui.table.MainFrame;

import java.util.ArrayList;
import java.util.List;

public class ValidatorImpl implements Validator {

    private List<Rule> pravila = new ArrayList<>();
    private String ime;
    @Override
    public boolean valid(String s) {
        pravila.add(new Rule("Pravilo1","aa") {
            @Override
            public boolean check() {
                ime = MainFrame.getInstance().getText();
                System.out.println(ime);
                if(ime.contains("Join") && ime.contains("On")){
                    return true;
                }
                else {
                    return false;
                }
            }
        });
        for (Rule pravilo:pravila) {
            if(!pravilo.check())
                return false;

        }
        return true;
    }
}
