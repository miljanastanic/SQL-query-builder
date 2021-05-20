package validator;

public class ValidatorImpl implements Validator {
    @Override
    public boolean valid(String s) {
        if(s.startsWith("var")){
            return true;
        }else
            return false;
    }
}
