/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.rivers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.rivers.model.Model;
import it.polito.tdp.rivers.model.River;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxRiver"
    private ComboBox<River> boxRiver; // Value injected by FXMLLoader

    @FXML // fx:id="txtStartDate"
    private TextField txtStartDate; // Value injected by FXMLLoader

    @FXML // fx:id="txtEndDate"
    private TextField txtEndDate; // Value injected by FXMLLoader

    @FXML // fx:id="txtNumMeasurements"
    private TextField txtNumMeasurements; // Value injected by FXMLLoader

    @FXML // fx:id="txtFMed"
    private TextField txtFMed; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML
    void doSelezionaFiume(ActionEvent event) {

    	River fiume = this.boxRiver.getValue();
    	this.model.infoFiume(fiume);
    	this.txtStartDate.setText(this.model.getPrimaMisurazione(fiume).toString());
    	this.txtEndDate.setText(this.model.getUltimaMisurazione(fiume).toString());
        this.txtNumMeasurements.setText(this.model.getnMisurazioni(fiume).toString());
        this.txtFMed.setText(this.model.getAvgMisurazioni(fiume).toString());
    }
    
    @FXML
    void doSimula(ActionEvent event) {
    	this.txtResult.clear();
    	River fiume = this.boxRiver.getValue();
    	if(fiume == null) {
    		this.txtResult.setText("Devi selezionare un fiume.");
    		return;
    	}
    	double k = 0.0; 
    	try {
    		k = Double.parseDouble(this.txtK.getText());
    	}
    	catch(NumberFormatException e) {
    		e.printStackTrace();
    		this.txtResult.setText("Devi inserire un valore numerico.");
    		return;
    	}
    	if(k <= 0) {
    		this.txtResult.setText("Devi inserire un valore positivo.");
    	}
    	this.model.simula(fiume, k);
    	this.txtResult.appendText("Non si è potuta garantire l'erogazione minima per " +
    			this.model.getGiorni() + " giorni.\n\n");
    	this.txtResult.appendText("L'occupazione media del bacino nel corso della "
    			+ "simulazione è " + this.model.getcMedia() + ".");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxRiver != null : "fx:id=\"boxRiver\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtStartDate != null : "fx:id=\"txtStartDate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtEndDate != null : "fx:id=\"txtEndDate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNumMeasurements != null : "fx:id=\"txtNumMeasurements\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtFMed != null : "fx:id=\"txtFMed\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
        this.boxRiver.getItems().addAll(this.model.getAllRivers());
    }
}
