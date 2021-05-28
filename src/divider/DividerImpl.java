package divider;

import java.util.Collections;
import java.util.List;

public class DividerImpl implements Divider{

    private Query queryy;
    private List<Query> parts;
    private String ourQuery;
    @Override
    public List<Query> devide1(String s) {
        String query = s;
        String[] varCheck;
        String[] functions;
        List<Query> queries;

        varCheck = query.split("new ");

        functions = varCheck[varCheck.length-1].split("\\).");


        for(int i=0; i<functions.length; i++){
            devide2(functions[i]);
        }
        queries = getAll();
        for (Query q:
             queries) {
            System.out.println(q.toString() + "ovo je moj query");
        }

        return queries;
    }

    public void devide2(String input){
        String[] probica1;
        String[] probica2;

        probica1 = input.split("\\(");
        probica2 = probica1[1].split("[,]");

        queryy = new Query(probica1[0], probica2, priorityMethod(probica1[0]));
        queryy.queryDivide();
    }

    public List<Query> getAll(){
        parts = queryy.getAllPartsOfQuery();
        Collections.sort(parts);
        return parts;
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
