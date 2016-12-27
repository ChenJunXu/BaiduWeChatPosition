package com.chenxuxu.baiduwechatposition;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;


/**
 * 仿微信欢迎页面，在此处处理android6.0权限
 *
 * @author chenjunxu
 * @date 16/12/26
 */
public class WelcomeActivity extends Activity {
    /**
     * 需要android6.0以上处理的权限
     */
    private String[] PERMISSIONS = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        // android系统大于等于6.0时需要处理时权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestNeedPermission();
        } else {
            goToMain();
        }
    }

    /**
     * 进入首页面
     */
    private void goToMain() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                WelcomeActivity.this.finish();
            }
        }, 1200);
    }

    /**
     * 判断是否需要进行权限的请求
     */
    private void requestNeedPermission() {
        boolean temp = false;
        for (String permission : PERMISSIONS) {
            if (!hasPermissionGranted(permission)) {
                temp = true;
                break;
            }
        }
        if (temp) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, 0);
        } else {
            goToMain();
        }
    }

    /**
     * 判断该权限是否已经授权
     *
     * @param permission
     * @return
     */
    private boolean hasPermissionGranted(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            boolean temp = false;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(WelcomeActivity.this, "拒绝" + grantResults[i] + "权限会导致定位失败！", Toast.LENGTH_LONG).show();
                    temp = true;
                    break;
                }
            }

            if (temp) {
                requestNeedPermission();
            } else {
                goToMain();
            }
        }
    }
}
