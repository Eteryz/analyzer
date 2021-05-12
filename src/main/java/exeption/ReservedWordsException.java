package exeption;

public class ReservedWordsException extends  Exception{
    public static int i;
    public ReservedWordsException(String id){super("ID является зарезервированным словом - "+ id);}
}
