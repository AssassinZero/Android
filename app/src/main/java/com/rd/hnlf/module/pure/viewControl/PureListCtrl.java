package com.rd.hnlf.module.pure.viewControl;

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
import com.rd.hnlf.module.pure.ui.fragment.PureListFrag;
import com.rd.hnlf.network.RDClient;
import com.rd.hnlf.network.RequestCallBack;
import com.rd.hnlf.network.api.PureService;
import com.rd.hnlf.router.RouterUrl;
import com.rd.network.entity.HttpResult;
import com.rd.network.entity.ListData;
import com.rd.tools.utils.ActivityManage;
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
 * Description: {@link PureListFrag}
 */
public class PureListCtrl extends BaseListControlWithButton {
    private ArrayList<OrderVM> data;
    /** 60 - 纯票订单 */
    private String             type;

    public PureListCtrl(String type) {
        this.type = type;
        // setPurpleButton(R.string.pure_sell);
        data = new ArrayList<>();
        initAdapter();
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
                ARouter.getInstance().build(RouterUrl.PURE_PURE_DETAIL)
                        .withString(BundleKeys.ID, data.get(position).getOrderId())
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
     * 卖出票据
     */
    @Override
    public void purpleButtonClick(View view) {
        super.purpleButtonClick(view);
        ARouter.getInstance().build(RouterUrl.PURE_MY_NOTE).navigation();
        ActivityManage.finish();
    }

    /**
     * 网络请求
     */
    public void reqData() {
        Call<HttpResult<ListData<OrderRec>>> call = RDClient.getService(PureService.class).getPureList(type, getListener().getCurrent());
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
                OrderVM vm = new OrderVM(Constant.NUMBER_2);
                vm.setOrderId(rec.getOrderId());
                vm.setAccountName(rec.getEnterpriseName());
                vm.setTotalAmount(rec.getBillsTotalAmt());
                vm.setSettlementAmount(rec.getSettlementAmount());
                vm.setNoteCount(rec.getBillsNum());
                vm.setOrderTime(rec.getCreateTime());
                vm.setOrderNo(rec.getOrderNo());
                vm.setStatus(rec.getBusinessStatus());
                if (rec.isReview()) {
                    vm.setButton1("复核");
                    vm.setOperate1(ButtonOperateLogic.PURE_REVIEW_ORDER);
                } else {
                    vm.reset();
                }
                data.add(vm);
            }
        }
    }
}