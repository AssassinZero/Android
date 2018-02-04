package com.rd.hnlf.module.common.viewControl;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.WebSettings;

import com.rd.hnlf.common.BaseParams;
import com.rd.hnlf.module.common.ui.WebViewAct;
import com.rd.tools.log.Logger;
import com.rd.views.appbar.ToolBar;

import im.delight.android.webview.AdvancedWebView;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/4/6 11:13
 * <p/>
 * Description: {@link WebViewAct}
 */
public class WebViewCtrl implements AdvancedWebView.Listener {
    private ToolBar         toolBar;
    private AdvancedWebView webView;

    public WebViewCtrl(ToolBar toolBar, AdvancedWebView webView, String url, String postData) {
        this.toolBar = toolBar;
        this.webView = webView;
        webView.addPermittedHostname(Uri.parse(BaseParams.URI).getHost());

        if (!TextUtils.isEmpty(url) && TextUtils.isEmpty(postData)) {
            webView.loadUrl(url);
        }
        if (TextUtils.isEmpty(url) && !TextUtils.isEmpty(postData)) {
            webView.loadHtml(postData);
        }

        WebSettings setting = webView.getSettings();
        // 页面缩放
        setting.setSupportZoom(true);
        setting.setBuiltInZoomControls(true);
        setting.setDisplayZoomControls(false);
        // 适应分辨率
        setting.setUseWideViewPort(true);
        setting.setLoadWithOverviewMode(true);
        // 设置UserAgent
        setting.setUserAgentString("Android");
    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) {
        Logger.i("onPageStarted url = " + url);
    }

    @Override
    public void onPageFinished(String url) {
        toolBar.setTitle(webView.getTitle());
    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {
        Logger.i("onPageError errorCode = " + errorCode);
    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {
        Logger.i("onDownloadRequested url = " + url);
    }

    @Override
    public void onExternalPageRequest(String url) {
        if (url.startsWith("tel:") || url.startsWith("sms:") || url.startsWith("mail:")) {
            webView.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        }
        Logger.i("onExternalPageRequest url = " + url);
    }
}