package cn.eoe.app.utils;

import java.io.Serializable;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;


public final class DateUtil implements Serializable {
	/**
  * 
  */
	private static final long serialVersionUID = -3098985139095632110L;

	private DateUtil() {
	}

	/**
	 * ��ʽ��������ʾ��ʽs
	 * 
	 * @param sdate
	 *            ԭʼ���ڸ�ʽ s - ��ʾ "yyyy-mm-dd" ��ʽ�����ڵ� String ����
	 * @param format
	 *            ��ʽ�������ڸ�ʽ
	 * @return ��ʽ�����������ʾ
	 */
	public static String dateFormat(String sdate, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		java.sql.Date date = java.sql.Date.valueOf(sdate);
		String dateString = formatter.format(date);

		return dateString;
	}

	/**
	 * �����������������
	 * 
	 * @param sd
	 *            ��ʼ���ڣ���ʽyyyy-MM-dd
	 * @param ed
	 *            ��ֹ���ڣ���ʽyyyy-MM-dd
	 * @return ���������������
	 */
	public static long getIntervalDays(String sd, String ed) {
		return ((java.sql.Date.valueOf(ed)).getTime() - (java.sql.Date.valueOf(sd)).getTime()) / (3600 * 24 * 1000);
	}

	/**
	 * ��ʼ����yyyy-MM����ֹ��yyyy-MM֮���ȵ�����
	 * 
	 * @param beginMonth
	 *            ��ʽΪyyyy-MM
	 * @param endMonth
	 *            ��ʽΪyyyy-MM
	 * @return ����
	 */
	public static int getInterval(String beginMonth, String endMonth) {
		int intBeginYear = Integer.parseInt(beginMonth.substring(0, 4));
		int intBeginMonth = Integer.parseInt(beginMonth.substring(beginMonth.indexOf("-") + 1));
		int intEndYear = Integer.parseInt(endMonth.substring(0, 4));
		int intEndMonth = Integer.parseInt(endMonth.substring(endMonth.indexOf("-") + 1));

		return ((intEndYear - intBeginYear) * 12) + (intEndMonth - intBeginMonth) + 1;
	}

	/**
	 * ��ݸ�ķ���λ�ÿ�ʼ��������/ʱ���ַ����磬ʱ���ı� "07/10/96 4:5 PM, PDT" ������ɵ�ͬ�� Date(837039928046) �� Date��
	 * 
	 * @param sDate
	 * @param dateFormat
	 * @return
	 */
	public static Date getDate(String sDate, String dateFormat) {
		SimpleDateFormat fmt = new SimpleDateFormat(dateFormat);
		ParsePosition pos = new ParsePosition(0);

		return fmt.parse(sDate, pos);
	}

	/**
	 * ȡ�õ�ǰ���ڵ���ݣ���yyyy��ʽ����.
	 * 
	 * @return ���� yyyy
	 */
	public static String getCurrentYear() {
		return getFormatCurrentTime("yyyy");
	}

	/**
	 * �Զ�������һ�ꡣ���統ǰ�����2007�꣬��ô���Զ�����2006
	 * 
	 * @return ���ؽ��ĸ�ʽΪ yyyy
	 */
	public static String getBeforeYear() {
		String currentYear = getFormatCurrentTime("yyyy");
		int beforeYear = Integer.parseInt(currentYear) - 1;
		return "" + beforeYear;
	}

	/**
	 * ȡ�õ�ǰ���ڵ��·ݣ���MM��ʽ����.
	 * 
	 * @return ��ǰ�·� MM
	 */
	public static String getCurrentMonth() {
		return getFormatCurrentTime("MM");
	}

	/**
	 * ȡ�õ�ǰ���ڵ������Ը�ʽ"dd"����.
	 * 
	 * @return ��ǰ���е�ĳ��dd
	 */
	public static String getCurrentDay() {
		return getFormatCurrentTime("dd");
	}

	/**
	 * ���ص�ǰʱ���ַ�
	 * <p>
	 * ��ʽ��yyyy-MM-dd
	 * 
	 * @return String ָ����ʽ�������ַ�.
	 */
	public static String getCurrentDate() {
		return getFormatDateTime(new Date(), "yyyy-MM-dd");
	}

