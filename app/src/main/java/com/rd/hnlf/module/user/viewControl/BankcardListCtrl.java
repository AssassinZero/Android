package com.rd.hnlf.module.user.viewControl;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.rd.hnlf.R;
import com.rd.hnlf.common.BundleKeys;
import com.rd.hnlf.common.Constant;
import com.rd.hnlf.common.ui.AbsRefreshAndLoadMore;
import com.rd.hnlf.common.ui.BaseListControl;
import com.rd.hnlf.module.user.dataModel.receive.BankcardRec;
import com.rd.hnlf.module.user.dataModel.receive.OauthTokenRec;
import com.rd.hnlf.module.user.ui.activity.BankcardListAct;
import com.rd.hnlf.module.user.viewModel.BankcardAdapter;
import com.rd.hnlf.module.user.viewModel.BankcardVM;
import com.rd.hnlf.network.RDClient;
import com.rd.hnlf.network.RequestCallBack;
import com.rd.hnlf.network.api.UserService;
import com.rd.hnlf.router.RouterUrl;
import com.rd.hnlf.utils.DialogUtils;
import com.rd.logic.info.SharedInfo;
import com.rd.network.entity.HttpResult;
import com.rd.network.entity.ListData;
import com.rd.tools.utils.AndroidUtil;
import com.rd.tools.utils.ContextHolder;
import com.rd.views.PlaceholderLayout;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.OnSweetClickListener;
import cn.pedant.SweetAlert.SweetAlertDialog;
import in.srain.cube.views.ptr.PtrFrameLayout;
import retrofit2.Call;
import retrofit2.Response;

import static com.rd.basic.BaseActivity.REQUEST_CODE;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/21 15:24
 * <p/>
 * Description: {@link BankcardListAct}
 */
public class BankcardListCtrl extends BaseListControl {
    private ArrayList<BankcardVM> data;

    public BankcardListCtrl() {
        data = new ArrayList<>();
        initAdapter();
        reqData();
    }

    /**
     * 初始化 RecyclerView 的 Adapter
     */
    private void initAdapter() {
        setLayoutManagerType(Constant.NUMBER_0);
        setRecyclerAdapter(new BankcardAdapter(data));
        // 刷新 加载 监听
        setListener(new AbsRefreshAndLoadMore() {
            @Override
            public void refreshInit(PtrFrameLayout ptrFrame) {
                ptrFrame.setEnabled(false);
            }

            @Override
            public void loadMoreInit(BaseQuickAdapter adapter) {
                adapter.setEnableLoadMore(false);
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
                if (view.getId() == R.id.deleteBtn) {
                    Context context = view.getContext();
                    DialogUtils.showDialog(context, context.getString(R.string.bankcard_list_delete_bankcard, data.get(position).getBankcardShort()),
                            context.getString(R.string.dialog_no), context.getString(R.string.dialog_yes)
                            , new OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            }, new OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                    deleteBankcard(view, position);
                                }
                            });
                }
            }

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                BankcardVM vm = data.get(position);
                if (vm.isNormal()) {
                    int p = vm.getItemType() == Constant.NUMBER_1 ? Constant.NUMBER__1 : position;

                    ARouter.getInstance().build(RouterUrl.USER_BANKCARD_EDIT)
                            .withInt(BundleKeys.POSITION, p)
                            .withObject(BundleKeys.ITEM, p == Constant.NUMBER__1 ? null : vm)
                            .navigation(AndroidUtil.getActivity(view), REQUEST_CODE);
                }
            }
        });
    }

    /**
     * 网络请求
     */
    private void reqData() {
        String                                  mobile = SharedInfo.getInstance().getEntity(OauthTokenRec.class).getMobile();
        Call<HttpResult<ListData<BankcardRec>>> call   = RDClient.getService(UserService.class).getBankcardList(mobile);
        call.enqueue(new RequestCallBack<HttpResult<ListData<BankcardRec>>>(getListener(), getPlaceHolderType()) {
            @Override
            public void onSuccess(Call<HttpResult<ListData<BankcardRec>>> call, Response<HttpResult<ListData<BankcardRec>>> response) {
                converter(response.body().getData().getList());
            }
        });
    }

    /**
     * 数据类型转换
     */
    private void converter(List<BankcardRec> list) {
        getListener().clear(data);
        if (null == list || list.isEmpty()) {
            setPlaceHolderType(PlaceholderLayout.EMPTY);
        } else {
            setPlaceHolderType(PlaceholderLayout.SUCCESS);
            for (BankcardRec rec : list) {
                BankcardVM vm = new BankcardVM();
                vm.setId(rec.getId());
                vm.setAccountName(rec.getAccountName());
                vm.setBankName(rec.getBankName());
                vm.setBankNo(rec.getBankNo());
                vm.setBranchName(rec.getOpeningBank());
                vm.setBranchNo(rec.getBankCode());
                vm.setBankcard(rec.getBankAccount());
                vm.setAccountType(rec.getType());
                data.add(vm);
            }
        }
        // 取消列表中的添加银行卡按钮
        // data.add(new BankcardVM(Constant.NUMBER_1));
    }

    /**
     * 更新数据
     */
    public void updateData(int position, BankcardVM vm) {
        if (position < 0) {
            position = data.size();
            // position = data.size() - 1;
            data.add(position, vm);
            getRecyclerAdapter().notifyItemInserted(position);
        } else {
            data.remove(position);
            data.add(position, vm);
            getRecyclerAdapter().notifyItemChanged(position);
        }
    }

    /**
     * 发送删除银行卡请求
     */
    private void deleteBankcard(View view, final int position) {
        Call<HttpResult> call = RDClient.getService(UserService.class).deleteBankcard(data.get(position).getId());
        call.enqueue(new RequestCallBack<HttpResult>() {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                data.remove(position);
                getRecyclerAdapter().notifyItemRemoved(position);
                ContextHolder.getContext().sendBroadcast(new Intent(BundleKeys.REFRESH_LIST));
            }
        });
    }
}