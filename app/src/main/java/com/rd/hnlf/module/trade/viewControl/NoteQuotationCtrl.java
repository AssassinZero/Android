package com.rd.hnlf.module.trade.viewControl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.rd.hnlf.R;
import com.rd.hnlf.common.BundleKeys;
import com.rd.hnlf.module.common.dataModel.receive.OrderBillRec;
import com.rd.hnlf.module.common.dataModel.submit.JsonSub;
import com.rd.hnlf.module.eCommerce.dataModel.submit.OrderBillSub;
import com.rd.hnlf.module.eCommerce.dataModel.submit.TradeNoteInfoSub;
import com.rd.hnlf.module.trade.dataModel.receive.DictionaryRec;
import com.rd.hnlf.module.trade.dataModel.receive.TradeDetailRec;
import com.rd.hnlf.module.trade.ui.activity.NoteQuotationAct;
import com.rd.hnlf.module.trade.viewModel.NoteQuotationVM;
import com.rd.hnlf.network.RDClient;
import com.rd.hnlf.network.RequestCallBack;
import com.rd.hnlf.network.api.TradeService;
import com.rd.hnlf.utils.DialogUtils;
import com.rd.hnlf.utils.LoadingDialogUtils;
import com.rd.network.entity.HttpResult;
import com.rd.tools.utils.AndroidUtil;
import com.rd.tools.utils.StringFormat;
import com.rd.tools.utils.ToastUtil;
import com.rd.views.textView.SingleSelectorView;

import cn.pedant.SweetAlert.OnSweetClickListener;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/31 9:56
 * <p/>
 * Description: {@link NoteQuotationAct}
 */
public class NoteQuotationCtrl {
    /** 需要上传的数据内容 */
    private TradeNoteInfoSub infoSub;
    private NoteQuotationVM  viewModel;
    private Activity activity;
    private SingleSelectorView ssv_type;  //票据类型
    private SingleSelectorView ssv_property;  //票据属性
    public NoteQuotationCtrl(TradeNoteInfoSub infoSub,Activity activity) {
        this.infoSub = infoSub;
        this.activity = activity;
        viewModel = new NoteQuotationVM();
        initView();
        if (!TextUtils.isEmpty(infoSub.getId())) {
            viewModel.setType(infoSub.getBillsType());
            viewModel.setProperty(infoSub.getBillsAttribute());
            viewModel.setQuotationMethod(infoSub.getQuotationMethod());
            viewModel.setApr(infoSub.getYearRate());
            viewModel.setDiscount(infoSub.getDiscount());
            viewModel.setFee(infoSub.getServiceFee());
        }
        reqData();
    }

    private void initView() {
        ssv_type = (SingleSelectorView) activity.findViewById(R.id.note_quotation_ssv_type);
        ssv_property = (SingleSelectorView) activity.findViewById(R.id.note_quotation_ssv_property);

    }

    /**
     * 网络请求 - 获取 票据类别、票据属性
     */
    private void reqData() {
        Call<HttpResult<DictionaryRec>> call = RDClient.getService(TradeService.class).getDictsByParams("BILL_TYPE,BILL_ATTRIBUTE");
        call.enqueue(new RequestCallBack<HttpResult<DictionaryRec>>() {
            @Override
            public void onSuccess(Call<HttpResult<DictionaryRec>> call, Response<HttpResult<DictionaryRec>> response) {
                DictionaryRec rec = response.body().getData();
                viewModel.setTypeList(rec.getBILL_TYPE());
                viewModel.setPropertyList(rec.getBILL_ATTRIBUTE());
                initSsvClick();
            }
        });
    }
    /**
     * 控制选择框是否可以弹出
     */
    private void initSsvClick() {
        String substring = infoSub.getOrderBills().get(0).getBillNo().substring(0, 1);
        if (substring.equals("1")){
            ssv_type.setClick(false);
            ssv_property.setClick(true);
            ssv_type.setKey("10");
        }else if (substring.equals("2")){
            ssv_type.setKey("11");
            ssv_type.setClick(false);
            ssv_property.setKey("16");
            ssv_property.setClick(false);
        }else {
            initListener();
        }
    }
    /**
     * 商票不可选择票据属性
     */
    private void initListener() {
        ssv_type.setOnValChanged(new SingleSelectorView.OnValueChanged() {
            @Override
            public void onValChanged(SingleSelectorView view, String key) {
                if (view.getKey().equals("11")){
                    ssv_property.setKey("16");
                    ssv_property.setClick(false);
                }else {
                    ssv_property.setClick(true);
                }
            }
        });
    }

