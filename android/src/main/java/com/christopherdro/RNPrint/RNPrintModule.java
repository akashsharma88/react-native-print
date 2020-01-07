package com.christopherdro.RNPrint;

import android.content.Context;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableNativeArray;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * NativeModule that allows JS to open emails sending apps chooser.
 */

public class RNPrintModule extends ReactContextBaseJavaModule {

    ReactApplicationContext reactContext;
    final String jobName = "Document";
    final HashMap<String, PrintAttributes.MediaSize> allPaperSize = new HashMap<String, PrintAttributes.MediaSize>() {
        {
            put("ISO_A0", PrintAttributes.MediaSize.ISO_A0);
            put("ISO_A1", PrintAttributes.MediaSize.ISO_A1);
            put("ISO_A10", PrintAttributes.MediaSize.ISO_A10);
            put("ISO_A2", PrintAttributes.MediaSize.ISO_A2);
            put("ISO_A3", PrintAttributes.MediaSize.ISO_A3);
            put("ISO_A4", PrintAttributes.MediaSize.ISO_A4);
            put("ISO_A5", PrintAttributes.MediaSize.ISO_A5);
            put("ISO_A6", PrintAttributes.MediaSize.ISO_A6);
            put("ISO_A7", PrintAttributes.MediaSize.ISO_A7);
            put("ISO_A8", PrintAttributes.MediaSize.ISO_A8);
            put("ISO_A9", PrintAttributes.MediaSize.ISO_A9);
            put("ISO_B0", PrintAttributes.MediaSize.ISO_B0);
            put("ISO_B1", PrintAttributes.MediaSize.ISO_B1);
            put("ISO_B10", PrintAttributes.MediaSize.ISO_B10);
            put("ISO_B2", PrintAttributes.MediaSize.ISO_B2);
            put("ISO_B3", PrintAttributes.MediaSize.ISO_B3);
            put("ISO_B4", PrintAttributes.MediaSize.ISO_B4);
            put("ISO_B5", PrintAttributes.MediaSize.ISO_B5);
            put("ISO_B6", PrintAttributes.MediaSize.ISO_B6);
            put("ISO_B7", PrintAttributes.MediaSize.ISO_B7);
            put("ISO_B8", PrintAttributes.MediaSize.ISO_B8);
            put("ISO_B9", PrintAttributes.MediaSize.ISO_B9);
            put("ISO_C0", PrintAttributes.MediaSize.ISO_C0);
            put("ISO_C1", PrintAttributes.MediaSize.ISO_C1);
            put("ISO_C10", PrintAttributes.MediaSize.ISO_C10);
            put("ISO_C2", PrintAttributes.MediaSize.ISO_C2);
            put("ISO_C3", PrintAttributes.MediaSize.ISO_C3);
            put("ISO_C4", PrintAttributes.MediaSize.ISO_C4);
            put("ISO_C5", PrintAttributes.MediaSize.ISO_C5);
            put("ISO_C6", PrintAttributes.MediaSize.ISO_C6);
            put("ISO_C7", PrintAttributes.MediaSize.ISO_C7);
            put("ISO_C8", PrintAttributes.MediaSize.ISO_C8);
            put("ISO_C9", PrintAttributes.MediaSize.ISO_C9);
            put("JIS_B0", PrintAttributes.MediaSize.JIS_B0);
            put("JIS_B1", PrintAttributes.MediaSize.JIS_B1);
            put("JIS_B10", PrintAttributes.MediaSize.JIS_B10);
            put("JIS_B2", PrintAttributes.MediaSize.JIS_B2);
            put("JIS_B3", PrintAttributes.MediaSize.JIS_B3);
            put("JIS_B4", PrintAttributes.MediaSize.JIS_B4);
            put("JIS_B5", PrintAttributes.MediaSize.JIS_B5);
            put("JIS_B6", PrintAttributes.MediaSize.JIS_B6);
            put("JIS_B7", PrintAttributes.MediaSize.JIS_B7);
            put("JIS_B8", PrintAttributes.MediaSize.JIS_B8);
            put("JIS_B9", PrintAttributes.MediaSize.JIS_B9);
            put("JIS_EXEC", PrintAttributes.MediaSize.JIS_EXEC);
            put("JPN_CHOU2", PrintAttributes.MediaSize.JPN_CHOU2);
            put("JPN_CHOU3", PrintAttributes.MediaSize.JPN_CHOU3);
            put("JPN_CHOU4", PrintAttributes.MediaSize.JPN_CHOU4);
            put("JPN_HAGAKI", PrintAttributes.MediaSize.JPN_HAGAKI);
            put("JPN_KAHU", PrintAttributes.MediaSize.JPN_KAHU);
            put("JPN_KAKU2", PrintAttributes.MediaSize.JPN_KAKU2);
            put("JPN_OUFUKU", PrintAttributes.MediaSize.JPN_OUFUKU);
            put("JPN_YOU4", PrintAttributes.MediaSize.JPN_YOU4);
            put("NA_FOOLSCAP", PrintAttributes.MediaSize.NA_FOOLSCAP);
            put("NA_GOVT_LETTER", PrintAttributes.MediaSize.NA_GOVT_LETTER);
            put("NA_INDEX_3X5", PrintAttributes.MediaSize.NA_INDEX_3X5);
            put("NA_INDEX_4X6", PrintAttributes.MediaSize.NA_INDEX_4X6);
            put("NA_INDEX_5X8", PrintAttributes.MediaSize.NA_INDEX_5X8);
            put("NA_JUNIOR_LEGAL", PrintAttributes.MediaSize.NA_JUNIOR_LEGAL);
            put("NA_LEDGER", PrintAttributes.MediaSize.NA_LEDGER);
            put("NA_LEGAL", PrintAttributes.MediaSize.NA_LEGAL);
            put("NA_LETTER", PrintAttributes.MediaSize.NA_LETTER);
            put("NA_MONARCH", PrintAttributes.MediaSize.NA_MONARCH);
            put("NA_QUARTO", PrintAttributes.MediaSize.NA_QUARTO);
            put("NA_TABLOID", PrintAttributes.MediaSize.NA_TABLOID);
            put("OM_DAI_PA_KAI", PrintAttributes.MediaSize.OM_DAI_PA_KAI);
            put("OM_JUURO_KU_KAI", PrintAttributes.MediaSize.OM_JUURO_KU_KAI);
            put("OM_PA_KAI", PrintAttributes.MediaSize.OM_PA_KAI);
            put("PRC_1", PrintAttributes.MediaSize.PRC_1);
            put("PRC_10", PrintAttributes.MediaSize.PRC_10);
            put("PRC_16K", PrintAttributes.MediaSize.PRC_16K);
            put("PRC_2", PrintAttributes.MediaSize.PRC_2);
            put("PRC_3", PrintAttributes.MediaSize.PRC_3);
            put("PRC_4", PrintAttributes.MediaSize.PRC_4);
            put("PRC_5", PrintAttributes.MediaSize.PRC_5);
            put("PRC_6", PrintAttributes.MediaSize.PRC_6);
            put("PRC_7", PrintAttributes.MediaSize.PRC_7);
            put("PRC_8", PrintAttributes.MediaSize.PRC_8);
            put("PRC_9", PrintAttributes.MediaSize.PRC_9);
            put("ROC_16K", PrintAttributes.MediaSize.ROC_16K);
            put("ROC_8K", PrintAttributes.MediaSize.ROC_8K);
            put("UNKNOWN_LANDSCAPE", PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE);
            put("UNKNOWN_PORTRAIT", PrintAttributes.MediaSize.UNKNOWN_PORTRAIT);
        }
    };

