package com.rd.hnlf.module.eCommerce.viewControl;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rd.basic.BaseActivity;
import com.rd.hnlf.R;
import com.rd.hnlf.common.BundleKeys;
import com.rd.hnlf.module.eCommerce.dataModel.submit.NoteDetailRec;
import com.rd.hnlf.module.eCommerce.ui.activity.NoteDetailAct;
import com.rd.hnlf.module.eCommerce.viewModel.NoteDetailVM;
import com.rd.hnlf.module.user.UserLogic;
import com.rd.hnlf.module.user.dataModel.receive.OauthTokenRec;
import com.rd.hnlf.network.RDClient;
import com.rd.hnlf.network.RequestCallBack;
import com.rd.hnlf.network.api.ECommerceService;
import com.rd.hnlf.router.RouterUrl;
import com.rd.logic.info.SharedInfo;
import com.rd.network.entity.HttpResult;
import com.rd.tools.utils.AndroidUtil;
import com.rd.tools.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/1 17:21
 * <p/>
 * Description: {@link NoteDetailAct}
 */
public class NoteDetailCtrl {
    private NoteDetailVM viewModel;
    private Activity activity;
    private String officePhone = "tel:";
    public NoteDetailCtrl(String id,Activity activity) {
        viewModel = new NoteDetailVM();
        this.activity = activity;
        reqData(id);
    }

    /**
     * 网络请求
     */
    private void reqData(String id) {
        Call<HttpResult<NoteDetailRec>> call = RDClient.getService(ECommerceService.class).getNoteDetail(id);
        call.enqueue(new RequestCallBack<HttpResult<NoteDetailRec>>() {
            @Override
            public void onSuccess(Call<HttpResult<NoteDetailRec>> call, Response<HttpResult<NoteDetailRec>> response) {
                NoteDetailRec rec = response.body().getData();
                viewModel.setAcceptName(rec.getAcceptorName());
                viewModel.setId(rec.getBillNo());
                viewModel.setTransferCount("转让1手");
                viewModel.setReendorsment(rec.getBackState());
                viewModel.setEndorsementVisible("回头背书".equals(rec.getBackState()));
                viewModel.setAmount(rec.getBillAmount());
                viewModel.setApr(rec.getYearRate());
                viewModel.setDiscount(rec.getDiscount());
                viewModel.setDueDate(rec.getDueDate());
                viewModel.setDays(rec.getAdjustDays());
                viewModel.setType(rec.getType());
                viewModel.setProperty(rec.getBillAttribute());
                viewModel.setHolderEnterprise(rec.getEnterpriseName());
                viewModel.setPhone(rec.getOfficePhone());
                viewModel.setPutOnTime(rec.getShelvesTime());
                viewModel.setDiscuss(rec.getIsDiscussPersonally());
                officePhone = "tel:" + rec.getOfficePhone();
                OauthTokenRec oauthTokenRec = SharedInfo.getInstance().getEntity(OauthTokenRec.class);
                if (null != oauthTokenRec && oauthTokenRec.isAgent()) {
                    // 代理商不可下单
                    viewModel.setEnable(false);
                } else {
                    // 转让中 且 不是面议的，允许下单
                    viewModel.setEnable("10".equals(rec.getTransactionState()) && "20".equals(rec.getIsDiscussPersonally()));
                }

                List<String> noteImages = new ArrayList<>();
                noteImages.add("http://www.qxcu.com/images/2017sc/d5a1bd3e-8c7f-44f7-9f87-c9a0a3523ef2.jpg");
                noteImages.add("http://a2.att.hudong.com/87/02/19300001382663138201022501627.jpg");
                viewModel.setNoteImages(noteImages);
                viewModel.setPrompt("这里是交易须知文案这里是交易须知文案这里是交易须知文案这里是交易须知文案这里是交易须知文案这里是交易须知文案" +
                        "这里是交易须知文案这里是交易须知文案这里是交易须知文案这里是交易须知文案这里是交易须知文案这里是交易须知文案这里是交易须知文案" +
                        "这里是交易须知文案这里是交易须知文案这里是交易须知文案这里是交易须知文案这里是交易须知文案这里是交易须知文案这里是交易须知文案" +
                        "这里是交易须知文案");
            }
        });
    }

    /**
     * 票据正反面点击
     */
    public void noteClick(View view) {
        ARouter.getInstance().build(RouterUrl.MY_NOTE_DETAIL)
                .withString(BundleKeys.ID, viewModel.getId())
                .withBoolean(BundleKeys.HIDDEN, true)
                .navigation();
    }

    /**
     * 交易须知点击
     */
    public void promptClick(View view) {
        ARouter.getInstance().build(RouterUrl.CONTENT_PREVIEW)
                .withString(BundleKeys.TITLE, view.getContext().getString(R.string.note_detail_prompt))
                .withString(BundleKeys.CONTENT, viewModel.getPrompt())
                .navigation();
    }

    /**
     * 交易须知点击
     */
    public void promptClick(View view, TextView promptView) {
        if (view instanceof TextView) {
            if (null == view.getTag()) {
                view.setTag("");
                ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_shrink, 0);
                promptView.setSingleLine(false);
                promptView.setEllipsize(null);
            } else {
                view.setTag(null);
                ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_expand, 0);
                promptView.setMaxLines(Integer.parseInt(promptView.getTag().toString()));
                promptView.setEllipsize(TextUtils.TruncateAt.END);
            }
        }
    }

    public void bannerClick(int position) {

    }

    /**
     * 查看联系人点击
     */
    public void contactWayClick(View view) {
        if (!UserLogic.isLand()) {
            ARouter.getInstance().build(RouterUrl.USER_LOGIN).navigation();
        }
    }

    /**
     * 拨打联系人电话
     */
    public void makingCallClick(View view) {
        if (!officePhone.equals("tel:")){
            startDialog();
        }else {
            ToastUtil.toast("获取联系方式失败");
        }
    }
    private void startDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("霍尼莱夫");
        builder.setMessage("是否拨联系人电话?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(officePhone));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(intent);
                    dialog.dismiss();
                }
        });
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    /**
     * 立即购买点击
     */
    public void buyNowClick(View view) {
        ARouter.getInstance().build(RouterUrl.E_COMMERCE_NOTE_PURCHASE)
                .withString(BundleKeys.ID, viewModel.getId())
                .navigation(AndroidUtil.getActivity(view), BaseActivity.REQUEST_CODE);
    }

    public NoteDetailVM getViewModel() {
        return viewModel;
    }
}