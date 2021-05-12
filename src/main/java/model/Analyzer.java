package model;

import exeption.CaseException;
import exeption.IntNumException;
import exeption.NumberCharactersException;
import exeption.ReservedWordsException;

import java.util.LinkedList;

public class Analyzer {

    //для получения индекса ализируемого символа
    public static int i;

    public  LinkedList<String> idList;

    public  LinkedList<String> typeList;

    public  LinkedList<Integer> intNumber;

    private String [] reservedWords = new String[]
            {"VAR","ARRAY","OF","BYTE","WORD","INTEGER","REAL","CHAR","DOUBLE"};

    public boolean result(String str)
            throws CaseException, ReservedWordsException, NumberCharactersException, IntNumException {
        idList = new LinkedList<>();
        intNumber = new LinkedList<>();
        typeList = new LinkedList<>();
        String s = str.toLowerCase();
        return stateMachine(s);
    }

    private boolean checkReservedWords(String id){
        for (int i = 0; i < reservedWords.length; i++) {
            if(id.equals(reservedWords[i].toLowerCase()))
                return false;
        }
        return true;
    }

    private boolean checkIntNumb(){
        for (int i = 0; i < intNumber.size()-2; i++) {
            int left = intNumber.get(2*i);
            int right = intNumber.get(2*i+1);
            if(left>right)
                return false;
        }
        return true;
    }

