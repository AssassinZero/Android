package com.rd.hnlf.common.binding;

import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;
import android.widget.Spinner;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/17 17:27
 * <p/>
 * Description:
 */
@InverseBindingMethods({
        // 定义 Spinner 的 android:selectedItemPosition 的双向绑定功能
        @InverseBindingMethod(type = Spinner.class, attribute = "android:selectedItemPosition"),
})
public class InverseBinding {
}