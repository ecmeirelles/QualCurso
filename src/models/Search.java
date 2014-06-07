package models;

import java.sql.Date;
import java.util.ArrayList;

import android.database.SQLException;
import android.text.format.DateFormat;

public class Search extends Bean{

	public static int COURSE = 0;
	public static int INSTITUTION = 1;

	private int id;
	private Date date;
	private int year;
	private int option;
	private String indicator;
	private int minValue;
	private int maxValue;


	public Search() {
		this.id = 0;
		this.identifier="search";
		this.relationship="";
	}

	public Search(int id) {
		this.id = id;
		this.identifier="search";
		this.relationship="";
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getOption() {
		return option;
	}

	public void setOption(int option) {
		this.option = option;
	}

	public String getIndicator() {
		return indicator;
	}

	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}

	public int getMinValue() {
		return minValue;
	}

	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}
	
	public boolean save() throws  SQLException {
		boolean result = false;
		GenericBeanDAO gDB = new GenericBeanDAO();
		if(Search.count()>=10){
			Search.first().delete();
		}
		result = gDB.insertBean(this);
		this.setId(Search.last().getId());
		return result;
	}
	
	public static Search get(int id) throws SQLException {
		Search result = new Search(id);
		GenericBeanDAO gDB = new GenericBeanDAO();	
		result = (Search) gDB.selectBean(result);
		return result;
	}

	public static ArrayList<Search> getAll() throws  SQLException {
		Search type = new Search();
		ArrayList<Search> result = new ArrayList<Search>();
		GenericBeanDAO gDB = new GenericBeanDAO();
			for (Bean b : gDB.selectAllBeans(type)) {
				result.add((Search) b);
			}
			return result;
	}

	public static int count() throws  SQLException {
		Search type = new Search();
		GenericBeanDAO gDB = new GenericBeanDAO();
		return gDB.countBean(type);
	}
	
	public static Search first() throws SQLException {
		Search result = new Search();
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = (Search) gDB.firstOrLastBean(result, false);
		return result;
	}

	public static Search last() throws SQLException {
		Search result = new Search();
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = (Search) gDB.firstOrLastBean(result, true);
		return result;
	}
	
	public static ArrayList<Search> getWhere(String field, String value,
			boolean like) throws  SQLException {
		Search type = new Search();
		ArrayList<Search> result = new ArrayList<Search>();
		GenericBeanDAO gDB = new GenericBeanDAO();
		for (Bean b : gDB.selectBeanWhere(type, field, value, like)) {
			result.add((Search) b);
		}
		return result;
	}
	
	public boolean delete() throws  SQLException {
		boolean result = false;
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = gDB.deleteBean(this);
		return result;
	}
	
	@Override
	public void setId(int id) {
		this.id = id;
		
	}
	
	@Override
	public String get(String field) {
		if(field.equals("_id")) {
			return Integer.toString(this.getId());
		}
		
		else if(field.equals("date")) {
			return this.getDate().toString();
		}
		
		else if (field.equals("year")) {
			return Integer.toString(this.getYear());
		}
		
		else if(field.equals("option")) {
			return Integer.toString(this.getOption());
		}
		
		else if(field.equals("indicator")) {
			return this.getIndicator();
		}
		
		else if(field.equals("min_value")) {
			return Integer.toString(this.getMinValue());
		}
		
		else if(field.equals("max_value")) {
			return Integer.toString(this.getMaxValue());
		}
		
		else {
			return "";
		}
	}

	@Override
	public void set(String field, String data) {
		if(field.equals("_id")){
			this.setId(Integer.parseInt(data));
		}else if(field.equals("date")){
			Date dateData = Date.valueOf(data);
			this.setDate(dateData);
		}else if (field.equals("year")) {
			this.setYear(Integer.parseInt(data));
		}
		
		else if (field.equals("option")) {
			this.setOption(Integer.parseInt(data));
		}
		
		else if (field.equals("indicator")) {
			this.setIndicator(data);
		}
		
		else if (field.equals("min_value")) {
			this.setMinValue(Integer.parseInt(data));
		}
		else if (field.equals("max_value")) {
			this.setMaxValue(Integer.parseInt(data));
		}
		
	}

	@Override
	public ArrayList<String> fieldsList() {
		ArrayList<String> fields = new ArrayList<String>();
		fields.add("_id");
		fields.add("date");
		fields.add("year");
		fields.add("option");
		fields.add("indicator");
		fields.add("min_value");
		fields.add("max_value");
		return fields;
	}

	@Override
	public int getId() {
		return this.id;
	}

}