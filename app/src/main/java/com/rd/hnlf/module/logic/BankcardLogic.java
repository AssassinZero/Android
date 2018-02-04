package com.rd.hnlf.module.logic;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/21 16:05
 * <p/>
 * Description:
 */
public class BankcardLogic {
    private BankcardLogic() {
    }

    public static BankcardLogic getInstance() {
        return BankcardLogicInstance.instance;
    }

    private static class BankcardLogicInstance {
        static BankcardLogic instance = new BankcardLogic();
    }

    public void getBankList() {

    }

    public void getBranchBankList() {

    }
}