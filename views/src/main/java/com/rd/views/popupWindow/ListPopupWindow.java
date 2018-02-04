package com.rd.views.popupWindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.rd.tools.utils.ContextHolder;
import com.rd.views.R;

import java.util.List;

/**
 * Author: TaoHao
 * E-mail: taoh@erongdu.com
 * Date: 2017/02/09$ 10:05$
 * <p/>
 * Description: 列表选择窗口
 */
public class ListPopupWindow extends PopupWindow {
    private ListView     listView;
    private TextView     viewProtocol;
    private List<String> listData;
    private Context      context;

    public ListPopupWindow(List<String> listData) {
        this.context = ContextHolder.getContext();
        this.listData = listData;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View           mView    = inflater.inflate(R.layout.list_popup_window, null);

        listView = (ListView) mView.findViewById(R.id.list);
        viewProtocol = (TextView) mView.findViewById(R.id.view_protocol);

        mView.findViewById(R.id.cancelBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        listView.setAdapter(new ListAdapter());

        // 设置PopupWindow的View
        this.setContentView(mView);
        // 设置PopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        // 设置PopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置PickPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupAnimation);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x80000000);
        // 设置PopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = viewProtocol.getTop();
                int y      = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    class ListAdapter extends BaseAdapter {
        public ListAdapter() {
        }

        @Override
        public int getCount() {
            return listData.size();
        }

        @Override
        public Object getItem(int position) {
            return listData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.list_popup_window_item, null);
                viewHolder.textView = (TextView) convertView.findViewById(R.id.textView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.textView.setText(listData.get(position));
            return convertView;
        }

        private class ViewHolder {
            private TextView textView;
        }
    }

    public ListView getListView() {
        return listView;
    }
}