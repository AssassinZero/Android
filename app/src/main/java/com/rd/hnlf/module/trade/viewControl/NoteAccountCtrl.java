package com.rd.hnlf.module.trade.viewControl;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rd.basic.BaseActivity;
import com.rd.hnlf.BR;
import com.rd.hnlf.R;
import com.rd.hnlf.common.BundleKeys;
import com.rd.hnlf.common.Constant;
import com.rd.hnlf.module.eCommerce.dataModel.submit.TradeNoteInfoSub;
import com.rd.hnlf.module.trade.ui.activity.NoteAccountAct;
import com.rd.hnlf.module.trade.viewModel.NoteAccountVM;
import com.rd.hnlf.module.user.dataModel.receive.BankcardRec;
import com.rd.hnlf.module.user.dataModel.receive.OauthTokenRec;
import com.rd.hnlf.module.user.viewModel.BankcardVM;
import com.rd.hnlf.network.RDClient;
import com.rd.hnlf.network.RequestCallBack;
import com.rd.hnlf.network.api.TradeService;
import com.rd.hnlf.network.api.UserService;
import com.rd.hnlf.router.RouterUrl;
import com.rd.hnlf.utils.InputCheck;
import com.rd.logic.info.SharedInfo;
import com.rd.network.entity.HttpResult;
import com.rd.network.entity.ListData;
import com.rd.tools.utils.AndroidUtil;
import com.rd.tools.utils.RegularUtil;
import com.rd.tools.utils.ToastUtil;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/29 15:58
 * <p/>
 * Description: {@link NoteAccountAct}
 */
public class NoteAccountCtrl extends BaseObservable {
    /**
     * -1 - 代理买卖(均可编辑)
     * 收票方 - 输入
     * 持票方 - 输入 + 新增
     * <p />
     * 0 -  我要买票(收票方账号不可编辑)
     * 收票方 - 选择
     * 持票方 - 输入 + 新增
     * <p />
     * 1 -  我要卖票(持票方账号不可编辑)
     * 收票方 - 输入
     * 持票方 - 选择 + 新增
     */
    private String           type;
    /** 需要上传的数据内容 */
    private TradeNoteInfoSub infoSub;
    private NoteAccountVM    viewModel;
    /**
     * 收款账户是否和背书账户一致
     * 0 - 是
     * 1 - 否
     */
    private String           same;

    public NoteAccountCtrl(String type, TradeNoteInfoSub infoSub) {
        this.type = type;
        this.infoSub = infoSub;
        viewModel = new NoteAccountVM();
        if (!TextUtils.isEmpty(infoSub.getId())) {
            // 收票方
            viewModel.getReceiverInfo().setAccount(infoSub.getCollectorAccountId());
            viewModel.getReceiverInfo().setBankcard(infoSub.getCollectingBankAccount());
            getReceiverBankAccount();
            // 持票方
            viewModel.getHolderInfo().setAccount(infoSub.getHoldAccountId());
            same = infoSub.getUniformity();
            viewModel.getHolderInfo().setBankcard(infoSub.getBankAccount());
            viewModel.getHolderInfo().setAccountName(infoSub.getHoldAccountName());
            viewModel.getHolderInfo().setBranchName(infoSub.getHoldAccountSubbranchName());
            viewModel.getHolderInfo().setBranchNo(infoSub.getHoldAccountSubbranchCode());
        }

        OauthTokenRec rec    = SharedInfo.getInstance().getEntity(OauthTokenRec.class);
        String        mobile = rec.getMobile();
        if (Constant.STATUS_0.equals(type)) {
            viewModel.getReceiverInfo().setAccount(mobile);
            getAllReceiverBankAccount(mobile);
        } else if (Constant.STATUS_1.equals(type)) {
            viewModel.getHolderInfo().setAccount(mobile);
        }

        // 设置交易类型
        if (rec.isNormal()) {
            // 普通用户发起卖票交易
            infoSub.setBusinessType("0102");
        } else if (rec.isAgent()) {
            // 代理商发起撮合交易
            infoSub.setBusinessType("0200");
        } else {
            if (Constant.STATUS_0.equals(type)) {
                // 会员发起收票交易
                infoSub.setBusinessType("0100");
            } else {
                // 会员发起卖票交易
                infoSub.setBusinessType("0101");
            }
        }
    }

