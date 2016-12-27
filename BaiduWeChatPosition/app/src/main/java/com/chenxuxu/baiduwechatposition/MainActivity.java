package com.chenxuxu.baiduwechatposition;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 百度地图仿微信定位
 *
 * @author chenjunxu
 * @date 16/12/23
 */
public class MainActivity extends Activity implements View.OnClickListener {
    /**
     * 显示位置
     */
    private TextView tv_show_position;
    /**
     * 点击获取位置
     */
    private Button btn_choose_position;
    /**
     * 请求码
     */
    private final static int REQUEST_CODE = 0x123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    /**
     * 初始化
     */
    private void initUI() {
        tv_show_position = (TextView) findViewById(R.id.tv_show_position);
        btn_choose_position = (Button) findViewById(R.id.btn_choose_position);

        // 注册监听
        btn_choose_position.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // 跳转页面
        Intent intent = new Intent(MainActivity.this, LocationActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 返回成功
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE && data != null) {
            String position = data.getStringExtra("position");
            tv_show_position.setText(position);
        }
    }
}
