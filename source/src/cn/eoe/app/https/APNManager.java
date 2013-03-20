package cn.eoe.app.https;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * 这个类是否有必要
 * 
 * @author mingxv
 * 
 */
public class APNManager {

	private ContentResolver resolver;
	private static final Uri PREFERRED_APN_URI = Uri
			.parse("content://telephony/carriers/preferapn");
	private static final Uri APN_TABLE_URI = Uri
			.parse("content://telephony/carriers");
	private TelephonyManager tm;

	private Context mContext;

	private static APNManager apnManager = null;

	public static APNManager getInstance(Context context) {
		if (apnManager != null) {
			apnManager = new APNManager(context);
		}
		return apnManager;
	}

	private APNManager(Context context) {
		resolver = context.getContentResolver();
		mContext = context;
		tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
	}

	/**
	 * 判断一个apn是否存在 存在返回id
	 * 
	 * @param apnNode
	 * @return
	 */
	public int isApnExisted(APN apnNode) {
		int apnId = -1;
		Cursor mCursor = resolver.query(APN_TABLE_URI, null, null, null, null);
		while (mCursor != null && mCursor.moveToNext()) {
			apnId = mCursor.getShort(mCursor.getColumnIndex("_id"));
			String name = mCursor.getString(mCursor.getColumnIndex("name"));
			String apn = mCursor.getString(mCursor.getColumnIndex("apn"));
			String type = mCursor.getString(mCursor.getColumnIndex("type"));
			String proxy = mCursor.getString(mCursor.getColumnIndex("proxy"));
			String port = mCursor.getString(mCursor.getColumnIndex("port"));
			String current = mCursor.getString(mCursor
					.getColumnIndex("current"));
			String mcc = mCursor.getString(mCursor.getColumnIndex("mcc"));
			String mnc = mCursor.getString(mCursor.getColumnIndex("mnc"));
			String numeric = mCursor.getString(mCursor
					.getColumnIndex("numeric"));
			Log.e("isApnExisted", "info:" + apnId + "_" + name + "_" + apn
					+ "_" + type + "_" + current + "_" + proxy);// 遍历了所有的apn
			if (/* apnNode.getName().equals(name) */(apnNode.getApn().equals(
					apn)
					&& apnNode.getMcc().equals(mcc)
					&& apnNode.getMnc().equals(mnc) && apnNode.getNumeric()
					.equals(numeric))
					&& (type == null || "default".equals(type) || ""
							.equals(type)))// || (apnNode.getApn().equals(apn)
											// && "".equals(proxy) &&
											// "".equals(port))
			{
				return apnId;
			} else {
				apnId = -1;
			}

		}
		return apnId;
	}

