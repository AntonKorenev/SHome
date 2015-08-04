package com.shometeam.ao.shome.CustomGuiElements.NavigationDrawer;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.shometeam.ao.shome.R;
import com.shometeam.ao.shome.Screens.ContactFragment;
import com.shometeam.ao.shome.Screens.MainPart.MainFragment;

/**
 * Created by ao on 24.07.15.
 */
public abstract class DrawerActivity extends AppCompatActivity implements
    NavigationView.OnNavigationItemSelectedListener {

  private static final long sDrawerCloseDelay = 350;
  private static final String sDrawerItemId = "DRAWER_ITEM_ID";

  private final Handler mDrawerActionHandler = new Handler();
  private DrawerLayout mDrawerLayout;
  private NavigationView mDrawerView;
  private ActionBarDrawerToggle mDrawerToggle;
  private int mDrawerItemId;

  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //initializing components
    mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    mDrawerView = (NavigationView) findViewById(R.id.navigation);
    TextView tvAccountName = (TextView) findViewById(R.id.text_view_header_account);
    TextView tvMailName = (TextView) findViewById(R.id.text_view_header_mail_adress);

    //initializing drawer text and avatar
    AccountManager manager = AccountManager.get(this);
    Account[] accounts = manager.getAccountsByType("com.google");
    String name = accounts[0].name;
    if(!name.isEmpty()){
      int dog = name.indexOf("@");
      tvAccountName.setText(name.substring(0,dog));
      tvMailName.setText(name.substring(dog+1));
    }

    //initializing toolbar
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    // load saved navigation state if exist
    if (null == savedInstanceState) {
      mDrawerItemId = R.id.main_item;
    } else {
      mDrawerItemId = savedInstanceState.getInt(sDrawerItemId);
    }

    // listen for navigation events
    mDrawerView.setNavigationItemSelectedListener(this);

    // select the correct nav menu item
    mDrawerView.getMenu().findItem(mDrawerItemId).setChecked(true);

    // set up the hamburger icon to open and close the drawer
    mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open,
        R.string.close);
    mDrawerLayout.setDrawerListener(mDrawerToggle);
    mDrawerToggle.syncState();
    navigate(mDrawerItemId);

    onCreateAdditional();
  }

  protected abstract void onCreateAdditional();

  /**
   * Performs the actual navigation logic, updating the main content fragment.
   */
  private void navigate(final int itemId) {
    switch (itemId) {
      case R.id.main_item:
        getFragmentManager()
            .beginTransaction()
            .replace(R.id.content, new MainFragment())
            .commit();
        break;
      case R.id.greenhouse_item:
        getFragmentManager()
            .beginTransaction()
            .replace(R.id.content, new MainFragment())
            .commit();
        break;
      case R.id.climateplan_item:
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.content, new MainFragment())
                .commit();
        break;
      case R.id.contact_item:
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.content, new ContactFragment())
                .commit();
        break;
      default:
        // ignore
        break;
    }
  }

  /**
   * Handles clicks on the navigation menu.
   */
  @Override
  public boolean onNavigationItemSelected(final MenuItem menuItem) {
    // update highlighted item in the navigation menu
    menuItem.setChecked(true);
    mDrawerItemId = menuItem.getItemId();

    if (menuItem.getGroupId() == R.id.gr2) {
      mDrawerView.getMenu().setGroupCheckable(R.id.gr1, false, true);
      mDrawerView.getMenu().setGroupCheckable(R.id.gr2, true, true);
    }else{
      mDrawerView.getMenu().setGroupCheckable(R.id.gr1, true, true);
      mDrawerView.getMenu().setGroupCheckable(R.id.gr2, false, true);
    }
    //Update highlighted item in the navigation menu
    menuItem.setChecked(true);

    // allow some time after closing the drawer before performing real navigation
    // so the user can see what is happening
    mDrawerLayout.closeDrawer(GravityCompat.START);
    mDrawerActionHandler.postDelayed(new Runnable() {
      @Override
      public void run() {
        navigate(menuItem.getItemId());
      }
    }, sDrawerCloseDelay);
    return true;
  }

  @Override
  public void onConfigurationChanged(final Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    mDrawerToggle.onConfigurationChanged(newConfig);
  }

  @Override
  public boolean onOptionsItemSelected(final MenuItem item) {
    if (item.getItemId() == android.support.v7.appcompat.R.id.home) {
      return mDrawerToggle.onOptionsItemSelected(item);
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onBackPressed() {
    if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
      mDrawerLayout.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  protected void onSaveInstanceState(final Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt(sDrawerItemId, mDrawerItemId);
  }
}