    private boolean stateMachine(String s)
            throws CaseException, ReservedWordsException, NumberCharactersException, IntNumException {
        String id = "";
        String type ="";
        String int_number = "";
        int count = 0;
        States state = States.Start;
        i = 0;
        char ch;
            while (i  < s.length()) {
                ch = s.charAt(i);
                switch (state) {
                    case Start:
                        i++;
                        if (ch == ' ')
                            state = States.Start;
                        else if (ch == 'v')
                            state = States.A;
                        else
                            throw new CaseException(state.toString(), "v");
                        break;
                    case A:
                        i++;
                        if (ch == 'a')
                            state = States.B;
                        else
                            throw new CaseException(state.toString(), "a");
                        break;
                    case B:
                        i++;
                        if (ch == 'r')
                            state = States.C;
                        else
                            throw new CaseException(state.toString(), "r");
                        break;
                    case C:
                        i++;
                        if (ch == ' ')
                            state = States.D;
                        else
                            throw new CaseException(state.toString(), "пробел");
                        break;
                    case D:
                        i++;
                        if (ch == ' ')
                            state = States.D;
                        else if ((ch >= 'a' && ch <= 'z') || ch == '_') {
                            id += ch;
                            state = States.E;
                        } else {
                            throw new CaseException(state.toString(),
                                    "пробел или буква латинского алфавита от a до z или _");
                        }
                        break;
                    case E:
                        i++;
                        if ((ch >= 'a' && ch <= 'z') || ch == '_' || (ch >= '0' && ch <= '9')) {
                            id += ch;
                            state = States.E;
                        } else if (ch == ' ') {
                            if (id.length() < 8 && checkReservedWords(id)) {
                                count++;
                                idList.addLast(id);
                            }else if (!checkReservedWords(id))
                                throw new ReservedWordsException(id);
                            else
                                throw new NumberCharactersException(id);
                            state = States.E1;
                            id = "";
                        } else if (ch == ',') {
                            if (id.length() < 8 && checkReservedWords(id)) {
                                idList.addLast(id);
                                count++;
                            }else if (!checkReservedWords(id))
                                throw new ReservedWordsException(id);
                            else
                                throw new NumberCharactersException(id);
                            state = States.D;
                            id = "";
                        } else if (ch == ':') {
                            if (id.length() < 8 && checkReservedWords(id)) {
                                count++;
                                idList.addLast(id);
                            } else if (!checkReservedWords(id))
                                throw new ReservedWordsException(id);
                            else
                                throw new NumberCharactersException(id);
                            state = States.H;
                            id = "";
                        } else {
                            throw new CaseException(state.toString(),
                                    "буквы латинского алфавита от a до z или _ или цифра от 0 до 9 " +
                                            "или запятая или пробел или : ");
                        }
                        break;
                    case E1:
                        i++;
                        if (ch == ' ')
                            state = States.E1;
                        else if (ch == ',') {
                            count++;
                            state = States.D;
                        }else if (ch == ':')
                            state = States.H;
                        else {
                            throw new CaseException(state.toString(),
                                    "пробел или запятая или : ");
                        }
                        break;
                    case H:
                        i++;
                        if (ch == 'a') {
                            type +=ch;
                            state = States.J;
                        } else if (ch == ' ') {
                            state = States.H;
                        }else if (ch == 'd') {
                            type +=ch;
                            state = States.I6;
                        } else if (ch == 'i'){
                            type +=ch;
                            state = States.I3;
                        }else if(ch == 'b') {
                            type +=ch;
                            state = States.I1;
                        }else if(ch == 'c') {
                            type +=ch;
                            state = States.I5;
                        }else if(ch == 'r') {
                            type +=ch;
                            state = States.I4;
                        }else if(ch == 'w') {
                            type +=ch;
                            state = States.I2;
                        }else {
                            throw new CaseException(state.toString(),
                                    "пробел или a или d или i или b или с или r или w");
                        }
                        break;
                    case J:
                        i++;
                        if(ch == 'r') {
                            type += ch;
                            state = States.J1;
                        }else {
                            throw new CaseException(state.toString(), "r");
                        }
                        break;
                    case J1:
                        i++;
                        if(ch == 'r') {
                            type += ch;
                            state = States.J2;
                        }else {
                            throw new CaseException(state.toString(), "r");
                        }
                        break;
                    case J2:
                        i++;
                        if(ch == 'a') {
                            type += ch;
                            state = States.J3;
                        }else {
                            throw new CaseException(state.toString(), "a");
                        }
                        break;
                    case J3:
                        i++;
                        if(ch == 'y') {
                            type += ch+" ";
                            state = States.J4;
                        }else
                            throw new CaseException(state.toString(),"y");
                        break;
                    case J4:
                        i++;
                        if(ch == ' ')
                            state = States.P;
                        else {
                            throw new CaseException(state.toString(), "пробел");
                        }
                        break;
                    case P:
                        i++;
                        if(ch == ' ')
                            state = States.P;
                        else if(ch == '[')
                            state = States.P1;
                        else {
                            throw new CaseException(state.toString(),"пробел или [");
                        }
                        break;
                    case P1:
                        i++;
                        if(ch == ' ')
                            state = States.P1;
                        else if(ch == '-') {
                            int_number+=ch;
                            state = States.P3;
                        }
                        else if(ch >= '0' && ch<= '9') {
                            int_number+=ch;
                            state = States.P2;
                        }
                        else {
                            throw new CaseException(state.toString(),
                                    "пробел или - или цифра от 0 до 9");
                        }
                        break;
                    case P2:
                        i++;
                        if(ch >= '0' && ch<= '9') {
                            int_number+= ch;
                            state = States.P2;
                        }
                        else if (ch == ' ') {
                            int int_num = Integer.parseInt(int_number);
                            if( int_num >= -32768 && int_num <= 32767)
                                intNumber.addLast(int_num);
                            else
                                throw new IntNumException(int_num);
                            int_number = "";
                            state = States.P22;
                        }
                        else if (ch == '.'){
                            int int_num = Integer.parseInt(int_number);
                            if( int_num >= -32768 && int_num <= 32767)
                                intNumber.addLast(int_num);
                            else
                                throw new IntNumException(int_num);
                            int_number = "";
                            state = States.Q1;
                        }
                        else {
                            throw new CaseException(state.toString(),
                                    "цифра от 0 до 9 или пробел или точка");
                        }
                        break;
                    case P3:
                        i++;
                        if(ch >= '0' && ch<= '9'){
                            int_number+=ch;
                            state = States.P2;
                        }
                        else {
                            throw new CaseException(state.toString(),
                                    "цифра от 0 до 9");
                        }
                        break;
                    case P22:
                        i++;
                        if(ch == ' ')
                            state = States.P22;
                        else if(ch == '.')
                            state = States.Q1;
                        else {
                            throw new CaseException(state.toString(),
                                    "пробел или точка");
                        }
                        break;
                    case Q1:
                        i++;
                        if(ch == '.')
                            state = States.Q2;
                        else {
                            throw new CaseException(state.toString(),
                                    "точка");
                        }
                        break;
                    case Q2:
                        i++;
                        if(ch == ' ')
                            state = States.Q2;
                        else if(ch >= '0' && ch <= '9'){
                            int_number+=ch;
                            state = States.Q3;
                        }
                        else {
                            throw new CaseException(state.toString(),
                                    "пробел или цфра от 0 до 9");
                        }
                        break;
                    case Q3:
                        i++;
                        if(ch >= '0' && ch <= '9') {
                            int_number+=ch;
                            state = States.Q3;
                        }
                        else if(ch == ' ') {
                            int int_num = Integer.parseInt(int_number);
                            if( int_num >= -32768 && int_num <= 32767)
                                intNumber.addLast(int_num);
                            else
                                throw new IntNumException(int_num);
                            int_number = "";
                            state = States.Q33;
                        }
                        else if(ch == ']') {
                            int int_num = Integer.parseInt(int_number);
                            if( int_num >= -32768 && int_num <= 32767)
                                intNumber.addLast(int_num);
                            else
                                throw new IntNumException(int_num);
                            if(!checkIntNumb())
                                throw new IntNumException();
                            int_number = "";
                            state = States.Q5;
                        }
                        else if(ch == ',') {
                            int int_num = Integer.parseInt(int_number);
                            if( int_num >= -32768 && int_num <= 32767)
                                intNumber.addLast(int_num);
                            else
                                throw new IntNumException(int_num);
                            int_number = "";
                            state = States.P1;
                        }
                        else {
                            throw new CaseException(state.toString(),
                                    "пробел или цфра от 0 до 9 или ] или запятая");
                        }
                        break;
                    case Q33:
                        i++;
                        if(ch == ' ')
                            state = States.Q33;
                        else if(ch == ']') {
                            if(!checkIntNumb())
                                throw new IntNumException();
                            state = States.Q5;
                        }
                        else if(ch == ',')
                            state = States.P1;
                        else {
                            throw new CaseException(state.toString(),
                                    "пробел или ] или запятая");
                        }
                        break;
                    case Q5:
                        i++;
                        if(ch == ' ')
                            state = States.R;
                        else {
                            throw new CaseException(state.toString(), "пробел");
                        }
                        break;
                    case R:
                        i++;
                        if(ch == ' ')
                            state = States.R;
                        else if(ch == 'o')
                            state = States.R1;
                        else {
                            throw new CaseException(state.toString(),
                                    "пробел или o");
                        }
                        break;
                    case R1:
                        i++;
                        if(ch == 'f')
                            state = States.R2;
                        else
                            throw new CaseException(state.toString(), "f");
                        break;
                    case R2:
                        i++;
                        if(ch == ' ')
                            state = States.T;
                        else
                            throw new CaseException(state.toString(),"пробел");
                        break;
                    case T:
                        i++;
                        if(ch == 'd') {
                            type +=ch;
                            state = States.I6;
                        }else if(ch == 'i') {
                            type +=ch;
                            state = States.I3;
                        }else if(ch == 'b') {
                            type +=ch;
                            state = States.I1;
                        }else if(ch == 'c') {
                            type +=ch;
                            state = States.I5;
                        }else if(ch == 'r') {
                            type +=ch;
                            state = States.I4;
                        }else if(ch == 'w') {
                            type +=ch;
                            state = States.I2;
                        }else if(ch == ' ') {
                            state = States.T;
                        }else {
                            throw new CaseException(state.toString(),
                                    "d или i или b или c или r или w или пробел");
                        }
                        break;
                    case I1:
                        i++;
                        if(ch == 'y') {
                            type +=ch;
                            state = States.K1;
                        }else
                            throw new CaseException(state.toString(),"y");
                        break;
                    case I2:
                        i++;
                        if(ch == 'o') {
                            type +=ch;
                            state = States.K2;
                        }else
                            throw new CaseException(state.toString(),"o");
                        break;
                    case I3:
                        i++;
                        if(ch == 'n') {
                            type +=ch;
                            state = States.K3;
                        }else
                            throw new CaseException(state.toString(),"n");
                        break;
                    case I4:
                        i++;
                        if(ch == 'e') {
                            type +=ch;
                            state = States.K4;
                        }else
                            throw new CaseException(state.toString(),"e");
                        break;
                    case I5:
                        i++;
                        if(ch == 'h') {
                            type +=ch;
                            state = States.K5;
                        }else
                            throw new CaseException(state.toString(),"h");
                        break;
                    case I6:
                        i++;
                        if(ch == 'o') {
                            type +=ch;
                            state = States.K6;
                        }else
                            throw new CaseException(state.toString(),"o");
                        break;
                    case K1:
                        i++;
                        if(ch == 't') {
                            type +=ch;
                            state = States.L1;
                        }else
                            throw new CaseException(state.toString(),"t");
                        break;
                    case K2:
                        i++;
                        if(ch == 'r') {
                            type +=ch;
                            state = States.L2;
                        }else
                            throw new CaseException(state.toString(),"r");
                        break;
                    case K3:
                        i++;
                        if(ch == 't') {
                            type +=ch;
                            state = States.L3;
                        }else
                            throw new CaseException(state.toString(),"t");
                        break;
                    case K4:
                        i++;
                        if(ch == 'a') {
                            type +=ch;
                            state = States.L4;
                        }else
                            throw new CaseException(state.toString(),"a");
                        break;
                    case K5:
                        i++;
                        if(ch == 'a') {
                            type +=ch;
                            state = States.L5;
                        }else
                            throw new CaseException(state.toString(),"a");
                        break;
                    case K6:
                        i++;
                        if(ch == 'u') {
                            type +=ch;
                            state = States.L6;
                        }else
                            throw new CaseException(state.toString(),"u");
                        break;
                    case L1:
                    case O1:
                        i++;
                        if(ch == 'e') {
                            type +=ch;
                            state = States.O;
                        }else
                            throw new CaseException(state.toString(),"e");
                        break;
                    case L2:
                        i++;
                        if(ch == 'd') {
                            type +=ch;
                            state = States.O;
                        }else
                            throw new CaseException(state.toString(),"d");
                        break;
                    case L3:
                        i++;
                        if(ch == 'e') {
                            type +=ch;
                            state = States.M3;
                        }else
                            throw new CaseException(state.toString(),"e");
                        break;
                    case L4:
                        i++;
                        if(ch == 'l') {
                            type +=ch;
                            state = States.O;
                        }else
                            throw new CaseException(state.toString(),"l");
                        break;
                    case L5:
                    case N2:
                        i++;
                        if(ch == 'r') {
                            type +=ch;
                            state = States.O;
                        }else
                            throw new CaseException(state.toString(),"r");
                        break;
                    case L6:
                        i++;
                        if(ch == 'b') {
                            type +=ch;
                            state = States.M6;
                        }else
                            throw new CaseException(state.toString(),"b");
                        break;
                    case M3:
                        i++;
                        if(ch == 'g') {
                            type +=ch;
                            state = States.N1;
                        }else
                            throw new CaseException(state.toString(),"g");
                        break;
                    case M6:
                        i++;
                        if(ch == 'l') {
                            type +=ch;
                            state = States.O1;
                        }else
                            throw new CaseException(state.toString(),"l");
                        break;
                    case N1:
                        i++;
                        if(ch == 'e') {
                            type +=ch;
                            state = States.N2;
                        }else
                            throw new CaseException(state.toString(),"e");
                        break;
                    case O:
                        if(ch == ' ') {
                            i++;
                            state = States.O;
                        }
                        else if(ch == ';') {
                            for (int j = 0; j < count; j++) {
                                typeList.addLast(type);
                            }
                            type ="";
                            count = 0;
                            if (i < (s.length()-1))
                                i++;
                            state = States.X;
                        }
                        else
                            throw new CaseException(state.toString(),"пробел или ;");
                        break;
                    case X:
                        if((ch >='a' && ch<= 'z')||ch =='_') {
                            i++;
                            id += ch;
                            state = States.E;
                        } else if(i == (s.length()-1)) {
                            state = States.Final;
                        } else if(ch == ' '){
                            i++;
                            state = States.X;
                        } else
                            throw new CaseException(state.toString(),
                                    "латинская буква от a до z или _ или пробел");
                        break;
                    case Final:
                        return true;
                }
            }throw new CaseException("Строка должна начинаться с ключевого слова <var>");
    }

    //метод подсчитывает объем памяти для идентификаторов языка турбо Паскаль
    public int [] memorySizeOfIdentifiers(LinkedList<String> typeList,
                                          LinkedList<Integer> intNumber){
        int[] arr = new int [typeList.size()];
        int a =1;
        for (int j = 0; j < typeList.size(); j++) {
            String str = typeList.get(j);
            String str2 = typeList.get(j).replaceAll("array ","");
            if(str != str2){
                for (int k = 0; k <intNumber.size() ; k+=2) {
                    a *= (intNumber.get(k+1)-intNumber.get(k)+1);
                }
                str = str2;
            }
            switch (str){
                case "integer":
                case "word":
                    arr[j] = 2*a;
                    break;
                case "real":
                    arr[j] = 6*a;
                    break;
                case "byte":
                case "char":
                    arr[j] = 1*a;
                    break;
                case "double":
                    arr[j] = 8*a;
                    break;

            }
        }
        return arr;
    }
}
