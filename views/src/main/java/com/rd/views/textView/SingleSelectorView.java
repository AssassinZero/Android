package com.rd.views.textView;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;
import android.graphics.Color;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.rd.tools.utils.AndroidUtil;

import java.util.List;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/29 16:47
 * <p/>
 * Description: 单项选择器
 */
@InverseBindingMethods({
        @InverseBindingMethod(type = SingleSelectorView.class, attribute = "key"),
})
public class SingleSelectorView extends AppCompatEditText {
    /** key 列表 */
    private List<String> keyList;
    /** value 列表 */
    private List<String> valueList;
    /** 字体颜色 */
    private int buttonColor = Color.BLUE;
    /** 当前选中项 */
    private int selected    = -1;
    /** 键 */
    private String         key;
    /** key 改动监听 */
    private OnValueChanged onValChanged;
    /** 是否需要新建一个弹出窗 */
    private boolean        isNeedRebuild;
    /** 初始化是否完成 */
    private boolean        initCompleted;

    public SingleSelectorView(Context context) {
        super(context);
        init(context);
    }

    public SingleSelectorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SingleSelectorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化
     */
    private void init(Context context) {
        // 单击触发onclick事件
        setFocusable(false);
        // 设置背景颜色
        setBackgroundColor(Color.TRANSPARENT);
        // 设置点击弹出选择框
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null == valueList || null == keyList || valueList.isEmpty() || keyList.size() < valueList.size()) {
                    return;
                }
                // 关闭输入法弹出窗
                AndroidUtil.closedInputMethod();
                // 选择框
                OptionsPickerView pickerView = (OptionsPickerView) view.getTag();
                if (null == pickerView || isNeedRebuild) {
                    Context context = AndroidUtil.getActivity(view);
                    pickerView = new OptionsPickerView.Builder(context, new OptionsPickerView.OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int options2, int options3, View view) {
                            selected = options1;
                            setValue(options1);
                        }
                    })
                            .setSubmitColor(buttonColor)
                            .setCancelColor(buttonColor)
                            .setSelectOptions(getPosition())
                            .build();
                    pickerView.setPicker(valueList);
                    view.setTag(pickerView);
                }
                pickerView.show();
            }
        });
    }

    /**
     * 选择项初始化
     *
     * @param showDefault
     *         是否默认选择第一项
     */
    public void initialize(boolean showDefault) {
        initCompleted = false;
        if (showDefault) {
            setValue(getPosition());
        }
    }

    public void setKeyList(List<String> keyList) {
        this.keyList = keyList;
    }

    public void setValueList(List<String> valueList) {
        this.valueList = valueList;
        isNeedRebuild = true;
    }

    public void setButtonColor(int buttonColor) {
        this.buttonColor = buttonColor;
    }

    /**
     * 设置选中项的 key 和 value
     */
    private void setValue(int position) {
        if (null != keyList && keyList.size() > position) {
            setKey(keyList.get(position));
        }
    }

    /**
     * 获取当前 key 的 position
     */
    private int getPosition() {
        if (null == keyList || keyList.isEmpty() || TextUtils.isEmpty(key)) {
            return 0;
        }

        int i = 0;
        for (String s : keyList) {
            if (s.equals(key)) {
                return i;
            }
            i++;
        }
        return 0;
    }

    public void setOnValChanged(OnValueChanged listener) {
        this.onValChanged = listener;
    }

    /**
     * 双向绑定 getter
     */
    public String getKey() {
        return key;
    }

    /**
     * 双向绑定 setter
     */
    public void setKey(String key) {
        if (!TextUtils.isEmpty(key) && (!key.equals(this.key) || !initCompleted)) {
            this.key = key;
            // 设置显示文字，如果 selected == -1 则表示是外部代码调用此方法
            //               如果 selected != -1 则表示是本类代码调用此方法
            int position = selected == -1 ? getPosition() : selected;
            if (null != valueList && valueList.size() > position) {
                setText(valueList.get(position));
            }
            selected = -1;
            initCompleted = true;
            if (null != this.onValChanged) {
                this.onValChanged.onValChanged(this, key);
            }
        }
    }

    @BindingAdapter("keyAttrChanged")
    public static void setKeyListener(SingleSelectorView view, final InverseBindingListener keyChange) {
        if (null == keyChange) {
            view.setOnValChanged(null);
        } else {
            view.setOnValChanged(new OnValueChanged() {
                @Override
                public void onValChanged(SingleSelectorView view, String key) {
                    keyChange.onChange();
                }
            });
        }
    }

    public interface OnValueChanged {
        void onValChanged(SingleSelectorView view, String key);
    }
}