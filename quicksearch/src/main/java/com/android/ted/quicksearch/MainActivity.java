package com.android.ted.quicksearch;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;


@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

    @ViewById(R.id.editText)
    EditText mEditText;

    @Click(R.id.button)
    void onClickBtn(){
        String input = mEditText.getText().toString();
        if (TextUtils.isEmpty(input)){
            return;
        }
        onSearchData(input,2);
    }


    @Click(R.id.check)
    void onClickCheck(){
        ResolveInfo info = getDefaultAppInfo();
        if(null == info || TextUtils.isEmpty(info.activityInfo.packageName)){
            Toast.makeText(this,"检查默认程序失败",Toast.LENGTH_SHORT).show();
            return;
        }
        if("android".equalsIgnoreCase(info.activityInfo.packageName)){
            Toast.makeText(this,"尚未设置默认程序",Toast.LENGTH_SHORT).show();
        }else {
            Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:"+info.activityInfo.packageName));
            startActivity(intent);
        }
    }

    @Click(R.id.set)
    void onClickSetBtn(){
        startChooseDialog();
    }

    private ResolveInfo getDefaultAppInfo(){
        PackageManager pm = getPackageManager();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://www.baidu.com/"));
        ResolveInfo info = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return info;
    }

    public void startChooseDialog() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.BROWSABLE");
        intent.setData(Uri.parse("http://www.baidu.com"));
        intent.setComponent(new ComponentName("android","com.android.internal.app.ResolverActivity"));
        startActivity(intent);
    }

    private void onSearchData(String data,int type){
        Uri searchUri;
        switch (type){
            case 0:
                searchUri = Uri.parse("http://www.baidu.com/s?wd="+data);
                break;
            case 1:
                searchUri = Uri.parse("http://s.m.taobao.com/search.htm?_input_charset=utf-8&sst=1&atype=b&top_search=1&tab=all&abtest=0&q="+data);
                break;
            case 2:
                searchUri = Uri.parse("http://m.jd.com/ware/search.action?sid=&keyword="+data);
                break;
            default:
                searchUri = Uri.parse("http://www.baidu.com/s?wd="+data);
                break;
        }
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.BROWSABLE");
        intent.setData(searchUri);
        startActivity(intent);
    }


}
