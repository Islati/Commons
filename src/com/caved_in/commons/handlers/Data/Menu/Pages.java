package com.caved_in.commons.handlers.Data.Menu;

import java.util.LinkedList;

public class Pages
{

	final String[] data;
	final int itemsPerPage;

	/**
	 * Constructor
	 * 
	 * @param data
	 *            The list of information in a String array available to be
	 *            displayed
	 * @param itemsPerPage
	 *            How many items will be returned per page
	 */
	public Pages(String[] data, int itemsPerPage)
	{
		this.data = data;
		this.itemsPerPage = Math.abs(itemsPerPage);
	}

	/**
	 * Returns the strings on a given page as a String[] array.
	 * 
	 * @param page
	 *            The page number to show (1 - pageNumbers)
	 * @return
	 */
	public String[] getStringsToSend(int page)
	{
		int startIndex = this.itemsPerPage * (Math.abs(page) - 1);
		LinkedList<String> list = new LinkedList<String>();
		if (page <= this.getPages())
		{
			for (int i = startIndex; i < (startIndex + this.itemsPerPage); i++)
			{
				if (this.data.length > i)
				{
					list.add(data[i]);
				}
			}
		}
		return list.toArray(new String[list.size()]);
	}

	/**
	 * Get the number of pages which can be displayed.
	 * 
	 * @return
	 */
	public int getPages()
	{
		return (int) Math.ceil((double) data.length / (double) this.itemsPerPage);
	}

	/**
	 * 
	 * @return The number of elements in the data array.
	 */
	public int getRawArrayLength()
	{
		return this.data.length;
	}

}