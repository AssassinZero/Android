package com.rd.hnlf.module.pure.viewControl;

import android.databinding.Bindable;
import android.databinding.ObservableBoolean;
import android.databinding.ViewDataBinding;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

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
import com.rd.hnlf.module.common.viewModel.bean.NoteInfo;
import com.rd.hnlf.module.logic.ButtonOperateLogic;
import com.rd.hnlf.module.pure.dataModel.receive.MyNoteRec;
import com.rd.hnlf.module.pure.ui.fragment.NoteListFrag;
import com.rd.hnlf.module.pure.viewModel.NoteVM;
import com.rd.hnlf.network.RDClient;
import com.rd.hnlf.network.RequestCallBack;
import com.rd.hnlf.network.api.PureService;
import com.rd.hnlf.router.RouterUrl;
import com.rd.network.entity.HttpResult;
import com.rd.network.entity.ListData;
import com.rd.tools.utils.ToastUtil;
import com.rd.views.PlaceholderLayout;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/22 15:00
 * <p/>
 * Description: {@link NoteListFrag}
 */
public class NoteListCtrl extends BaseListControl {
    private ArrayList<NoteVM> data;
    /**
     * 普通用户 - 历史订单 72
     * 会员用户 - 持有中 - 70,已上架 - 71,历史订单 - 72
     */
    private String            type;
    /**
     * 0 - 按钮都不显示
     * 1 - 显示纯票交易、上架按钮
     * 2 - 显示全选、发起交易
     * 3 - 显示全选、上架按钮
     */
    private int               mode;
    /** 单选框是否可见 */
    private ObservableBoolean visible   = new ObservableBoolean(false);
    /** 是否全选 */
    public  ObservableBoolean checkable = new ObservableBoolean(false);

    public NoteListCtrl(String type) {
        this.type = type;
        if (Constant.NOTE_HOLDING.equals(type)) {
            setMode(Constant.NUMBER_1);
        }
        data = new ArrayList<>();
        initAdapter();
    }

