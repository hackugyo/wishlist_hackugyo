package com.github.kevinsawicki.wishlist;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class AssetsUtils {

    /**
     * read a Text file from assets.
     * 
     * @param context
     * @param fileName
     * @return Text
     * @throws IOException
     */
    public static String readTextFromAsset(Context context, String fileName) throws IOException {
        AssetManager am = context.getResources().getAssets();
        BufferedReader br = null;

        StringBuilder sb = new StringBuilder();
        try {
            br = new BufferedReader(new InputStreamReader(am.open(fileName), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            // ignore.
            return null;
        }

        String str;
        while ((str = br.readLine()) != null) {
            sb.append(str + "\n");
        }
        if (br != null) br.close();

        return sb.toString();
    }

    public static void copyDataFromAssetToPath(Context context, String assetName, File path) throws IOException {

        // asset 内のデータベースファイルにアクセス  
        InputStream mInput;
        try {
            mInput = context.getAssets().open(assetName);
        } catch (IOException e) {
            LogUtils.e("assetsに接続できない．", e);
            throw e;
        }

        // デフォルトのデータベースパスに作成した空のDB
        OutputStream mOutput;
        try {
            path.getParentFile().mkdirs();
            mOutput = new FileOutputStream(path);
        } catch (FileNotFoundException e) {
            LogUtils.e("assetsに接続できない．", e);
            throw e;
        }

        // コピー  
        byte[] buffer = new byte[1024];
        int size;
        while ((size = mInput.read(buffer)) > 0) {
            mOutput.write(buffer, 0, size);
        }

        // Close the streams  
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }
}
