package com.rd.hnlf.module.eCommerce.filter;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.rd.hnlf.R;
import com.rd.hnlf.common.Constant;
import com.rd.hnlf.module.common.viewModel.bean.ConditionBean;
import com.rd.hnlf.module.eCommerce.viewModel.ConditionVM;
import com.rd.views.filter.adapter.MenuAdapter;
import com.rd.views.filter.adapter.SimpleTextAdapter;
import com.rd.views.filter.interfaces.OnFilterDoneListener;
import com.rd.views.filter.interfaces.OnFilterItemClickListener;
import com.rd.views.filter.typeview.SingleListView;
import com.rd.views.filter.util.UIUtil;
import com.rd.views.filter.view.FilterCheckedTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/4 15:27
 * <p/>
 * Description:
 */
public class DropMenuAdapter implements MenuAdapter {
    private final Context                             mContext;
    private       String[]                            titles;
    private       OnFilterDoneListener<ConditionBean> onFilterDoneListener;
    private       ConditionVM                         viewModel;
    private       List<ConditionBean>                 data;
    private       Map<String, ConditionBean>          selectedMap;
    private       SingleListView<ConditionBean>       amountView;
    private       SingleListView<ConditionBean>       dueDateView;

    public DropMenuAdapter(Context context, String[] titles, OnFilterDoneListener<ConditionBean> listener, ConditionVM viewModel) {
        this.mContext = context;
        this.titles = titles;
        this.onFilterDoneListener = listener;
        this.viewModel = viewModel;
        this.selectedMap = new HashMap<>();
        convert();
    }

    private void convert() {
        data = new ArrayList<>();
        data = new ArrayList<>();
        data.add(new ConditionBean("0", mContext.getString(R.string.condition_type), "", Constant.NUMBER_1));
        data.addAll(viewModel.getTypeList());
        data.add(new ConditionBean("0", mContext.getString(R.string.condition_release_date), "", Constant.NUMBER_1));
        data.addAll(viewModel.getReleaseDateList());
        data.add(new ConditionBean("0", mContext.getString(R.string.condition_due_date), "", Constant.NUMBER_1));
        data.addAll(viewModel.getDueDateList());
        data.add(new ConditionBean("0", mContext.getString(R.string.condition_amount), "", Constant.NUMBER_1));
        data.addAll(viewModel.getAmountList());
        data.add(new ConditionBean("0", mContext.getString(R.string.condition_enterprise), "", Constant.NUMBER_1));
        data.addAll(viewModel.getEnterpriseList());

        for (ConditionBean bean : data) {
            if (bean.isFlag()) {
                selectedMap.put(bean.getRemark(), bean);
            }
        }
    }

    @Override
    public int getMenuCount() {
        return titles.length;
    }

    @Override
    public String getMenuTitle(int position) {
        return titles[position];
    }

    @Override
    public int getBottomMargin(int position) {
        if (position == 2) {
            return 0;
        }
        return UIUtil.dp(mContext, 100);
    }

    @Override
    public View getView(int position, FrameLayout parentContainer) {
        View view = parentContainer.getChildAt(position);

        switch (position) {
            case Constant.NUMBER_0:
                amountView = createSingleListView(viewModel.getAmountList());
                view = amountView;
                break;

            case Constant.NUMBER_1:
                dueDateView = createSingleListView(viewModel.getDueDateList());
                view = dueDateView;
                break;

            case Constant.NUMBER_2:
                view = createFilterGridView();
                break;
        }
        return view;
    }

    private SingleListView<ConditionBean> createSingleListView(List<ConditionBean> list) {
        SingleListView<ConditionBean> singleListView = new SingleListView<ConditionBean>(mContext)
                .adapter(new SimpleTextAdapter<ConditionBean>(null, mContext) {
                    @Override
                    public String provideText(ConditionBean bean) {
                        return bean.toString();
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                    }
                })
                .onItemClick(new OnFilterItemClickListener<ConditionBean>() {
                    @Override
                    public void onItemClick(int position, ConditionBean item) {
                        String remark = item.getRemark();
                        if (selectedMap.containsKey(remark)) {
                            selectedMap.get(remark).setFlag(false);
                        }
                        item.setFlag(true);
                        selectedMap.put(remark, item);

                        onFilterDoneListener.onFilterDone(item);
                    }
                });

        singleListView.setList(list, 0);
        return singleListView;
    }

    private View createFilterGridView() {
        return new FilterGridView(mContext).setData(data)
                .setOnItemChildClickListener(new OnItemChildClickListener() {
                    @Override
                    public void onSimpleItemChildClick(BaseQuickAdapter adapter, final View view, final int position) {
                    }

                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        ConditionBean item   = (ConditionBean) adapter.getItem(position);
                        String        remark = item.getRemark();
                        if (selectedMap.containsKey(remark)) {
                            selectedMap.get(remark).setFlag(false);
                        }
                        item.setFlag(true);
                        selectedMap.put(remark, item);
                    }
                }).setOnButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != onFilterDoneListener) {
                            // MAP 转 LIST
                            ConditionBean[] list = new ConditionBean[selectedMap.size()];
                            int             i    = 0;
                            for (Map.Entry entry : selectedMap.entrySet()) {
                                ConditionBean item = (ConditionBean) entry.getValue();
                                list[i++] = item;

                                if (ConditionBean.REMARK_AMOUNT.equals(item.getRemark())) {
                                    checkedItem(amountView, viewModel.getAmountList(), item);
                                }

                                if (ConditionBean.REMARK_DUE_DATE.equals(item.getRemark())) {
                                    checkedItem(dueDateView, viewModel.getDueDateList(), item);
                                }
                            }
                            onFilterDoneListener.onFilterDone(list);
                        }
                    }
                }).build();
    }

    private void checkedItem(SingleListView<ConditionBean> view, List<ConditionBean> list, ConditionBean item) {
        int i = 0;
        for (ConditionBean bean : list) {
            if (bean == item) {
                view.setItemChecked(i, true);
            }
            i++;
        }
    }
}