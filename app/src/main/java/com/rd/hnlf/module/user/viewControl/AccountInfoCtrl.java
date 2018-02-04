package com.rd.hnlf.module.user.viewControl;

import android.text.TextUtils;
import android.view.View;

import com.bigkoo.pickerview.TimePickerView;
import com.rd.hnlf.R;
import com.rd.hnlf.module.common.dataModel.submit.JsonSub;
import com.rd.hnlf.module.user.dataModel.receive.AccountInfoRec;
import com.rd.hnlf.module.user.dataModel.submit.AccountInfoSub;
import com.rd.hnlf.module.user.ui.activity.AccountInfoAct;
import com.rd.hnlf.module.user.viewModel.AccountInfoVM;
import com.rd.hnlf.network.RDClient;
import com.rd.hnlf.network.RequestCallBack;
import com.rd.hnlf.network.api.UserService;
import com.rd.hnlf.utils.LoadingDialogUtils;
import com.rd.network.entity.HttpResult;
import com.rd.tools.utils.ActivityManage;
import com.rd.tools.utils.AndroidUtil;
import com.rd.tools.utils.DateUtil;
import com.rd.tools.utils.ToastUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/18 18:07
 * <p/>
 * Description: {@link AccountInfoAct}
 */
public class AccountInfoCtrl {
    private AccountInfoVM viewModel;
    /** 生日选择控件 */
    private TimePickerView timePickerView = null;
    /** 生日选择开始日期 */
    private Calendar startDate;
    /** 生日选择结束日期 */
    private Calendar endDate;
    /** 生日选择默认日期 */
    private Calendar initDate;

    public AccountInfoCtrl() {
        viewModel = new AccountInfoVM();
        // 初始化生日选择控件
        startDate = Calendar.getInstance();
        startDate.set(1900, 0, 1);
        endDate = Calendar.getInstance();
        endDate.setTimeInMillis(System.currentTimeMillis());
        initDate = Calendar.getInstance();
        initDate.set(1990, 0, 1);
        reqData();
    }

    private void reqData() {
        Call<HttpResult<AccountInfoRec>> call = RDClient.getService(UserService.class).getAccountInfo();
        call.enqueue(new RequestCallBack<HttpResult<AccountInfoRec>>() {
            @Override
            public void onSuccess(Call<HttpResult<AccountInfoRec>> call, Response<HttpResult<AccountInfoRec>> response) {
                convert(response.body().getData());
            }
        });
    }

    /**
     * 数据类型转换
     */
    private void convert(AccountInfoRec rec) {
        viewModel.setCompanyName(rec.getEnterpriseName());
        viewModel.setCompanyAddress(rec.getEnterpriseAddress());
        viewModel.setContacts(rec.getName());
        viewModel.setGender(rec.getSex());
        viewModel.setBirthday(rec.getBirthday());
        viewModel.setEmail(rec.getEmail());
        try {
            if (!TextUtils.isEmpty(viewModel.getBirthday())) {
                initDate.setTimeInMillis(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(viewModel.getBirthday()).getTime());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生日点击
     */
    public void birthdayClick(View view) {
        AndroidUtil.closedInputMethod();
        if (null == timePickerView) {
            timePickerView = new TimePickerView.Builder(AndroidUtil.getActivity(view), new TimePickerView.OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    viewModel.setBirthday(DateUtil.formatter(DateUtil.Format.DATE, date.getTime()));
                }
            })
                    // 年月日时分秒 的显示与否，不设置则默认全部显示
                    .setType(new boolean[]{true, true, true, false, false, false})
                    // 是否显示中间选中项的 label 文字
                    .isCenterLabel(true)
                    .setRangDate(startDate, endDate)
                    .build();
            timePickerView.setDate(initDate);
        }
        timePickerView.show();
    }

    /**
     * 保存点击
     */
    public void submitClick(View view) {
        if (TextUtils.isEmpty(viewModel.getCompanyName())) {
            ToastUtil.toast(R.string.account_info_company_name_hint);
        } else if (TextUtils.isEmpty(viewModel.getCompanyAddress())) {
            ToastUtil.toast(R.string.account_info_company_address_hint);
        } else if (TextUtils.isEmpty(viewModel.getContacts())) {
            ToastUtil.toast(R.string.account_info_contacts_hint);
        } else if (TextUtils.isEmpty(viewModel.getBirthday())) {
            ToastUtil.toast(view.getContext().getString(R.string.validate_please_choose, view.getContext().getString(R.string.account_info_birthday)));
        } else if (TextUtils.isEmpty(viewModel.getEmail())) {
            ToastUtil.toast(R.string.account_info_email_hint);
        } else {
            doSave(view);
        }
    }

    /**
     * 发送保存请求
     */
    private void doSave(View view) {
        AccountInfoSub sub = new AccountInfoSub();
        sub.setEnterpriseName(viewModel.getCompanyName());
        sub.setEnterpriseAddress(viewModel.getCompanyAddress());
        sub.setName(viewModel.getContacts());
        sub.setSex(viewModel.getGender());
        sub.setBirthday(viewModel.getBirthday());
        sub.setEmail(viewModel.getEmail());
        Call<HttpResult> call = RDClient.getService(UserService.class).saveOrUpdateAccountInfo(new JsonSub().setAccountInfo(sub));
        call.enqueue(new RequestCallBack<HttpResult>(new LoadingDialogUtils().show(view.getContext())) {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                ToastUtil.toast(response.body().getMsg());
                ActivityManage.finish();
            }
        });
    }

    public AccountInfoVM getViewModel() {
        return viewModel;
    }
}