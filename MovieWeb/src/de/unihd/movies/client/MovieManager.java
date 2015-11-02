package de.unihd.movies.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;

/**
 * The Class MovieManager.
 */
public class MovieManager implements EntryPoint {
	private ArrayList<Movie> movies = new ArrayList<Movie>();
	{
	Movie m1 = new Movie(1, "The Avengers", 143, "english", "HULK Smash!", "New York");
	Movie m2 = new Movie(2, "The Martian", 144, "english", "Seriously Matt Damon is lost again, ON MARS", "Mars");
	Movie m3 = new Movie(3, "Chappie", 120, "dutch", "simply Die Antwoord", "South Africa");
	Movie m4 = new Movie(4, "Kingsman", 129, "german", "Like James Bond just better", "Around the World");
	this.movies.add(m1);
	this.movies.add(m2);
	this.movies.add(m3);
	this.movies.add(m4);
	}
	public void onModuleLoad() {
		MovieUI movieUI = new MovieUI(this.movies);
		movieUI.show();
	}

	
}
