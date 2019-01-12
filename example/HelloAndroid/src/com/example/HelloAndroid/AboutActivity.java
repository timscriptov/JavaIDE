package com.example.HelloAndroid;

import android.app.Activity;
import android.content.pm.*;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class AboutActivity extends Activity
{
  private TextView tv;
  
//===================================================================
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
//===================================================================
  {
    setContentView(R.layout.info);
    tv = (TextView)findViewById(R.id.vwInfo);
    fnShowInfos();
    super.onCreate(savedInstanceState);
  }
//===================================================================
  private void fnShowInfos()
//===================================================================
  {
    String stInfo = "", pkgName;
    PackageManager pm;
    PackageInfo pkgInfo;
    ApplicationInfo appInfo;
   
    try
    {
      pm = getPackageManager();
      pkgName = getPackageName();
      pkgInfo = pm.getPackageInfo(pkgName,0);
      appInfo = getApplicationInfo();
      stInfo += "Package name:       "+pkgName+"\n";
      stInfo += "Version name:       "+pkgInfo.versionName +"\n";
      stInfo += "Version code:       "+pkgInfo.versionCode +"\n";
      stInfo += "Target SDK version: "+appInfo.targetSdkVersion +"\n";
      stInfo += "Device API level:   "+android.os.Build.VERSION.SDK_INT+"\n";
      tv.setText (stInfo);
    }
    catch (Exception e)
    {
      Log.e(MainActivity.stProgramName, e.toString());
    }
  } // fnShowInfos
//===================================================================
} // InfoActivity
