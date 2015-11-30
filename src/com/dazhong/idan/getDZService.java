package com.dazhong.idan;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;

import android.os.StrictMode;
import android.util.Xml;

public class getDZService {
	public static String getAddress(String mobile, String sCode)
			throws Exception {

		String soap = readSoap(sCode);
		System.out.println(soap);
		soap = soap.replaceAll("\\$mobile", mobile);

		byte[] entity = soap.getBytes("utf-8");

		String path = "http://www.dzzcgs.com:8088/OrderService.asmx";
		HttpURLConnection conn = (HttpURLConnection) new URL(path)
				.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type",
				"application/soap+xml;charset=utf-8");
		conn.setRequestProperty("Content-Length", String.valueOf(entity.length));
		conn.getOutputStream().write(entity);
		if (conn.getResponseCode() == 200) {
			return parseSoap(conn.getInputStream(), sCode);
		}
		return null;
	}

	public static String getAddress(String sCode) throws Exception {
		String soap = readSoap(sCode);
		byte[] entity = soap.getBytes("utf-8");
		String path = MainActivity.SERVICEADRRESS;
		HttpURLConnection conn = (HttpURLConnection) new URL(path)
				.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type",
				"application/soap+xml;charset=utf-8");
		conn.setRequestProperty("Content-Length", String.valueOf(entity.length));
		conn.getOutputStream().write(entity);
		if (conn.getResponseCode() == 200) {
			return parseSoap(conn.getInputStream(), sCode);
		}
		return null;
	}

	public static String parseXML(InputStream responseStream, String strresult)
			throws Exception {

		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(responseStream, "UTF-8");
		int event = parser.getEventType();
		while (event != XmlPullParser.END_DOCUMENT) {
			switch (event) {
			case XmlPullParser.START_TAG:
				if (strresult.equals(parser.getName())) {
					return parser.nextText();
				}
				break;
			}
			event = parser.next();
		}
		return null;
	}

	private static String parseSoap(InputStream xml, String scode)
			throws Exception {
		String sEnd = scode + "Result";
		XmlPullParser pullParser = Xml.newPullParser();
		pullParser.setInput(xml, "UTF-8");
		int event = pullParser.getEventType();
		while (event != XmlPullParser.END_DOCUMENT) {
			switch (event) {
			case XmlPullParser.START_TAG:
				if (sEnd.equals(pullParser.getName())) {
					return pullParser.nextText();
				}
				break;
			}
			event = pullParser.next();
		}
		return null;
	}

	private static String readSoap(String scode) throws Exception {
		InputStream inStream = null;
		String sPath = scode + ".xml";
		inStream = getDZService.class.getClassLoader().getResourceAsStream(sPath);
		byte[] data = read(inStream);
		return new String(data);

	}

	public static byte[] read(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		return outStream.toByteArray();
	}

	public static byte[] toByteArray(InputStream input) throws Exception {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
		}
		return output.toByteArray();
	}
}