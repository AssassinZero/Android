package com.rd.hnlf.module.trade.viewControl;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.rd.basic.BaseActivity;
import com.rd.hnlf.BR;
import com.rd.hnlf.R;
import com.rd.hnlf.common.BundleKeys;
import com.rd.hnlf.common.Constant;
import com.rd.hnlf.common.ui.BaseDataBindingAdapter;
import com.rd.hnlf.common.ui.BaseDataBindingViewHolder;
import com.rd.hnlf.common.ui.BaseListControlWithButton;
import com.rd.hnlf.module.common.dataModel.receive.OrderBillRec;
import com.rd.hnlf.module.eCommerce.dataModel.submit.OrderBillSub;
import com.rd.hnlf.module.eCommerce.dataModel.submit.TradeNoteInfoSub;
import com.rd.hnlf.module.trade.dataModel.receive.TradeDetailRec;
import com.rd.hnlf.module.trade.ui.activity.NoteInfoAct;
import com.rd.hnlf.module.trade.viewModel.NoteDealingVM;
import com.rd.hnlf.network.RDClient;
import com.rd.hnlf.network.RequestCallBack;
import com.rd.hnlf.network.api.TradeService;
import com.rd.hnlf.router.RouterUrl;
import com.rd.hnlf.utils.DialogUtils;
import com.rd.hnlf.utils.LoadingDialogUtils;
import com.rd.network.entity.HttpResult;
import com.rd.tools.utils.ActivityManage;
import com.rd.tools.utils.AndroidUtil;
import com.rd.tools.utils.ConverterUtil;
import com.rd.tools.utils.DateUtil;
import com.rd.tools.utils.ToastUtil;
import com.rd.views.PlaceholderLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.OnSweetClickListener;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/29 11:27
 * <p/>
 * Description:{@link NoteInfoAct}
 */
public class NoteInfoCtrl extends BaseListControlWithButton {
    /**
     * -1 - 代理买卖
     * 0  - 我要买票
     * 1  - 我要卖票
     */
    private String                   type;
    /** 需要上传的数据内容 */
    private TradeNoteInfoSub         infoSub;
    private ArrayList<NoteDealingVM> data;

    /**
     * @param type
     *         类型
     * @param id
     *         订单ID
     */
    public NoteInfoCtrl(String type, String id) {
        this.type = type;
        data = new ArrayList<>();
        setWhiteButton(R.string.note_add);
        setPurpleButton(R.string.operate_next);
        initAdapter();
        reqData(id);
    }

    /**
     * 初始化 RecyclerView 的 Adapter
     */
    private void initAdapter() {
        setLayoutManagerType(Constant.NUMBER_0);
        setPlaceHolderType(PlaceholderLayout.SUCCESS);
        setRecyclerAdapter(new BaseDataBindingAdapter<NoteDealingVM, BaseDataBindingViewHolder>(R.layout.trade_note_dealing_item, data) {
            @Override
            protected void convert(BaseDataBindingViewHolder helper, NoteDealingVM item) {
                ViewDataBinding binding = helper.getBinding();
                binding.setVariable(BR.item, item);
                binding.executePendingBindings();
                helper.addOnClickListener(R.id.deleteBtn);
            }
        });
        // 设置点击事件
        setItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                int viewId = view.getId();
                switch (viewId) {
                    case R.id.deleteBtn:
                        deleteNote(view, position);
                        break;

                    default:
                        break;
                }
            }

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                // 修改票据内容
                NoteDealingVM item = data.get(position);
                item.setPosition(position);

