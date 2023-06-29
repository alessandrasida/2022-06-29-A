package it.polito.tdp.itunes.db;

import java.util.List;

import it.polito.tdp.itunes.model.Album;
import it.polito.tdp.itunes.model.BilancioAlbum;
import it.polito.tdp.itunes.model.Model;

public class TestItunesDAO {

	public static void main(String[] args) {
		/*ItunesDAO dao = new ItunesDAO();
		System.out.println(dao.getAllAlbums().size());
		System.out.println(dao.getAllArtists().size());
		System.out.println(dao.getAllPlaylists().size());
		System.out.println(dao.getAllTracks().size());
		System.out.println(dao.getAllGenres().size());
		System.out.println(dao.getAllMediaTypes().size());*/

		
		Model model = new Model();
		model.creaGrafo(18);
		List<Album> album = model.getVertici();
		
		List<BilancioAlbum> test = model.getBilancioSUccessori(album.get(11));
		
		System.out.println( test);
	}

}
