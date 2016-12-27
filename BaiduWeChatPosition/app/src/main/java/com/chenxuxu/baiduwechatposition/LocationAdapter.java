package com.chenxuxu.baiduwechatposition;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;


/**
 * 地点列表适配器
 *
 * @author chenjunxu
 * @date 16/12/23
 */
public class LocationAdapter extends BaseAdapter {
    private Context context;
    private List<PoiInfo> datas;
    /**
     * 选中的item下标
     */
    private int selectItemIndex;

    public LocationAdapter(Context context, List<PoiInfo> datas) {
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

    public void setSelectItemIndex(int selectItemIndex) {
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
        LocatorViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new LocatorViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.location_item_poi, null, false);
            viewHolder.tv_poi_name = (TextView) convertView.findViewById(R.id.tv_poi_name);
            viewHolder.tv_poi_address = (TextView) convertView.findViewById(R.id.tv_poi_address);
            viewHolder.img_cur_point = (ImageView) convertView.findViewById(R.id.img_cur_point);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (LocatorViewHolder) convertView.getTag();
        }

        if (datas != null && datas.size() > 0) {
            PoiInfo poiInfo = datas.get(position);
            viewHolder.tv_poi_address.setText(poiInfo.address);
            viewHolder.tv_poi_name.setText(poiInfo.name);

            if (selectItemIndex == position) {
                viewHolder.img_cur_point.setImageResource(R.drawable.position_is_select);
            } else {
                viewHolder.img_cur_point.setImageDrawable(null);
            }
        }
        return convertView;
    }

    private class LocatorViewHolder {
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
