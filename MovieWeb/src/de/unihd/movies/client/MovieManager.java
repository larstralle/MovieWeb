package de.unihd.movies.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.unihd.movies.client.service.MovieManagerService;
import de.unihd.movies.client.service.MovieManagerServiceAsync;

/**
 * The Class MovieManager.
 */
public class MovieManager implements EntryPoint {
	private ArrayList<Movie> movies = new ArrayList<Movie>();
	{
	Movie m1 = new Movie("The Avengers", 143, "english", "HULK Smash!", "New York");
	Movie m2 = new Movie("The Martian", 144, "english", "Seriously Matt Damon is lost again, ON MARS", "Mars");
	Movie m3 = new Movie("Chappie", 120, "dutch", "simply Die Antwoord", "South Africa");
	Movie m4 = new Movie("Kingsman", 129, "german", "Like James Bond just better", "Around the World");
	this.movies.add(m1);
	this.movies.add(m2);
	this.movies.add(m3);
	this.movies.add(m4);
	}
	public void remove(Movie pMovie){
		this.movies.remove(pMovie);
	}
	private MovieManagerServiceAsync mMS = GWT.create(MovieManagerService.class);
	
	private void loadMovies(){
		if (mMS == null){
			mMS = GWT.create(MovieManagerService.class);
		} 
		AsyncCallback<ArrayList<Movie>> callback = new AsyncCallback<ArrayList<Movie>>(){

			@Override
			public void onFailure(Throwable caught) {
								
			}

			@Override
			public void onSuccess(ArrayList<Movie> result) {
				movies = result;				
			}};
			mMS.loadMovies(callback);
	}
	
	public void onModuleLoad() {
		this.loadMovies();
		MovieUI movieUI = new MovieUI(this.movies);
		movieUI.show();
	}

	
}
