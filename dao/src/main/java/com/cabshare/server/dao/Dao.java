/**
 * @author Priyesh Mishra
 * Created on 27-Dec-2015
 */

package com.cabshare.server.dao;
/**
 * 
 * Interface for DAO objects.
 *
 */
public interface Dao {
	public int create(final Object object);
	public Object retrieve(final String primaryKey);
	public boolean update(final Object object);
	public boolean delete(final Object object);
}
