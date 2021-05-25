package validator;

public abstract class Rule {

    private String name;
    private String context;
    //private boolean check = false;

    public Rule(String name,String context){
        this.name = name;
        this.context = context;
        //this.check = check;
    }

    public String getName() {
        return name;
    }

    public String getContext() {
        return context;
    }
    public abstract boolean check();

}
