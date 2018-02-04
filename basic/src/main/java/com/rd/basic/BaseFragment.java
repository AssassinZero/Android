/*
 * Fragment的生命周期
 * onAttach                 当Activity与Fragment发生关联时调用
 * onCreate
 * setUserVisibleHint
 * onCreateView             创建该Fragment的视图
 * onViewCreated
 * onActivityCreated        当Activity的onCreate()；方法返回时调用
 * onViewStateRestored
 * onStart
 * onResume
 * onPause
 * onSaveInstanceState
 * onStop
 * onDestroyView            与onCreateView相对应，当该Fragment被移除时调用
 * onDestroy
 * onDetach                 与onAttach()相对应，当Fragment与Activity的关联被取消时调用
 *
 * http://www.jianshu.com/p/662c46cd3b5f
 */
package com.rd.basic;

import android.support.v4.app.Fragment;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/4/25 15:25
 * <p/>
 * Description: Fragment基类
 */
public class BaseFragment extends Fragment {
}