package com.sai628.moviejie.activity.info;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sai628.moviejie.R;
import com.sai628.moviejie.activity.base.BaseActivity;
import com.sai628.moviejie.model.LinkDetailInfo;
import com.sai628.moviejie.service.net.NetHelper;
import com.sai628.moviejie.service.net.NetService;
import com.sai628.moviejie.utils.ContextUtil;
import com.sai628.moviejie.utils.SystemUtil;
import com.sai628.moviejie.view.CustomDialog;
import com.sai628.moviejie.view.LoadingMenu;

import es.dmoral.toasty.Toasty;


/**
 * @author Sai
 * @ClassName: LinkInfoActivity
 * @Description: "下载链接"信息视图
 * @date 11/05/2018 17:26
 */
public class LinkInfoActivity extends BaseActivity implements View.OnClickListener, LoadingMenu.OnRetryClickListener
{
    public static final String EXTRA_LINK = "link";

    private ImageView backIv;

    private TextView movieTitleTv;
    private TextView linkNameTv;
    private TextView linkSizeTv;
    private TextView downloadLinkTv;
    private Button copyBtn;
    private Button thunderBtn;

    private LoadingMenu loadingMenu;

    private LinkDetailInfo linkDetailInfo;
    private String link;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_link_info_view);

        initData();
        initView();
        loadingMenu.showLoading();
        loadLinkDetailInfo();
    }


    private void initData()
    {
        link = getIntent().getStringExtra(EXTRA_LINK);
    }


    private void initView()
    {
        backIv = findViewById(R.id.info_link_info_view_back_imageview);
        backIv.setOnClickListener(this);

        movieTitleTv = findViewById(R.id.info_link_info_view_title_textview);
        linkNameTv = findViewById(R.id.info_link_info_view_name_textview);
        linkSizeTv = findViewById(R.id.info_link_info_view_size_textview);
        downloadLinkTv = findViewById(R.id.info_link_info_view_download_link_textview);
        copyBtn = findViewById(R.id.info_link_info_view_copy_button);
        thunderBtn = findViewById(R.id.info_link_info_view_thunder_button);

        movieTitleTv.setOnClickListener(this);
        copyBtn.setOnClickListener(this);
        thunderBtn.setOnClickListener(this);

        loadingMenu = findViewById(R.id.loading_menu);
        loadingMenu.setOnRetryClickListener(this);
    }


    private void updateUI(LinkDetailInfo info)
    {
        linkDetailInfo = info;

        movieTitleTv.setText(linkDetailInfo.getMovie_title());
        linkNameTv.setText(linkDetailInfo.getName());
        linkSizeTv.setText(String.format("大小: %s", linkDetailInfo.getSize()));
        downloadLinkTv.setText(linkDetailInfo.getDownload_link());

        if (TextUtils.isEmpty(linkDetailInfo.getMovie_link()))
        {
            movieTitleTv.setVisibility(View.GONE);
        }
    }


    private void loadLinkDetailInfo()
    {
        NetService.getLinkDetailInfo(this, link, new NetHelper.NetCallback()
        {
            @Override
            public void onSuccess(Object data)
            {
                LinkDetailInfo info = (LinkDetailInfo) data;
                if (info == null)
                {
                    loadingMenu.showEmpty();
                    return;
                }

                loadingMenu.dismiss();
                updateUI(info);
            }


            @Override
            public void onError(int errorCode, String errorMsg)
            {
                loadingMenu.showFail();
            }


            @Override
            public void onFailure(String failureMsg)
            {
                loadingMenu.showFail();
            }
        });
    }


    private void showNotFoundAppDialog(String appName, final String packageName)
    {
        CustomDialog.showDialog(this, getString(R.string.prompt), String.format("\"%s\"应用没安装或版本过低, 立即下载?", appName),
                getString(R.string.download_now), new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                        ContextUtil.openURL(getThis(), getString(R.string.thunder_download_page_url));
                    }
                }, getString(R.string.cancel), new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });
    }


    @Override
    public void onRetryClick(View v)
    {
        loadLinkDetailInfo();
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.info_link_info_view_back_imageview:
                finish();
                break;

            case R.id.info_link_info_view_title_textview:
                String movieLink = linkDetailInfo.getMovie_link();
                if (!TextUtils.isEmpty(movieLink))
                {
                    ContextUtil.readMovieInfo(getThis(), movieLink);
                }
                break;

            case R.id.info_link_info_view_copy_button:
                SystemUtil.copyToClipboard(getThis(), linkDetailInfo.getDownload_link());
                Toasty.success(getThis(), "已复制到剪贴板").show();
                break;

            case R.id.info_link_info_view_thunder_button:
                if (SystemUtil.isInstallPackage(this, getString(R.string.thunder_package_name)))
                {
                    String link = String.format("AA%sZZ", linkDetailInfo.getDownload_link());
                    String linkBase64 = Base64.encodeToString(link.getBytes(), Base64.NO_WRAP);
                    ContextUtil.openURL(getThis(), "thunder://" + linkBase64);
                }
                else
                {
                    showNotFoundAppDialog(getString(R.string.thunder_app_name), getString(R.string.thunder_package_name));
                }
                break;
        }
    }
}
