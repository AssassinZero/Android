package com.rd.hnlf.module.common.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.rd.basic.BaseActivity;
import com.rd.hnlf.R;
import com.rd.hnlf.common.BundleKeys;
import com.rd.hnlf.databinding.CommonContentPreviewActBinding;
import com.rd.hnlf.router.RouterExtras;
import com.rd.hnlf.router.RouterUrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/10/24 17:09
 * <p/>
 * Description: 内容预览
 */
@Route(path = RouterUrl.CONTENT_PREVIEW, extras = RouterExtras.EXTRA_COMMON)
public class ContentPreviewAct extends BaseActivity {
    @Autowired(name = BundleKeys.TITLE)
    String title;
    @Autowired(name = BundleKeys.CONTENT)
    String content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonContentPreviewActBinding binding = DataBindingUtil.setContentView(this, R.layout.common_content_preview_act);
        binding.toolBar.setTitle(title);
        binding.setContent(content);
    }
}