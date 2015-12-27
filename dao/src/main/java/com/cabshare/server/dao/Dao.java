/*
 * Created by Priyesh Mishra on 27-Dec-2015
 */

package com.cabshare.server.dao;

public interface Dao {
	public int create(final Object object);
	public Object retrieve(String primaryKey);
	public boolean update(final Object object);
	public boolean delete(final Object object);
}
