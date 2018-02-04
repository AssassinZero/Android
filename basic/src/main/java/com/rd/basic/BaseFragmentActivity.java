package com.rd.basic;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/2/18 10:11
 * <p/>
 * Description: Fragment 嵌套 Fragment ，onActivityResult 无效的解决方法
 */
public class BaseFragmentActivity extends BaseActivity {
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        FragmentManager fm    = getSupportFragmentManager();
        int             index = requestCode >> 16;
        if (index != 0) {
            index--;
            if (fm.getFragments() == null || index < 0 || index >= fm.getFragments().size()) {
                return;
            }
            Fragment frag = fm.getFragments().get(index);
            if (frag != null) {
                handleResult(frag, requestCode, resultCode, data);
            } else {
            }
        }
    }

    /**
     * 递归调用，对所有子 Fragment 生效
     */
    private void handleResult(Fragment frag, int requestCode, int resultCode, Intent data) {
        frag.onActivityResult(requestCode & 0xffff, resultCode, data);
        List<Fragment> frags = frag.getChildFragmentManager().getFragments();
        if (frags != null) {
            for (Fragment f : frags) {
                if (f != null)
                    handleResult(f, requestCode, resultCode, data);
            }
        }
    }
}