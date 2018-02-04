package com.rd.hnlf.router.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.DegradeService;
import com.rd.hnlf.router.RouterUrl;
import com.rd.tools.utils.ToastUtil;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/3/16 11:19
 * <p/>
 * Description: 自定义全局降级策略
 */
@Route(path = RouterUrl.DEGRADE)
public class DegradeServiceImpl implements DegradeService {
    @Override
    public void onLost(Context context, Postcard postcard) {
        ToastUtil.toast("页面不存在，敬请期待");
    }

    @Override
    public void init(Context context) {
    }
}