    public RNPrintModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;

    }

    @Override
    public String getName() {
        return "RNPrint";
    }

    WebView mWebView;

    @ReactMethod
    public void getPaperSize(Promise promise) {
        try {
            Set<String> keys = allPaperSize.keySet();
            WritableArray paperSize = new WritableNativeArray();
            for (String k : keys) {
                paperSize.pushString(k);
            }
            promise.resolve(paperSize);
        } catch (Exception e) {
            promise.reject(e);
        }

    }

    @ReactMethod
    public void print(final ReadableMap options, final Promise promise) {

        final String html = options.hasKey("html") ? options.getString("html") : null;
        final String filePath = options.hasKey("filePath") ? options.getString("filePath") : null;
        final boolean isLandscape = options.hasKey("isLandscape") ? options.getBoolean("isLandscape") : false;
        final String paperSize = options.hasKey("paperSize") ? options.getString("paperSize") : "NA_LETTER";

        if ((html == null && filePath == null) || (html != null && filePath != null)) {
            promise.reject(getName(),
                    "Must provide either `html` or `filePath`.  Both are either missing or passed together");
            return;
        }

        if (html != null) {
            try {
                UiThreadUtil.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Create a WebView object specifically for printing
                        WebView webView = new WebView(reactContext);
                        webView.setWebViewClient(new WebViewClient() {
                            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                return false;
                            }

                            @Override
                            public void onPageFinished(WebView view, String url) {
                                // Get the print manager.
                                PrintManager printManager = (PrintManager) getCurrentActivity()
                                        .getSystemService(Context.PRINT_SERVICE);
                                // Create a wrapper PrintDocumentAdapter to clean up when done.
                                PrintDocumentAdapter adapter = new PrintDocumentAdapter() {
                                    private final PrintDocumentAdapter mWrappedInstance = mWebView
                                            .createPrintDocumentAdapter();

                                    @Override
                                    public void onStart() {
                                        mWrappedInstance.onStart();
                                    }

                                    @Override
                                    public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes,
                                            CancellationSignal cancellationSignal, LayoutResultCallback callback,
                                            Bundle extras) {
                                        mWrappedInstance.onLayout(oldAttributes, newAttributes, cancellationSignal,
                                                callback, extras);
                                    }

                                    @Override
                                    public void onWrite(PageRange[] pages, ParcelFileDescriptor destination,
                                            CancellationSignal cancellationSignal, WriteResultCallback callback) {
                                        mWrappedInstance.onWrite(pages, destination, cancellationSignal, callback);
                                    }

                                    @Override
                                    public void onFinish() {
                                        mWrappedInstance.onFinish();
                                    }
                                };
                                // Pass in the ViewView's document adapter.
                                PrintAttributes.Builder builder = new PrintAttributes.Builder();
                                PrintAttributes.MediaSize printAttr = allPaperSize.get(paperSize);
                                if (isLandscape)
                                    printAttr.asLandscape();
                                builder.setMediaSize(printAttr);
                                printManager.print(jobName, adapter, builder.build());
                                mWebView = null;
                                promise.resolve(jobName);
                            }
                        });

                        webView.loadDataWithBaseURL(null, html, "text/HTML", "UTF-8", null);

                        // Keep a reference to WebView object until you pass the PrintDocumentAdapter
                        // to the PrintManager
                        mWebView = webView;
                    }
                });
            } catch (Exception e) {
                promise.reject("print_error", e);
            }
        } else {
            try {

                PrintManager printManager = (PrintManager) getCurrentActivity().getSystemService(Context.PRINT_SERVICE);
                PrintDocumentAdapter pda = new PrintDocumentAdapter() {

                    @Override
                    public void onWrite(PageRange[] pages, final ParcelFileDescriptor destination,
                            CancellationSignal cancellationSignal, final WriteResultCallback callback) {
                        try {
                            boolean isUrl = URLUtil.isValidUrl(filePath);

                            if (isUrl) {
                                new Thread(new Runnable() {
                                    public void run() {
                                        try {
                                            InputStream input = new URL(filePath).openStream();
                                            loadAndClose(destination, callback, input);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
                            } else {
                                InputStream input = new FileInputStream(filePath);
                                loadAndClose(destination, callback, input);
                            }

                        } catch (FileNotFoundException ee) {
                            promise.reject(getName(), "File not found");
                        } catch (Exception e) {
                            // Catch exception
                            promise.reject(getName(), e);
                        }
                    }

                    @Override
                    public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes,
                            CancellationSignal cancellationSignal, LayoutResultCallback callback, Bundle extras) {

                        if (cancellationSignal.isCanceled()) {
                            callback.onLayoutCancelled();
                            return;
                        }

                        PrintDocumentInfo pdi = new PrintDocumentInfo.Builder(jobName)
                                .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT).build();

                        callback.onLayoutFinished(pdi, true);
                    }
                };

                // PrintAttributes printAttributes = new PrintAttributes.Builder()
                // .setMediaSize(isLandscape ? PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE
                // : PrintAttributes.MediaSize.UNKNOWN_PORTRAIT)
                // .build();

                PrintAttributes.Builder builder = new PrintAttributes.Builder();
                PrintAttributes.MediaSize printAttr = allPaperSize.get(paperSize);
                if (isLandscape)
                    printAttr.asLandscape();
                builder.setMediaSize(printAttr);
                printManager.print(jobName, pda, builder.build());
                promise.resolve(jobName);

            } catch (Exception e) {
                promise.reject(getName(), e);
            }
        }
    }

    private void loadAndClose(ParcelFileDescriptor destination, PrintDocumentAdapter.WriteResultCallback callback,
            InputStream input) throws IOException {
        OutputStream output = null;
        output = new FileOutputStream(destination.getFileDescriptor());

        byte[] buf = new byte[1024];
        int bytesRead;

        while ((bytesRead = input.read(buf)) > 0) {
            output.write(buf, 0, bytesRead);
        }

        callback.onWriteFinished(new PageRange[] { PageRange.ALL_PAGES });

        try {
            input.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
