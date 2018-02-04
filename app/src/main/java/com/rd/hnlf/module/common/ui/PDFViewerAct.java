package com.rd.hnlf.module.common.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.rd.basic.BaseActivity;
import com.rd.hnlf.R;
import com.rd.hnlf.common.BundleKeys;
import com.rd.hnlf.databinding.CommonPdfViewerActBinding;
import com.rd.hnlf.router.RouterExtras;
import com.rd.hnlf.router.RouterUrl;

import java.io.File;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/7/24 14:41
 * <p/>
 * Description: PDF文件预览，入参为PDF文件的本地路径
 */
@Route(path = RouterUrl.PDF_VIEWER, extras = RouterExtras.EXTRA_COMMON)
public class PDFViewerAct extends BaseActivity implements OnPageChangeListener, OnLoadCompleteListener {
    int pageNumber;
    @Autowired(name = BundleKeys.PATH)
    String filePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File file = new File(filePath);
        if (file.exists()) {
            CommonPdfViewerActBinding binding = DataBindingUtil.setContentView(this, R.layout.common_pdf_viewer_act);
            binding.toolBar.setTitle(file.getName());
            binding.pdfView.fromFile(file)
                    .defaultPage(pageNumber)
                    .onPageChange(this)
                    .enableAnnotationRendering(true)
                    .onLoad(this)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .load();
        }
    }

    @Override
    public void loadComplete(int nbPages) {
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
    }
}