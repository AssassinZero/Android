package com.rd.hnlf.common.binding;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rd.hnlf.BR;
import com.rd.hnlf.R;
import com.rd.hnlf.common.Constant;
import com.rd.hnlf.common.ui.AbsRefreshAndLoadMore;
import com.rd.hnlf.module.common.viewModel.bean.KVPBean;
import com.rd.hnlf.utils.GlideImageLoader;
import com.rd.hnlf.utils.PlaceholderHelper;
import com.rd.tools.utils.AndroidUtil;
import com.rd.tools.utils.ContextHolder;
import com.rd.tools.utils.ConverterUtil;
import com.rd.tools.utils.DensityUtil;
import com.rd.tools.utils.EditTextFormat;
import com.rd.tools.utils.RegularUtil;
import com.rd.views.PlaceholderLayout;
import com.rd.views.editText.EditTextWithDrawable;
import com.rd.views.recyclerView.CustomLinearLayoutManager;
import com.rd.views.recyclerView.DividerLine;
import com.rd.views.textView.SingleSelectorView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/3/13 14:42
 * <p/>
 * Description:
 */
public class BasicBinding {
    /**
     * 下拉刷新，上拉加载
     *
     * @param ptrFrame
     *         view
     * @param refreshListener
     *         实现操作
     */
    @BindingAdapter({"refreshListener"})
    public static void refreshListener(final PtrFrameLayout ptrFrame, final AbsRefreshAndLoadMore refreshListener) {
        if (null == refreshListener) {
            ptrFrame.setEnabled(false);
            return;
        } else {
            refreshListener.setPtrFrame(ptrFrame);
            ptrFrame.setEnabled(true);
        }
        ptrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refreshListener.refresh();
            }
        });
        ptrFrame.disableWhenHorizontalMove(true);
        if (ptrFrame instanceof PtrClassicFrameLayout) {
            ((PtrClassicFrameLayout) ptrFrame).setLastUpdateTimeRelateObject(ptrFrame);
        }
        refreshListener.refreshInit(ptrFrame);
    }

    /**
     * 为 RecyclerView 设置 adapter 、layoutManager
     */
    @BindingAdapter(value = {"recyclerAdapter", "layoutManagerType", "itemTouchListener", "loadMoreListener"}, requireAll = false)
    public static void recyclerViewAdapter(RecyclerView recyclerView, RecyclerView.Adapter adapter, int layoutManagerType,
                                           RecyclerView.OnItemTouchListener itemTouchListener, final AbsRefreshAndLoadMore loadMoreListener) {
        // set Adapter
        if (null == recyclerView.getAdapter()) {
            recyclerView.setAdapter(adapter);
        } else {
            recyclerView.swapAdapter(adapter, true);
        }

        // add OnItemTouchListener
        if (null != itemTouchListener) {
            recyclerView.addOnItemTouchListener(itemTouchListener);
        }

        // set LayoutManager
        switch (layoutManagerType) {
            case Constant.NUMBER_0: {
                // 线性布局管理器 - 垂直
                recyclerView.setLayoutManager(new CustomLinearLayoutManager(recyclerView.getContext()));
                break;
            }

            case Constant.NUMBER_1: {
                // 线性布局管理器 - 垂直(ScrollView 嵌套 RecyclerView)
                recyclerView.setLayoutManager(new CustomLinearLayoutManager(recyclerView.getContext()) {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                });
                break;
            }

            case Constant.NUMBER_2: {
                // 线性布局管理器 - 水平
                LinearLayoutManager linearLayoutManager = new CustomLinearLayoutManager(recyclerView.getContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                break;
            }

            default:
                break;
        }
        // set LoadMore
        if (null != loadMoreListener && adapter instanceof BaseQuickAdapter) {
            loadMoreListener.setAdapter((BaseQuickAdapter) adapter);
            ((BaseQuickAdapter) adapter).setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    loadMoreListener.loadMore();
                }
            }, recyclerView);
            loadMoreListener.loadMoreInit((BaseQuickAdapter) adapter);
        }
        // set Animation
        // if (adapter instanceof BaseQuickAdapter) {
        //     ((BaseQuickAdapter) adapter).openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        //     ((BaseQuickAdapter) adapter).isFirstOnly(false);
        // }

    }

    @BindingAdapter(value = {"placeHolderType", "retryListener"}, requireAll = false)
    public static void placeHolder(RecyclerView recyclerView, int placeHolderType, PlaceholderLayout.OnReloadListener listener) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (null != adapter && adapter instanceof BaseQuickAdapter) {
            if (PlaceholderLayout.SUCCESS == placeHolderType) {
                View emptyView = ((BaseQuickAdapter) adapter).getEmptyView();
                if (null != emptyView) {
                    ((FrameLayout) ((BaseQuickAdapter) adapter).getEmptyView()).removeAllViews();
                }
            } else {
                PlaceholderLayout layout = new PlaceholderLayout(recyclerView.getContext());
                layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                PlaceholderHelper.getInstance().setStatus(layout, placeHolderType);
                if (null != listener) {
                    layout.setOnReloadListener(listener);
                }
                // layout.setContentView(recyclerView);
                ((BaseQuickAdapter) adapter).setEmptyView(layout);
            }
        }
    }

    /**
     * 设置EditText是否可编辑
     */
    @BindingAdapter({"editable"})
    public static void setEditable(EditText view, boolean editable) {
        // 是否可编辑
        if (editable) {
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
        } else {
            view.setFocusable(false);
            view.setFocusableInTouchMode(false);
        }
    }

    @BindingAdapter({"movementMethod"})
    public static void setMovementMethod(TextView textView, boolean flag) {
        if (flag) {
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    /**
     * 为RecyclerView添加分割线
     *
     * @param type
     *         水平 - HORIZONTAL = 0;
     *         垂直 - VERTICAL = 1;
     *         十字 - CROSS = 9;
     */
    @BindingAdapter({"addItemDecoration"})
    public static void addItemDecoration(RecyclerView view, int type) {
        DividerLine dividerLine;
        switch (type) {
            case DividerLine.HORIZONTAL:
                dividerLine = new DividerLine(DividerLine.HORIZONTAL);
                view.addItemDecoration(dividerLine);
                break;

            case DividerLine.VERTICAL:
                dividerLine = new DividerLine(DividerLine.VERTICAL);
                view.addItemDecoration(dividerLine);
                break;

            case DividerLine.CROSS:
                dividerLine = new DividerLine(DividerLine.CROSS);
                view.addItemDecoration(dividerLine);
                break;

            case Constant.NUMBER_2:
                dividerLine = new DividerLine(DividerLine.HORIZONTAL);
                dividerLine.setMarginStart(20);
                view.addItemDecoration(dividerLine);
                break;

            case DividerLine.DEFAULT:
            default:
                break;
        }
    }

    /**
     * 设置view的宽高比
     *
     * @param view
     *         view对象
     * @param widthRatio
     *         宽度占比
     * @param aspectRatio
     *         宽高比
     *         app:widthRatio="@{0.60f}"
     *         app:aspectRatio="@{0.67f}"
     */
    @BindingAdapter(value = {"widthRatio", "aspectRatio"}, requireAll = false)
    public static void aspectRatio(View view, float widthRatio, float aspectRatio) {
        // 宽度
        float width;
        // 高度
        float height;
        // view 的 LayoutParams
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

        if (widthRatio != 0) {
            if (widthRatio > 1) {
                widthRatio = 1;
            }
            width = DensityUtil.getWidth(AndroidUtil.getActivity(view)) * widthRatio;
        } else {
            width = DensityUtil.getWidth(AndroidUtil.getActivity(view));
        }

        if (aspectRatio != 0) {
            height = width * aspectRatio;
        } else {
            height = layoutParams.height;
        }
        layoutParams.width = (int) width;
        layoutParams.height = (int) height;

        view.setLayoutParams(layoutParams);
        view.requestLayout();
    }

    /**
     * 设置 TextView CompoundDrawables 的点击事件
     */
    @BindingAdapter(value = {"leftListener", "topListener", "rightListener", "bottomListener"}, requireAll = false)
    public static void editTextWithDrawable(EditTextWithDrawable view,
                                            EditTextWithDrawable.DrawableLeftListener leftListener,
                                            EditTextWithDrawable.DrawableTopListener topListener,
                                            EditTextWithDrawable.DrawableRightListener rightListener,
                                            EditTextWithDrawable.DrawableBottomListener bottomListener) {
        if (null != leftListener) {
            view.setLeftListener(leftListener);
        }

        if (null != topListener) {
            view.setTopListener(topListener);
        }

        if (null != rightListener) {
            view.setRightListener(rightListener);
        }

        if (null != bottomListener) {
            view.setBottomListener(bottomListener);
        }
    }

    /**
     * EditText Filter
     */
    @BindingAdapter(value = {"filterType", "watcher"}, requireAll = false)
    public static void editTextFilter(EditText view, int type, EditTextFormat.EditTextFormatWatcher watcher) {
        switch (type) {
            // 限制EditText只能输入7位整数和6位小数
            case Constant.NUMBER__1:
                EditTextFormat.addFilter(view, EditTextFormat.getDecimalFilter(7, 6));
                break;

            // 限制EditText只能输入9为整数和2位小数
            case Constant.NUMBER_0:
                EditTextFormat.addFilter(view, EditTextFormat.getDecimalFilter(9, 2));
                break;

            // 限制EditText不能输入空格
            case Constant.NUMBER_1:
                EditTextFormat.addFilter(view, EditTextFormat.getBlankFilter());
                break;

            // 银行卡卡号输入格式化
            case Constant.NUMBER_2:
                EditTextFormat.bankCardNumAddSpace(view, watcher);
                break;

            // 手机号输入格式化
            case Constant.NUMBER_3:
                EditTextFormat.phoneNumAddSpace(view, watcher);
                break;

            // 身份证号输入格式化
            case Constant.NUMBER_4:
                EditTextFormat.IDCardNumAddSpace(view, watcher);
                break;

            // 限制EditText只能输入不大于100的值，且只保留两位小数
            case Constant.NUMBER_5:
                EditTextFormat.addFilter(view, EditTextFormat.getPercentFilter());
                break;

            // 限制EditText只能输入数字和Xx
            case Constant.NUMBER_6:
                EditTextFormat.addFilter(view, EditTextFormat.getIDCardFilter());
                break;

            // 限制EditText只能输入正整数
            case Constant.NUMBER_7:
                EditTextFormat.addFilter(view, EditTextFormat.getNumberFilter());
                break;

            // 限制EditText只能输入数字和字母
            case Constant.NUMBER_8:
                EditTextFormat.addFilter(view, EditTextFormat.getPasswordFilter());
                break;

            // 限制EditText只能输入中文
            case Constant.NUMBER_9:
                EditTextFormat.addFilter(view, EditTextFormat.getChineseFilter());
                break;

            default:
                break;
        }
    }

    @BindingAdapter({"loadHtmlData"})
    public static void loadHtmlData(WebView webView, String data) {
        if (TextUtils.isEmpty(data)) {
            return;
        }
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.loadDataWithBaseURL(null, data, "text/html", "UTF-8", null);
    }

    /**
     * 为ImageView设置图片
     *
     * @param imageView
     *         imageView
     * @param path
     *         路径
     * @param defaultImage
     *         默认图片
     * @param errorImage
     *         加载失败图片
     */
    @BindingAdapter(value = {"src", "defaultImage", "errorImage", "skipCache"}, requireAll = false)
    public static void setImage(final ImageView imageView, String path, Drawable defaultImage, Drawable errorImage, boolean skipCache) {
        Context context = imageView.getContext();
        if (null == errorImage) {
            errorImage = ContextCompat.getDrawable(context, R.drawable.default_picture);
        }
        if (TextUtils.isEmpty(path)) {
            // 加载默认图片
            if (defaultImage != null) {
                imageView.setImageDrawable(defaultImage);
            } else {
                imageView.setImageDrawable(errorImage);
            }
        } else {
            if (RegularUtil.isInteger(path)) {
                // 加载资源图片
                if (null == defaultImage) {
                    Glide.with(context).load(ConverterUtil.getInteger(path)).thumbnail(0.1f).error(errorImage).into(imageView);
                } else {
                    Glide.with(context).load(ConverterUtil.getInteger(path)).thumbnail(0.1f).placeholder(defaultImage).error(errorImage).into(imageView);
                }
            } else {
                // 加载网络图片
                if (null == defaultImage) {
                    if (skipCache) {
                        Glide.with(context).load(path)
                                .error(errorImage)
                                .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                                .into(imageView);
                    } else {
                        Glide.with(context).load(path).thumbnail(0.1f)
                                .error(errorImage)
                                .into(imageView);
                    }
                } else {
                    if (skipCache) {
                        Glide.with(context).load(path)
                                .placeholder(defaultImage).error(errorImage)
                                .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                                .into(imageView);
                    } else {
                        Glide.with(context).load(path).thumbnail(0.1f)
                                .placeholder(defaultImage).error(errorImage)
                                .into(imageView);
                    }
                }
            }
        }
    }

    @BindingAdapter(value = {"drawableRight"}, requireAll = false)
    public static void setDrawable(final TextView textView, String drawableRight) {
        Glide.with(textView.getContext()).load(drawableRight).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                textView.setCompoundDrawablesWithIntrinsicBounds(null, null, resource, null);
            }
        });
    }

    @BindingAdapter(value = {"ultraData", "ultraLayout"}, requireAll = false)
    public static void ultraViews(ViewGroup viewGroup, List data, int layoutId) {
        if (null != data) {
            Context context = viewGroup.getContext();
            int     i       = 0;
            for (Object obj : data) {
                ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, viewGroup, false);
                binding.setVariable(BR.item, obj);
                binding.setVariable(BR.first, i++ == 0);
                viewGroup.addView(binding.getRoot());
            }
        }
    }

    @BindingAdapter(value = {"bannerImages"}, requireAll = false)
    public static void setBanner(Banner banner, List<String> imageUrls) {
        if (null != imageUrls && !imageUrls.isEmpty()) {
            // 设置 banner 样式
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            // 设置指示器位置（当 banner 模式中有指示器时）
            banner.setIndicatorGravity(BannerConfig.CENTER);
            // 设置图片加载器
            banner.setImageLoader(new GlideImageLoader());
            // 设置图片集合
            banner.setImages(imageUrls);
            // 设置自动轮播，默认为 true
            banner.isAutoPlay(false);
            // banner 设置方法全部调用完毕时最后调用
            banner.start();
        }
    }

    @BindingAdapter(value = {"KVPlist", "arrayList", "keyList", "valueList", "showHint"}, requireAll = false)
    public static void singleSelectorInit(SingleSelectorView view, List<KVPBean> KVPlist, String[] arrayList,
                                          List<String> keyList, List<String> valueList, boolean showHint) {
        if (null != KVPlist && !KVPlist.isEmpty()) {
            keyList = new ArrayList<>();
            valueList = new ArrayList<>();
            for (KVPBean bean : KVPlist) {
                keyList.add(bean.getCode());
                valueList.add(bean.getText());
            }
        }

        if (null != arrayList && arrayList.length > 0) {
            keyList = Arrays.asList(ContextHolder.getContext().getResources().getStringArray(R.array.key_array));
            valueList = Arrays.asList(arrayList);
        }

        view.setKeyList(keyList);
        view.setValueList(valueList);
        view.setButtonColor(ContextCompat.getColor(view.getContext(), R.color.text_purple));
        view.initialize(!showHint);
    }
}