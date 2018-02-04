package com.rd.hnlf.module.user.viewControl;

import android.app.Activity;
import android.content.Intent;
import android.databinding.Bindable;
import android.databinding.ViewDataBinding;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

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
import com.rd.hnlf.module.common.viewModel.bean.KVPBean;
import com.rd.hnlf.module.user.ui.activity.BankChooseAct;
import com.rd.hnlf.network.RDClient;
import com.rd.hnlf.network.RequestCallBack;
import com.rd.hnlf.network.api.UserService;
import com.rd.network.entity.HttpResult;
import com.rd.network.entity.ListData;
import com.rd.tools.utils.AndroidUtil;
import com.rd.tools.utils.ContextHolder;
import com.rd.views.recyclerView.DividerLine;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/28 10:01
 * <p/>
 * Description: {@link BankChooseAct}
 */
public class BankChooseCtrl extends BaseListControl {
    private ArrayList<KVPBean> data;
    /** 银行编码 */
    private String             code;
    /** 搜索内容 */
    private String             content;

    public BankChooseCtrl(String code) {
        data = new ArrayList<>();
        this.code = code;
        initAdapter();
        reqData();
    }

    /**
     * 初始化 RecyclerView 的 Adapter
     */
    private void initAdapter() {
        setLayoutManagerType(Constant.NUMBER_0);
        setItemDecorationType(DividerLine.HORIZONTAL);
        setRecyclerAdapter(new BaseDataBindingAdapter<KVPBean, BaseDataBindingViewHolder>(R.layout.user_bank_choose_item, data) {
            @Override
            protected void convert(BaseDataBindingViewHolder helper, KVPBean item) {
                ViewDataBinding binding = helper.getBinding();
                binding.setVariable(BR.item, item);
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
                KVPBean bean = data.get(position);
                if (TextUtils.isEmpty(bean.getCode())) {
                    return;
                }

                Activity activity = AndroidUtil.getActivity(view);

                Intent intent = new Intent();
                intent.putExtra(BundleKeys.ITEM, bean);

                activity.setResult(Activity.RESULT_OK, intent);
                activity.finish();
            }
        });
        getListener().setRefreshEnable(false);
    }

    /**
     * 网络请求
     */
    private void reqData() {
        if (TextUtils.isEmpty(code)) {
            chooseBank();
        } else {
            chooseBranch();
        }
    }

    /**
     * 获取开户银行列表
     */
    private void chooseBank() {
        Call<HttpResult<ListData<KVPBean>>> call = RDClient.getService(UserService.class).getBankList(content, getListener().getCurrent());
        call.enqueue(new RequestCallBack<HttpResult<ListData<KVPBean>>>(getListener(), null) {
            @Override
            public void onSuccess(Call<HttpResult<ListData<KVPBean>>> call, Response<HttpResult<ListData<KVPBean>>> response) {
                converter(response.body().getData().getList());
            }
        });
    }

    /**
     * 获取开户支行列表
     */
    private void chooseBranch() {
        Call<HttpResult<ListData<KVPBean>>> call = RDClient.getService(UserService.class).getBranchList(code, content, getListener().getCurrent(), 20);
        call.enqueue(new RequestCallBack<HttpResult<ListData<KVPBean>>>(getListener(), null) {
            @Override
            public void onSuccess(Call<HttpResult<ListData<KVPBean>>> call, Response<HttpResult<ListData<KVPBean>>> response) {
                converter(response.body().getData().getList());
            }
        });
    }

    /**
     * 数据类型转换
     */
    private void converter(List<KVPBean> list) {
        getListener().clear(data);
        if (null == list || list.isEmpty()) {
            data.add(new KVPBean(null, ContextHolder.getContext().getString(R.string.bankcard_search_error, content)));
        } else {
            data.addAll(list);
        }
    }

    /**
     * 搜索
     */
    public boolean searchAction(TextView view, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            AndroidUtil.closedInputMethod();
            getListener().refresh();
        }
        return true;
    }

    /**
     * 取消按钮
     */
    public void cancelClick(View view) {
        AndroidUtil.getActivity(view).onBackPressed();
    }

    @Bindable
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        notifyPropertyChanged(BR.content);
    }
}