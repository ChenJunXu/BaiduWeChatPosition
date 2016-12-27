package com.chenxuxu.baiduwechatposition;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;

import java.util.ArrayList;
import java.util.List;


/**
 * 用百度地图仿微信定位(搜索页面)
 *
 * @author chenjunxu
 * @date 16/12/9
 */
public class SearchPositionActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private Context mContext;
    /**
     * 附近地点列表
     */
    private ListView lv_locator_search_position;
    /**
     * 列表适配器
     */
    private SearchPositionAdapter locatorAdapter;
    /**
     * 列表数据
     */
    private List<SuggestionResult.SuggestionInfo> datas;
    /**
     * 进度条
     */
    private ProgressBar pb_location_search_load_bar;
    /**
     * 建议查询
     */
    private SuggestionSearch mSuggestionSearch;
    /**
     * 输入框
     */
    private EditText et_search;
    /**
     * 返回
     */
    private ImageView img_search_back;
    /**
     * 发送
     */
    private TextView tv_search_send;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_position);
        initUI();
    }

    /**
     * 初始化Ui
     */
    private void initUI() {
        mContext = this;
        lv_locator_search_position = (ListView) findViewById(R.id.lv_locator_search_position);
        img_search_back = (ImageView) findViewById(R.id.img_search_back);
        tv_search_send = (TextView) findViewById(R.id.tv_search_send);
        pb_location_search_load_bar = (ProgressBar) findViewById(R.id.pb_location_search_load_bar);
        et_search = (EditText) findViewById(R.id.et_search);
        // 建议查询
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(mSuggestionResultListener);

        // 列表初始化
        datas = new ArrayList();
        locatorAdapter = new SearchPositionAdapter(this, datas);
        lv_locator_search_position.setAdapter(locatorAdapter);

        // 注册监听
        lv_locator_search_position.setOnItemClickListener(this);
        img_search_back.setOnClickListener(this);
        tv_search_send.setOnClickListener(this);
    }

    /**
     * 获取搜索的内容
     */
    OnGetSuggestionResultListener mSuggestionResultListener = new OnGetSuggestionResultListener() {
        public void onGetSuggestionResult(SuggestionResult res) {
            pb_location_search_load_bar.setVisibility(View.GONE);
            if (res == null || res.getAllSuggestions() == null) {
                Toast.makeText(mContext, "没找到结果", Toast.LENGTH_LONG).show();
                return;
            }
            //获取在线建议检索结果
            if (datas != null) {
                datas.clear();
                for (SuggestionResult.SuggestionInfo suggestionInfos : res.getAllSuggestions()) {
                    datas.add(suggestionInfos);
                }
                locatorAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSuggestionSearch.destroy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        locatorAdapter.setSelectSearchItemIndex(position);
        locatorAdapter.notifyDataSetChanged();

        Intent intent = new Intent();
        // 设置坐标
        intent.putExtra("LatLng", datas.get(position).pt);
        setResult(RESULT_OK, intent);
        SearchPositionActivity.this.finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_search_back:
                SearchPositionActivity.this.finish();
                break;
            case R.id.tv_search_send:
                if (!TextUtils.isEmpty(et_search.getText().toString())) {
                    pb_location_search_load_bar.setVisibility(View.VISIBLE);
                    // 根据输入框的内容，进行搜索
                    mSuggestionSearch.requestSuggestion(new SuggestionSearchOption().keyword(et_search.getText().toString()).city(""));
                } else {
                    Toast.makeText(mContext, "请输入地点", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}