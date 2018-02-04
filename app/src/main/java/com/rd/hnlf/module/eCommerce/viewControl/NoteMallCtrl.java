package com.rd.hnlf.module.eCommerce.viewControl;

import android.content.Context;
import android.databinding.Bindable;
import android.databinding.ObservableBoolean;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.widget.CheckBox;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.rd.basic.BaseActivity;
import com.rd.hnlf.BR;
import com.rd.hnlf.R;
import com.rd.hnlf.common.BundleKeys;
import com.rd.hnlf.common.Constant;
import com.rd.hnlf.common.ui.AbsRefreshAndLoadMore;
import com.rd.hnlf.common.ui.BaseDataBindingAdapter;
import com.rd.hnlf.common.ui.BaseDataBindingViewHolder;
import com.rd.hnlf.common.ui.BaseListControl;
import com.rd.hnlf.module.common.viewModel.bean.ConditionBean;
import com.rd.hnlf.module.common.viewModel.bean.KVPBean;
import com.rd.hnlf.module.eCommerce.dataModel.receive.ConditionRec;
import com.rd.hnlf.module.eCommerce.dataModel.receive.NoteMallRec;
import com.rd.hnlf.module.eCommerce.dataModel.submit.ConditionSub;
import com.rd.hnlf.module.eCommerce.filter.DropMenuAdapter;
import com.rd.hnlf.module.eCommerce.ui.activity.NoteMallAct;
import com.rd.hnlf.module.eCommerce.viewModel.ConditionVM;
import com.rd.hnlf.module.eCommerce.viewModel.NoteMallVM;
import com.rd.hnlf.module.user.dataModel.receive.OauthTokenRec;
import com.rd.hnlf.network.RDClient;
import com.rd.hnlf.network.RequestCallBack;
import com.rd.hnlf.network.api.ECommerceService;
import com.rd.hnlf.router.RouterUrl;
import com.rd.logic.info.SharedInfo;
import com.rd.network.entity.HttpResult;
import com.rd.network.entity.ListData;
import com.rd.tools.utils.AndroidUtil;
import com.rd.tools.utils.ToastUtil;
import com.rd.views.PlaceholderLayout;
import com.rd.views.filter.DropDownMenu;
import com.rd.views.filter.interfaces.OnFilterDoneListener;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/31 10:42
 * <p/>
 * Description: {@link NoteMallAct}
 */
public class NoteMallCtrl extends BaseListControl implements OnFilterDoneListener<ConditionBean> {
    private ArrayList<NoteMallVM> data;
    private ConditionVM           viewModel;
    private DropDownMenu          dropDownMenu;
    private ConditionSub          sub;
    /**
     * -1 - 不显示按钮
     * 0  - 显示批量下单
     * 1  - 显示全选、确认下单按钮
     */
    private int                   mode;
    /** 单选框是否可见 */
    private ObservableBoolean visible = new ObservableBoolean(false);

    public NoteMallCtrl(DropDownMenu dropDownMenu) {
        this.dropDownMenu = dropDownMenu;
        data = new ArrayList<>();
        viewModel = new ConditionVM();
        sub = new ConditionSub();

        OauthTokenRec rec = SharedInfo.getInstance().getEntity(OauthTokenRec.class);
        if (rec != null && rec.isAgent()) {
            setMode(Constant.NUMBER__1);
        }

        initAdapter();
        reqData();
    }

