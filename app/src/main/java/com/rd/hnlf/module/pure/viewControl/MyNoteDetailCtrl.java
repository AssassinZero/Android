package com.rd.hnlf.module.pure.viewControl;

import android.app.Activity;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.rd.hnlf.R;
import com.rd.hnlf.module.eCommerce.dataModel.submit.NoteDetailBean;
import com.rd.hnlf.module.eCommerce.dataModel.submit.NoteDetailRec;
import com.rd.hnlf.module.pure.ui.activity.MyNoteDetailAct;
import com.rd.hnlf.module.pure.viewModel.MyNoteDetailVM;
import com.rd.hnlf.module.pure.viewModel.bean.NegativeInfo;
import com.rd.hnlf.network.RDClient;
import com.rd.hnlf.network.RequestCallBack;
import com.rd.hnlf.network.api.PureService;
import com.rd.network.entity.HttpResult;
import com.rd.tools.utils.RandomUtil;

import java.util.ArrayList;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/22 16:49
 * <p/>
 * Description: {@link MyNoteDetailAct}
 */
public class MyNoteDetailCtrl {
    private MyNoteDetailVM viewModel;
    /** 是否信息隐藏显示 */
    private boolean        hidden;
    private Activity activity;
    private WebView web_view;

    public MyNoteDetailCtrl(String orderNo, boolean hidden,Activity activity) {
        this.hidden = hidden;
        this.activity = activity;
        viewModel = new MyNoteDetailVM();
        initView();
        reqData(orderNo);
    }

    private void initView() {
        web_view = (WebView) activity.findViewById(R.id.my_dote_detail_web_view);
        WebSettings ws = web_view.getSettings();
        ws.setSupportZoom(true);
        ws.setJavaScriptEnabled(true);
        ws.setJavaScriptCanOpenWindowsAutomatically(true);
        ws.setBuiltInZoomControls(true);//support zoom
        //webSettings.setPluginsEnabled(true);//support flash
        ws.setUseWideViewPort(true);// 这个很关键
        ws.setLoadWithOverviewMode(true);

        web_view.setWebViewClient(new WebViewClient());
        web_view.setWebChromeClient(new WebChromeClient());
        web_view.addJavascriptInterface(new AndroidtoJs(),"test");
        web_view.loadUrl("file:///android_asset/index.html");


    }
    // 继承自Object类
    public class AndroidtoJs extends Object {

        // 定义JS需要调用的方法
        // 被JS调用的方法必须加入@JavascriptInterface注解
        @JavascriptInterface
        public String dateToHtml() {
           return "AndroidDateToHtml";
        }
    }

    /**
     * 网络请求
     */
    private void reqData(String orderNo) {
        Call<HttpResult<NoteDetailBean>> call = RDClient.getService(PureService.class).getNoteDetails(orderNo);
        call.enqueue(new RequestCallBack<HttpResult<NoteDetailBean>>() {
            @Override
            public void onSuccess(Call<HttpResult<NoteDetailBean>> call, Response<HttpResult<NoteDetailBean>> response) {

                NoteDetailBean bean = response.body().getData();

                converter(bean);
            }
        });

    }


    /**
     * 数据类型转换
     */
    private void converter(NoteDetailBean rec) {
        // 基本信息
        viewModel.getBasicInfo().setId(rec.getBillNo());
        viewModel.getBasicInfo().setAmount(rec.getBillAmount());
        viewModel.getBasicInfo().setDate(rec.getBillIssueDate());
        viewModel.getBasicInfo().setDueDate(rec.getBillDueDate());
        // 出票人信息
        viewModel.getDrawerInfo().setFullName(rec.getIssueName());
        viewModel.getDrawerInfo().setAccountName(rec.getIssueBankAccount());
        viewModel.getDrawerInfo().setBranchName(rec.getIssueOpenBank());
        // 收款人信息
        viewModel.getPayeeInfo().setFullName(rec.getPayeeName());
        viewModel.getPayeeInfo().setAccountName(rec.getPayeeBankAccount());
        viewModel.getPayeeInfo().setBranchName(rec.getPayeeOpenBank());
        // 承兑人信息
        viewModel.getAcceptorInfo().setFullName(rec.getAcceptorName());
        viewModel.getAcceptorInfo().setAccountName(rec.getAcceptorBankAccount());
        viewModel.getAcceptorInfo().setBranchName(rec.getAcceptorBankName());
        viewModel.getAcceptorInfo().setBranchNo(rec.getAcceptorBankCode());
        // 票据背面信息
        ArrayList<NegativeInfo> negativeInfo = new ArrayList<>();

        NegativeInfo info1 = new NegativeInfo();
        info1.setEndorser(rec.getIndorserName());
        info1.setEndorsee(rec.getEndorseeName());
        info1.setDate(rec.getEndorsementDate());
        if ("Y".equals(rec.getNotNegotiable())){
            info1.setTransferable("是");
        }else {
            info1.setTransferable("否");
        }

//        info1.setEndorser("信息隐藏");
//        info1.setEndorsee("信息隐藏");

//        NegativeInfo info2 = new NegativeInfo();
//        info2.setEndorser(rec.getIndorserName());
//        info2.setEndorsee(rec.getEndorseeName());
//        info2.setDate(rec.getEndorsementDate());
//        info2.setTransferable(rec.getNotNegotiable());
//        info2.setEndorser("信息隐藏");
//        info2.setEndorsee("信息隐藏");

        negativeInfo.add(info1);
//        negativeInfo.add(info2);
        viewModel.setNegativeInfo(negativeInfo);
        if (hidden) {
//            viewModel.getDrawerInfo().setFullName("信息隐藏");
//            viewModel.getPayeeInfo().setFullName("信息隐藏");
//            viewModel.getAcceptorInfo().setFullName("信息隐藏");
        }
    }

    public MyNoteDetailVM getViewModel() {
        return viewModel;
    }
}