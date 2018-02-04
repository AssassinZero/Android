package com.rd.hnlf.module.pure.viewControl;

import com.rd.hnlf.module.eCommerce.dataModel.submit.NoteDetailRec;
import com.rd.hnlf.module.pure.ui.activity.MyNoteDetailAct;
import com.rd.hnlf.module.pure.viewModel.MyNoteDetailVM;
import com.rd.hnlf.module.pure.viewModel.bean.NegativeInfo;
import com.rd.hnlf.network.RDClient;
import com.rd.hnlf.network.RequestCallBack;
import com.rd.hnlf.network.api.PureService;
import com.rd.network.entity.HttpResult;
import com.rd.tools.utils.RandomUtil;

import java.util.ArrayList;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/22 16:49
 * <p/>
 * Description: {@link MyNoteDetailAct}
 */
public class MyNoteDetailCtrl {
    private MyNoteDetailVM viewModel;
    /** 是否信息隐藏显示 */
    private boolean        hidden;

    public MyNoteDetailCtrl(String orderNo, boolean hidden) {
        this.hidden = hidden;
        viewModel = new MyNoteDetailVM();
        reqData(orderNo);
    }

    /**
     * 网络请求
     */
    private void reqData(String orderNo) {
        Call<HttpResult<NoteDetailRec>> call = RDClient.getService(PureService.class).getNoteDetails(orderNo);
        call.enqueue(new RequestCallBack<HttpResult<NoteDetailRec>>() {
            @Override
            public void onSuccess(Call<HttpResult<NoteDetailRec>> call, Response<HttpResult<NoteDetailRec>> response) {

                NoteDetailRec rec = response.body().getData();

                converter(rec);
            }
        });
    }

    /**
     * 数据类型转换
     */
    private void converter(NoteDetailRec rec) {
        // 基本信息
        viewModel.getBasicInfo().setId(rec.getBillNo());
        viewModel.getBasicInfo().setAmount(rec.getBillAmount());
        long time = System.currentTimeMillis() - new Random().nextInt();
        viewModel.getBasicInfo().setDate(time + "");
        viewModel.getBasicInfo().setDueDate(time + new Random().nextInt() + "");
        // 出票人信息
        viewModel.getDrawerInfo().setFullName("杭州市海蓝科技股份有限公司");
        viewModel.getDrawerInfo().setAccountName("杭州市海蓝科技股份有限公司");
        viewModel.getDrawerInfo().setBranchName("招商银行湖墅支行");
        // 收款人信息
        viewModel.getPayeeInfo().setFullName("杭州市海蓝科技股份有限公司");
        viewModel.getPayeeInfo().setAccountName("杭州市海蓝科技股份有限公司");
        viewModel.getPayeeInfo().setBranchName("招商银行湖墅支行");
        // 承兑人信息
        viewModel.getAcceptorInfo().setFullName("杭州市海蓝科技股份有限公司");
        viewModel.getAcceptorInfo().setAccountName("杭州市海蓝科技股份有限公司");
        viewModel.getAcceptorInfo().setBranchName("招商银行湖墅支行");
        // 票据背面信息
        ArrayList<NegativeInfo> negativeInfo = new ArrayList<>();

        NegativeInfo info1 = new NegativeInfo();
        info1.setEndorser("杭州市海蓝科技股份有限公司");
        info1.setEndorsee("杭州市海蓝科技股份有限公司");
        info1.setDate(System.currentTimeMillis() - new Random().nextInt() + "");
        info1.setTransferable("是");
        info1.setEndorser("信息隐藏");
        info1.setEndorsee("信息隐藏");

        NegativeInfo info2 = new NegativeInfo();
        info2.setEndorser("杭州市海蓝科技股份有限公司");
        info2.setEndorsee("杭州市海蓝科技股份有限公司");
        info2.setDate(System.currentTimeMillis() - new Random().nextInt() + "");
        info2.setTransferable("否");
        info2.setEndorser("信息隐藏");
        info2.setEndorsee("信息隐藏");

        negativeInfo.add(info1);
        negativeInfo.add(info2);
        viewModel.setNegativeInfo(negativeInfo);
        if (hidden) {
            viewModel.getDrawerInfo().setFullName("信息隐藏");
            viewModel.getPayeeInfo().setFullName("信息隐藏");
            viewModel.getAcceptorInfo().setFullName("信息隐藏");
        }
    }

    public MyNoteDetailVM getViewModel() {
        return viewModel;
    }
}