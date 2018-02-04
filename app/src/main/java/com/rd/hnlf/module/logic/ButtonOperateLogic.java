package com.rd.hnlf.module.logic;

import android.content.Context;
import android.content.Intent;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rd.hnlf.R;
import com.rd.hnlf.common.BundleKeys;
import com.rd.hnlf.common.Constant;
import com.rd.hnlf.network.RDClient;
import com.rd.hnlf.network.RequestCallBack;
import com.rd.hnlf.network.api.ECommerceService;
import com.rd.hnlf.network.api.PureService;
import com.rd.hnlf.network.api.TradeService;
import com.rd.hnlf.router.RouterUrl;
import com.rd.hnlf.utils.DialogUtils;
import com.rd.hnlf.utils.LoadingDialogUtils;
import com.rd.network.entity.HttpResult;
import com.rd.tools.utils.ContextHolder;
import com.rd.tools.utils.ConverterUtil;

import cn.pedant.SweetAlert.OnSweetClickListener;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/8 10:21
 * <p/>
 * Description: 按钮逻辑处理类
 */
public class ButtonOperateLogic {
    private static final String SHOW_DETAIL       = "showDetail";
    private static final String CONFIRM_ORDER     = "confirmOrder";
    private static final String RETURN_ORDER      = "returnOrder";
    private static final String RECALL_ORDER      = "recallOrder";
    private static final String UPDATE_ORDER      = "updateOrder";
    private static final String CLOSE_ORDER       = "closeOrder";
    private static final String PAYMENT_ORDER     = "paymentOrder";
    private static final String REVIEW_ORDER      = "reviewOrder";
    public static final  String PURE_REVIEW_ORDER = "pureReviewOrder";
    public static final  String PULL_OFF_NOTE     = "pullOffNote";
    private static final String CANCEL_ORDER      = "cancelOrder";
    private static final String RECITE_ORDER      = "reciteOrder";

    private ButtonOperateLogic() {
    }

    public static ButtonOperateLogic getInstance() {
        return OperateLogicInstance.instance;
    }

    private static class OperateLogicInstance {
        static ButtonOperateLogic instance = new ButtonOperateLogic();
    }

    /**
     * 初始化操作按钮
     */
    public void initButtons(ButtonOperateRec rec, ButtonOperateVM viewModel, String type) {
        viewModel.reset();
        switch (type) {
            // 卖出
            case Constant.TRADE_SELLER_ALL:
            case Constant.TRADE_SELLER_CONFIRM:
            case Constant.TRADE_SELLER_MODIFY:
            case Constant.E_COMMERCE_SELL:
                if (rec.getSellerButtonList().size() == 1) {
                    viewModel.setButton1(rec.getSellerButtonList().get(0).getText());
                    viewModel.setOperate1(rec.getSellerButtonList().get(0).getCode());
                } else if (rec.getSellerButtonList().size() == 2) {
                    viewModel.setButton1(rec.getSellerButtonList().get(0).getText());
                    viewModel.setOperate1(rec.getSellerButtonList().get(0).getCode());
                    viewModel.setButton2(rec.getSellerButtonList().get(1).getText());
                    viewModel.setOperate2(rec.getSellerButtonList().get(1).getCode());
                }
                break;

            // 代理
            case Constant.TRADE_NOTE_DEALING:
                if (rec.getAgentButtonList().size() == 1) {
                    viewModel.setButton1(rec.getAgentButtonList().get(0).getText());
                    viewModel.setOperate1(rec.getAgentButtonList().get(0).getCode());
                } else if (rec.getAgentButtonList().size() == 2) {
                    viewModel.setButton1(rec.getAgentButtonList().get(0).getText());
                    viewModel.setOperate1(rec.getAgentButtonList().get(0).getCode());
                    viewModel.setButton2(rec.getAgentButtonList().get(1).getText());
                    viewModel.setOperate2(rec.getAgentButtonList().get(1).getCode());
                }
                break;

            // 买进
            case Constant.TRADE_BUYER_ALL:
            case Constant.TRADE_BUYER_HANDLE:
            case Constant.TRADE_BUYER_REVIEW:
            case Constant.TRADE_BUYER_PAYMENT:
            case Constant.E_COMMERCE_BUYER:
            case Constant.E_COMMERCE_BUY:
                if (rec.getBuyerButtonList().size() == 1) {
                    viewModel.setButton1(rec.getBuyerButtonList().get(0).getText());
                    viewModel.setOperate1(rec.getBuyerButtonList().get(0).getCode());
                } else if (rec.getBuyerButtonList().size() == 2) {
                    viewModel.setButton1(rec.getBuyerButtonList().get(0).getText());
                    viewModel.setOperate1(rec.getBuyerButtonList().get(0).getCode());
                    viewModel.setButton2(rec.getBuyerButtonList().get(1).getText());
                    viewModel.setOperate2(rec.getBuyerButtonList().get(1).getCode());
                }
                break;
        }
    }

