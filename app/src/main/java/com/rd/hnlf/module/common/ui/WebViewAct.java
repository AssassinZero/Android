package com.rd.hnlf.module.common.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.rd.basic.BaseActivity;
import com.rd.hnlf.R;
import com.rd.hnlf.common.BundleKeys;
import com.rd.hnlf.databinding.CommonWebViewActBinding;
import com.rd.hnlf.module.common.viewControl.WebViewCtrl;
import com.rd.hnlf.router.RouterExtras;
import com.rd.hnlf.router.RouterUrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/16 17:52
 * <p/>
 * Description:
 */
@Route(path = RouterUrl.WEB_VIEW, extras = RouterExtras.EXTRA_COMMON)
public class WebViewAct extends BaseActivity {
    private CommonWebViewActBinding binding;
    @Autowired(name = BundleKeys.URL)
    String url;
    @Autowired(name = BundleKeys.POST_DATA)
    String postData;
    @Autowired(name = BundleKeys.TITLE)
    String title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.common_web_view_act);
        binding.webView.setListener(this, new WebViewCtrl(binding.toolBar, binding.webView, url, postData));
        binding.toolBar.setTitle(title);
        binding.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                binding.progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    binding.progressBar.setVisibility(View.GONE);
                } else {
                    binding.progressBar.setVisibility(View.VISIBLE);
                }
                super.onProgressChanged(view, newProgress);
            }

            // @Override
            // public void onReceivedTitle(WebView view, String title) {
            //     super.onReceivedTitle(view, title);
            //     binding.toolBar.setTitle(title);
            // }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.webView.onResume();
    }

    @Override
    protected void onPause() {
        binding.webView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        binding.webView.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        binding.webView.onActivityResult(requestCode, resultCode, intent);
    }

    @Override
    public void onBackPressed() {
        if (!binding.webView.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }
}