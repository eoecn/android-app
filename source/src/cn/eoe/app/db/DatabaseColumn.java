package cn.eoe.app.db;

import java.util.ArrayList;
import java.util.Map;

import android.net.Uri;
import android.provider.BaseColumns;

public abstract class DatabaseColumn implements BaseColumns {
	/**
	 * The identifier to indicate a specific ContentProvider
	 */
	public static final String AUTHORITY = "cn.eoe.app.provider";
	/**
	 * The database's name
	 */
	public static final String DATABASE_NAME = "eoecn.db";
	/**
	 * The version of current database
	 */
	public static final int DATABASE_VERSION = 1;
	/**
	 * Classes's name extends from this class.
	 */
	public static final String[] SUBCLASSES = new String[] {
			"cn.eoe.app.db.BlogColumn", "cn.eoe.app.db.NewsColumn",
			"cn.eoe.app.db.DetailColumn", "cn.eoe.app.db.ImageCacheColumn",
			"cn.eoe.app.db.RequestCacheColumn" };

	public String getTableCreateor() {
		return getTableCreator(getTableName(), getTableMap());
	}

	/**
	 * Get sub-classes of this class.
	 * 
	 * @return Array of sub-classes.
	 */
	@SuppressWarnings("unchecked")
	public static final Class<DatabaseColumn>[] getSubClasses() {
		ArrayList<Class<DatabaseColumn>> classes = new ArrayList<Class<DatabaseColumn>>();
		Class<DatabaseColumn> subClass = null;
		for (int i = 0; i < SUBCLASSES.length; i++) {
			try {
				subClass = (Class<DatabaseColumn>) Class.forName(SUBCLASSES[i]);
				classes.add(subClass);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				continue;
			}
		}
		return classes.toArray(new Class[0]);
	}

	/**
	 * Create a sentence to create a table by using a hash-map.
	 * 
	 * @param tableName
	 *            The table's name to create.
	 * @param map
	 *            A map to store table columns info.
	 * @return
	 */
	private static final String getTableCreator(String tableName,
			Map<String, String> map) {
		String[] keys = map.keySet().toArray(new String[0]);
		String value = null;
		StringBuilder creator = new StringBuilder();
		creator.append("CREATE TABLE ").append(tableName).append("( ");
		int length = keys.length;
		for (int i = 0; i < length; i++) {
			value = map.get(keys[i]);
			creator.append(keys[i]).append(" ");
			creator.append(value);
			if (i < length - 1) {
				creator.append(",");
			}
		}
		creator.append(")");
		return creator.toString();
	}

	abstract public String getTableName();

	abstract public Uri getTableContent();

	abstract protected Map<String, String> getTableMap();

}
