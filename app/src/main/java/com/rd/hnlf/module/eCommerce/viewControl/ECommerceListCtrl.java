package com.rd.hnlf.module.eCommerce.viewControl;

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
import com.rd.hnlf.common.ui.BaseListControl;
import com.rd.hnlf.module.common.dataModel.receive.OrderRec;
import com.rd.hnlf.module.common.viewModel.OrderVM;
import com.rd.hnlf.module.eCommerce.ui.fragment.ECommerceListFrag;
import com.rd.hnlf.module.logic.ButtonOperateLogic;
import com.rd.hnlf.network.RDClient;
import com.rd.hnlf.network.RequestCallBack;
import com.rd.hnlf.network.api.ECommerceService;
import com.rd.hnlf.router.RouterUrl;
import com.rd.network.entity.HttpResult;
import com.rd.network.entity.ListData;
import com.rd.views.PlaceholderLayout;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/23 10:13
 * <p/>
 * Description: {@link ECommerceListFrag}
 */
public class ECommerceListCtrl extends BaseListControl {
    private ArrayList<OrderVM> data;
    /**
     * 普通用户 - 我是买家 40
     * 会员用户 - 电商订单 50 - 买入票据,51 - 卖出票据
     */
    private String             type;
    private String             searchType;

    public ECommerceListCtrl(String type) {
        this.type = type;
        data = new ArrayList<>();
        initAdapter();
        initData();
    }

    /**
     * 根据 type 设置 searchType 的值
     * <p />
     * searchType
     * 普通用户 - 我是买家      1006
     * 会员用户 - 买入票据      1007
     * 会员用户 - 卖出票据      1008
     */
    private void initData() {
        if (Constant.E_COMMERCE_BUYER.equals(type)) {
            searchType = "1006";
        } else if (Constant.E_COMMERCE_BUY.equals(type)) {
            searchType = "1007";
        } else if (Constant.E_COMMERCE_SELL.equals(type)) {
            searchType = "1008";
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
                ARouter.getInstance().build(RouterUrl.E_COMMERCE_DETAIL)
                        .withString(BundleKeys.ID, data.get(position).getOrderNo())
                        .withString(BundleKeys.TYPE, type)
                        .navigation();
            }
        });
    }

    /**
     * 按钮1 点击
     */
    private void button1Click(View view, int position) {
        ButtonOperateLogic.getInstance().execute(view.getContext(), data.get(position).getOperate1(), data.get(position).getOrderNo(), type);
    }

    /**
     * 按钮2 点击
     */
    private void button2Click(View view, int position) {
        ButtonOperateLogic.getInstance().execute(view.getContext(), data.get(position).getOperate1(), data.get(position).getOrderNo(), type);
    }

    /**
     * 网络请求
     */
    public void reqData() {
        Call<HttpResult<ListData<OrderRec>>> call = RDClient.getService(ECommerceService.class).getECommerceList(searchType, getListener().getCurrent());
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
                OrderVM vm = new OrderVM(Constant.NUMBER_0);
                vm.setOrderId(rec.getOrderId());
                vm.setAccountName(rec.getEnterpriseName());
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