package com.rd.hnlf.module.pure.viewControl;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.rd.hnlf.R;
import com.rd.hnlf.common.BundleKeys;
import com.rd.hnlf.common.Constant;
import com.rd.hnlf.module.common.dataModel.submit.JsonSub;
import com.rd.hnlf.module.common.viewModel.bean.NoteInfo;
import com.rd.hnlf.module.common.viewModel.bean.QuotationInfo;
import com.rd.hnlf.module.pure.dataModel.receive.NoteModifyRec;
import com.rd.hnlf.module.pure.dataModel.submit.MyNoteSub;
import com.rd.hnlf.module.pure.dataModel.submit.NoteSub;
import com.rd.hnlf.module.pure.ui.activity.NotePutOnAct;
import com.rd.hnlf.module.pure.viewModel.PureDetailVM;
import com.rd.hnlf.network.RDClient;
import com.rd.hnlf.network.RequestCallBack;
import com.rd.hnlf.network.api.PureService;
import com.rd.hnlf.utils.LoadingDialogUtils;
import com.rd.network.entity.HttpResult;
import com.rd.network.entity.ListData;
import com.rd.tools.utils.ActivityManage;
import com.rd.tools.utils.ContextHolder;
import com.rd.tools.utils.ConverterUtil;
import com.rd.tools.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/28 15:31
 * <p/>
 * Description: {@link NotePutOnAct}
 */
public class NotePutOnCtrl {
    private PureDetailVM viewModel;
    private String       billNos;

    public NotePutOnCtrl(String ids) {
        viewModel = new PureDetailVM();
        viewModel.setQuotationInfo(new QuotationInfo());
        billNos = "[";
        for (String s : ids.split(",")) {
            billNos += "\"" + s + "\",";
        }
        billNos = billNos.substring(0, billNos.length() - 1) + "]";
        reqData();
    }

    /**
     * 网络请求
     */
    private void reqData() {
        Call<HttpResult<ListData<NoteModifyRec>>> call = RDClient.getService(PureService.class).getPutOnDetail(billNos);
        call.enqueue(new RequestCallBack<HttpResult<ListData<NoteModifyRec>>>() {
            @Override
            public void onSuccess(Call<HttpResult<ListData<NoteModifyRec>>> call, Response<HttpResult<ListData<NoteModifyRec>>> response) {
                converter(response.body().getData().getList());
            }
        });
    }

    /**
     * 数据类型转换
     */
    private void converter(List<NoteModifyRec> list) {
        ArrayList<NoteInfo> noteInfo = new ArrayList<>();
        for (NoteModifyRec rec : list) {
            NoteInfo info = new NoteInfo(Constant.NUMBER_2);
            info.setId(rec.getBillNo());
            info.setAmount(rec.getFaceAmount());
            info.setDays(rec.getAdjustDays());
            info.setDueDate(rec.getDueDate());
            info.setType(rec.getTypeText());
            info.setProperty(rec.getBillAttributeText());
            noteInfo.add(info);
        }
        viewModel.setNoteInfo(noteInfo);
    }

    /**
     * 上架点击
     */
    public void submitClick(View view) {
        if (!viewModel.getQuotationInfo().isDiscuss() &&
                viewModel.getQuotationInfo().isAprMode() && TextUtils.isEmpty(viewModel.getQuotationInfo().getOriginApr())) {
            ToastUtil.toast(R.string.quotation_info_edit_apr_hint);
        } else if (!viewModel.getQuotationInfo().isDiscuss() &&
                viewModel.getQuotationInfo().isDiscountMode() && TextUtils.isEmpty(viewModel.getQuotationInfo().getOriginDiscount())) {
            ToastUtil.toast(R.string.quotation_info_edit_discount_hint);
        } else {
            doSubmit(view);
        }
    }

    /**
     * 上架
     */
    private void doSubmit(View view) {
        MyNoteSub     sub     = new MyNoteSub();
        List<NoteSub> billNos = new ArrayList<>();
        for (NoteInfo info : viewModel.getNoteInfo()) {
            NoteSub noteSub = new MyNoteSub();
            noteSub.setBillNo(info.getId());
            noteSub.setFaceAmount(info.getOriginAmount());
            noteSub.setAdjustDays(info.getDays());
            noteSub.setDueDate(info.getOriginDueDate());
            billNos.add(noteSub);
        }
        sub.setBillNos(billNos);
        sub.setIsDiscussPersonally(viewModel.getQuotationInfo().getDiscussPersonally());
        sub.setQuotationMethod(viewModel.getQuotationInfo().getQuotationMethod());
        sub.setYearRate(ConverterUtil.getDouble(viewModel.getQuotationInfo().getOriginApr()) / 100 + "");
        sub.setDiscount(viewModel.getQuotationInfo().getOriginDiscount());

        Call<HttpResult> call = RDClient.getService(PureService.class).putOnNote(new JsonSub().setFormData(sub));
        call.enqueue(new RequestCallBack<HttpResult>(new LoadingDialogUtils().show(view.getContext())) {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                ToastUtil.toast(response.body().getMsg());
                ContextHolder.getContext().sendBroadcast(new Intent(BundleKeys.REFRESH_LIST));
                ActivityManage.finish();
            }
        });
    }

    public PureDetailVM getViewModel() {
        return viewModel;
    }
}