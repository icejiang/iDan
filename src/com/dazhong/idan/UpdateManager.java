package com.dazhong.idan;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

public class UpdateManager {
	// 应用程序Context
	private Context mContext;
	// 提示消息
	private String updateMsg = "有最新的软件包，请下载！";
	// 下载安装包的网络路径
	private String apkUrl = "http://www.dz-zc.com/dzapp.apk";
	private Dialog noticeDialog;// 提示有软件更新的对话框
	private Dialog downloadDialog;// 下载对话框
	private static final String savePath = "dzupdate/";// 保存apk的文件夹
	private static final String saveFileName = "dzUpdateRelease.apk";
	// 进度条与通知UI刷新的handler和msg常量
	private ProgressBar mProgress;
	private static final int DOWN_UPDATE = 1;
	private static final int DOWN_OVER = 2;
	private int progress;// 当前进度
	private Thread downLoadThread; // 下载线程
	private boolean interceptFlag = false;// 用户取消下载
	// 通知处理刷新界面的handler
	private Handler mHandler = new Handler() {
		@SuppressLint("HandlerLeak")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DOWN_UPDATE:
				mProgress.setProgress(progress);
				break;
			case DOWN_OVER:
				installApk();
				break;
			}
			super.handleMessage(msg);
		}
	};

	public UpdateManager(Context context) {
		this.mContext = context;
	}

	// 显示更新程序对话框，供主程序调用
	public void checkUpdateInfo() {
		showNoticeDialog();
	}

	private void showNoticeDialog() {
		android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(
				mContext);// Builder，可以通过此builder设置改变AleartDialog的默认的主题样式及属性相关信息
		builder.setTitle("软件版本更新");
		builder.setMessage(updateMsg);
		builder.setPositiveButton("下载", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();// 当取消对话框后进行操作一定的代码？取消对话框
				// downloadApk();
				showDownloadDialog();
			}
		});
		builder.setNegativeButton("以后再说", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		noticeDialog = builder.create();
		noticeDialog.show();
	}

	protected void showDownloadDialog() {
		android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(
				mContext);
		builder.setTitle("软件版本更新");
		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.progress, null);
		mProgress = (ProgressBar) v.findViewById(R.id.progress);
		builder.setView(v);// 设置对话框的内容为一个View
		builder.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				interceptFlag = true;
			}
		});
		downloadDialog = builder.create();
		downloadDialog.show();
		System.out.println("now download");
		downloadApk();
	}

	private void downloadApk() {
		System.out.println("begin download!");
		downLoadThread = new Thread(mdownApkRunnable);
		downLoadThread.start();
	}

	protected void installApk() {
		System.out.println("begin install");
		File apkfile = new File(saveFileName);
		System.out.println(apkfile.toString());
		if (!apkfile.exists()) {
			System.out.println("no file find");
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");// File.toString()会返回路径信息
		mContext.startActivity(i);
	}

	private Runnable mdownApkRunnable = new Runnable() {
		@Override
		public void run() {
			URL url;
			try {
				url = new URL(apkUrl);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.connect();
				int length = conn.getContentLength();
				System.out.println("length is "+length);
				InputStream ins = conn.getInputStream();
				File file = new File(savePath);
				if (!file.exists()) {
					file.mkdir();
				}
				String apkFile = saveFileName;
				File ApkFile = new File(apkFile);
				if (ApkFile.exists())
					ApkFile.delete();
//				FileOutputStream outStream = new FileOutputStream(ApkFile);
				FileOutputStream outStream =mContext.openFileOutput(saveFileName, Context.MODE_PRIVATE);
				
				System.out.println("write now");
				int count = 0;
				byte buf[] = new byte[1024];
				do {
					int numread = ins.read(buf);
					count += numread;
					System.out.println("count is "+count+",num is "+numread);
					progress = (int) (((float) count / length) * 100);
					System.out.println("progress is "+progress);
					// 下载进度
					mHandler.sendEmptyMessage(DOWN_UPDATE);
					if (numread <= 0) {
						// 下载完成通知安装
						mHandler.sendEmptyMessage(DOWN_OVER);
						break;
					}
					outStream.write(buf, 0, numread);
				} while (!interceptFlag);// 点击取消停止下载
				outStream.close();
				ins.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
}
