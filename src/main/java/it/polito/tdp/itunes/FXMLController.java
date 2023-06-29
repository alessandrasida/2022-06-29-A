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
    	if( this.cmbA1.getValue() == null) {
    		this.txtResult.setText("Selezionare un album.");
    		
    	}
    	this.txtResult.clear();
    	Album a = this.cmbA1.getValue();
    	List<BilancioAlbum> risultato = this.model.getBilancioSUccessori(a);
    	for(BilancioAlbum ba : risultato) {
    		this.txtResult.appendText(ba + "\n");
    	}
    	
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	if( this.cmbA1.getValue() == null) {
    		this.txtResult.setText("Selezionare un album.");	
    	}
    	if( this.cmbA2.getValue() == null) {
    		this.txtResult.setText("Selezionare un secondo album album.");	
    	}
    	this.txtResult.clear();
    	Album inizio = this.cmbA1.getValue();
    	Album fine = this.cmbA2.getValue();
    	Integer x=0 ;
    	
    	try {
    		x = Integer.parseInt(this.txtX.getText());
    		if( x < 0) {
    			this.txtResult.setText("Inserire un bvalore numerico maggiore di 0.");
    			return;
    		}
    		
    	}catch(NumberFormatException e) {
    		this.txtResult.appendText("Inserire un valore numerico valido.");
    		return;
    	}
    	
    	List<Album> result = this.model.cercaPercorso(inizio, fine, x);
    	for( Album a : result) {
    		this.txtResult.appendText(a + "\n");
    	}
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	if( this.txtN.getText() == null) {
    		this.txtResult.setText("Inserire un valore.");
    		return;
    	}
    	
    	int n = 0;
    	
    	try {
    		n = Integer.parseInt(this.txtN.getText());
    		if( n < 0) {
    			this.txtResult.setText("Inserire un bvalore numerico maggiore di 0.");
    			return;
    		}
    		
    	}catch(NumberFormatException e) {
    		this.txtResult.appendText("Inserire un valore numerico valido.");
    		return;
    		
    	}
    	this.model.clearGrafo();
    	this.model.creaGrafo(n);
		this.txtResult.setText("Grafo creato.");
		this.txtResult.appendText("\n#Vertici: " + this.model.getNVertici() );
		this.txtResult.appendText("\n#Archi: " + this.model.getNArchi() );
    		
		List<Album> album = this.model.getVertici();
    	
		for(Album a: album) {
			this.cmbA1.getItems().add(a);
		}
		for(Album a: album) {
			this.cmbA2.getItems().add(a);
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
