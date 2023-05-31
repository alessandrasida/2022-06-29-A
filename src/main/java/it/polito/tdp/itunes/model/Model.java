package it.polito.tdp.itunes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.itunes.db.ItunesDAO;

public class Model {
	
	private List<Album> allAlbum;
	private SimpleDirectedWeightedGraph<Album, DefaultWeightedEdge> grafo;
	private ItunesDAO dao;
	private int bestScore;
	private List<Album> bestPath;
	
	
	public Model() {
		this.allAlbum = new ArrayList<>();
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		this.dao = new ItunesDAO();
		
	}
	
	
	
	public List<Album> getPath(Album souce, Album target, int minimo){
		List<Album> parziale = new ArrayList<>();
		this.bestPath = new ArrayList<>();
		this.bestScore = 0;
		parziale.add(souce);
		
		ricorsione( parziale, target, minimo);
		return bestPath;
	}
	
	
	private void ricorsione(List<Album> parziale, Album target, int minimo) {
		Album current  = parziale.get(parziale.size()-1);
		
		//condiz di terminazione
		if( current.equals(target)) {
			//controllo se è migliore di best
			if( getScore( parziale) > this.bestScore) {
				this.bestScore = getScore(parziale);
				this.bestPath = new ArrayList<>(parziale);
				return;
			}
		}
		
		//continuo ad aggiungere elementi in parziale
		List<Album> successori = Graphs.successorListOf(this.grafo,  current);
		
		for( Album a : successori) {
			if( this.grafo.getEdgeWeight(this.grafo.getEdge(current, a)) >= minimo) {
				parziale.add(a);
				ricorsione(parziale, target, minimo);
				parziale.remove(a);
			}
		}
		
	}



	private int getScore(List<Album> parziale) {
		Album source = parziale.get(0);
		int score =0;
		for( Album a : parziale.subList(1, parziale.size()-1)) {
			if( getBilancio(a) > getBilancio(source))
				score += 1;
			
		}
		
		return score;
	}



	public void buidGraph(int n) {
		clearGraph();
		loadNodes(n);
		
		Graphs.addAllVertices(this.grafo, this.allAlbum);
		for( Album a1 : this.allAlbum) {
			for( Album a2 : this.allAlbum) {
				int peso = a1.getN() - a2.getN();
				if( peso > 0) {
					Graphs.addEdgeWithVertices(this.grafo, a2, a1, n);
				}
			}
		}
		
	}
	
	
	private int getBilancio( Album a) {
		int bilancio = 0;
		List<DefaultWeightedEdge> edgesIn = new ArrayList<>( this.grafo.incomingEdgesOf(a));
		List<DefaultWeightedEdge> edgesOut = new ArrayList<>( this.grafo.outgoingEdgesOf(a));
		
		for( DefaultWeightedEdge edge : edgesIn) {
			bilancio += this.grafo.getEdgeWeight(edge);
		}
		for( DefaultWeightedEdge edge : edgesOut) {
			bilancio -= this.grafo.getEdgeWeight(edge);
		}
		
		return bilancio;
		
	}
	
	public List<BilancioAlbum> getAdiacenti( Album a){
		List<Album> successori = Graphs.successorListOf(this.grafo, a);
		List<BilancioAlbum> bilancioSucc = new ArrayList<>();
		
		for( Album aa : successori) {
			bilancioSucc.add(new BilancioAlbum( aa, this.getBilancio(aa)));
			
		}
		Collections.sort(bilancioSucc);
		return bilancioSucc;
	}
	
 	public List<Album> getVertices(){
		List<Album> vertici =  new ArrayList<>( this.grafo.vertexSet());
		Collections.sort(vertici);
		return vertici;
		
	}
	
	private void clearGraph() {
		this.allAlbum = new ArrayList<>();
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
	}

	private void loadNodes(int n) {
		if( this.allAlbum.isEmpty())
			this.allAlbum = dao.getFilteredAlbums(n);
		
	}

	public int getNumVertici() {
		return this.grafo.vertexSet().size();
	}

	public int getNumEdges() {
		return this.grafo.edgeSet().size();
	}
	
	
	
}
