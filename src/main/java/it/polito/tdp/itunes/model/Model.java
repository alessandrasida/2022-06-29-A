package it.polito.tdp.itunes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.itunes.db.ItunesDAO;

public class Model {
	
	ItunesDAO dao;
	SimpleDirectedWeightedGraph<Album, DefaultWeightedEdge> grafo;
	List<Album> best;
	List<Album> album;
	Integer x;
	Integer bilancioMin;
	
	public Model() {
		
	dao = new ItunesDAO();
	grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
	

	
	}
	
	public void clearGrafo() {
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		this.dao = new ItunesDAO();
		
	}
	
	
	
	public void creaGrafo(int n) {
		List<Album> vertici = dao.getAlbumGrafo(n);
		Graphs.addAllVertices(this.grafo, vertici);
		
		
		for( Album a : vertici) {
			
			for( Album a2 : vertici) {
				if( ! a.equals(a2)) {
					if( (a.getN() - a2.getN()) > 0 )
						Graphs.addEdgeWithVertices(this.grafo, a2, a , a.getN()-a2.getN());
					//if( a2.getN()-a.getN()> 0)
					//	Graphs.addEdgeWithVertices(this.grafo, a2, a, a2.getN()-a.getN());
					
				}
				
			}
		}
		
		
	}
	
	public List<BilancioAlbum> getBilancioSUccessori(Album a){
		List<Album>  successori = Graphs.successorListOf(grafo, a);
		List<BilancioAlbum> result = new LinkedList<>();
		
		for(Album album : successori) {
			result.add(new BilancioAlbum( album, this.getBilancio(album)));
		}
		Collections.sort(result);
		
		return result;
		
	}
	
	
	public int getBilancio( Album a) {
		
		int bilancio;
		
		List<Album> precedenti = Graphs.predecessorListOf(this.grafo, a);
		List<Album> successori = Graphs.successorListOf(grafo, a);
		
		int sommaEntranti = 0;
		if( precedenti.size()>0) {
			for( Album a1 : precedenti) {
				DefaultWeightedEdge e = grafo.getEdge(a1, a);
				sommaEntranti += grafo.getEdgeWeight(e);
				
			}
		}
		
		int sommaSuccessori = 0;
		if( successori.size()>0) {
			for( Album a2 : successori) {
				DefaultWeightedEdge e = grafo.getEdge(a, a2);
				sommaSuccessori += grafo.getEdgeWeight(e);
			}
		}
		
		
		bilancio = sommaEntranti - sommaSuccessori;		
		return bilancio;
		
	}
	
	
	
	public List<Album> cercaPercorso(Album a1, Album a2, int x) {
		List<Album> parziale = new ArrayList<>();
		best = new ArrayList<>();
		this.album = new ArrayList<>(this.grafo.vertexSet());
		this.x = x;
		this.bilancioMin = this.getBilancio(a1);
		parziale.add(a1);
		ricorsione(parziale, a2);
		
		return best;
	}
	
	
	
	
	
	
	private void ricorsione(List<Album> parziale,  Album arrivo) {
		
		Album current = parziale.get(parziale.size()-1);
		
		//condizione di terminazione
		if( current.equals(arrivo)) {
			if( contoBilancio(parziale) > contoBilancio(best)) {
				this.best = new ArrayList<>(parziale);
			}
		}
		
		
		for( DefaultWeightedEdge e : grafo.edgeSet()) {
			if( grafo.getEdgeSource(e).equals(current)) {
				if( grafo.getEdgeWeight(e) >= this.x) {
					Album target = Graphs.getOppositeVertex(grafo, e, current);
					parziale.add(target);
					ricorsione( parziale, arrivo);
					//backtracking
					parziale.remove(parziale.size()-1);
					
				}
			}
		}
		
	}
	
	
	
	
	

	private int contoBilancio(List<Album> lista ) {
		int n = 0;
		
		for( Album a : lista) {
			if( this.getBilancio(a) > this.bilancioMin) {
				n ++;
			}
		}
		return n;
	}

	public List<Album> getVertici(){
		List<Album> result = new ArrayList<>(this.grafo.vertexSet());
		Collections.sort(result);
		return result;
	}
	
	
	public int getNVertici() {
		return this.grafo.vertexSet().size();
	}
	
	
	public int getNArchi() {
		return this.grafo.edgeSet().size();
	}
	
	
	
	
	
}