    public void execute(final Context context, String operate, final String id, String type) {
        switch (operate) {
            // 查看详情
            case SHOW_DETAIL:
                break;

            // 确认交易
            case CONFIRM_ORDER:
                DialogUtils.showDialog(context, R.string.operate_toast_confirm_order, R.string.operate_toast_reject, R.string.operate_toast_confirm,
                        new OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                confirmOrder(context, id, "reject");
                            }
                        }, new OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                confirmOrder(context, id, "confirm");
                            }
                        });
                break;

            // 退回
            case RETURN_ORDER:
                DialogUtils.showDialog(context, R.string.operate_toast_return_order, R.string.dialog_no, R.string.dialog_yes, new OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                }, new OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        returnOrder(context, id);
                    }
                });
                break;

            // 撤回
            case RECALL_ORDER:
                DialogUtils.showDialog(context, R.string.operate_toast_recall_order, R.string.dialog_no, R.string.dialog_yes, new OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                }, new OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        recallOrder(context, id);
                    }
                });
                break;

            // 修改
            case UPDATE_ORDER:
                if (ConverterUtil.getInteger(type) < 30) {
                    // 我是卖家
                    ARouter.getInstance().build(RouterUrl.TRADE_NOTE_INFO)
                            .withString(BundleKeys.TYPE, Constant.STATUS_1)
                            .withString(BundleKeys.ID, id)
                            .navigation();
                } else {
                    // 我是买家
                    ARouter.getInstance().build(RouterUrl.TRADE_NOTE_INFO)
                            .withString(BundleKeys.TYPE, Constant.STATUS_0)
                            .withString(BundleKeys.ID, id)
                            .navigation();
                }
                break;

            // 关闭订单
            case CLOSE_ORDER:
                DialogUtils.
                        showDialog(context, R.string.operate_toast_close_order, R.string.dialog_cancel, R.string.dialog_confirm, new OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        }, new OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                closeOrder(context, id);
                            }
                        });
                break;

            // 签收并支付
            case PAYMENT_ORDER:
                DialogUtils.showDialog(context, R.string.operate_toast_payment_order, R.string.dialog_no, R.string.dialog_yes, new OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                }, new OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        paymentOrder(context, id);
                    }
                });
                break;

            // 复核
            case REVIEW_ORDER:
                DialogUtils
                        .showDialog(context, R.string.operate_toast_review_order, R.string.dialog_cancel, R.string.dialog_confirm, new OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        }, new OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                reviewOrder(context, id);
                            }
                        });
                break;

            // 纯票复核
            case PURE_REVIEW_ORDER:
                DialogUtils
                        .showDialog(context, R.string.operate_toast_review_order, R.string.dialog_refuse, R.string.dialog_review, new OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                pureReviewOrder(context, id, "refuse");
                            }
                        }, new OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                pureReviewOrder(context, id, "pass");
                            }
                        });
                break;

            // 票据下架
            case PULL_OFF_NOTE:
                DialogUtils
                        .showDialog(context, R.string.operate_toast_pull_off, R.string.dialog_no, R.string.dialog_yes, new OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        }, new OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                pullOffNote(context, id);
                            }
                        });
                break;

            // 电商订单 - 取消订单
            case CANCEL_ORDER:
                DialogUtils
                        .showDialog(context, R.string.operate_toast_cancel_order, R.string.dialog_cancel, R.string.dialog_confirm, new OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        }, new OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                cancelOrder(context, id);
                            }
                        });
                break;

            // 电商订单 - 背书
            case RECITE_ORDER:
                DialogUtils
                        .showDialog(context, R.string.operate_toast_recite_order, R.string.dialog_cancel, R.string.dialog_confirm, new OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        }, new OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                reciteOrder(context, id);
                            }
                        });
                break;

            default:
                break;
        }
    }

    /**
     * 确认交易
     */
    private void confirmOrder(Context context, String id, String comment) {
        Call<HttpResult> call = RDClient.getService(TradeService.class).confirmOrder(id, comment);
        call.enqueue(new RequestCallBack<HttpResult>(new LoadingDialogUtils().show(context)) {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                callback();
            }
        });
    }

    /**
     * 退回
     */
    private void returnOrder(Context context, String id) {
        Call<HttpResult> call = RDClient.getService(TradeService.class).returnOrder(id);
        call.enqueue(new RequestCallBack<HttpResult>(new LoadingDialogUtils().show(context)) {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                callback();
            }
        });
    }

    /**
     * 撤回
     */
    private void recallOrder(Context context, String id) {
        Call<HttpResult> call = RDClient.getService(TradeService.class).recallOrder(id);
        call.enqueue(new RequestCallBack<HttpResult>(new LoadingDialogUtils().show(context)) {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                callback();
            }
        });
    }

    /**
     * 关闭订单
     */
    private void closeOrder(Context context, String id) {
        Call<HttpResult> call = RDClient.getService(TradeService.class).closeOrder(id);
        call.enqueue(new RequestCallBack<HttpResult>(new LoadingDialogUtils().show(context)) {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                callback();
            }
        });
    }

    /**
     * 签收并支付
     */
    private void paymentOrder(Context context, String id) {
        Call<HttpResult> call = RDClient.getService(TradeService.class).paymentOrder(id);
        call.enqueue(new RequestCallBack<HttpResult>(new LoadingDialogUtils().show(context)) {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                callback();
            }
        });
    }

    /**
     * 复核
     */
    private void reviewOrder(Context context, String id) {
        Call<HttpResult> call = RDClient.getService(TradeService.class).reviewOrder(id);
        call.enqueue(new RequestCallBack<HttpResult>(new LoadingDialogUtils().show(context)) {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                callback();
            }
        });
    }

    /**
     * 纯票复核
     */
    private void pureReviewOrder(Context context, String id, String result) {
        Call<HttpResult> call = RDClient.getService(PureService.class).pureReviewOrder(id, result);
        call.enqueue(new RequestCallBack<HttpResult>(new LoadingDialogUtils().show(context)) {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                callback();
            }
        });
    }

    /**
     * 票据下架
     */
    private void pullOffNote(Context context, String id) {
        Call<HttpResult> call = RDClient.getService(PureService.class).pullOffNote(id);
        call.enqueue(new RequestCallBack<HttpResult>(new LoadingDialogUtils().show(context)) {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                callback();
            }
        });
    }

    /**
     * 撤销订单
     */
    private void cancelOrder(Context context, String id) {
        Call<HttpResult> call = RDClient.getService(ECommerceService.class).cancelOrder(id);
        call.enqueue(new RequestCallBack<HttpResult>(new LoadingDialogUtils().show(context)) {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                callback();
            }
        });
    }

    /**
     * 背书
     */
    private void reciteOrder(Context context, String id) {
        Call<HttpResult> call = RDClient.getService(ECommerceService.class).reciteOrder(id);
        call.enqueue(new RequestCallBack<HttpResult>(new LoadingDialogUtils().show(context)) {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                callback();
            }
        });
    }

    /**
     * 回调刷新列表
     */
    private void callback() {
        ContextHolder.getContext().sendBroadcast(new Intent(BundleKeys.REFRESH_LIST));
    }
}