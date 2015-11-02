package de.unihd.movies.client;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.*;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * MovieUI that contains a table of movies.
 * */
public class MovieUI extends Composite {

	/**
	 * The main panel which contain all other widgets
	 * */
	private VerticalPanel panel;

	/**
	 * Creates a MovieUI with the given list of movies.
	 * 
	 * @param movies
	 *            The list of movies to show.
	 * */
	public MovieUI(ArrayList<Movie> movies) {

		panel = new VerticalPanel();

		// Create a table containing all movies
		CellTable<Movie> movieTable = new CellTable<Movie>();
		Column<Movie, Number> idColumn = new Column<Movie, Number>(new NumberCell()){
			public Integer getValue(Movie object){
				return object.getId();
			}
		};
		movieTable.addColumn(idColumn, "ID");
		idColumn.setSortable(true);
		TextColumn<Movie> nameColumn = new TextColumn<Movie>(){
			public String getValue(Movie object){
				return object.getName();
			}
		};
		movieTable.addColumn(nameColumn, "Name");
		nameColumn.setSortable(true);
		Column<Movie, Number> timeColumn = new Column<Movie, Number>(new NumberCell()){
			public Integer getValue(Movie object){
				return object.getTime();
			}
		};
		movieTable.addColumn(timeColumn, "Time");
		timeColumn.setSortable(true);
		TextColumn<Movie> languageColumn = new TextColumn<Movie>(){
			public String getValue(Movie object){
				return object.getLanguage();
			}
		};
		movieTable.addColumn(languageColumn, "Language");
		languageColumn.setSortable(true);
		TextColumn<Movie> descriptionColumn = new TextColumn<Movie>(){
			public String getValue(Movie object){
				return object.getDescription();
			}
		};
		movieTable.addColumn(descriptionColumn, "Description");
		descriptionColumn.setSortable(true);
		TextColumn<Movie> placeColumn = new TextColumn<Movie>(){
			public String getValue(Movie object){
				return object.getPlace();
			}
		};
		movieTable.addColumn(placeColumn, "Place");
		placeColumn.setSortable(true);
		ListDataProvider<Movie> provider = new ListDataProvider<Movie>();
		provider.addDataDisplay(movieTable);
		List <Movie> temp = provider.getList();
		for (Movie movie:movies){
			temp.add(movie);
		}
		ListHandler<Movie> columnSortHandler = new ListHandler<Movie>(temp);
		columnSortHandler.setComparator(nameColumn, new Comparator<Movie>(){
			public int compare(Movie m1, Movie m2){
				if(m1==m2){
					return 0;
				}
				if(m1 != null){
					return (m2 != null) ? m1.getName().compareTo(m2.getName()) : 1;
				}
				return -1;
			}
		});
		columnSortHandler.setComparator(languageColumn, new Comparator<Movie>(){
			public int compare(Movie m1, Movie m2){
				if(m1==m2){
					return 0;
				}
				if(m1 != null){
					return (m2 != null) ? m1.getLanguage().compareTo(m2.getLanguage()) : 1;
				}
				return -1;
			}
		});
		columnSortHandler.setComparator(placeColumn, new Comparator<Movie>(){
			public int compare(Movie m1, Movie m2){
				if(m1==m2){
					return 0;
				}
				if(m1 != null){
					return (m2 != null) ? m1.getPlace().compareTo(m2.getPlace()) : 1;
				}
				return -1;
			}
		});
		columnSortHandler.setComparator(descriptionColumn, new Comparator<Movie>(){
			public int compare(Movie m1, Movie m2){
				if(m1==m2){
					return 0;
				}
				if(m1 != null){
					return (m2 != null) ? m1.getDescription().compareTo(m2.getDescription()) : 1;
				}
				return -1;
			}
		});
		columnSortHandler.setComparator(idColumn, new Comparator<Movie>(){
			public int compare(Movie m1, Movie m2){
				if(m1==m2){
					return 0;
				}
				if(m1 != null && m1 != null){
					if (m1.getId() > m2.getId()){
						return 1;
					}
				}
				return -1;
			}
		});
		columnSortHandler.setComparator(timeColumn, new Comparator<Movie>(){
			public int compare(Movie m1, Movie m2){
				if(m1==m2){
					return 0;
				}
				if(m1 != null && m1 != null){
					if (m1.getTime() > m2.getTime()){
						return 1;
					}
				}
				return -1;
			}
		});
		movieTable.addColumnSortHandler(columnSortHandler);
		movieTable.getColumnSortList().push(idColumn);
		movieTable.getColumnSortList().push(nameColumn);
		movieTable.getColumnSortList().push(timeColumn);
		movieTable.getColumnSortList().push(languageColumn);
		movieTable.getColumnSortList().push(descriptionColumn);
		movieTable.getColumnSortList().push(placeColumn);

		// Add the table to the panel
		panel.add(movieTable);
	}

	/**
	 * Shows the content of the MovieUI.
	 * */
	public void show() {
		initWidget(panel);
		RootPanel.get("content").add(this);
		this.setVisible(true);
	}

}