    /**
     * 初始化 RecyclerView 的 Adapter
     */
    private void initAdapter() {
        setLayoutManagerType(Constant.NUMBER_0);
        setItemDecorationType(Constant.NUMBER_2);
        setClipToPadding(false);
        setRecyclerAdapter(new BaseDataBindingAdapter<NoteMallVM, BaseDataBindingViewHolder>(R.layout.e_commerce_note_mall_item, data) {
            @Override
            protected void convert(BaseDataBindingViewHolder helper, NoteMallVM item) {
                ViewDataBinding binding = helper.getBinding();
                binding.setVariable(BR.item, item);
                binding.setVariable(BR.visible, visible);
                binding.executePendingBindings();
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
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, final View view, final int position) {
            }

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (visible.get()) {
                    NoteMallVM vm = data.get(position);
                    if (vm.isDiscuss()) {
                        ToastUtil.toastNow(R.string.mall_discuss_error);
                    } else if (!vm.transferable()) {
                        ToastUtil.toastNow(R.string.mall_transfer_error);
                    } else {
                        vm.setSelect();
                    }
                } else {
                    ARouter.getInstance().build(RouterUrl.E_COMMERCE_NOTE_DETAIL).withString(BundleKeys.ID, data.get(position).getId()).navigation();
                }
            }
        });
    }

    @Override
    public void onFilterDone(ConditionBean... items) {
        for (ConditionBean item : items) {
            // 选择该项
            item.setFlag(true);
            // 设置查询条件
            switch (item.getRemark()) {
                case ConditionBean.REMARK_TYPE:
                    sub.setType(item.getKey());
                    break;

                case ConditionBean.REMARK_RELEASE_DATE:
                    sub.setShelvesTime(item.getKey());
                    break;

                case ConditionBean.REMARK_DUE_DATE:
                    sub.setDueDate(item.getKey());
                    break;

                case ConditionBean.REMARK_AMOUNT:
                    sub.setFaceAmount(item.getKey());
                    break;

                case ConditionBean.REMARK_ENTERPRISE:
                    sub.setEnterpriseName(item.getValue());
                    break;

                default:
                    break;
            }
        }
        dropDownMenu.close();
        getListener().refresh();
    }

    /**
     * 批量下单点击
     */
    public void batchOrderClick(View view) {
        setMode(Constant.NUMBER_1);
        visible.set(true);
        getListener().setRefreshEnable(false);
        // getListener().setLoadMoreEnable(false);
    }

    /**
     * 全选点击
     */
    public void selectAllClick(View view) {
        if (view instanceof CheckBox) {
            for (NoteMallVM vm : data) {
                if (vm.transferable()) {
                    vm.select.set(((CheckBox) view).isChecked());
                }
            }
        }
    }

    /**
     * 确认下单点击
     */
    public void confirmOrderClick(View view) {
        String ids = "";
        for (NoteMallVM vm : data) {
            if (vm.select.get()) {
                ids += vm.getId() + ",";
            }
        }
        if (ids.length() > 0) {
            ARouter.getInstance().build(RouterUrl.E_COMMERCE_NOTE_PURCHASE)
                    .withString(BundleKeys.ID, ids)
                    .navigation(AndroidUtil.getActivity(view), BaseActivity.REQUEST_CODE);
        }
    }

    /**
     * 按条件查询
     */
    public void reqData() {
        if (null == dropDownMenu.getTag()) {
            getConditions();
        } else {
            queryByConditions();
        }
    }

    /**
     * 获取查询条件
     */
    private void getConditions() {
        Call<HttpResult<ConditionRec>> call = RDClient.getService(ECommerceService.class).getConditions();
        call.enqueue(new RequestCallBack<HttpResult<ConditionRec>>(getListener(), getPlaceHolderType()) {
            @Override
            public void onSuccess(Call<HttpResult<ConditionRec>> call, Response<HttpResult<ConditionRec>> response) {
                dropDownMenu.setTag("");
                converterConditions(response.body().getData());
                Context context = dropDownMenu.getContext();
                String[] titleList = new String[]{
                        context.getString(R.string.condition_amount),
                        context.getString(R.string.condition_due_date),
                        context.getString(R.string.condition_filtrate)};
                dropDownMenu.setMenuAdapter(new DropMenuAdapter(context, titleList, NoteMallCtrl.this, viewModel));
                queryByConditions();
            }
        });
    }

    /**
     * 数据类型转换
     */
    private void converterConditions(ConditionRec rec) {
        // 票据类别
        List<ConditionBean> typeList = new ArrayList<>();
        for (KVPBean bean : rec.getTypeList()) {
            typeList.add(new ConditionBean(bean.getCode(), bean.getText(), ConditionBean.REMARK_TYPE));
        }
        if (typeList.size() > 0) {
            typeList.get(0).setFlag(true);
        }
        viewModel.setTypeList(typeList);
        // 发布日期
        List<ConditionBean> releaseDateList = new ArrayList<>();
        for (KVPBean bean : rec.getShelvesTimeList()) {
            releaseDateList.add(new ConditionBean(bean.getCode(), bean.getText(), ConditionBean.REMARK_RELEASE_DATE));
        }
        if (releaseDateList.size() > 0) {
            releaseDateList.get(0).setFlag(true);
        }
        viewModel.setReleaseDateList(releaseDateList);
        // 到期期限
        List<ConditionBean> dueDateList = new ArrayList<>();
        for (KVPBean bean : rec.getDueDateList()) {
            dueDateList.add(new ConditionBean(bean.getCode(), bean.getText(), ConditionBean.REMARK_DUE_DATE));
        }
        if (dueDateList.size() > 0) {
            dueDateList.get(0).setFlag(true);
        }
        viewModel.setDueDateList(dueDateList);
        // 票面金额
        List<ConditionBean> amountList = new ArrayList<>();
        for (KVPBean bean : rec.getFaceAmountList()) {
            amountList.add(new ConditionBean(bean.getCode(), bean.getText(), ConditionBean.REMARK_AMOUNT));
        }
        if (amountList.size() > 0) {
            amountList.get(0).setFlag(true);
        }
        viewModel.setAmountList(amountList);
        // 所属企业
        List<ConditionBean> enterpriseList = new ArrayList<>();
        for (KVPBean bean : rec.getCountEnterpriselist()) {
            enterpriseList.add(new ConditionBean(bean.getCode(), bean.getText(), ConditionBean.REMARK_ENTERPRISE));
        }
        if (enterpriseList.size() > 0) {
            enterpriseList.get(0).setFlag(true);
            // 默认查询第一个企业
            sub.setEnterpriseName(enterpriseList.get(0).getValue());
        } else {
            sub.setEnterpriseName(" ");
        }
        viewModel.setEnterpriseList(enterpriseList);
    }

    /**
     * 按条件查询
     */
    private void queryByConditions() {
        sub.setCurrentPage(getListener().getCurrent());

        Call<HttpResult<ListData<NoteMallRec>>> call = RDClient.getService(ECommerceService.class).getNoteMallList(sub);
        call.enqueue(new RequestCallBack<HttpResult<ListData<NoteMallRec>>>(getListener(), getPlaceHolderType()) {
            @Override
            public void onSuccess(Call<HttpResult<ListData<NoteMallRec>>> call, Response<HttpResult<ListData<NoteMallRec>>> response) {
                converter(response.body().getData().getList());
            }
        });
    }

    /**
     * 数据类型转换
     */
    private void converter(List<NoteMallRec> list) {
        getListener().clear(data);
        if (null == list || list.isEmpty()) {
            setPlaceHolderType(PlaceholderLayout.EMPTY);
        } else {
            setPlaceHolderType(PlaceholderLayout.SUCCESS);
            for (NoteMallRec rec : list) {
                NoteMallVM vm = new NoteMallVM();
                vm.setId(rec.getBillNo());
                vm.setIsDiscussPersonally(rec.getIsDiscussPersonally());
                vm.setAcceptName(rec.getAcceptorName());
                vm.setType(rec.getType());
                vm.setAmount(rec.getFaceAmount());
                vm.setProperty(rec.getBillAttribute());
                vm.setMode(rec.getQuotationMethod(), rec.getYearRate(), rec.getDiscount());
                vm.setDueDate(rec.getDueDate());
                vm.setStatus(rec.getTransactionState());
                data.add(vm);
            }
        }
    }

    @Bindable
    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
        notifyPropertyChanged(BR.mode);
    }

    /**
     * 退出选择模式
     *
     * @return 当前是否在选择模式
     */
    public boolean exitSelectMode() {
        if (mode == Constant.NUMBER_1) {
            setMode(Constant.NUMBER_0);
            visible.set(false);
            getListener().setRefreshEnable(true);
            // getListener().setLoadMoreEnable(true);
            return true;
        } else {
            return false;
        }
    }

    public ConditionVM getViewModel() {
        return viewModel;
    }
}