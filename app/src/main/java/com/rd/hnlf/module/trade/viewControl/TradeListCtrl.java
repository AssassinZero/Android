package com.rd.hnlf.module.trade.viewControl;

import android.databinding.ViewDataBinding;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.rd.hnlf.BR;
import com.rd.hnlf.R;
import com.rd.hnlf.common.BundleKeys;
import com.rd.hnlf.common.Constant;
import com.rd.hnlf.common.ui.AbsRefreshAndLoadMore;
import com.rd.hnlf.common.ui.BaseDataBindingAdapter;
import com.rd.hnlf.common.ui.BaseDataBindingViewHolder;
import com.rd.hnlf.common.ui.BaseListControlWithButton;
import com.rd.hnlf.module.common.dataModel.receive.OrderRec;
import com.rd.hnlf.module.common.viewModel.OrderVM;
import com.rd.hnlf.module.logic.ButtonOperateLogic;
import com.rd.hnlf.module.trade.ui.fragment.TradeListFrag;
import com.rd.hnlf.module.user.dataModel.receive.OauthTokenRec;
import com.rd.hnlf.network.RDClient;
import com.rd.hnlf.network.RequestCallBack;
import com.rd.hnlf.network.api.TradeService;
import com.rd.hnlf.router.RouterUrl;
import com.rd.logic.info.SharedInfo;
import com.rd.network.entity.HttpResult;
import com.rd.network.entity.ListData;
import com.rd.tools.utils.ConverterUtil;
import com.rd.views.PlaceholderLayout;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/24 16:03
 * <p/>
 * Description: {@link TradeListFrag}
 */
public class TradeListCtrl extends BaseListControlWithButton {
    private ArrayList<OrderVM> data;
    /**
     * 普通用户 - 我是卖家 10 - 全部,11 - 待确认,12 - 待修改
     * 会员用户 - 我是卖家 10 - 全部,11 - 待确认,12 - 待修改
     * 代理商   - 票据交易 20
     * 会员用户 - 我是买家 30 - 全部,31 - 待付款,32 - 待确认,33 - 待复核
     */
    private String             type;
    private String             searchType;
    private String             businessState;

    public TradeListCtrl(String type) {
        this.type = type;
        setPurpleButton(R.string.operate_transaction);
        data = new ArrayList<>();
        initAdapter();
        initData();
    }

    /**
     * 根据 type 设置 searchType 和 businessState 的值
     * <p />
     * searchType
     * 普通客户   - 我是卖家    1001
     * 会员用户   - 我是卖家    1003
     * 会员用户   - 我是买家    1004
     * 代理商用户 - 票据交易    1005
     * <p />
     * businessState
     * 默认查询全部订单
     * 普通用户 - 我是卖家   待确认 0012  待修改 0022
     * 会员用户 - 我是卖家   待确认 0012  待修改 0022
     * 会员用户 - 我是买家   待付款 0040  待确认 0011 待复核 0041
     */
    private void initData() {
        int _type = ConverterUtil.getInteger(type);
        businessState = "";

        if (_type < 20) {
            searchType = SharedInfo.getInstance().getEntity(OauthTokenRec.class).isNormal() ? "1001" : "1003";
            if (Constant.TRADE_SELLER_CONFIRM.equals(type)) {
                businessState = "0012,0010";
            } else if (Constant.TRADE_SELLER_MODIFY.equals(type)) {
                businessState = "0020,0022";
            }
        } else if (_type < 30) {
            searchType = "1005";
        } else {
            searchType = "1004";
            if (Constant.TRADE_BUYER_PAYMENT.equals(type)) {
                businessState = "0040";
            } else if (Constant.TRADE_BUYER_HANDLE.equals(type)) {
                businessState = "0011,0010";
            } else if (Constant.TRADE_BUYER_REVIEW.equals(type)) {
                businessState = "0041";
            }
        }
    }

