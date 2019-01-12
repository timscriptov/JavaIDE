package com.example.HelloAndroid;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity
{
  private TextView tv;
  private Resources res;
  public static String stProgramName="HelloAndroid";
  
//===================================================================
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
//===================================================================
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    tv = (TextView)findViewById(R.id.vwHello);
    res = getResources();
  }
//===================================================================
  @Override 
  public void onBackPressed()
//===================================================================
  {
    
  }//onBackPressed
//===================================================================
  public void OnClickBtnGo (final View view)
//===================================================================
  {
    tv.append (res.getString(R.string.msg_click));
  }
//===================================================================
  public boolean onCreateOptionsMenu (Menu menu)
//===================================================================
  {
    getMenuInflater().inflate(R.menu.mainmenu, menu);
    return super.onCreateOptionsMenu(menu);
  }
//===================================================================
  public boolean onOptionsItemSelected (MenuItem item)
//===================================================================
  {
    switch (item.getItemId())
    {
      case R.id.opt_exit:
        finish();
        return true;
      case R.id.opt_infos:
        fnInfos ();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  } //onOptionsItemSelected
//===================================================================
  private void fnInfos ()
//===================================================================
  {
    final Intent i;
    try
    {
      i = new Intent (this, AboutActivity.class);
      Log.i(stProgramName, "Starting info activity");
      startActivity(i);
    }
    catch (Exception e)
    { tv.append(e.getMessage()); }
  } //fnInfos
//===================================================================
} // MainActivity