	/**
	 * ���ص�ǰָ����ʱ�������ʽΪyyyy-MM-dd HH:mm:ss
	 * 
	 * @return ��ʽΪyyyy-MM-dd HH:mm:ss���ܹ�19λ��
	 */
	public static String getCurrentDateTime() {
		return getFormatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * ���ظ�ʱ���ַ�
	 * <p>
	 * ��ʽ��yyyy-MM-dd
	 * 
	 * @param date
	 *            ����
	 * @return String ָ����ʽ�������ַ�.
	 */
	public static String getFormatDate(Date date) {
		return getFormatDateTime(date, "yyyy-MM-dd");
	}

	/**
	 * ����ƶ��ĸ�ʽ�����������ַ�����2007-05-05
	 * 
	 * @param format
	 *            "yyyy-MM-dd" ���� "yyyy/MM/dd",��ȻҲ�����Ǳ����ʽ��
	 * @return ָ����ʽ�������ַ�
	 */
	public static String getFormatDate(String format) {
		return getFormatDateTime(new Date(), format);
	}

	/**
	 * ���ص�ǰʱ���ַ�
	 * <p>
	 * ��ʽ��yyyy-MM-dd HH:mm:ss
	 * 
	 * @return String ָ����ʽ�������ַ�.
	 */
	public static String getCurrentTime() {
		return getFormatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * ���ظ�ʱ���ַ�
	 * <p>
	 * ��ʽ��yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 *            ����
	 * @return String ָ����ʽ�������ַ�.
	 */
	public static String getFormatTime(Date date) {
		return getFormatDateTime(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * ���ظ��ʱ���ַ�
	 * <p>
	 * ��ʽ��yyyy-MM-dd
	 * 
	 * @param date
	 *            ����
	 * @return String ָ����ʽ�������ַ�.
	 */
	public static String getFormatShortTime(Date date) {
		return getFormatDateTime(date, "yyyy-MM-dd");
	}

	/**
	 * ��ݸ�ĸ�ʽ������ʱ���ַ�
	 * <p>
	 * ��ʽ�����������˵��.�ͷ���getFormatDate��һ��ġ�
	 * 
	 * @param format
	 *            ���ڸ�ʽ�ַ�
	 * @return String ָ����ʽ�������ַ�.
	 */
	public static String getFormatCurrentTime(String format) {
		return getFormatDateTime(new Date(), format);
	}

	/**
	 * ��ݸ�ĸ�ʽ��ʱ��(Date���͵�)������ʱ���ַ���Ϊͨ�á�<br>
	 * 
	 * @param date
	 *            ָ��������
	 * @param format
	 *            ���ڸ�ʽ�ַ�
	 * @return String ָ����ʽ�������ַ�.
	 */
	public static String getFormatDateTime(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * ȡ��ָ�������յ����ڶ���.
	 * 
	 * @param year
	 *            ��
	 * @param month
	 *            ��ע���Ǵ�1��12
	 * @param day
	 *            ��
	 * @return һ��java.util.Date()���͵Ķ���
	 */
	public static Date getDateObj(int year, int month, int day) {
		Calendar c = new GregorianCalendar();
		c.set(year, month - 1, day);
		return c.getTime();
	}

	/**
	 * ��ȡָ�����ڵ���һ�졣
	 * 
	 * @param date
	 *            yyyy/MM/dd
	 * @return yyyy/MM/dd
	 */
	public static String getDateTomorrow(String date) {

		Date tempDate = null;
		if (date.indexOf("/") > 0)
			tempDate = getDateObj(date, "[/]");
		if (date.indexOf("-") > 0)
			tempDate = getDateObj(date, "[-]");
		tempDate = getDateAdd(tempDate, 1);
		return getFormatDateTime(tempDate, "yyyy/MM/dd");
	}

	/**
	 * ��ȡ��ָ���������ָ����������ڡ�
	 * 
	 * @param date
	 *            yyyy/MM/dd
	 * @param offset
	 *            ������
	 * @return yyyy/MM/dd
	 */
	public static String getDateOffset(String date, int offset) {

		// Date tempDate = getDateObj(date, "[/]");
		Date tempDate = null;
		if (date.indexOf("/") > 0)
			tempDate = getDateObj(date, "[/]");
		if (date.indexOf("-") > 0)
			tempDate = getDateObj(date, "[-]");
		tempDate = getDateAdd(tempDate, offset);
		return getFormatDateTime(tempDate, "yyyy/MM/dd");
	}

	/**
	 * ȡ��ָ���ָ���ָ�������յ����ڶ���.
	 * 
	 * @param argsDate
	 *            ��ʽΪ"yyyy-MM-dd"
	 * @param split
	 *            ʱ���ʽ�ļ�������硰-������/����Ҫ��ʱ��һ��������
	 * @return һ��java.util.Date()���͵Ķ���
	 */
	public static Date getDateObj(String argsDate, String split) {
		String[] temp = argsDate.split(split);
		int year = new Integer(temp[0]).intValue();
		int month = new Integer(temp[1]).intValue();
		int day = new Integer(temp[2]).intValue();
		return getDateObj(year, month, day);
	}

	/**
	 * ȡ�ø��ַ����������ڶ�������ģʽ����patternָ���ĸ�ʽ.
	 * 
	 * @param dateStr
	 *            �������� �Ӹ��ַ�Ŀ�ʼ�����ı��������һ�����ڡ��÷�����ʹ�ø��ַ������ı��� �й����ڷ����ĸ����Ϣ������� parse(String, ParsePosition) ������һ��
	 *            String��Ӧ���俪ʼ�����з���
	 * 
	 * @param pattern
	 *            ����ģʽ
	 * @return ���ַ����������ڶ���
	 */
	public static Date getDateFromString(String dateStr, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date resDate = null;
		try {
			resDate = sdf.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resDate;
	}

	/**
	 * ȡ�õ�ǰDate����.
	 * 
	 * @return Date ��ǰDate����.
	 */
	public static Date getDateObj() {
		Calendar c = new GregorianCalendar();
		return c.getTime();
	}

	/**
	 * 
	 * @return ��ǰ�·��ж����죻
	 */
	public static int getDaysOfCurMonth() {
		int curyear = new Integer(getCurrentYear()).intValue(); // ��ǰ���
		int curMonth = new Integer(getCurrentMonth()).intValue();// ��ǰ�·�
		int mArray[] = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		// �ж��������� ��2�·���29�죻
		if ((curyear % 400 == 0) || ((curyear % 100 != 0) && (curyear % 4 == 0))) {
			mArray[1] = 29;
		}
		return mArray[curMonth - 1];
		// ���Ҫ�����¸��µ�����ע�⴦���·�12���������ֹ����Խ�磻
		// ���Ҫ�����ϸ��µ�����ע�⴦���·�1���������ֹ����Խ�磻
	}

	/**
	 * ���ָ�������� ����ָ���·ݣ�yyyy-MM���ж����졣
	 * 
	 * @param time
	 *            yyyy-MM
	 * @return ����ָ���·ݵ�����
	 */
	public static int getDaysOfCurMonth(final String time) {
		if (time.length() != 7) {
			throw new NullPointerException("����ĸ�ʽ������yyyy-MM");
		}
		String[] timeArray = time.split("-");
		int curyear = new Integer(timeArray[0]).intValue(); // ��ǰ���
		int curMonth = new Integer(timeArray[1]).intValue();// ��ǰ�·�
		if (curMonth > 12) {
			throw new NullPointerException("����ĸ�ʽ������yyyy-MM�������·ݱ���С�ڵ���12��");
		}
		int mArray[] = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		// �ж��������� ��2�·���29�죻
		if ((curyear % 400 == 0) || ((curyear % 100 != 0) && (curyear % 4 == 0))) {
			mArray[1] = 29;
		}
		if (curMonth == 12) {
			return mArray[0];
		}
		return mArray[curMonth - 1];
		// ���Ҫ�����¸��µ�����ע�⴦���·�12���������ֹ����Խ�磻
		// ���Ҫ�����ϸ��µ�����ע�⴦���·�1���������ֹ����Խ�磻
	}

	/**
	 * ����ָ��Ϊ���Ϊyear�¶�month���·��ڣ���weekOfMonth�����ڵĵ�dayOfWeek���ǵ��µļ��š�<br>
	 * 00 00 00 01 02 03 04 <br>
	 * 05 06 07 08 09 10 11<br>
	 * 12 13 14 15 16 17 18<br>
	 * 19 20 21 22 23 24 25<br>
	 * 26 27 28 29 30 31 <br>
	 * 2006��ĵ�һ���ܵ�1��7��Ϊ��05 06 07 01 02 03 04 <br>
	 * 2006��ĵڶ����ܵ�1��7��Ϊ��12 13 14 08 09 10 11 <br>
	 * 2006��ĵ�����ܵ�1��7��Ϊ��19 20 21 15 16 17 18 <br>
	 * 2006��ĵ��ĸ��ܵ�1��7��Ϊ��26 27 28 22 23 24 25 <br>
	 * 2006��ĵ�����ܵ�1��7��Ϊ��02 03 04 29 30 31 01 ������û�о��Զ�ת���¸����ˡ�
	 * 
	 * @param year
	 *            ��ʽΪyyyy <br>
	 * @param month
	 *            ��ʽΪMM,����ֵ��[1-12]��<br>
	 * @param weekOfMonth
	 *            ��[1-6],��Ϊһ���������6���ܡ�<br>
	 * @param dayOfWeek
	 *            ������1��7֮�䣬����1��7��1��ʾ�����죬7��ʾ������<br>
	 *            -6Ϊ������-1Ϊ�����壬0Ϊ������ <br>
	 * @return <type>int</type>
	 */
	public static int getDayofWeekInMonth(String year, String month, String weekOfMonth, String dayOfWeek) {
		Calendar cal = new GregorianCalendar();
		// �ھ���Ĭ�����Ի�����Ĭ��ʱ����ʹ�õ�ǰʱ�乹��һ��Ĭ�ϵ� GregorianCalendar��
		int y = new Integer(year).intValue();
		int m = new Integer(month).intValue();
		cal.clear();// ��������ǰ������
		cal.set(y, m - 1, 1);// ����������Ϊ���µĵ�һ�졣
		cal.set(Calendar.DAY_OF_WEEK_IN_MONTH, new Integer(weekOfMonth).intValue());
		cal.set(Calendar.DAY_OF_WEEK, new Integer(dayOfWeek).intValue());
		// System.out.print(cal.get(Calendar.MONTH)+" ");
		// System.out.print("��"+cal.get(Calendar.WEEK_OF_MONTH)+"\t");
		// WEEK_OF_MONTH��ʾ�����ڱ��µĵڼ����ܡ�����1�������ڼ�������ʾ�ڱ��µĵ�һ����
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * ���ָ����������Сʱ���룬����һ��java.Util.Date����
	 * 
	 * @param year
	 *            ��
	 * @param month
	 *            �� 0-11
	 * @param date
	 *            ��
	 * @param hourOfDay
	 *            Сʱ 0-23
	 * @param minute
	 *            �� 0-59
	 * @param second
	 *            �� 0-59
	 * @return һ��Date����
	 */
	public static Date getDate(int year, int month, int date, int hourOfDay, int minute, int second) {
		Calendar cal = new GregorianCalendar();
		cal.set(year, month, date, hourOfDay, minute, second);
		return cal.getTime();
	}

	/**
	 * ���ָ�����ꡢ�¡��շ��ص�ǰ�����ڼ���1��ʾ�����졢2��ʾ����һ��7��ʾ������
	 * 
	 * @param year
	 * @param month
	 *            month�Ǵ�1��ʼ��12����
	 * @param day
	 * @return ����һ����?�����������ڼ������֡�1��ʾ�����졢2��ʾ����һ��7��ʾ������
	 */
	public static int getDayOfWeek(String year, String month, String day) {
		Calendar cal = new GregorianCalendar(new Integer(year).intValue(), new Integer(month).intValue() - 1, new Integer(day)
				.intValue());
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * ���ָ�����ꡢ�¡��շ��ص�ǰ�����ڼ���1��ʾ�����졢2��ʾ����һ��7��ʾ������
	 * 
	 * @param date
	 *            "yyyy/MM/dd",����"yyyy-MM-dd"
	 * @return ����һ����?�����������ڼ������֡�1��ʾ�����졢2��ʾ����һ��7��ʾ������
	 */
	public static int getDayOfWeek(String date) {
		String[] temp = null;
		if (date.indexOf("/") > 0) {
			temp = date.split("/");
		}
		if (date.indexOf("-") > 0) {
			temp = date.split("-");
		}
		return getDayOfWeek(temp[0], temp[1], temp[2]);
	}

	/**
	 * ���ص�ǰ���������ڼ������磺�����ա�����һ��������ȵȡ�
	 * 
	 * @param date
	 *            ��ʽΪ yyyy/MM/dd ���� yyyy-MM-dd
	 * @return ���ص�ǰ���������ڼ�
	 */
	public static String getChinaDayOfWeek(String date) {
		String[] weeks = new String[] { "������", "����һ", "���ڶ�", "������", "������", "������", "������" };
		int week = getDayOfWeek(date);
		return weeks[week - 1];
	}

	/**
	 * ���ָ�����ꡢ�¡��շ��ص�ǰ�����ڼ���1��ʾ�����졢2��ʾ����һ��7��ʾ������
	 * 
	 * @param date
	 * 
	 * @return ����һ����?�����������ڼ������֡�1��ʾ�����졢2��ʾ����һ��7��ʾ������
	 */
	public static int getDayOfWeek(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * �����ƶ��������ڵ�����һ���еĵڼ����ܡ�<br>
	 * created by wangmj at 20060324.<br>
	 * 
	 * @param year
	 * @param month
	 *            ��Χ1-12<br>
	 * @param day
	 * @return int
	 */
	public static int getWeekOfYear(String year, String month, String day) {
		Calendar cal = new GregorianCalendar();
		cal.clear();
		cal.set(new Integer(year).intValue(), new Integer(month).intValue() - 1, new Integer(day).intValue());
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * ȡ�ø����ڼ���һ�����������ڶ���.
	 * 
	 * @param date
	 *            ������ڶ���
	 * @param amount
	 *            ��Ҫ��ӵ������������ǰ������ʹ�ø���Ϳ���.
	 * @return Date ����һ�������Ժ��Date����.
	 */
	public static Date getDateAdd(Date date, int amount) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(GregorianCalendar.DATE, amount);
		return cal.getTime();
	}

	/**
	 * ȡ�ø����ڼ���һ�����������ڶ���.
	 * 
	 * @param date
	 *            ������ڶ���
	 * @param amount
	 *            ��Ҫ��ӵ������������ǰ������ʹ�ø���Ϳ���.
	 * @param format
	 *            �����ʽ.
	 * @return Date ����һ�������Ժ��Date����.
	 */
	public static String getFormatDateAdd(Date date, int amount, String format) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(GregorianCalendar.DATE, amount);
		return getFormatDateTime(cal.getTime(), format);
	}

	/**
	 * ��õ�ǰ���ڹ̶������������ڣ���ǰ60��dateAdd(-60)
	 * 
	 * @param amount
	 *            �����ļ�����ڳ��ȣ���ǰΪ�������Ϊ��
	 * @param format
	 *            ������ڵĸ�ʽ.
	 * @return java.lang.String ���ո�ʽ����ļ���������ַ�.
	 */
	public static String getFormatCurrentAdd(int amount, String format) {

		Date d = getDateAdd(new Date(), amount);

		return getFormatDateTime(d, format);
	}

	/**
	 * ȡ�ø��ʽ��������������
	 * 
	 * @param format
	 *            ��������ĸ�ʽ
	 * @return String ���ʽ�������ַ�.
	 */
	public static String getFormatYestoday(String format) {
		return getFormatCurrentAdd(-1, format);
	}

	/**
	 * ����ָ�����ڵ�ǰһ�졣<br>
	 * 
	 * @param sourceDate
	 * @param format
	 *            yyyy MM dd hh mm ss
	 * @return ���������ַ���ʽ��formcatһ�¡�
	 */
	public static String getYestoday(String sourceDate, String format) {
		return getFormatDateAdd(getDateFromString(sourceDate, format), -1, format);
	}

	/**
	 * ������������ڣ�<br>
	 * 
	 * @param format
	 * @return ���������ַ���ʽ��formcatһ�¡�
	 */
	public static String getFormatTomorrow(String format) {
		return getFormatCurrentAdd(1, format);
	}

	/**
	 * ����ָ�����ڵĺ�һ�졣<br>
	 * 
	 * @param sourceDate
	 * @param format
	 * @return ���������ַ���ʽ��formcatһ�¡�
	 */
	public static String getFormatDateTommorrow(String sourceDate, String format) {
		return getFormatDateAdd(getDateFromString(sourceDate, format), 1, format);
	}

	/**
	 * ��������Ĭ�� TimeZone�������ָ����ʽ��ʱ���ַ�
	 * 
	 * @param dateFormat
	 * @return ���������ַ���ʽ��formcatһ�¡�
	 */
	public static String getCurrentDateString(String dateFormat) {
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		sdf.setTimeZone(TimeZone.getDefault());

		return sdf.format(cal.getTime());
	}

	// /**
	// * @deprecated ������ʹ�á� ���ص�ǰʱ�䴮 ��ʽ:yyMMddhhmmss,���ϴ�����ʱʹ��
	// *
	// * @return String
	// */
	// public static String getCurDate() {
	// GregorianCalendar gcDate = new GregorianCalendar();
	// int year = gcDate.get(GregorianCalendar.YEAR);
	// int month = gcDate.get(GregorianCalendar.MONTH) + 1;
	// int day = gcDate.get(GregorianCalendar.DAY_OF_MONTH);
	// int hour = gcDate.get(GregorianCalendar.HOUR_OF_DAY);
	// int minute = gcDate.get(GregorianCalendar.MINUTE);
	// int sen = gcDate.get(GregorianCalendar.SECOND);
	// String y;
	// String m;
	// String d;
	// String h;
	// String n;
	// String s;
	// y = new Integer(year).toString();
	//
	// if (month < 10) {
	// m = "0" + new Integer(month).toString();
	// } else {
	// m = new Integer(month).toString();
	// }
	//
	// if (day < 10) {
	// d = "0" + new Integer(day).toString();
	// } else {
	// d = new Integer(day).toString();
	// }
	//
	// if (hour < 10) {
	// h = "0" + new Integer(hour).toString();
	// } else {
	// h = new Integer(hour).toString();
	// }
	//
	// if (minute < 10) {
	// n = "0" + new Integer(minute).toString();
	// } else {
	// n = new Integer(minute).toString();
	// }
	//
	// if (sen < 10) {
	// s = "0" + new Integer(sen).toString();
	// } else {
	// s = new Integer(sen).toString();
	// }
	//
	// return "" + y + m + d + h + n + s;
	// }

	/**
	 * ��ݸ�ĸ�ʽ������ʱ���ַ� ��getFormatDate(String format)���ơ�
	 * 
	 * @param format
	 *            yyyy MM dd hh mm ss
	 * @return ����һ��ʱ���ַ�
	 */
	public static String getCurTimeByFormat(String format) {
		Date newdate = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(newdate);
	}

	/**
	 * ��ȡ����ʱ�䴮ʱ��Ĳ�ֵ����λΪ��
	 * 
	 * @param startTime
	 *            ��ʼʱ�� yyyy-MM-dd HH:mm:ss
	 * @param endTime
	 *            ����ʱ�� yyyy-MM-dd HH:mm:ss
	 * @return ����ʱ��Ĳ�ֵ(��)
	 */
	public static long getDiff(String startTime, String endTime) {
		long diff = 0;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date startDate = ft.parse(startTime);
			Date endDate = ft.parse(endTime);
			diff = startDate.getTime() - endDate.getTime();
			diff = diff / 1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return diff;
	}

	/**
	 * ��ȡСʱ/����/��
	 * 
	 * @param second
	 *            ��
	 * @return ��Сʱ�����ӡ����ʱ���ַ�����3Сʱ23����13�롣
	 */
	public static String getHour(long second) {
		long hour = second / 60 / 60;
		long minute = (second - hour * 60 * 60) / 60;
		long sec = (second - hour * 60 * 60) - minute * 60;

		return hour + "Сʱ" + minute + "����" + sec + "��";

	}

	/**
	 * ����ָ��ʱ���ַ�
	 * <p>
	 * ��ʽ��yyyy-MM-dd HH:mm:ss
	 * 
	 * @return String ָ����ʽ�������ַ�.
	 */
	public static String getDateTime(long microsecond) {
		return getFormatDateTime(new Date(microsecond), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * ���ص�ǰʱ���ʵ��Сʱ�������ʱ�䡣
	 * <p>
	 * ��ʽ��yyyy-MM-dd HH:mm:ss
	 * 
	 * @return Float �Ӽ�ʵ��Сʱ.
	 */
	public static String getDateByAddFltHour(float flt) {
		int addMinute = (int) (flt * 60);
		Calendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		cal.add(GregorianCalendar.MINUTE, addMinute);
		return getFormatDateTime(cal.getTime(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * ����ָ��ʱ���ָ��Сʱ��������ʱ�䡣
	 * <p>
	 * ��ʽ��yyyy-MM-dd HH:mm:ss
	 * 
	 * @return ʱ��.
	 */
	public static String getDateByAddHour(String datetime, int minute) {
		String returnTime = null;
		Calendar cal = new GregorianCalendar();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date;
		try {
			date = ft.parse(datetime);
			cal.setTime(date);
			cal.add(GregorianCalendar.MINUTE, minute);
			returnTime = getFormatDateTime(cal.getTime(), "yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return returnTime;

	}

	/**
	 * ��ȡ����ʱ�䴮ʱ��Ĳ�ֵ����λΪСʱ
	 * 
	 * @param startTime
	 *            ��ʼʱ�� yyyy-MM-dd HH:mm:ss
	 * @param endTime
	 *            ����ʱ�� yyyy-MM-dd HH:mm:ss
	 * @return ����ʱ��Ĳ�ֵ(��)
	 */
	public static int getDiffHour(String startTime, String endTime) {
		long diff = 0;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date startDate = ft.parse(startTime);
			Date endDate = ft.parse(endTime);
			diff = startDate.getTime() - endDate.getTime();
			diff = diff / (1000 * 60 * 60);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Long(diff).intValue();
	}

	/**
	 * ������ݵ�������
	 * 
	 * @param selectName
	 *            ���������
	 * @param value
	 *            ��ǰ�������ֵ
	 * @param startYear
	 *            ��ʼ���
	 * @param endYear
	 *            �������
	 * @return ����������html
	 */
	public static String getYearSelect(String selectName, String value, int startYear, int endYear) {
		int start = startYear;
		int end = endYear;
		if (startYear > endYear) {
			start = endYear;
			end = startYear;
		}
		StringBuffer sb = new StringBuffer("");
		sb.append("<select name=\"" + selectName + "\">");
		for (int i = start; i <= end; i++) {
			if (!value.trim().equals("") && i == Integer.parseInt(value)) {
				sb.append("<option value=\"" + i + "\" selected>" + i + "</option>");
			} else {
				sb.append("<option value=\"" + i + "\">" + i + "</option>");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}

	/**
	 * ������ݵ�������
	 * 
	 * @param selectName
	 *            ���������
	 * @param value
	 *            ��ǰ�������ֵ
	 * @param startYear
	 *            ��ʼ���
	 * @param endYear
	 *            ������� ���翪ʼ���Ϊ2001�������Ϊ2005��ô������������ֵ����2001��2002��2003��2004��2005����
	 * @return ������ݵ��������html��
	 */
	public static String getYearSelect(String selectName, String value, int startYear, int endYear, boolean hasBlank) {
		int start = startYear;
		int end = endYear;
		if (startYear > endYear) {
			start = endYear;
			end = startYear;
		}
		StringBuffer sb = new StringBuffer("");
		sb.append("<select name=\"" + selectName + "\">");
		if (hasBlank) {
			sb.append("<option value=\"\"></option>");
		}
		for (int i = start; i <= end; i++) {
			if (!value.trim().equals("") && i == Integer.parseInt(value)) {
				sb.append("<option value=\"" + i + "\" selected>" + i + "</option>");
			} else {
				sb.append("<option value=\"" + i + "\">" + i + "</option>");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}

	/**
	 * ������ݵ�������
	 * 
	 * @param selectName
	 *            ���������
	 * @param value
	 *            ��ǰ�������ֵ
	 * @param startYear
	 *            ��ʼ���
	 * @param endYear
	 *            �������
	 * @param js
	 *            �����jsΪjs�ַ����� " onchange=\"changeYear()\" " ,�����κ�js�ķ����Ϳ�����jspҳ���б�д���������롣
	 * @return ������ݵ�������
	 */
	public static String getYearSelect(String selectName, String value, int startYear, int endYear, boolean hasBlank, String js) {
		int start = startYear;
		int end = endYear;
		if (startYear > endYear) {
			start = endYear;
			end = startYear;
		}
		StringBuffer sb = new StringBuffer("");

		sb.append("<select name=\"" + selectName + "\" " + js + ">");
		if (hasBlank) {
			sb.append("<option value=\"\"></option>");
		}
		for (int i = start; i <= end; i++) {
			if (!value.trim().equals("") && i == Integer.parseInt(value)) {
				sb.append("<option value=\"" + i + "\" selected>" + i + "</option>");
			} else {
				sb.append("<option value=\"" + i + "\">" + i + "</option>");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}

	/**
	 * ������ݵ�������
	 * 
	 * @param selectName
	 *            ���������
	 * @param value
	 *            ��ǰ�������ֵ
	 * @param startYear
	 *            ��ʼ���
	 * @param endYear
	 *            �������
	 * @param js
	 *            �����jsΪjs�ַ����� " onchange=\"changeYear()\" " ,�����κ�js�ķ����Ϳ�����jspҳ���б�д���������롣
	 * @return ������ݵ�������
	 */
	public static String getYearSelect(String selectName, String value, int startYear, int endYear, String js) {
		int start = startYear;
		int end = endYear;
		if (startYear > endYear) {
			start = endYear;
			end = startYear;
		}
		StringBuffer sb = new StringBuffer("");
		sb.append("<select name=\"" + selectName + "\" " + js + ">");
		for (int i = start; i <= end; i++) {
			if (!value.trim().equals("") && i == Integer.parseInt(value)) {
				sb.append("<option value=\"" + i + "\" selected>" + i + "</option>");
			} else {
				sb.append("<option value=\"" + i + "\">" + i + "</option>");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}

	/**
	 * ��ȡ�·ݵ�������
	 * 
	 * @param selectName
	 * @param value
	 * @param hasBlank
	 * @return �����·ݵ�������
	 */
	public static String getMonthSelect(String selectName, String value, boolean hasBlank) {
		StringBuffer sb = new StringBuffer("");
		sb.append("<select name=\"" + selectName + "\">");
		if (hasBlank) {
			sb.append("<option value=\"\"></option>");
		}
		for (int i = 1; i <= 12; i++) {
			if (!value.trim().equals("") && i == Integer.parseInt(value)) {
				sb.append("<option value=\"" + i + "\" selected>" + i + "</option>");
			} else {
				sb.append("<option value=\"" + i + "\">" + i + "</option>");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}

	/**
	 * ��ȡ�·ݵ�������
	 * 
	 * @param selectName
	 * @param value
	 * @param hasBlank
	 * @param js
	 * @return �����·ݵ�������
	 */
	public static String getMonthSelect(String selectName, String value, boolean hasBlank, String js) {
		StringBuffer sb = new StringBuffer("");
		sb.append("<select name=\"" + selectName + "\" " + js + ">");
		if (hasBlank) {
			sb.append("<option value=\"\"></option>");
		}
		for (int i = 1; i <= 12; i++) {
			if (!value.trim().equals("") && i == Integer.parseInt(value)) {
				sb.append("<option value=\"" + i + "\" selected>" + i + "</option>");
			} else {
				sb.append("<option value=\"" + i + "\">" + i + "</option>");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}

	/**
	 * ��ȡ���������Ĭ�ϵ�Ϊ1-31�� ע�⣺�˷������ܹ����·����������������
	 * 
	 * @param selectName
	 * @param value
	 * @param hasBlank
	 * @return ������������
	 */
	public static String getDaySelect(String selectName, String value, boolean hasBlank) {
		StringBuffer sb = new StringBuffer("");
		sb.append("<select name=\"" + selectName + "\">");
		if (hasBlank) {
			sb.append("<option value=\"\"></option>");
		}
		for (int i = 1; i <= 31; i++) {
			if (!value.trim().equals("") && i == Integer.parseInt(value)) {
				sb.append("<option value=\"" + i + "\" selected>" + i + "</option>");
			} else {
				sb.append("<option value=\"" + i + "\">" + i + "</option>");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}

	/**
	 * ��ȡ���������Ĭ�ϵ�Ϊ1-31
	 * 
	 * @param selectName
	 * @param value
	 * @param hasBlank
	 * @param js
	 * @return ��ȡ���������
	 */
	public static String getDaySelect(String selectName, String value, boolean hasBlank, String js) {
		StringBuffer sb = new StringBuffer("");
		sb.append("<select name=\"" + selectName + "\" " + js + ">");
		if (hasBlank) {
			sb.append("<option value=\"\"></option>");
		}
		for (int i = 1; i <= 31; i++) {
			if (!value.trim().equals("") && i == Integer.parseInt(value)) {
				sb.append("<option value=\"" + i + "\" selected>" + i + "</option>");
			} else {
				sb.append("<option value=\"" + i + "\">" + i + "</option>");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}

	/**
	 * ��������֮���ж��ٸ���ĩ�������ĩ��ָ������������죬һ����ĩ���ؽ��Ϊ2������Ϊ4���Դ����ơ��� ���˷���Ŀǰ����ͳ��˾���ó���¼���� ע�⿪ʼ���ںͽ�������Ҫͳһ������
	 * 
	 * @param startDate
	 *            ��ʼ���� ����ʽ"yyyy/MM/dd" ����"yyyy-MM-dd"
	 * @param endDate
	 *            �������� ����ʽ"yyyy/MM/dd"����"yyyy-MM-dd"
	 * @return int
	 */
	public static int countWeekend(String startDate, String endDate) {
		int result = 0;
		Date sdate = null;
		Date edate = null;
		if (startDate.indexOf("/") > 0 && endDate.indexOf("/") > 0) {
			sdate = getDateObj(startDate, "/"); // ��ʼ����
			edate = getDateObj(endDate, "/");// ��������
		}
		if (startDate.indexOf("-") > 0 && endDate.indexOf("-") > 0) {
			sdate = getDateObj(startDate, "-"); // ��ʼ����
			edate = getDateObj(endDate, "-");// ��������
		}

		// ���ȼ����������Щ���ڣ�Ȼ���ҳ������������������
		int sumDays = Math.abs(getDiffDays(startDate, endDate));
		int dayOfWeek = 0;
		for (int i = 0; i <= sumDays; i++) {
			dayOfWeek = getDayOfWeek(getDateAdd(sdate, i)); // ����ÿ��һ�������
			if (dayOfWeek == 1 || dayOfWeek == 7) { // 1 ������ 7������
				result++;
			}
		}
		return result;
	}

	/**
	 * ������������֮���������졣 ע�⿪ʼ���ںͽ�������Ҫͳһ������
	 * 
	 * @param startDate
	 *            ��ʽ"yyyy/MM/dd" ����"yyyy-MM-dd"
	 * @param endDate
	 *            ��ʽ"yyyy/MM/dd" ����"yyyy-MM-dd"
	 * @return ����
	 */
	public static int getDiffDays(String startDate, String endDate) {
		long diff = 0;
		SimpleDateFormat ft = null;
		if (startDate.indexOf("/") > 0 && endDate.indexOf("/") > 0) {
			ft = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		}
		if (startDate.indexOf("-") > 0 && endDate.indexOf("-") > 0) {
			ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
		try {
			Date sDate = ft.parse(startDate + " 00:00:00");
			Date eDate = ft.parse(endDate + " 00:00:00");
			diff = eDate.getTime() - sDate.getTime();
			diff = diff / 86400000;// 1000*60*60*24;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return (int) diff;

	}

	/**
	 * ������������֮�����ϸ��������(������ʼ���ںͽ�������)�� ���磺2007/07/01 ��2007/07/03 ,��ô�������� {"2007/07/01","2007/07/02","2007/07/03"} ע�⿪ʼ���ںͽ�������Ҫͳһ������
	 * 
	 * @param startDate
	 *            ��ʽ"yyyy/MM/dd"���� yyyy-MM-dd
	 * @param endDate
	 *            ��ʽ"yyyy/MM/dd"���� yyyy-MM-dd
	 * @return ����һ���ַ��������
	 */
	public static String[] getArrayDiffDays(String startDate, String endDate) {
		int LEN = 0; // ������������֮���ܹ��ж�����
		// ���������ںͿ�ʼ������ͬ
		if (startDate.equals(endDate)) {
			return new String[] { startDate };
		}
		Date sdate = null;
		if (startDate.indexOf("/") > 0 && endDate.indexOf("/") > 0) {
			sdate = getDateObj(startDate, "/"); // ��ʼ����
		}
		if (startDate.indexOf("-") > 0 && endDate.indexOf("-") > 0) {
			sdate = getDateObj(startDate, "-"); // ��ʼ����
		}

		LEN = getDiffDays(startDate, endDate);
		String[] dateResult = new String[LEN + 1];
		dateResult[0] = startDate;
		for (int i = 1; i < LEN + 1; i++) {
			if (startDate.indexOf("/") > 0 && endDate.indexOf("/") > 0) {
				dateResult[i] = getFormatDateTime(getDateAdd(sdate, i), "yyyy/MM/dd");
			}
			if (startDate.indexOf("-") > 0 && endDate.indexOf("-") > 0) {
				dateResult[i] = getFormatDateTime(getDateAdd(sdate, i), "yyyy-MM-dd");
			}
		}

		return dateResult;
	}

	/**
	 * �ж�һ�������Ƿ��ڿ�ʼ���ںͽ�������֮�䡣
	 * 
	 * @param srcDate
	 *            Ŀ������ yyyy/MM/dd ���� yyyy-MM-dd
	 * @param startDate
	 *            ��ʼ���� yyyy/MM/dd ���� yyyy-MM-dd
	 * @param endDate
	 *            �������� yyyy/MM/dd ���� yyyy-MM-dd
	 * @return ���ڵ��ڿ�ʼ����С�ڵ��ڽ������ڣ���ô����true�����򷵻�false
	 */
	public static boolean isInStartEnd(String srcDate, String startDate, String endDate) {
		if (startDate.compareTo(srcDate) <= 0 && endDate.compareTo(srcDate) >= 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ��ȡ���������Ĭ�ϵ�Ϊ1-4�� ע�⣺�˷������ܹ����·����������������
	 * 
	 * @param selectName
	 * @param value
	 * @param hasBlank
	 * @return ��ü��ȵ�������
	 */
	public static String getQuarterSelect(String selectName, String value, boolean hasBlank) {
		StringBuffer sb = new StringBuffer("");
		sb.append("<select name=\"" + selectName + "\">");
		if (hasBlank) {
			sb.append("<option value=\"\"></option>");
		}
		for (int i = 1; i <= 4; i++) {
			if (!value.trim().equals("") && i == Integer.parseInt(value)) {
				sb.append("<option value=\"" + i + "\" selected>" + i + "</option>");
			} else {
				sb.append("<option value=\"" + i + "\">" + i + "</option>");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}

	/**
	 * ��ȡ���ȵ�������Ĭ�ϵ�Ϊ1-4
	 * 
	 * @param selectName
	 * @param value
	 * @param hasBlank
	 * @param js
	 * @return ��ȡ���ȵ�������
	 */
	public static String getQuarterSelect(String selectName, String value, boolean hasBlank, String js) {
		StringBuffer sb = new StringBuffer("");
		sb.append("<select name=\"" + selectName + "\" " + js + ">");
		if (hasBlank) {
			sb.append("<option value=\"\"></option>");
		}
		for (int i = 1; i <= 4; i++) {
			if (!value.trim().equals("") && i == Integer.parseInt(value)) {
				sb.append("<option value=\"" + i + "\" selected>" + i + "</option>");
			} else {
				sb.append("<option value=\"" + i + "\">" + i + "</option>");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}

	/**
	 * ����ʽΪyyyy����yyyy.MM����yyyy.MM.dd������ת��Ϊyyyy/MM/dd�ĸ�ʽ��λ����ģ�����01��<br>
	 * ����.1999 = 1999/01/01 ���磺1989.02=1989/02/01
	 * 
	 * @param argDate
	 *            ��Ҫ����ת�������ڡ���ʽ����Ϊyyyy����yyyy.MM����yyyy.MM.dd
	 * @return ���ظ�ʽΪyyyy/MM/dd���ַ�
	 */
	public static String changeDate(String argDate) {
		if (argDate == null || argDate.trim().equals("")) {
			return "";
		}
		String result = "";
		// ����Ǹ�ʽΪyyyy/MM/dd�ľ�ֱ�ӷ���
		if (argDate.length() == 10 && argDate.indexOf("/") > 0) {
			return argDate;
		}
		String[] str = argDate.split("[.]"); // .�Ƚ�����
		int LEN = str.length;
		for (int i = 0; i < LEN; i++) {
			if (str[i].length() == 1) {
				if (str[i].equals("0")) {
					str[i] = "01";
				} else {
					str[i] = "0" + str[i];
				}
			}
		}
		if (LEN == 1) {
			result = argDate + "/01/01";
		}
		if (LEN == 2) {
			result = str[0] + "/" + str[1] + "/01";
		}
		if (LEN == 3) {
			result = str[0] + "/" + str[1] + "/" + str[2];
		}
		return result;
	}

	/**
	 * ����ʽΪyyyy����yyyy.MM����yyyy.MM.dd������ת��Ϊyyyy/MM/dd�ĸ�ʽ��λ����ģ�����01��<br>
	 * ����.1999 = 1999/01/01 ���磺1989.02=1989/02/01
	 * 
	 * @param argDate
	 *            ��Ҫ����ת�������ڡ���ʽ����Ϊyyyy����yyyy.MM����yyyy.MM.dd
	 * @return ���ظ�ʽΪyyyy/MM/dd���ַ�
	 */
	public static String changeDateWithSplit(String argDate, String split) {
		if (argDate == null || argDate.trim().equals("")) {
			return "";
		}
		if (split == null || split.trim().equals("")) {
			split = "-";
		}
		String result = "";
		// ����Ǹ�ʽΪyyyy/MM/dd�ľ�ֱ�ӷ���
		if (argDate.length() == 10 && argDate.indexOf("/") > 0) {
			return argDate;
		}
		// ����Ǹ�ʽΪyyyy-MM-dd�ľ�ֱ�ӷ���
		if (argDate.length() == 10 && argDate.indexOf("-") > 0) {
			return argDate;
		}
		String[] str = argDate.split("[.]"); // .�Ƚ�����
		int LEN = str.length;
		for (int i = 0; i < LEN; i++) {
			if (str[i].length() == 1) {
				if (str[i].equals("0")) {
					str[i] = "01";
				} else {
					str[i] = "0" + str[i];
				}
			}
		}
		if (LEN == 1) {
			result = argDate + split + "01" + split + "01";
		}
		if (LEN == 2) {
			result = str[0] + split + str[1] + split + "01";
		}
		if (LEN == 3) {
			result = str[0] + split + str[1] + split + str[2];
		}
		return result;
	}

	/**
	 * ����ָ�����ڵĵ���һ���µ�����
	 * 
	 * @param argDate
	 *            ��ʽΪyyyy-MM-dd����yyyy/MM/dd
	 * @return ��һ���µ�����
	 */
	public static int getNextMonthDays(String argDate) {
		String[] temp = null;
		if (argDate.indexOf("/") > 0) {
			temp = argDate.split("/");
		}
		if (argDate.indexOf("-") > 0) {
			temp = argDate.split("-");
		}
		Calendar cal = new GregorianCalendar(new Integer(temp[0]).intValue(), new Integer(temp[1]).intValue() - 1, new Integer(
				temp[2]).intValue());
		int curMonth = cal.get(Calendar.MONTH);
		cal.set(Calendar.MONTH, curMonth + 1);

		int curyear = cal.get(Calendar.YEAR);// ��ǰ���
		curMonth = cal.get(Calendar.MONTH);// ��ǰ�·�,0-11

		int mArray[] = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		// �ж��������� ��2�·���29�죻
		if ((curyear % 400 == 0) || ((curyear % 100 != 0) && (curyear % 4 == 0))) {
			mArray[1] = 29;
		}
		return mArray[curMonth];
	}

	public static void main(String[] args) {
		System.out.println(DateUtil.getCurrentDateTime());
		System.out.println("first=" + changeDateWithSplit("2000.1", ""));
		System.out.println("second=" + changeDateWithSplit("2000.1", "/"));
		String[] t = getArrayDiffDays("2008/02/15", "2008/02/19");
		for (int i = 0; i < t.length; i++) {
			System.out.println(t[i]);
		}
		t = getArrayDiffDays("2008-02-15", "2008-02-19");
		for (int i = 0; i < t.length; i++) {
			System.out.println(t[i]);
		}
		System.out.println(getNextMonthDays("2008/02/15") + "||" + getCurrentMonth() + "||" + DateUtil.changeDate("1999"));
		System.out.println(DateUtil.changeDate("1999.1"));
		System.out.println(DateUtil.changeDate("1999.11"));
		System.out.println(DateUtil.changeDate("1999.1.2"));
		System.out.println(DateUtil.changeDate("1999.11.12"));
	}
}