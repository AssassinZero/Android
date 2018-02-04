package com.rd.hnlf.module.trade.viewControl;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.bigkoo.pickerview.TimePickerView;
import com.rd.hnlf.R;
import com.rd.hnlf.common.BundleKeys;
import com.rd.hnlf.module.trade.ui.activity.AddNoteAct;
import com.rd.hnlf.module.trade.viewModel.NoteDealingVM;
import com.rd.hnlf.network.RDClient;
import com.rd.hnlf.network.RequestCallBack;
import com.rd.hnlf.network.api.TradeService;
import com.rd.network.entity.HttpResult;
import com.rd.tools.utils.ActivityManage;
import com.rd.tools.utils.AndroidUtil;
import com.rd.tools.utils.ToastUtil;

import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/29 14:31
 * <p/>
 * Description:{@link AddNoteAct}
 */
public class AddNoteCtrl {
    private NoteDealingVM viewModel;
    /** 到期日选择控件 */
    private TimePickerView timePickerView = null;
    /** 到期日选择开始日期 */
    private Calendar startDate;
    /** 到期日选择结束日期 */
    private Calendar endDate;
    /** 默认选中的日期 */
    private Calendar selectedDate;

    public AddNoteCtrl(NoteDealingVM item) {
        startDate = Calendar.getInstance();
        startDate.setTimeInMillis(System.currentTimeMillis());
        endDate = Calendar.getInstance();
        endDate.setTimeInMillis(startDate.getTimeInMillis());
        // 加100年
        endDate.add(Calendar.YEAR, 100);

        if (null != item) {
            viewModel = item.division();
            selectedDate = Calendar.getInstance();
            selectedDate.setTimeInMillis(item.getDueDate());
        } else {
            viewModel = new NoteDealingVM();
            selectedDate = startDate;
        }
    }

    /**
     * 到期日点击
     */
    public void dueDateClick(View view) {
        AndroidUtil.closedInputMethod();
        if (null == timePickerView) {
            timePickerView = new TimePickerView.Builder(AndroidUtil.getActivity(view), new TimePickerView.OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    viewModel.setDueDate(date.getTime());
                }
            })
                    // 年月日时分秒 的显示与否，不设置则默认全部显示
                    .setType(new boolean[]{true, true, true, false, false, false})
                    // 是否显示中间选中项的 label 文字
                    .isCenterLabel(true)
                    .setRangDate(startDate, endDate)
                    .build();
            timePickerView.setDate(selectedDate);
        }
        timePickerView.show();
    }

    /**
     * 保存点击
     */
    public void submitClick(View view) {
        if (TextUtils.isEmpty(viewModel.getId())) {
            ToastUtil.toast(R.string.note_no_hint);
        } else if (TextUtils.isEmpty(viewModel.getAmount())) {
            ToastUtil.toast(R.string.note_amount_hint);
        } else if (TextUtils.isEmpty(viewModel.getDueDateStr())) {
            ToastUtil.toast(R.string.note_due_date_hint);
        } else if (TextUtils.isEmpty(viewModel.getDays())) {
            ToastUtil.toast(R.string.note_days_hint);
        } else if (viewModel.getId().length() != 6 && viewModel.getId().length() != 30) {
            ToastUtil.toast(R.string.note_no_error);
        } else {
            doSubmit(view);
        }
    }

    /**
     * 检查票据是否可交易
     */
    private void doSubmit(final View view) {
        Call<HttpResult> call = RDClient.getService(TradeService.class).checkBillIsOccupied(viewModel.getId());
        call.enqueue(new RequestCallBack<HttpResult>() {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                Intent intent = new Intent();
                intent.putExtra(BundleKeys.ITEM, viewModel.multiplication());
                AndroidUtil.getActivity(view).setResult(Activity.RESULT_OK, intent);
                ActivityManage.finish();
            }
        });
    }

    public NoteDealingVM getViewModel() {
        return viewModel;
    }
}