    /**
     * 收票方帐号查询
     */
    public boolean searchHolderAction(TextView view, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            AndroidUtil.closedInputMethod();
            getReceiverBankAccount();
        }
        return true;
    }

    /**
     * 接口获取收票方账户信息(所有签约卡)
     */
    private void getAllReceiverBankAccount(String mobile) {
        if (!RegularUtil.isPhone(mobile)) {
            return;
        }
        Call<HttpResult<ListData<BankcardRec>>> call = RDClient.getService(UserService.class).getBankcardList(mobile);
        call.enqueue(new RequestCallBack<HttpResult<ListData<BankcardRec>>>() {
            @Override
            public void onSuccess(Call<HttpResult<ListData<BankcardRec>>> call, Response<HttpResult<ListData<BankcardRec>>> response) {
                HashMap<String, BankcardVM> bankcardMap = new HashMap<>();
                for (BankcardRec rec : response.body().getData().getList()) {
                    if ("20".equals(rec.getType())) {
                        BankcardVM vm = new BankcardVM();
                        vm.setBankcard(rec.getBankAccount());
                        vm.setAccountName(rec.getAccountName());
                        vm.setBranchName(rec.getOpeningBank());
                        vm.setBranchNo(rec.getBankCode());
                        bankcardMap.put(vm.getBankcard(), vm);
                    }
                }
                viewModel.getReceiverInfo().setBankcardMap(bankcardMap);
            }
        });
    }

    /**
     * 接口获取收票方账户信息(单张签约卡)
     */
    private void getReceiverBankAccount() {
        String mobile   = viewModel.getReceiverInfo().getAccount();
        String bankcard = viewModel.getReceiverInfo().getBankcard();
        if (TextUtils.isEmpty(mobile)) {
            ToastUtil.toast(R.string.note_receiver_account_hint);
        } else if (!RegularUtil.isPhone(mobile)) {
            ToastUtil.toast(R.string.note_receiver_account_error);
        } else if (!InputCheck.checkBankcard(bankcard)) {
            ToastUtil.toast(R.string.note_receiver_bankcard_error);
        } else {
            Call<HttpResult<BankcardRec>> call = RDClient.getService(TradeService.class).getSignBankAccount(mobile, bankcard);
            call.enqueue(new RequestCallBack<HttpResult<BankcardRec>>() {
                @Override
                public void onSuccess(Call<HttpResult<BankcardRec>> call, Response<HttpResult<BankcardRec>> response) {
                    BankcardRec rec = response.body().getData();
                    if ("20".equals(rec.getType())) {
                        viewModel.getReceiverInfo().setAccountName(rec.getAccountName());
                        viewModel.getReceiverInfo().setBranchName(rec.getOpeningBank());
                        viewModel.getReceiverInfo().setBranchNo(rec.getBankCode());
                    } else {
                        ToastUtil.toast(R.string.bankcard_bankcard_type_error);
                    }
                }

                @Override
                public void onFailure(Call<HttpResult<BankcardRec>> call, Throwable t) {
                    super.onFailure(call, t);
                    viewModel.getReceiverInfo().setAccountName("");
                    viewModel.getReceiverInfo().setBranchName("");
                    viewModel.getReceiverInfo().setBranchNo("");
                }
            });
        }
    }

    /**
     * 接口获取持票方账户信息(没限制)
     */
    private void getHolderBankAccount(String mobile) {
        if (!RegularUtil.isPhone(mobile) || !isVisible()) {
            return;
        }
        Call<HttpResult<ListData<BankcardRec>>> call = RDClient.getService(TradeService.class).getAllBankAccount(mobile);
        call.enqueue(new RequestCallBack<HttpResult<ListData<BankcardRec>>>() {
            @Override
            public void onSuccess(Call<HttpResult<ListData<BankcardRec>>> call, Response<HttpResult<ListData<BankcardRec>>> response) {
                HashMap<String, BankcardVM> bankcardMap = new HashMap<>();
                for (BankcardRec rec : response.body().getData().getList()) {
                    BankcardVM vm = new BankcardVM();
                    vm.setBankcard(rec.getBankAccount());
                    vm.setAccountName(rec.getAccountName());
                    vm.setBranchName(rec.getOpeningBank());
                    vm.setBranchNo(rec.getBankCode());
                    bankcardMap.put(vm.getBankcard(), vm);
                }
                viewModel.getHolderInfo().setBankcardMap(bankcardMap);
            }
        });
    }

    /**
     * 新增持票方账户
     */
    public void addNoteClick(View view) {
        String mobile = viewModel.getHolderInfo().getAccount();
        if (TextUtils.isEmpty(mobile)) {
            ToastUtil.toast(R.string.note_holder_account_hint);
        } else if (!RegularUtil.isPhone(mobile)) {
            ToastUtil.toast(R.string.note_holder_account_error);
        } else {
            ARouter.getInstance().build(RouterUrl.USER_BANKCARD_EDIT)
                    .withInt(BundleKeys.POSITION, Constant.NUMBER__2)
                    .withString(BundleKeys.ID, mobile)
                    .navigation(AndroidUtil.getActivity(view), BaseActivity.REQUEST_CODE);
        }
    }

    /**
     * 新增持票方账户回调
     */
    public void addNote(BankcardVM info) {
        viewModel.getHolderInfo().setSelectedBankcard(info);
    }

    /**
     * 下一步点击
     */
    public void nextClick(View view) {
        Context context = view.getContext();
        if (TextUtils.isEmpty(viewModel.getReceiverInfo().getAccount())) {
            ToastUtil.toast(R.string.note_receiver_account_hint);
        } else if (!RegularUtil.isPhone(viewModel.getReceiverInfo().getAccount())) {
            ToastUtil.toast(R.string.note_receiver_account_error);
        } else if (TextUtils.isEmpty(viewModel.getReceiverInfo().getBankcard())) {
            ToastUtil.toast(R.string.bankcard_bankcard_hint);
        } else if (TextUtils.isEmpty(viewModel.getHolderInfo().getAccount())) {
            ToastUtil.toast(R.string.note_holder_account_hint);
        } else if (!RegularUtil.isPhone(viewModel.getHolderInfo().getAccount())) {
            ToastUtil.toast(R.string.note_holder_account_error);
        } else if (isVisible() && TextUtils.isEmpty(viewModel.getHolderInfo().getBankcard())) {
            ToastUtil.toast(context.getString(R.string.validate_cannot_be_empty, context.getString(R.string.bankcard_bankcard)));
        } else if (TextUtils.isEmpty(viewModel.getReceiverInfo().getAccountName())) {
            ToastUtil.toast(R.string.note_receiver_account_info);
        } else if (isVisible() && TextUtils.isEmpty(viewModel.getHolderInfo().getAccountName())) {
            ToastUtil.toast(R.string.note_holder_account_info);
        } else {
            doNext(view);
        }
    }

    private void doNext(View view) {
        // 收票方信息
        infoSub.setCollectorAccountId(viewModel.getReceiverInfo().getAccount());
        infoSub.setCollectingBankAccount(viewModel.getReceiverInfo().getBankcard());

        // 持票方信息
        infoSub.setHoldAccountId(viewModel.getHolderInfo().getAccount());
        // 10 - 是, 20 - 否
        infoSub.setUniformity(getSame());
        infoSub.setBankAccount(viewModel.getHolderInfo().getBankcard());
        infoSub.setHoldAccountName(viewModel.getHolderInfo().getAccountName());
        infoSub.setBankCode(viewModel.getHolderInfo().getBankNo());
        infoSub.setHoldAccountSubbranchName(viewModel.getHolderInfo().getBranchName());
        infoSub.setHoldAccountSubbranchCode(viewModel.getHolderInfo().getBranchNo());

        ARouter.getInstance().build(RouterUrl.TRADE_NOTE_QUOTATION)
                .withObject(BundleKeys.ITEM, infoSub)
                .navigation(AndroidUtil.getActivity(view), BaseActivity.REQUEST_CODE_CLOSED);
    }

    @Bindable
    public String getSame() {
        return same;
    }

    public void setSame(String same) {
        this.same = same;
        if (Constant.STATUS_1.equals(type)) {
            getHolderBankAccount(viewModel.getHolderInfo().getAccount());
        }
        notifyPropertyChanged(BR.same);
        notifyPropertyChanged(BR.visible);
    }

    /**
     * 是否显示持票方账户的银行信息
     */
    @Bindable
    public boolean isVisible() {
        return "20".equals(getSame());
    }

    public NoteAccountVM getViewModel() {
        return viewModel;
    }

    public String getType() {
        return type;
    }
}