    /**
     * 初始化 RecyclerView 的 Adapter
     */
    private void initAdapter() {
        setLayoutManagerType(Constant.NUMBER_0);
        setRecyclerAdapter(new BaseDataBindingAdapter<OrderVM, BaseDataBindingViewHolder>(R.layout.basic_order_item, data) {
            @Override
            protected void convert(BaseDataBindingViewHolder helper, OrderVM item) {
                ViewDataBinding binding = helper.getBinding();
                binding.setVariable(BR.item, item);
                binding.executePendingBindings();
                helper.addOnClickListener(R.id.button1);
                helper.addOnClickListener(R.id.button2);
            }
        });
        // 刷新 加载 监听
        setListener(new AbsRefreshAndLoadMore() {
            @Override
            public void refreshInit(PtrFrameLayout ptrFrame) {
            }

            @Override
            public void loadMoreInit(BaseQuickAdapter adapter) {
                adapter.setPreLoadNumber(5);
            }

            @Override
            public void onRequest() {
                reqData();
            }
        });
        // 设置点击事件
        setItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                int viewId = view.getId();
                switch (viewId) {
                    case R.id.button1:
                        button1Click(view, position);
                        break;

                    case R.id.button2:
                        button2Click(view, position);
                        break;

                    default:
                        break;
                }
            }

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ARouter.getInstance().build(RouterUrl.TRADE_DETAIL)
                        .withString(BundleKeys.ID, data.get(position).getOrderId())
                        .withString(BundleKeys.TYPE, type)
                        .navigation();
            }
        });
    }

    /**
     * 按钮1 点击
     */
    private void button1Click(View view, int position) {
        ButtonOperateLogic.getInstance().execute(view.getContext(), data.get(position).getOperate1(), data.get(position).getOrderId(), type);
    }

    /**
     * 按钮2 点击
     */
    private void button2Click(View view, int position) {
        ButtonOperateLogic.getInstance().execute(view.getContext(), data.get(position).getOperate2(), data.get(position).getOrderId(), type);
    }

    /**
     * 发起交易
     */
    @Override
    public void purpleButtonClick(View view) {
        super.purpleButtonClick(view);
        int t = ConverterUtil.getInteger(type);
        if (t >= 30) {
            // 我是买家
            ARouter.getInstance().build(RouterUrl.TRADE_NOTE_INFO).withString(BundleKeys.TYPE, Constant.STATUS_0).navigation();
        } else if (t == 20) {
            // 代理商 - 我是卖家
            ARouter.getInstance().build(RouterUrl.TRADE_NOTE_INFO).withString(BundleKeys.TYPE, Constant.STATUS__1).navigation();
        } else {
            // 普通用户 - 我是卖家
            ARouter.getInstance().build(RouterUrl.TRADE_NOTE_INFO).withString(BundleKeys.TYPE, Constant.STATUS_1).navigation();
        }
    }

    /**
     * 网络请求
     */
    public void reqData() {
        Call<HttpResult<ListData<OrderRec>>> call = RDClient.getService(TradeService.class).getTradeList(searchType, businessState, getListener().getCurrent());
        call.enqueue(new RequestCallBack<HttpResult<ListData<OrderRec>>>(getListener(), getPlaceHolderType()) {
            @Override
            public void onSuccess(Call<HttpResult<ListData<OrderRec>>> call, Response<HttpResult<ListData<OrderRec>>> response) {
                converter(response.body().getData().getList());
            }
        });
    }

    /**
     * 数据类型转换
     */
    private void converter(List<OrderRec> list) {
        getListener().clear(data);
        if (null == list || list.isEmpty()) {
            setPlaceHolderType(PlaceholderLayout.EMPTY);
        } else {
            setPlaceHolderType(PlaceholderLayout.SUCCESS);
            for (OrderRec rec : list) {
                OrderVM vm = new OrderVM(Constant.NUMBER_1);
                vm.setOrderId(rec.getOrderId());
                vm.setAccountName(rec.getEnterpriseName());
                vm.setType(rec.getBusinessType());
                vm.setStatus(rec.getBusinessStatus());
                vm.setTotalAmount(rec.getBillsTotalAmt());
                vm.setSettlementAmount(rec.getSettlementAmount());
                vm.setNoteCount(rec.getBillsNum());
                vm.setOrderTime(rec.getCreateTime());
                vm.setOrderNo(rec.getOrderNo());
                ButtonOperateLogic.getInstance().initButtons(rec, vm, type);
                data.add(vm);
            }
        }
    }
}