package com.rd.hnlf.network;

import android.databinding.ObservableInt;

import com.rd.hnlf.R;
import com.rd.hnlf.common.ui.AbsRefreshAndLoadMore;
import com.rd.network.entity.HttpResult;
import com.rd.network.entity.ListData;
import com.rd.network.exception.ApiException;
import com.rd.tools.utils.ToastUtil;
import com.rd.views.PlaceholderLayout;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/4/7 17:29
 * <p/>
 * Description: 网络请求回调封装类
 */
public abstract class RequestCallBack<T> implements Callback<T> {
    public abstract void onSuccess(Call<T> call, Response<T> response);

    private AbsRefreshAndLoadMore listener;
    private LoadingDialog         loadingDialog;
    private ObservableInt         placeHolderType;

    public RequestCallBack() {
    }

    public RequestCallBack(AbsRefreshAndLoadMore listener, ObservableInt placeHolderType) {
        this.listener = listener;
        this.placeHolderType = placeHolderType;
    }

    public RequestCallBack(LoadingDialog loadingDialog) {
        this.loadingDialog = loadingDialog;
    }

    public RequestCallBack(AbsRefreshAndLoadMore listener, LoadingDialog loadingDialog, ObservableInt placeHolderType) {
        this.listener = listener;
        this.loadingDialog = loadingDialog;
        this.placeHolderType = placeHolderType;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        complete(response);
        if (null != response.body() && response.isSuccessful()) {
            onSuccess(call, response);
            if (null != loadingDialog) {
                loadingDialog.loadSuccess();
            }
        } else {
            onFailed(call, response);
            if (null != loadingDialog) {
                loadingDialog.loadFailed();
            }
        }
    }

    public void onFailed(Call<T> call, Response<T> response) {
        complete(null);
        if (null == response.body() || response.code() >= 400) {
            setType(PlaceholderLayout.NO_NETWORK);
            ToastUtil.toast(R.string.app_network_error);
        } else {
            setType(PlaceholderLayout.ERROR);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        complete(null);
        if (null != loadingDialog) {
            loadingDialog.loadFailed();
        }
        if (t instanceof ApiException) {
            ExceptionHandling.operate(((ApiException) t).getResult());
        }

        if (t instanceof IOException) {
            setType(PlaceholderLayout.NO_NETWORK);
            ToastUtil.toast(R.string.app_network_error);
        } else {
            setType(PlaceholderLayout.ERROR);
        }
        t.printStackTrace();
    }

    private void setType(int type) {
        if (null != placeHolderType) {
            placeHolderType.set(type);
        }
    }

    private void complete(Response<T> response) {
        if (null != listener) {
            if (null != response
                    && response.body() instanceof HttpResult
                    && ((HttpResult) response.body()).getData() instanceof ListData) {
                listener.setPageMo(((ListData) ((HttpResult) response.body()).getData()).getPage());
            }
            listener.complete();
        }
    }
}