package controller;

import exeption.CaseException;
import exeption.IntNumException;
import exeption.NumberCharactersException;
import exeption.ReservedWordsException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Analyzer;

import javax.swing.*;

public class MainController {

    @FXML
    private TextField textBox;

    @FXML
    private ListView<String> idListView;

    @FXML
    private ListView<String> typeListView;

    @FXML
    private ListView<Integer> weightListView;

    @FXML
    void OnClickMethod() {
        Analyzer analyzer = new Analyzer();
        if(!textBox.getText().trim().equals("")) {
            try {
                if(analyzer.result(textBox.getText())) {
                    JOptionPane.showMessageDialog(null,
                            "Ошибок не обнаружено!");
                    ObservableList<String> langs = FXCollections.observableArrayList();
                    for (int i = 0; i < analyzer.idList.size(); i++) {
                        langs.addAll(analyzer.idList.get(i));
                    }
                    idListView.setItems(langs);
                    ObservableList<String> langs2 = FXCollections.observableArrayList();
                    for (int i = 0; i < analyzer.typeList.size(); i++) {
                        langs2.addAll(analyzer.typeList.get(i));
                    }
                    typeListView.setItems(langs2);
                    int [] memorySizeID = analyzer.memorySizeOfIdentifiers(analyzer.typeList,analyzer.intNumber);
                    ObservableList<Integer> langs3 = FXCollections.observableArrayList();
                    for (int i = 0; i < memorySizeID.length; i++) {
                        langs3.addAll(memorySizeID[i]);
                    }
                    weightListView.setItems(langs3);
                }
            }catch (CaseException e){
                JOptionPane.showMessageDialog(null, e.getMessage());
                textBox.positionCaret(Analyzer.i);
            }catch (NumberCharactersException | ReservedWordsException | IntNumException e){
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        else
            JOptionPane.showMessageDialog(null, "Заполните строку!");
    }

    @FXML
    void initialize() {

    }
}
