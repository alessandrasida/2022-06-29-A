/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.itunes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import it.polito.tdp.itunes.model.Album;
import it.polito.tdp.itunes.model.BilancioAlbum;
import it.polito.tdp.itunes.model.Model;
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

    @FXML // fx:id="btnAdiacenze"
    private Button btnAdiacenze; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="cmbA1"
    private ComboBox<Album> cmbA1; // Value injected by FXMLLoader

    @FXML // fx:id="cmbA2"
    private ComboBox<Album> cmbA2; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="txtX"
    private TextField txtX; // Value injected by FXMLLoader

    @FXML
    void doCalcolaAdiacenze(ActionEvent event) {
    	Album a = this.cmbA1.getValue();
    	if( a == null) {
    		this.txtResult.setText("Please select an element from combo box");
    		return;
    	}
    	
    	List<BilancioAlbum> bilanci = model.getAdiacenti(a);
    	this.txtResult.setText("Printing successors of node " + a + ".\n");
    	for( BilancioAlbum b : bilanci) {
    		this.txtResult.appendText(b + "\n");
    	}
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
String input = this.txtX.getText();
    	
    	if( input == "") {
    		this.txtResult.setText("Input String for N is empty.");
    		return;
    	}
    	
    	try {
    		int InputNum = Integer.parseInt(input);
    		Album source = this.cmbA1.getValue();
    		Album target = this.cmbA2.getValue();
    		if( source == null || target == null) {
    			this.txtResult.setText("Please select an Album from the combo box ");
    			return;

    		}
    		List<Album> path = model.getPath(source, target, InputNum);
    		
    		if( path.isEmpty()) {
    			this.txtResult.setText("No path between " + source + " and " + target);
    			return;
    		}
    		
    		this.txtResult.setText("Printing path between " + source + " and " + target + "\n");
    		
    		for( Album a : path) {
    			this.txtResult.appendText(a+ "\n");
    		}
    	}catch( NumberFormatException e) {
    		this.txtResult.setText("Input string for N is not a valid number.");
    	}
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtResult.clear();
    	
    	String input = this.txtN.getText();
    	
    	if( input == "") {
    		this.txtResult.setText("Input String for N is empty.");
    	}
    	
    	try {
    		int InputNum = Integer.parseInt(input);
    		this.model.buidGraph(InputNum);
    		int numV = model.getNumVertici();
    		int numE = model.getNumEdges();
    		
    		this.txtResult.setText("Graph correctly created. \n");
    		this.txtResult.appendText("Number vertices: " + numV + "\n");
    		this.txtResult.appendText("Number edges: " + numE + "\n");
    		
    		this.cmbA1.getItems().setAll(model.getVertices());
    		this.cmbA2.getItems().setAll(model.getVertices());
    		
    	}catch( NumberFormatException e) {
    		this.txtResult.setText("Input string for N is not a valid number.");
    	}
    	
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAdiacenze != null : "fx:id=\"btnAdiacenze\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbA1 != null : "fx:id=\"cmbA1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbA2 != null : "fx:id=\"cmbA2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX != null : "fx:id=\"txtX\" was not injected: check your FXML file 'Scene.fxml'.";

    }

    
    public void setModel(Model model) {
    	this.model = model;
    }
}
