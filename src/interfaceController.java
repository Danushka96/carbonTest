import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * @author danushka
 */
public class interfaceController implements Initializable {
    public ComboBox type;
    public ComboBox year;
    public TextField capacity;
    public TextField total;
    public Button calculate;
    public Text cError;
    public Text yError;
    public Text vError;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setYear();
        setVehicleType();
    }

    public void generate(ActionEvent actionEvent) {
        if(checkErrors()) return;
        String vehicleType = type.getSelectionModel().getSelectedItem().toString();
        int productYear = Integer.parseInt(year.getSelectionModel().getSelectedItem().toString());
        int engineCapasity = Integer.parseInt(capacity.getText());

        double taxPerUnit = 0;
        double fullTotal = 0;

        int yearCount = LocalDate.now().getYear() - productYear;
        if(vehicleType.equals("Hybrid")){
            if(yearCount<5) taxPerUnit = 0.25;
            else if(yearCount<10) taxPerUnit = 0.50;
            else taxPerUnit = 1;
        }else if(vehicleType.equals("Fuel")){
            if(yearCount<5) taxPerUnit = 0.50;
            else if(yearCount<10) taxPerUnit = 1;
            else taxPerUnit = 1.50;
        }else{
            if(yearCount<5) fullTotal = 1000;
            else if(yearCount<10) fullTotal = 2000;
            else fullTotal = 3000;
        }
        if(fullTotal==0){
            fullTotal = engineCapasity*taxPerUnit;
        }
        total.setText("RS "+Double.toString(fullTotal)+" /=");
    }

    public void setYear(){
        for(int i=LocalDate.now().getYear();i>=1960;i--){
            year.getItems().add(i);
        }
    }

    public void setVehicleType(){
        type.getItems().add("Hybrid");
        type.getItems().add("Fuel");
        type.getItems().add("Passenger Bus");
    }

    public boolean checkErrors(){
        capacity.setTextFormatter(new TextFormatter<>(c -> {
            if (!c.getControlNewText().matches("\\d*"))
                return null;
            else
                return c;
        }));
        if(year.getSelectionModel().isEmpty()){
            yError.setVisible(true);
            return true;
        }else{
            yError.setVisible(false);
        }
        if(type.getSelectionModel().isEmpty()){
            vError.setVisible(true);
            return true;
        }else{
            vError.setVisible(false);
        }
        if(capacity.getText().equals("")){
            cError.setVisible(true);
            return true;
        }else{
            cError.setVisible(false);
        }
        return false;
    }
}
