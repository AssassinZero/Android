package com.rd.logic;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.rd.tools.Config;
import com.rd.tools.utils.AndroidUtil;
import com.rd.tools.utils.FileUtil;
import com.rd.views.popupWindow.PickPopupWindow;

import java.io.File;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/5/20 17:41
 * <p/>
 * Description: 拍照逻辑处理类
 */
public class PhotographLogic {
    // 相册
    public static final int    REQUEST_CODE_PICK    = 0x800;
    // 拍照
    public static final int    REQUEST_CODE_TAKE    = 0x801;
    // 剪裁
    public static final int    REQUEST_CODE_CUTTING = 0x802;
    // 照片存储路径
    private static      String FILE_PATH            = Config.ROOT_PATH.get() + "/photo";
    // 本次操作需要保存的照片名
    private String          IMAGE_FILE_NAME;
    private PickPopupWindow popupWindow;
    private File            takePhotoFile;
    private File            cuttingPhotoFile;

    private PhotographLogic() {
    }

    public static PhotographLogic getInstance() {
        return PhotographLogicInstance.instance;
    }

    private static class PhotographLogicInstance {
        static PhotographLogic instance = new PhotographLogic();
    }

    /**
     * 获取照片
     *
     * @param view
     *         view
     * @param photoName
     *         照片名称
     */
    public void obtain(View view, String photoName) {
        if (TextUtils.isEmpty(photoName)) {
            return;
        }
        check();
        IMAGE_FILE_NAME = "temp_" + photoName;
        takePhotoFile = new File(FILE_PATH, IMAGE_FILE_NAME);
        popupWindow = new PickPopupWindow(view.getContext(), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                // 相册
                if (R.id.pickPhotoBtn == view.getId()) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    (AndroidUtil.getActivity(view)).startActivityForResult(intent, REQUEST_CODE_PICK);
                } else if (R.id.takePhotoBtn == view.getId()) {
                    // 拍照
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(takePhotoFile));
                        (AndroidUtil.getActivity(view)).startActivityForResult(intent, REQUEST_CODE_TAKE);
                    }
                }
            }
        });
        popupWindow.showAtLocation(view.getRootView(), Gravity.BOTTOM, 0, 0);
    }

    /** onActivityResult */
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data, int width, int height, PhotographCallback callback) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                // 相册
                case REQUEST_CODE_PICK:
                    // 做非空判断，当我们觉得不满意想重新剪裁的时候便不会报异常，下同
                    if (null != data) {
                        startPhotoZoom(activity, data.getData(), width, height);
                    }
                    break;

                // 拍照
                case REQUEST_CODE_TAKE:
                    startPhotoZoom(activity, Uri.fromFile(takePhotoFile), width, height);
                    break;

                // 剪裁
                case REQUEST_CODE_CUTTING:
                    if (!TextUtils.isEmpty(IMAGE_FILE_NAME)) {
                        callback.obtain(cuttingPhotoFile);
                        FileUtil.deleteFile(takePhotoFile);
                        takePhotoFile = null;
                    }
                    break;
            }
        }
    }

    /**
     * 剪裁头像
     */
    private void startPhotoZoom(Activity activity, Uri uri, int width, int height) {
        cuttingPhotoFile = new File(FILE_PATH, IMAGE_FILE_NAME.replace("temp_", ""));
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", width);
        intent.putExtra("aspectY", height);
        // outputX outputY 是剪裁图片的宽高
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        // 文件输出格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // 是否保留比例
        intent.putExtra("scale", true);
        // 是否返回数据
        intent.putExtra("return-data", false);
        // 关闭人脸检测
        intent.putExtra("noFaceDetection", true);
        // 保存为文件
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cuttingPhotoFile));
        activity.startActivityForResult(intent, REQUEST_CODE_CUTTING);
    }

    /**
     * 检验目录是否存在
     */
    private void check() {
        File dir = new File(FILE_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * 增加目录
     */
    public void addDirectory(String directoryName) {
        if (!TextUtils.isEmpty(directoryName)) {
            FILE_PATH = Config.ROOT_PATH.get() + File.separator + directoryName;
        } else {
            FILE_PATH = Config.ROOT_PATH.get();
        }
    }

    public interface PhotographCallback {
        void obtain(File file);
    }
}