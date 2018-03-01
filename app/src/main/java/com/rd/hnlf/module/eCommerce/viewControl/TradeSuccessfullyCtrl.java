package com.rd.hnlf.module.eCommerce.viewControl;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rd.hnlf.R;
import com.rd.hnlf.module.eCommerce.ui.activity.TradeSuccessfullyAct;
import com.rd.hnlf.router.RouterUrl;

/**
 * Author: chaiyuan
 * E-mail: 275108586@qq..com
 * Date: 2018/2/28
 * <p/>
 * Description: {@link TradeSuccessfullyAct}
 */
public class TradeSuccessfullyCtrl {

    private Context context;
    public TradeSuccessfullyCtrl(Context context){
        this.context = context;
        startDialog();

    }




    /**
     * 返回首页
     */
    public void homeBackClick(View view){
        ARouter.getInstance().build(RouterUrl.MAIN).navigation();
    }

    private void startDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("票付安温馨提示");
        builder.setMessage("请通过网银向如下账户进行转账，务必在15分钟内完成!");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setIcon(R.mipmap.ic_launcher);
        builder.create().show();
    }
//    public TradeSuccessfullyVM getViewModel() {
//        return viewModel;
//    }

}