                ARouter.getInstance().build(RouterUrl.TRADE_ADD_NOTE)
                        .withObject(BundleKeys.ITEM, item)
                        .navigation(AndroidUtil.getActivity(view), BaseActivity.REQUEST_CODE);
            }
        });
    }

    /**
     * 根据ID是否空 选择是否去获取交易订单数据
     */
    private void reqData(String id) {
        infoSub = new TradeNoteInfoSub();
        if (!TextUtils.isEmpty(id)) {
            Call<HttpResult<TradeDetailRec>> call = RDClient.getService(TradeService.class).getTradeDetailInfo(id);
            call.enqueue(new RequestCallBack<HttpResult<TradeDetailRec>>(new LoadingDialogUtils().show(ActivityManage.peek())) {
                @Override
                public void onSuccess(Call<HttpResult<TradeDetailRec>> call, Response<HttpResult<TradeDetailRec>> response) {
                    TradeDetailRec rec = response.body().getData();
                    infoSub.setId(rec.getId());
                    // 票面信息
                    for (OrderBillRec billRec : rec.getOrderBills()) {
                        NoteDealingVM vm = new NoteDealingVM();
                        vm.setId(billRec.getBillNo());
                        vm.setAmount(billRec.getBillAmount());
                        Date date = DateUtil.getDate(billRec.getDueDateStr(), DateUtil.Format.DATE);
                        vm.setDueDate(date == null ? 0 : date.getTime());
                        vm.setDays(billRec.getAdjustDays());
                        data.add(vm);
                    }
                    getRecyclerAdapter().notifyDataSetChanged();
                    // 收票方/持票方信息
                    infoSub.setCollectorAccountId(rec.getCollectorAccountId());
                    infoSub.setCollectingBankAccount(rec.getCollectingBankAccount());
                    infoSub.setHoldAccountId(rec.getHoldAccountId());
                    infoSub.setUniformity(rec.getUniformity());
                    infoSub.setBankAccount(rec.getBankAccount());
                    infoSub.setHoldAccountName(rec.getHoldAccountName());
                    infoSub.setHoldAccountSubbranchName(rec.getHoldAccountSubbranchName());
                    infoSub.setHoldAccountSubbranchCode(rec.getHoldAccountSubbranchCode());
                    // 报价信息
                    infoSub.setBillsType(rec.getBillsType());
                    infoSub.setBillsAttribute(rec.getBillsAttribute());
                    infoSub.setQuotationMethod(rec.getQuotationMethod());
                    infoSub.setYearRate(ConverterUtil.getDouble(rec.getYearRate()) * 100 + "");
                    infoSub.setDiscount(rec.getDiscount());
                    infoSub.setServiceFee(ConverterUtil.getDouble(rec.getServiceFee()) + "");
                }
            });
        }
    }

    /**
     * 删除指定票据
     */
    private void deleteNote(View view, final int position) {
        Context context = view.getContext();
        DialogUtils.showDialog(context, context.getString(R.string.note_delete, data.get(position).getShortId()), context.getString(R.string.dialog_confirm),
                true, new OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        data.remove(position);
                        getRecyclerAdapter().notifyItemRemoved(position);
                    }
                });
    }

    /**
     * 添加票据点击
     */
    @Override
    public void whiteButtonClick(View view) {
        super.whiteButtonClick(view);
        ARouter.getInstance().build(RouterUrl.TRADE_ADD_NOTE).navigation(AndroidUtil.getActivity(view), BaseActivity.REQUEST_CODE);
    }

    /**
     * 下一步点击
     */
    @Override
    public void purpleButtonClick(View view) {
        super.purpleButtonClick(view);
        if (data.size() > 0) {
            List<OrderBillSub> list = new ArrayList<>();
            for (NoteDealingVM vm : data) {
                OrderBillSub sub = new OrderBillSub();
                sub.setBillNo(vm.getId());
                sub.setBillAmount(vm.getAmount());
                sub.setDueDateStr(vm.getDueDateStr());
                sub.setAdjustDays(vm.getDays());
                list.add(sub);
            }
            infoSub.setOrderBills(list);

            ARouter.getInstance().build(RouterUrl.TRADE_NOTE_ACCOUNT)
                    .withString(BundleKeys.TYPE, type)
                    .withObject(BundleKeys.ITEM, infoSub)
                    .navigation(AndroidUtil.getActivity(view), BaseActivity.REQUEST_CODE_CLOSED);
        } else {
            ToastUtil.toast(R.string.note_add_error);
        }
    }

    /**
     * 添加票据回调
     */
    public void addNote(NoteDealingVM vm) {
        int position = vm.getPosition();
        if (position != Constant.NUMBER__1) {
            data.remove(position);
            data.add(position, vm);
            getRecyclerAdapter().notifyItemChanged(position);
        } else {
            data.add(vm);
            getRecyclerAdapter().notifyItemInserted(data.size());
        }
    }
}