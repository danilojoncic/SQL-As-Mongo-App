package utils;

import third_perspective.Clauses.*;
import third_perspective.Query;

import java.util.ArrayList;
import java.util.Arrays;

public class Parser {

    private String[] standardKeyword = {"select","where","from","orderby"};
    private static Parser instance;
    private Parser(){

    }
    public static Parser getInstance(){
        if(instance == null){
            instance = new Parser();
        }
        return instance;
    }

    public Query createFromString(String input){
        Query query = new Query(new ArrayList<Composite>());
        String[] parts = input.split("\\s");
        for(int i = 0; i < parts.length;i++){
            Clause clause;
            if(i < parts.length && parts[i].equalsIgnoreCase("select")){
                clause = new Select(new ArrayList<String>());
                i++;
                while(i < parts.length && !Arrays.asList(standardKeyword).contains(parts[i])) {
                    ((Select)clause).addFields(parts[i]);
                    i++;
                }
                query.addClause(clause);
            }
            if(i < parts.length && parts[i].equalsIgnoreCase("from")){
                i++;
                clause = new From(new ArrayList<String>());
                while(i < parts.length && !Arrays.asList(standardKeyword).contains(parts[i])){
                    ((From)clause).addFields(parts[i]);
                    i++;
                }
                query.addClause(clause);
            }
            if(i < parts.length && parts[i].equalsIgnoreCase("where")){
                i++;
                clause = new Where(new ArrayList<String>());
                while(i < parts.length && !Arrays.asList(standardKeyword).contains(parts[i])){
                    ((Where)clause).addFields(parts[i]);
                    i++;
                }
                query.addClause(clause);
            }
            if(i < parts.length && parts[i].equalsIgnoreCase("orderby")){
                i++;
                clause = new Order_By(new ArrayList<String>());
                while(i < parts.length && !Arrays.asList(standardKeyword).contains(parts[i])){
                    ((Order_By)clause).addFields(parts[i]);
                    i++;
                }
                query.addClause(clause);
            }
            //subquery / podupit logika
            if(i < parts.length && parts[i].contains("(")){
                i++;
                StringBuilder sb = new StringBuilder();
                while(i < parts.length && !parts[i].contains(")")){
                    sb.append(" " + parts[i]);
                    i++;
                }
                Query subquery = createSubQuery(sb.toString());
                query.addClause(subquery);
            }
        }
        return query;
    }
    private Query createSubQuery(String input){
        Query query = new Query(new ArrayList<Composite>());
        String[] parts = input.split("\\s");
        for(int i = 0; i < parts.length;i++){
            Clause clause;
            if(i < parts.length && parts[i].equalsIgnoreCase("select")){
                clause = new Select(new ArrayList<String>());
                i++;
                while(i < parts.length && !Arrays.asList(standardKeyword).contains(parts[i])) {
                    ((Select)clause).addFields(parts[i]);
                    i++;
                }
                query.addClause(clause);
            }
            if(i < parts.length && parts[i].equalsIgnoreCase("from")){
                i++;
                clause = new From(new ArrayList<String>());
                while(i < parts.length && !Arrays.asList(standardKeyword).contains(parts[i])){
                    ((From)clause).addFields(parts[i]);
                    i++;
                }
                query.addClause(clause);
            }
            if(i < parts.length && parts[i].equalsIgnoreCase("where")){
                i++;
                clause = new Where(new ArrayList<String>());
                while(i < parts.length && !Arrays.asList(standardKeyword).contains(parts[i])){
                    ((Where)clause).addFields(parts[i]);
                    i++;
                }
                query.addClause(clause);
            }
            if(i < parts.length && parts[i].equalsIgnoreCase("orderby")){
                i++;
                clause = new Order_By(new ArrayList<String>());
                while(i < parts.length && !Arrays.asList(standardKeyword).contains(parts[i])){
                    ((Order_By)clause).addFields(parts[i]);
                    i++;
                }
                query.addClause(clause);
            }

        }
        return query;
    }
}
