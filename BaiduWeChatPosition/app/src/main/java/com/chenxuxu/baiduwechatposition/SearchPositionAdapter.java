package com.chenxuxu.baiduwechatposition;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.search.sug.SuggestionResult;

import java.util.List;


/**
 * poi地点列表适配器(搜索页面)
 *
 * @author chenjunxu
 *
 */
public class SearchPositionAdapter extends BaseAdapter {
    private Context context;
    private List<SuggestionResult.SuggestionInfo> datas;
    /**
     * 选中的item下标
     */
    private int selectItemIndex;

    public SearchPositionAdapter(Context context, List<SuggestionResult.SuggestionInfo> datas) {
        this.datas = datas;
        this.context = context;
        // 默认第一个为选中项
        selectItemIndex = 0;
    }

    @Override
    public int getCount() {
        if (datas == null) {
            return 0;
        }
        return datas.size();
    }

    public void setSelectSearchItemIndex(int selectItemIndex) {
        this.selectItemIndex = selectItemIndex;
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LocatorSearchViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new LocatorSearchViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.location_item_poi, null, false);
            viewHolder.tv_poi_name = (TextView) convertView.findViewById(R.id.tv_poi_name);
            viewHolder.tv_poi_address = (TextView) convertView.findViewById(R.id.tv_poi_address);
            viewHolder.img_cur_point = (ImageView) convertView.findViewById(R.id.img_cur_point);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (LocatorSearchViewHolder) convertView.getTag();
        }

        if (datas != null && datas.size() > 0) {
            SuggestionResult.SuggestionInfo suggestionInfo = datas.get(position);
            viewHolder.tv_poi_address.setText(suggestionInfo.city + suggestionInfo.district);
            viewHolder.tv_poi_name.setText(suggestionInfo.key);

            if (selectItemIndex == position) {
                viewHolder.img_cur_point.setImageResource(R.drawable.position_is_select);
            } else {
                viewHolder.img_cur_point.setImageDrawable(null);
            }
        }
        return convertView;
    }

    private class LocatorSearchViewHolder {
        /**
         * 名称
         */
        private TextView tv_poi_name;
        /**
         * 地址
         */
        private TextView tv_poi_address;
        /**
         * 选中的按钮
         */
        private ImageView img_cur_point;
    }
}