    /**
     * 发起交易点击
     */
    public void submitClick(View view) {
        if (null == viewModel.getTypeList() || null == viewModel.getPropertyList()) {
            reqData();
            ToastUtil.toast(R.string.validate_init_exceptions);
        } else if (viewModel.isAprMode() && TextUtils.isEmpty(viewModel.getApr())) {
            ToastUtil.toast(R.string.quotation_info_edit_apr_hint);
        } else if (viewModel.isDiscountMode() && TextUtils.isEmpty(viewModel.getDiscount())) {
            ToastUtil.toast(R.string.quotation_info_edit_discount_hint);
        } else if (TextUtils.isEmpty(viewModel.getFee())) {
            ToastUtil.toast(R.string.quotation_info_edit_fee_hint);
        } else {
            getTotalSettleAmount(view);
        }
    }

    private void getTotalSettleAmount(final View view) {
        infoSub.setBillsType(viewModel.getType());
        Log.e("viewModel.getType()",viewModel.getType());
        infoSub.setBillsAttribute(viewModel.getProperty());
        Log.e("viewModel.getProperty()",viewModel.getProperty());
        // 10 - 年利率, 20 - 每十万
        infoSub.setQuotationMethod(viewModel.getQuotationMethod());
        infoSub.setYearRate(viewModel.getApr());
        infoSub.setDiscount(viewModel.getDiscount());
        infoSub.setServiceFee(viewModel.getFee());

        Call<HttpResult<TradeDetailRec>> call = RDClient.getService(TradeService.class).getTotalSettleAmount(new JsonSub().setDataMsg(infoSub));
        call.enqueue(new RequestCallBack<HttpResult<TradeDetailRec>>(new LoadingDialogUtils().show(view.getContext())) {
            @Override
            public void onSuccess(Call<HttpResult<TradeDetailRec>> call, Response<HttpResult<TradeDetailRec>> response) {
                infoSub.getOrderBills().clear();
                TradeDetailRec rec = response.body().getData();
                if (null != rec.getOrderBills()) {
                    for (OrderBillRec billRec : rec.getOrderBills()) {
                        OrderBillSub sub = new OrderBillSub();
                        sub.setAdjustDays(billRec.getAdjustDays());
                        sub.setBillAmount(billRec.getBillAmount());
                        sub.setBillNo(billRec.getBillNo());
                        sub.setDueDateStr(billRec.getDueDateStr());
                        sub.setSingleSettleAmt(billRec.getSingleSettleAmt());
                        infoSub.getOrderBills().add(sub);
                    }
                }
                infoSub.setSettlementAmount(rec.getSettlementAmount());

                Context context = view.getContext();
                DialogUtils.showDialog(context,
                        context.getString(R.string.current_transaction_settlement_amount, StringFormat.doubleFormat(rec.getSettlementAmount())),
                        context.getString(R.string.dialog_cancel),
                        context.getString(R.string.operate_transaction), false,
                        new OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        },
                        new OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                doSubmit(view);
                            }
                        });
            }
        });
    }

    /**
     * 提交数据到后台
     */
    private void doSubmit(final View view) {
        Call<HttpResult> call = RDClient.getService(TradeService.class).initiateBusiness(new JsonSub().setFormData(infoSub));
        call.enqueue(new RequestCallBack<HttpResult>(new LoadingDialogUtils().show(view.getContext())) {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                ToastUtil.toast(response.body().getMsg());
                Activity activity = AndroidUtil.getActivity(view);
                activity.setResult(Activity.RESULT_OK);
                activity.sendBroadcast(new Intent(BundleKeys.REFRESH_LIST));
                activity.finish();
            }
        });
    }

    public NoteQuotationVM getViewModel() {
        return viewModel;
    }
}