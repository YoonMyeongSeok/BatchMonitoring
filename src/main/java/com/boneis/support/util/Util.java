package com.boneis.support.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Util {

	/**
     * 문자열을 받아서 null이면 false로 리턴
     *
     * @param str
     * @return boolean
     */
    public static final boolean isNull(String str) {
        if (str == null || str.trim().equals("") || str.trim().equals("null"))
            return true;
        else
            return false;
    }
    
    /**
     * 문자열을 long형으로 변환
     * @param str
     * @return
     */
    public static final long parseLong( String str )
    {
        if(isNull(str)) return 0L;
        else{
            try{
                return Long.parseLong(str);
            }catch(NumberFormatException e){
                return 0L;
            }
        }
    }
    
    /**
     * 문자열을 long형으로 변환 (null인경우 dft 반환)
     * @param str
     * @return
     */
    public static final long parseLong( String str, long dft )
    {
        if(isNull(str)) return dft;
        else{
            try{
                return Long.parseLong(str);
            }catch(NumberFormatException e){
                return dft;
            }
        }
    }
    
    /**
     * 날짜 변환
     * @param pattern
     * @return
     */
    public static String currentFormatDate(String pattern) {
    	Calendar cal = Calendar.getInstance();
    	SimpleDateFormat formatter = new SimpleDateFormat(pattern);
    	return formatter.format(cal.getTime());
    }
    
    /**
     * 서버 아이피 얻기
     * @param pattern
     * @return
     */
    public static String getServerIp() {
    	String ip = "";
    	try {
			InetAddress Address = InetAddress.getLocalHost();
			ip = Address.getHostAddress();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		return ip;
    }
    
}
