package exeption;

public class NumberCharactersException extends  Exception{
    public NumberCharactersException(String id){super("Недопустимое количество символов в ID(должно быть <= 8) - "+ id);}
}