	/**
	 * 设置默认的apn
	 * 
	 * @param apnId
	 * @return
	 */
	public boolean setDefaultApn(int apnId) {
		boolean res = false;
		ContentValues values = new ContentValues();
		values.put("apn_id", apnId);
		try {
			resolver.update(PREFERRED_APN_URI, values, null, null);
			Cursor c = resolver.query(PREFERRED_APN_URI, new String[] { "name",
					"apn" }, "_id=" + apnId, null, null);
			if (c != null) {
				res = true;
				c.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;

	}

	/**
	 * 删除所有apn
	 */
	public void deleteApn() {
		resolver.delete(APN_TABLE_URI, null, null);
	}

	public APN getDefaultAPN() {
		String id = "";
		String apn = "";
		String proxy = "";
		String name = "";
		String port = "";
		String type = "";
		String mcc = "";
		String mnc = "";
		String numeric = "";
		APN apnNode = new APN();
		Cursor mCursor = resolver.query(PREFERRED_APN_URI, null, null, null,
				null);
		if (mCursor == null) {
			// throw new Exception("Non prefer apn exist");
			return null;

		}
		while (mCursor != null && mCursor.moveToNext()) {
			id = mCursor.getString(mCursor.getColumnIndex("_id"));
			name = mCursor.getString(mCursor.getColumnIndex("name"));
			apn = mCursor.getString(mCursor.getColumnIndex("apn"))
					.toLowerCase();
			proxy = mCursor.getString(mCursor.getColumnIndex("proxy"));
			port = mCursor.getString(mCursor.getColumnIndex("port"));
			mcc = mCursor.getString(mCursor.getColumnIndex("mcc"));
			mnc = mCursor.getString(mCursor.getColumnIndex("mnc"));
			numeric = mCursor.getString(mCursor.getColumnIndex("numeric"));
			Log.d("getDefaultAPN", "default Apn info:" + id + "_" + name + "_"
					+ apn + "_" + proxy + "_" + proxy);

		}
		apnNode.setName(name);
		apnNode.setApn(apn);
		apnNode.setProxy(proxy);
		apnNode.setPort(port);
		apnNode.setMcc(mcc);
		apnNode.setMnc(mnc);
		apnNode.setNumeric(numeric);
		return apnNode;
	}

	public int getDefaultNetworkType() {
		int networkType = -1;
		ConnectivityManager connectivity = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {

		} else {
			// wifi network have priority
			NetworkInfo wifiNetworkInfo = connectivity
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (wifiNetworkInfo != null
					&& wifiNetworkInfo.getState() == NetworkInfo.State.CONNECTED) {
				return ConnectivityManager.TYPE_WIFI;
			}

			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			for (int i = 0; i < info.length; i++) {
				if (info[i].getState() == NetworkInfo.State.CONNECTED) {
					// use first available network
					networkType = info[i].getType();
					break;
				}
			}
		}

		return networkType;
	}

	// 添加一个APN
	public int InsetAPN() {
		APN checkApn = new APN();
		checkApn.setName("爱家物联专用接口");
		checkApn.setApn("ctnet");
		// checkApn.setProxy("10.0.0.200");
		// checkApn.setPort("80");
		checkApn.setUser("qdaj@qdaj.vpdn.sd");
		checkApn.setPassword("123456");
		checkApn.setMcc(getMCC());
		checkApn.setMnc(getMNC());
		checkApn.setNumeric(getSimOperator());
		return addNewApn(checkApn);

	}

	/**
	 * 增加新的apn
	 * 
	 * @param apnNode
	 * @return
	 */
	private int addNewApn(APN apnNode) {
		int apnId = -1;
		ContentValues values = new ContentValues();
		values.put("name", apnNode.getName());
		values.put("apn", apnNode.getApn());
		values.put("proxy", apnNode.getProxy());
		values.put("port", apnNode.getPort());
		values.put("user", apnNode.getUser());
		values.put("password", apnNode.getPassword());
		values.put("mcc", apnNode.getMcc());
		values.put("mnc", apnNode.getMnc());
		values.put("numeric", apnNode.getNumeric());
		// Note: this values need to be update, and for now, it only for XT800.
		Cursor c = null;
		try {

			Uri newRow = resolver.insert(APN_TABLE_URI, values);

			if (newRow != null) {
				c = resolver.query(newRow, null, null, null, null);

				int idindex = c.getColumnIndex("_id");
				c.moveToFirst();
				apnId = c.getShort(idindex);
				Log.d("Robert", "New ID: " + apnId
						+ ": Inserting new APN succeeded!");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (c != null)
			c.close();
		return apnId;
	}

	private String getMCC() {
		String numeric = tm.getSimOperator();
		String mcc = numeric.substring(0, 3);
		Log.i("MCC  is", mcc);
		return mcc;
	}

	private String getMNC() {

		String numeric = tm.getSimOperator();
		String mnc = numeric.substring(3, numeric.length());
		Log.i("MNC  is", mnc);
		return mnc;
	}

	private String getSimOperator() {
		String SimOperator = tm.getSimOperator();
		return SimOperator;
	}

	public String matchAPN(String currentName) {
		if ("".equals(currentName) || null == currentName) {
			return "";
		}
		currentName = currentName.toLowerCase();
		if (currentName.startsWith("cmnet") || currentName.startsWith("CMNET"))
			return "cmnet";
		else if (currentName.startsWith("cmwap")
				|| currentName.startsWith("CMWAP"))
			return "cmwap";
		else if (currentName.startsWith("3gwap")
				|| currentName.startsWith("3GWAP"))
			return "3gwap";
		else if (currentName.startsWith("3gnet")
				|| currentName.startsWith("3GNET"))
			return "3gnet";
		else if (currentName.startsWith("uninet")
				|| currentName.startsWith("UNINET"))
			return "uninet";
		else if (currentName.startsWith("uniwap")
				|| currentName.startsWith("UNIWAP"))
			return "uniwap";
		else if (currentName.startsWith("default")
				|| currentName.startsWith("DEFAULT"))
			return "default";
		else
			return "";
	}
	
	/**
	 * 获取Apn列表
	 * 
	 * @param context
	 * @return
	 */
	public List<APN> getAPNList() {
		String tag = "Main.getAPNList()";

		String projection[] = { "_id,apn,type,current" };
		Cursor cr = mContext.getContentResolver().query(APN_TABLE_URI, projection, null,
				null, null);
		List<APN> list = new ArrayList<APN>();
		while (cr != null && cr.moveToNext()) {
			Log.d(tag,
					cr.getString(cr.getColumnIndex("_id")) + "  "
							+ cr.getString(cr.getColumnIndex("apn")) + "  "
							+ cr.getString(cr.getColumnIndex("type")) + "  "
							+ cr.getString(cr.getColumnIndex("current")));
			APN a = new APN();
			a.apnId = cr.getString(cr.getColumnIndex("_id"));
			a.apn = cr.getString(cr.getColumnIndex("apn"));
			a.type = cr.getString(cr.getColumnIndex("type"));
			list.add(a);
		}
		if (cr != null)
			cr.close();
		return list;
	}

}