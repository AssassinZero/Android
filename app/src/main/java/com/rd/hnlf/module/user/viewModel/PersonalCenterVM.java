package com.rd.hnlf.module.user.viewModel;

import android.text.Html;
import android.text.Spanned;

import com.rd.hnlf.R;
import com.rd.hnlf.module.user.dataModel.receive.OauthTokenRec;
import com.rd.hnlf.module.user.viewControl.PersonalCenterCtrl;
import com.rd.logic.info.SharedInfo;
import com.rd.tools.utils.ContextHolder;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/18 14:43
 * <p/>
 * Description: {@link PersonalCenterCtrl}
 */
public class PersonalCenterVM {
    private OauthTokenRec rec;
    /** 头像地址 */
    private String        avatar;
    /** 欢迎语 */
    private String        greeting;
    /** 用户类型: 10 - 普通用户，20 - 代理商，30/40 - 会员用户 */
    private String        type;

    public PersonalCenterVM() {
        rec = SharedInfo.getInstance().getEntity(OauthTokenRec.class);
        avatar = rec.getAvatar();
        greeting = ContextHolder.getContext().getString(R.string.personal_greeting, rec.getMobile(), rec.getUserTypeStr());
        type = rec.getUserType();
    }

    public String getAvatar() {
        return avatar;
    }

    public Spanned getGreeting() {
        return Html.fromHtml(greeting);
    }

    /**
     * 是否是普通用户
     */
    public boolean isNormal() {
        return rec.isNormal();
    }

    /**
     * 是否是代理商
     */
    public boolean isAgent() {
        return rec.isAgent();
    }

    /**
     * 是否是普通用户
     */
    public boolean isVIP() {
        return rec.isVIP();
    }
}