package com.yeungeek.monkeyandroid.ui.signin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yeungeek.monkeyandroid.rxbus.RxBus;
import com.yeungeek.monkeyandroid.rxbus.event.SignInEvent;
import com.yeungeek.monkeyandroid.ui.base.view.BaseActivity;

import javax.inject.Inject;

import timber.log.Timber;


public class SignInDialogFragment extends DialogFragment {
    private String url;
    private final static String EXTRA_URL = "extra_url";

    @Inject
    RxBus rxBus;

    public static SignInDialogFragment newInstance(final String url) {
        SignInDialogFragment fragment = new SignInDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_URL, url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseActivity) getActivity()).activityComponent().inject(this);
        url = getArguments().getString(EXTRA_URL);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final WebView webView = new WebView(this.getActivity()) {
            boolean layoutChangedOnce = false;
            @Override
            protected void onLayout(boolean changed, int l, int t, int r, int b) {
                if (!layoutChangedOnce) {
                    super.onLayout(changed, l, t, r, b);
                    layoutChangedOnce = true;
                }
            }

            @Override
            protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
                super.onFocusChanged(true, direction, previouslyFocusedRect);
            }

            @Override
            public boolean onCheckIsTextEditor() {
                return true;
            }
        };

        webView.loadUrl(url);
        webView.setFocusable(true);
        webView.setFocusableInTouchMode(true);
        webView.requestFocus(View.FOCUS_DOWN);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri uri = Uri.parse(url);
                if (uri.getScheme().equals("http")) {
                    Timber.d("### get url %s",url);
                    if(null != rxBus && rxBus.hasObservers()){
                        rxBus.send(new SignInEvent(uri));
                    }

                    SignInDialogFragment.this.dismiss();
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        webView.requestFocus(View.FOCUS_DOWN);
        builder.setView(webView);
        return builder.create();
    }
}
