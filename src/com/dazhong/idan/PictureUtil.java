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

	
	// 七牛后台的key
//	 private static String AccessKey ="TH2TPSkdkFovEk-8M9GTXkUtNNCKSXXBSqyaI_Kb";
	private static String AccessKey = "OI-CyZOrXiYINs8pBFc2BRs29aQcJQG3XHHu7X5Y";
	// 七牛后台的secret
//	 private static String SecretKey ="CbswH01wNB6ax29-GxzziEi0QqoBqsBM3TPn1dWy";
	private static String SecretKey = "jeEylWPul9tVQbFGNLHgMX77YRls_EesQuIPpcqS";
	private static final String MAC_NAME = "HmacSHA1";
	private static final String ENCODING = "UTF-8";
	// unix时间戳:2065-12-31 00:00:00
	private static long delayTimes = 3029414400l;
//	 private String spaceName = "mytest";
	private String spaceName = "driverapp";
	private static final String rootDir = Environment.getExternalStorageDirectory() + File.separator + "zhongxing/";
	
	
	/**保存截图的方法*/
	public boolean saveScreen(View view,String noteId){
		//判断sdcard是否可用
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
     * 上传
     *
     * @param spaceName bucketName的名字
     * @param path   上传文件的路径地址
     * @param name   指定七牛服务上的文件名，或 null
     */
    public void uploadPic(final String spaceName, final String path, final String name) {
        try {
            // 1:第一种方式 构造上传策略
            JSONObject _json = new JSONObject();
            _json.put("deadline", delayTimes);// 有效时间为一个小时
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
                       	 Log.i("jxb", key + ",\r\n " + info + ",\r\n " + response+" 图片反馈");
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
     * 使用 HMAC-SHA1 签名方法对对encryptText进行签名
     *
     * @param encryptText 被签名的字符串
     * @param encryptKey  密钥
     * @return
     * @throws Exception
     */
    public byte[] HmacSHA1Encrypt(String encryptText, String encryptKey)
            throws Exception {
        byte[] data = encryptKey.getBytes(ENCODING);
        // 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
        SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
        // 生成一个指定 Mac 算法 的 Mac 对象
        Mac mac = Mac.getInstance(MAC_NAME);
        // 用给定密钥初始化 Mac 对象
        mac.init(secretKey);
        byte[] text = encryptText.getBytes(ENCODING);
        // 完成 Mac 操作
        return mac.doFinal(text);
    }
    
    /**
     * 通过key获取上传的资源文件的全路径
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
            //1:构造URL
            String encode = URLEncoder.encode(name, "UTF-8");
            sb.append(encode);
            //2:为url加上过期时间  unix时间
            sb.append("?e=" + delayTimes);//delayTimes = 1451491200
            //3:对1 2 操作后的url进行hmac-sha1签名 secrect
            String s = sb.toString();
            byte[] bytes = HmacSHA1Encrypt(s, SecretKey);
            String sign = UrlSafeBase64.encodeToString(bytes);
            //4:将accsesskey 连接起来
            sb.append("&token=" + AccessKey + ":" + sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
	}*/
	
}
