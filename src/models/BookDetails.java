/**@description: BookDetails class to get book details in given format.
 * @author: Samhita Argula
 * @date: 20 Apr, 2024
 */
package models;

import java.sql.Date;

public class BookDetails {
	private final int index;
	private final String name;
	private final String author;
	private final String bookCode;
	private final Date date;
	private final String availability;

	// Constructor initializing each book detail
	public BookDetails(int index, String name, String author, String bookCode, Date date, String availability){
		this.index = index;
		this.name = name;
		this.author = author;
		this.bookCode = bookCode;
		this.date = date;
		this.availability = availability;
	}

	// Getters for each individual book detail
	public int getIndex(){
		return index;
	}
	public String getName(){
		return name;
	}
	public String getAuthor(){
		return author;
	}
	public String getBookCode(){
		return bookCode;
	}
	public Date getDate(){
		return date;
	}
	public String getAvailability(){
		return availability;
	}
}