    /**
     * 初始化 RecyclerView 的 Adapter
     */
    private void initAdapter() {
        setLayoutManagerType(Constant.NUMBER_0);
        setRecyclerAdapter(new BaseDataBindingAdapter<NoteVM, BaseDataBindingViewHolder>(R.layout.pure_note_item, data) {
            @Override
            protected void convert(BaseDataBindingViewHolder helper, NoteVM item) {
                ViewDataBinding binding = helper.getBinding();
                binding.setVariable(BR.item, item);
                binding.setVariable(BR.visible, visible);
                binding.executePendingBindings();
                helper.addOnClickListener(R.id.notePullOff);
                helper.addOnClickListener(R.id.noteModify);
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
                switch (view.getId()) {
                    // 已上架 - 下架
                    case R.id.notePullOff:
                        notePullOff(view, data.get(position).getId());
                        break;

                    // 已上架 - 修改
                    case R.id.noteModify:
                        noteModify(data.get(position).getId());
                        break;

                    default:
                        break;
                }
            }

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (type) {
                    // 持有中
                    case Constant.NOTE_HOLDING:
                        if (visible.get()) {
                            NoteVM vm = data.get(position);
                            if (vm.isOverdue()) {
                                ToastUtil.toastNow(R.string.note_overdue);
                            } else {
                                vm.setSelect();
                            }
                        } else {
                            ARouter.getInstance().build(RouterUrl.MY_NOTE_DETAIL)
                                    .withString(BundleKeys.ID, data.get(position).getId())
                                    .navigation();
                        }
                        break;

                    // 已上架
                    case Constant.NOTE_PUT_ON:
                        ARouter.getInstance().build(RouterUrl.MY_NOTE_DETAIL)
                                .withString(BundleKeys.ID, data.get(position).getId())
                                .navigation();
                        break;

                    // 历史记录
                    case Constant.NOTE_HISTORY:
                        ARouter.getInstance().build(RouterUrl.MY_NOTE_DETAIL)
                                .withString(BundleKeys.ID, data.get(position).getId())
                                .navigation();
                        break;
                }
            }
        });
    }

    /**
     * 纯票交易点击
     */
    public void transactionClick(View view) {
        setMode(Constant.NUMBER_2);
        visible.set(true);
        getListener().setRefreshEnable(false);
    }

    /**
     * 上架点击
     */
    public void putOnClick(View view) {
        setMode(Constant.NUMBER_3);
        visible.set(true);
        getListener().setRefreshEnable(false);
    }

    /**
     * 全选点击
     */
    public void selectAllClick(View view) {
        if (view instanceof CheckBox) {
            for (NoteVM vm : data) {
                if (!vm.isOverdue()) {
                    vm.select.set(((CheckBox) view).isChecked());
                }
            }
        }
    }

    /**
     * 去纯票交易页面
     */
    public void goTransactionClick(View view) {
        ArrayList<NoteInfo> list = new ArrayList<>();
        for (NoteVM vm : data) {
            if (vm.select.get()) {
                list.add(vm);
            }
        }
        if (list.size() > 0) {
            ARouter.getInstance().build(RouterUrl.PURE_TRANSACTION)
                    .withParcelableArrayList(BundleKeys.ITEM, list)
                    .navigation();
        }
    }

    /**
     * 去上架页面
     */
    public void goPutOnClick(View view) {
        String id = "";
        for (NoteVM vm : data) {
            if (vm.select.get()) {
                id += vm.getId() + ",";
            }
        }
        if (id.length() > 0) {
            ARouter.getInstance().build(RouterUrl.PURE_NOTE_PUT_ON)
                    .withString(BundleKeys.ID, id.substring(0, id.length() - 1))
                    .navigation();
        }
    }

    /**
     * 已上架 - 下架
     */
    private void notePullOff(View view, String id) {
        ButtonOperateLogic.getInstance().execute(view.getContext(), ButtonOperateLogic.PULL_OFF_NOTE, id, "");
    }

    /**
     * 已上架 - 修改
     */
    private void noteModify(String id) {
        ARouter.getInstance().build(RouterUrl.PURE_MODIFY_NOTE)
                .withString(BundleKeys.ID, id)
                .navigation();
    }

    /**
     * 网络请求
     */
    public void reqData() {
        String searchType;
        if (Constant.NOTE_HOLDING.equals(type)) {
            // 持有中
            searchType = "1001";
        } else if (Constant.NOTE_PUT_ON.equals(type)) {
            // 已上架
            searchType = "1002";
        } else {
            // 历史票据
            searchType = "1003";
        }

        Call<HttpResult<ListData<MyNoteRec>>> call = RDClient.getService(PureService.class).getNoteList(searchType, getListener().getCurrent());
        call.enqueue(new RequestCallBack<HttpResult<ListData<MyNoteRec>>>(getListener(), getPlaceHolderType()) {
            @Override
            public void onSuccess(Call<HttpResult<ListData<MyNoteRec>>> call, Response<HttpResult<ListData<MyNoteRec>>> response) {

                converter(response.body().getData().getList());
            }
        });
    }

    /**
     * 数据类型转换
     */
    private void converter(List<MyNoteRec> list) {
        getListener().clear(data);
        if (null == list || list.isEmpty()) {
            setPlaceHolderType(PlaceholderLayout.EMPTY);
        } else {
            setPlaceHolderType(PlaceholderLayout.SUCCESS);
            for (MyNoteRec rec : list) {
                NoteVM vm = new NoteVM(type);
                vm.setNoteId(rec.getBillId());
                vm.setId(rec.getBillNo());
                vm.setAmount(rec.getBillAmount());
                vm.setProperty(rec.getBillAttributeText());
                vm.setType(rec.getBillTypeText());
                vm.setDueDate(rec.getDueDate());
                vm.setTransferState(rec.getTransferState());
                vm.setOverdue(rec.getOverdue());
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
     * 当前是否在选择模式
     */
    public boolean exitSelectMode() {
        if (mode == Constant.NUMBER_2 || mode == Constant.NUMBER_3) {
            setMode(Constant.NUMBER_1);
            visible.set(false);
            // 允许刷新
            getListener().setRefreshEnable(true);
            // 全部重置为未选择
            for (NoteVM vm : data) {
                vm.select.set(false);
            }
            checkable.set(false);
            return true;
        } else {
            return false;
        }
    }
}