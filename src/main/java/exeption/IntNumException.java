package exeption;

public class IntNumException extends  Exception{
    public IntNumException(int int_num){super("Int-number должно находится в диапазоне -32768 - 32767:  "+ int_num);}
    public IntNumException(){super("Левая граница bound должна иметь значение меньше правой!!!");}
}
