package com.rd.hnlf.module.pure.viewControl;

import android.text.TextUtils;
import android.view.View;

import com.rd.hnlf.R;
import com.rd.hnlf.common.Constant;
import com.rd.hnlf.module.common.viewModel.bean.NoteInfo;
import com.rd.hnlf.module.common.viewModel.bean.QuotationInfo;
import com.rd.hnlf.module.pure.dataModel.receive.NoteModifyRec;
import com.rd.hnlf.module.pure.dataModel.submit.MyNoteSub;
import com.rd.hnlf.module.pure.ui.activity.ModifyNoteAct;
import com.rd.hnlf.module.pure.viewModel.PureDetailVM;
import com.rd.hnlf.network.RDClient;
import com.rd.hnlf.network.RequestCallBack;
import com.rd.hnlf.network.api.PureService;
import com.rd.hnlf.utils.LoadingDialogUtils;
import com.rd.network.entity.HttpResult;
import com.rd.tools.utils.ActivityManage;
import com.rd.tools.utils.ConverterUtil;
import com.rd.tools.utils.ToastUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/28 15:31
 * <p/>
 * Description: {@link ModifyNoteAct}
 */
public class ModifyNoteCtrl {
    private PureDetailVM viewModel;
    private String       id;
    private MyNoteSub    sub;

    public ModifyNoteCtrl(String id) {
        this.id = id;
        viewModel = new PureDetailVM();
        sub = new MyNoteSub();
        reqData();
    }

    /**
     * 修改点击
     */
    public void modifyClick(View view) {
        if (viewModel.getQuotationInfo().isAprMode() && TextUtils.isEmpty(viewModel.getQuotationInfo().getOriginApr())) {
            ToastUtil.toast(R.string.quotation_info_edit_apr_hint);
        } else if (viewModel.getQuotationInfo().isDiscountMode() && TextUtils.isEmpty(viewModel.getQuotationInfo().getOriginDiscount())) {
            ToastUtil.toast(R.string.quotation_info_edit_discount_hint);
        } else if (TextUtils.isEmpty(viewModel.getNoteInfo().get(0).getDays())) {
            ToastUtil.toast(R.string.note_days_hint);
        } else {
            doModify(view);
        }
    }

    /**
     * 提交数据至接口
     */
    private void doModify(View view) {
        sub.setIsDiscussPersonally(viewModel.getQuotationInfo().getDiscussPersonally());
        sub.setQuotationMethod(viewModel.getQuotationInfo().getQuotationMethod());
        sub.setYearRate(ConverterUtil.getDouble(viewModel.getQuotationInfo().getOriginApr()) / 100 + "");
        sub.setDiscount(viewModel.getQuotationInfo().getOriginDiscount());
        sub.setAdjustDays(viewModel.getNoteInfo().get(0).getDays());

        Call<HttpResult> call = RDClient.getService(PureService.class).modifyMyNote(sub);
        call.enqueue(new RequestCallBack<HttpResult>(new LoadingDialogUtils().show(view.getContext())) {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                ToastUtil.toast(response.body().getMsg());
                // ContextHolder.getContext().sendBroadcast(new Intent(BundleKeys.REFRESH_LIST));
                ActivityManage.finish();
            }
        });
    }

    /**
     * 网络请求
     */
    private void reqData() {
        Call<HttpResult<NoteModifyRec>> call = RDClient.getService(PureService.class).getModifyDetail(id);
        call.enqueue(new RequestCallBack<HttpResult<NoteModifyRec>>() {
            @Override
            public void onSuccess(Call<HttpResult<NoteModifyRec>> call, Response<HttpResult<NoteModifyRec>> response) {
                NoteModifyRec rec = response.body().getData();
                // 票面信息
                ArrayList<NoteInfo> noteInfo = new ArrayList<>();
                NoteInfo            info     = new NoteInfo(Constant.NUMBER_2);
                info.setId(rec.getBillNo());
                info.setAmount(rec.getFaceAmount());
                info.setDays(rec.getAdjustDays());
                info.setDueDate(rec.getDueDate());
                info.setType(rec.getTypeText());
                info.setProperty(rec.getBillAttributeText());
                noteInfo.add(info);
                viewModel.setNoteInfo(noteInfo);
                // 报价信息
                QuotationInfo quotationInfo = new QuotationInfo();
                quotationInfo.setDiscussPersonally(rec.getIsDiscussPersonally());
                quotationInfo.setDiscount(rec.getDiscount());
                quotationInfo.setApr(ConverterUtil.getDouble(rec.getYearRate()) * 100 + "");
                quotationInfo.setQuotationMethod(rec.getQuotationMethod());
                viewModel.setQuotationInfo(quotationInfo);
                viewModel.setTransactionState(rec.getTransactionState());
                viewModel.setOriginalMoney(rec.getOriginalMoney());

                // 初始化提交需要用到的数据
                sub.setBillNo(id);
                sub.setDueDate(rec.getDueDate());
                sub.setFaceAmount(rec.getFaceAmount());
                sub.setOriginalMoney(rec.getOriginalMoney());
            }
        });
    }

    public PureDetailVM getViewModel() {
        return viewModel;
    }
}