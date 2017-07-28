package com.zbar;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.blankj.utilcode.utils.ToastUtils;
import com.monkeyshen.appframe.appframelibrary.R;
import com.monkeyshen.appframe.appframelibrary.base.activity.BaseActivity;
import com.zbar.camera.CameraPreview;
import com.zbar.camera.ScanCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * 这个activity打开相机，在后台线程做常规的扫描；它绘制了一个结果view来帮助正确地显示条形码，在扫描的时候显示反馈信息，
 * 然后在扫描成功的时候覆盖扫描结果
 * 
 */
public final class CaptureActivity extends BaseActivity {
	private RelativeLayout mScanCropView;
	private ImageView mScanLine;
	private ValueAnimator mScanAnimator;

	private CameraPreview mPreviewView;
	private String mResultString;
	public static void toActivity(Context context){
		Intent intent = new Intent(context,CaptureActivity.class);
		context.startActivity(intent);
	}
	/**
	 * OnCreate中初始化一些辅助类，如InactivityTimer（休眠）、Beep（声音）以及AmbientLight（闪光灯）
	 */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		// 保持Activity处于唤醒状态
		Window window = getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setToolbarTitle("扫一扫");
		mPreviewView = (CameraPreview) findViewById(R.id.capture_preview);
		mScanCropView = (RelativeLayout) findViewById(R.id.capture_crop_view);
		mScanLine = (ImageView) findViewById(R.id.capture_scan_line);

		mPreviewView.setScanCallback(resultCallback);
		startScanUnKnowPermission();

	}

	@Override
	public void initView() {

	}

	@Override
	public int getContentViewResource() {
		return R.layout.activity_qr_scan;
	}

	/**
	 * Stop scan.
	 */
	private void stopScan() {
		mScanAnimator.cancel();
		mPreviewView.stop();
	}

	/**
	 * There is a camera when the direct scan.
	 */
	private void startScanWithPermission() {
		if (mPreviewView.start()) {
			if(mScanAnimator != null){
				mScanAnimator.start();
			}

		} else {
			ToastUtils.showShortToast("打开相机失败或没有权限");
			finish();
			return;
//			new AlertDialog.Builder(this)
//					.setTitle("相机连接失败")
//					.setMessage("您的相机可能被其它应用占用，请检查后再使用")
//					.setCancelable(false)
//					.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							finish();
//						}
//					})
//					.show();
		}
	}

	private static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 110;
	private static final int REQUEST_CAMERA = 100;
	private void startScanUnKnowPermission() {

		if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
				!= PackageManager.PERMISSION_GRANTED) {
			ToastUtils.showShortToast("打开相机失败或没有权限");
			finish();
			return;
//			requestPermission(Manifest.permission.CAMERA,
//					"相机权限",
//					REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);
		}else{
			startScanWithPermission();
		}
    }

	private void requestPermission(final String permission, String rationale, final int requestCode) {
//		if (shouldShowRequestPermissionRationale(permission)) {
//			new AlertDialog.Builder(mContext)
//					.setTitle(R.string.mis_permission_dialog_title)
//					.setMessage(rationale)
//					.setPositiveButton(R.string.mis_permission_dialog_ok, new DialogInterface.OnClickListener() {
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							requestPermissions(new String[]{permission}, requestCode);
//						}
//					})
//					.setNegativeButton(R.string.mis_permission_dialog_cancel, null)
//					.create().show();
//		} else {
//			requestPermissions(new String[]{permission}, requestCode);
//		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if (requestCode == REQUEST_STORAGE_WRITE_ACCESS_PERMISSION) {
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				startScanWithPermission();
			}
		} else {
			super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		}
	}
	@Override
	protected void onResume() {
		super.onResume();
		if (mScanAnimator != null) {
            startScanUnKnowPermission();
		}
	}

	@Override
	public void onPause() {
		// Must be called here, otherwise the camera should not be released properly.
		stopScan();
		super.onPause();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	/**
	 * 扫描成功，处理反馈信息
	 */
	 private ScanCallback resultCallback = new ScanCallback() {
		@Override
		public void onScanResult(String result) {
			stopScan();
			if(!TextUtils.isEmpty(result)){
				mResultString = result;

			}else{
				startScanWithPermission();
				Toast.makeText(CaptureActivity.this, "Scan failed!", Toast.LENGTH_SHORT).show();
			}
		}
	};

	/**
	 * url 解析参数
	 * @param url
	 * @return
	 */
	public static Map<String, String> urlToMap(String url) {
		Map<String, String>map = null;
		if (url != null && url.indexOf("&") > -1 && url.indexOf("=") > -1) {
			map = new HashMap<String, String>();
			String[] arrTemp = url.split("&");
			for (String str : arrTemp) {
				String[] qs = str.split("=");
				map.put(qs[0], qs[1]);
			}
		}
		return map;
	}


	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (mScanAnimator == null) {
			int height = mScanCropView.getMeasuredHeight() - 25;
			mScanAnimator = ObjectAnimator.ofFloat(mScanLine, "translationY", 0F, height).setDuration(3000);
			mScanAnimator.setInterpolator(new LinearInterpolator());
			mScanAnimator.setRepeatCount(ValueAnimator.INFINITE);
			mScanAnimator.setRepeatMode(ValueAnimator.REVERSE);
            startScanUnKnowPermission();
		}
	}

}
