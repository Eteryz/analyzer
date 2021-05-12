import exeption.CaseException;
import exeption.IntNumException;
import exeption.NumberCharactersException;
import exeption.ReservedWordsException;
import model.Analyzer;
import org.junit.Assert;
import org.junit.Test;

import javax.swing.*;

public class AnalyzerTest {

    @Test
    public void resultTest(){
        Analyzer analyzer = new Analyzer();
        try {
            //boolean actualResult = analyzer.result("VaR Bad: REAL; C: ARRAY [0..79] OF CHAR;");
            boolean actualResult = analyzer.result("VAR Abc : ARRAY [-25..30,10..15] OF INTEGER;");
           // boolean actualResult = analyzer.result("VAR sdas_9, K, _B_M : BYTE; C : WORD; ar, E_7 : CHAR; Abc : ARRAY [-25..30,10..15] OF INTEGER;");
            System.out.println("Идентификаторы: "+
                    analyzer.idList.toString() + "\nInt-numb: "+ analyzer.intNumber.toString());
            System.out.println("Типы "+ analyzer.typeList.toString());
            Assert.assertTrue(actualResult);
        }catch (CaseException | NumberCharactersException | ReservedWordsException | IntNumException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }


}
