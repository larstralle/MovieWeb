package de.unihd.movies.client;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.cell.client.*;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.view.client.SingleSelectionModel;

import de.unihd.movies.client.filter.FilteredListDataProvider;
import de.unihd.movies.client.filter.MovieFilter;
import de.unihd.movies.client.service.MovieManagerService;
import de.unihd.movies.client.service.MovieManagerServiceAsync;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Button;

// TODO: Auto-generated Javadoc
/**
 * MovieUI that contains a table of movies.
 * */
public class MovieUI extends Composite {

	/** The main panel which contain all other widgets. */
	private VerticalPanel panel;
	private HorizontalPanel buttonPanel;
	private ArrayList<String> languages = new ArrayList<String>();{
	this.languages.add("geman");
	this.languages.add("english");
	this.languages.add("french");
	this.languages.add("spanish");
	}
	 private static final ProvidesKey<Movie> KEY_PROVIDER = new ProvidesKey<Movie>() {
		@Override
		public Object getKey(Movie row) {
		   return row.getId();
		}
	 };
	 
		
	/**
	 * Creates a MovieUI with the given list of movies.
	 * 
	 * @param movies
	 *            The list of movies to show.
	 * */
	
	public MovieUI(ArrayList<Movie> movies) {

		panel = new VerticalPanel();
		buttonPanel = new HorizontalPanel();
		
		// Create a table containing all movies
		final CellTable<Movie> movieTable = new CellTable<Movie>(KEY_PROVIDER);
		final SingleSelectionModel<Movie> selectionModel = new SingleSelectionModel<Movie>();
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				// TODO Auto-generated method stub
				Movie selectedMovie = selectionModel.getSelectedObject();
			}
		});
		movieTable.setSelectionModel(selectionModel);
		
		/*final ListDataProvider<Movie> provider = new ListDataProvider<Movie>();
		provider.addDataDisplay(movieTable);
		List <Movie> temp = provider.getList();
		for (Movie movie:movies){
			temp.add(movie);
		}*/
		
		MovieFilter filter = new MovieFilter();
		final FilteredListDataProvider<Movie> filterProvider = new FilteredListDataProvider<Movie>(filter);
		filterProvider.addDataDisplay(movieTable);
		List <Movie> filtTemp = filterProvider.getList();
		for (Movie movie:movies){
			filtTemp.add(movie);
		}
		
		final TextBox filterText= new TextBox();
		filterText.addValueChangeHandler(new ValueChangeHandler<String>() {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				filterProvider.setFilter(filterText.getValue());
				movieTable.redraw();
			}
		});
		
		Button deleteButton = new Button("Delete");
		deleteButton.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event){
				Movie selected = selectionModel.getSelectedObject();
				filterProvider.getList().remove(selected);
			}
		});
		
		Button addButton = new Button("Add Movie");
		addButton.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event){
				filterProvider.getList().add(new Movie());
			}
		});
		

		
		Column<Movie, Number> idColumn = new Column<Movie, Number>(new NumberCell()){
			public Integer getValue(Movie object){
				return object.getId();
			}
		};
		movieTable.addColumn(idColumn, "ID");
		idColumn.setSortable(true);
		
		final EditTextCell nameCell = new EditTextCell();
		Column<Movie,String> nameColumn = new Column<Movie,String>(nameCell){
			public String getValue(Movie object){
				return object.getName();
			}
		};
		nameColumn.setSortable(true);
		nameColumn.setFieldUpdater(new FieldUpdater<Movie, String>() {
			@Override
			public void update(int index, Movie object, String value){
				if(value.length()<1){
					Window.alert("Name must have at least one character");
					nameCell.clearViewData(KEY_PROVIDER.getKey(object));
					movieTable.redraw();
					return;
				}
				//Window.alert("You changed the Name of: "+ object.getName() + " to: " + value);
				object.setName(value);
				movieTable.redraw();
			}
		});
		movieTable.addColumn(nameColumn, "Name");

		final EditTextCell timeCell= new EditTextCell();
		Column<Movie, String> timeColumn = new Column<Movie, String>(timeCell){
			public String getValue(Movie object){
				return Integer.toString(object.getTime());
			}
		};

		timeColumn.setSortable(true);
		timeColumn.setFieldUpdater(new FieldUpdater<Movie, String>() {
			@Override
			public void update(int index, Movie object, String value){
				try{
					int intValue =Integer.parseInt(value);
				} catch (NumberFormatException e){
					Window.alert("Please enter a number");
					timeCell.clearViewData(KEY_PROVIDER.getKey(object));
					movieTable.redraw();
					return;
				}
				int intValue =Integer.parseInt(value);
				if(intValue < 0){
					Window.alert("Time can not be negative");
					timeCell.clearViewData(KEY_PROVIDER.getKey(object));
					movieTable.redraw();
					return;
				}
				Window.alert("You changed the Time of: "+ object.getName() + " to: " + value);
				object.setTime(intValue);
				movieTable.redraw();
			}
		});
		movieTable.addColumn(timeColumn, "Time");
		SelectionCell languageSelection = new SelectionCell(languages);
		Column<Movie, String> languageColumn = new Column<Movie, String>(languageSelection){
			@Override
			public String getValue(Movie object){
				return object.getLanguage();
			}
		};
		movieTable.addColumn(languageColumn, "Language");
		languageColumn.setSortable(true);
		
		final EditTextCell descriptionCell = new EditTextCell();
		Column<Movie, String> descriptionColumn = new Column<Movie, String>(descriptionCell){
			public String getValue(Movie object){
				return object.getDescription();
			}
		};
		movieTable.addColumn(descriptionColumn, "Description");
		descriptionColumn.setSortable(true);
		descriptionColumn.setFieldUpdater(new FieldUpdater<Movie, String>() {
			@Override
			public void update(int index, Movie object, String value){
				//Window.alert("You changed the Description of: "+ object.getName());
				object.setDescription(value);
				//movieTable.redraw();
			}
		});

		Column<Movie, String> placeColumn = new Column<Movie, String>(new EditTextCell()){
			@Override
			public String getValue(Movie object){
				return object.getPlace();
			}
		};
		movieTable.addColumn(placeColumn, "Place");
		placeColumn.setSortable(true);
		placeColumn.setFieldUpdater(new FieldUpdater<Movie, String>() {
			@Override
			public void update(int index, Movie object, String value){
				//Window.alert("You changed the place of: "+ object.getName() + " to: " + value);
				object.setPlace(value);
				//movieTable.redraw();
			}
		});
		

		
		ListHandler<Movie> columnSortHandler = new ListHandler<Movie>(filtTemp);
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
		panel.setSpacing(10);
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		buttonPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_DEFAULT);
		buttonPanel.add(addButton);
		buttonPanel.add(deleteButton);
		buttonPanel.add(filterText);
		panel.add(buttonPanel);
		panel.getElement().setAttribute("align", "center");
		panel.add(movieTable);
	}

	/**
	 * Shows the content of the MovieUI.
	 * */
	public void show() {

		initWidget(panel);
		RootPanel.get().add(this);
		this.setVisible(true);
	}

}
