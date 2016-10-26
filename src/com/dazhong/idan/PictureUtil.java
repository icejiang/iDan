package com.dazhong.idan;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.utils.UrlSafeBase64;

public class PictureUtil {

	
	// ��ţ��̨��key
//	 private static String AccessKey ="TH2TPSkdkFovEk-8M9GTXkUtNNCKSXXBSqyaI_Kb";
	private static String AccessKey = "OI-CyZOrXiYINs8pBFc2BRs29aQcJQG3XHHu7X5Y";
	// ��ţ��̨��secret
//	 private static String SecretKey ="CbswH01wNB6ax29-GxzziEi0QqoBqsBM3TPn1dWy";
	private static String SecretKey = "jeEylWPul9tVQbFGNLHgMX77YRls_EesQuIPpcqS";
	private static final String MAC_NAME = "HmacSHA1";
	private static final String ENCODING = "UTF-8";
	// unixʱ���:2065-12-31 00:00:00
	private static long delayTimes = 3029414400l;
//	 private String spaceName = "mytest";
	private String spaceName = "driverapp";
	private static final String rootDir = Environment.getExternalStorageDirectory() + File.separator + "zhongxing/";
	
	
	/**�����ͼ�ķ���*/
	public boolean saveScreen(View view,String noteId){
		//�ж�sdcard�Ƿ����
		if(!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
			return false;
		}
		File file  = new File(rootDir);
		if(!file.exists()){
			file.mkdir();
		}
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();
		try {
			bitmap.compress(CompressFormat.JPEG, 100, new FileOutputStream(new File(rootDir + noteId + ".jpg")));
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}finally{
			view.setDrawingCacheEnabled(false);
			bitmap = null;
		}
	}
	
	
	/**
     * �ϴ�
     *
     * @param spaceName bucketName������
     * @param path   �ϴ��ļ���·����ַ
     * @param name   ָ����ţ�����ϵ��ļ������� null
     */
    public void uploadPic(final String spaceName, final String path, final String name) {
        try {
            // 1:��һ�ַ�ʽ �����ϴ�����
            JSONObject _json = new JSONObject();
            _json.put("deadline", delayTimes);// ��Чʱ��Ϊһ��Сʱ
            _json.put("scope", spaceName);
            String _encodedPutPolicy = UrlSafeBase64.encodeToString(_json
                    .toString().getBytes());
            byte[] _sign = HmacSHA1Encrypt(_encodedPutPolicy, SecretKey);
            String _encodedSign = UrlSafeBase64.encodeToString(_sign);
            final String _uploadToken = AccessKey + ':' + _encodedSign + ':'
                    + _encodedPutPolicy;
            UploadManager uploadManager = new UploadManager();
            uploadManager.put(path, name, _uploadToken,
                    new UpCompletionHandler() {
                        @Override
                        public void complete(String key, ResponseInfo info,
                                             JSONObject response) {
                       	 Log.i("jxb", key + ",\r\n " + info + ",\r\n " + response+" ͼƬ����");
                       	 if (null != response){
                       		 File file = new File(path);
                       		 file.delete();
                       	 }
                        }

						
                    }, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * ʹ�� HMAC-SHA1 ǩ�������Զ�encryptText����ǩ��
     *
     * @param encryptText ��ǩ�����ַ���
     * @param encryptKey  ��Կ
     * @return
     * @throws Exception
     */
    public byte[] HmacSHA1Encrypt(String encryptText, String encryptKey)
            throws Exception {
        byte[] data = encryptKey.getBytes(ENCODING);
        // ���ݸ������ֽ����鹹��һ����Կ,�ڶ�����ָ��һ����Կ�㷨������
        SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
        // ����һ��ָ�� Mac �㷨 �� Mac ����
        Mac mac = Mac.getInstance(MAC_NAME);
        // �ø�����Կ��ʼ�� Mac ����
        mac.init(secretKey);
        byte[] text = encryptText.getBytes(ENCODING);
        // ��� Mac ����
        return mac.doFinal(text);
    }
    
    /**
     * ͨ��key��ȡ�ϴ�����Դ�ļ���ȫ·��
     *
     * @param name
     * @param spaceName
     * @return
     */
    /*public static String getFileUrl(String spaceName, String name) {
        String url = HdUtils.transDomai2Zone(spaceName);
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        try {
            //1:����URL
            String encode = URLEncoder.encode(name, "UTF-8");
            sb.append(encode);
            //2:Ϊurl���Ϲ���ʱ��  unixʱ��
            sb.append("?e=" + delayTimes);//delayTimes = 1451491200
            //3:��1 2 �������url����hmac-sha1ǩ�� secrect
            String s = sb.toString();
            byte[] bytes = HmacSHA1Encrypt(s, SecretKey);
            String sign = UrlSafeBase64.encodeToString(bytes);
            //4:��accsesskey ��������
            sb.append("&token=" + AccessKey + ":" + sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
	}*/
	
}
