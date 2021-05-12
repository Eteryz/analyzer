package exeption;

import model.Analyzer;

import java.lang.Exception;

public class CaseException extends Exception{
    public CaseException(String s){super(s);}
    public CaseException(String state,String symbol){
        super("Ошибка синтаксиса в состоянии - "+ state +
            "\nЗамените символ по индексу - "+ Analyzer.i+
                "\nОжидается символ: \n"+ symbol);}
}
