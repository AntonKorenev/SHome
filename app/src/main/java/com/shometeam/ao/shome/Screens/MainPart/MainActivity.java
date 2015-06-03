package com.shometeam.ao.shome.Screens.MainPart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Toast;

import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Badgeable;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.shometeam.ao.shome.R;
import com.shometeam.ao.shome.Screens.ContactFragment;
import com.shometeam.ao.shome.Screens.HelpFragment;
import com.shometeam.ao.shome.Screens.RoomsPart.RoomsActivity;
import com.shometeam.ao.shome.Screens.SettingsPart.SettingsActivity;
import com.shometeam.ao.shome.Screens.StatisticsFragment;
import com.shometeam.ao.shome.SyncClasses.DropboxConnector;

public class MainActivity extends AppCompatActivity {

    private Drawer.Result drawerResult = null;
    private DropboxConnector mDBoxConnector;
    private String mCurrentActivityName = "Home";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //начнем коннект к dropbox
        mDBoxConnector = new DropboxConnector();
        mDBoxConnector.startAuthentification(this);

        /*
        //пример загрузки/закачки
        final File tempDir = this.getCacheDir();
        File tempFile, tempFile2;
        String[] names = {"arcx.txt"};
        try {
            tempFile = File.createTempFile("fileas", ".txt", tempDir);
            FileConnector.writeTo(tempFile, "22222222");
            mDBoxConnector.upload(this, names, tempFile);

            tempFile2 = File.createTempFile("fileas2", ".txt", tempDir);
            FileConnector.writeTo(tempFile2,"11111111");
            tempFile2 = mDBoxConnector.download(names, tempFile2).get(0);

            FileConnector.show(tempFile2, "Downloaded file" );

        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }*/


        // Инициализируем Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Иниц первый фрагмент(главный экран)
        Fragment f = new MainFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();

        final Intent roomsIntent = new Intent(MainActivity.this, RoomsActivity.class);
        final Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);

        // Инициализируем Navigation Drawer
        drawerResult = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.drawer_header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(FontAwesome.Icon.faw_home).withBadge("99").withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_rooms).withIcon(FontAwesome.Icon.faw_gamepad),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_statistics).withIcon(FontAwesome.Icon.faw_eye).withBadge("6").withIdentifier(2),
                        new SectionDrawerItem().withName(R.string.drawer_item_settings),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_shome_settings).withIcon(FontAwesome.Icon.faw_cog),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_help).withIcon(FontAwesome.Icon.faw_question).setEnabled(true),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_contact).withIcon(FontAwesome.Icon.faw_github).withBadge("12+").withIdentifier(1)
                )
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Скрываем клавиатуру при открытии Navigation Drawer
                        InputMethodManager inputMethodManager = (InputMethodManager) MainActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(), 0);
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                    }

                    @Override
                    public void onDrawerSlide(View view, float v) {

                    }
                })
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    // Обработка клика
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        if (drawerItem instanceof Nameable) {
                            mCurrentActivityName = MainActivity.this.getString(((Nameable) drawerItem).getNameRes());
                            Toast.makeText(MainActivity.this, mCurrentActivityName, Toast.LENGTH_SHORT).show();
                            Fragment f;
                            switch (mCurrentActivityName) {
                                case "Rooms":
                                    startActivity(roomsIntent);
                                    break;
                                case "Home":
                                    f = new MainFragment();
                                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
                                    break;
                                case "Statistics":
                                    f = new StatisticsFragment();
                                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
                                    break;
                                case "Smart home":
                                    startActivity(settingsIntent);
                                    break;
                                case "Help":
                                    f = new HelpFragment();
                                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
                                    break;
                                default:
                                    f = new ContactFragment();
                                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
                                    break;
                            }

                        }
                        if (drawerItem instanceof Badgeable) {
                            Badgeable badgeable = (Badgeable) drawerItem;
                            if (badgeable.getBadge() != null) {
                                // учтите, не делайте так, если ваш бейдж содержит символ "+"
                                try {
                                    int badge = Integer.valueOf(badgeable.getBadge());
                                    if (badge > 0) {
                                        drawerResult.updateBadge(String.valueOf(badge - 1), position);
                                    }
                                } catch (Exception e) {
                                    Log.d("test", "Не нажимайте на бейдж, содержащий плюс! :)");
                                }
                            }
                        }
                    }
                })
                .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
                    @Override
                    // Обработка длинного клика, например, только для SecondaryDrawerItem
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        if (drawerItem instanceof SecondaryDrawerItem) {
                            Toast.makeText(MainActivity.this, MainActivity.this.getString(((SecondaryDrawerItem) drawerItem).getNameRes()), Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                })
                .build();

    }

    @Override
    public void onBackPressed() {
        // Закрываем Navigation Drawer по нажатию системной кнопки "Назад" если он открыт
        if (drawerResult.isDrawerOpen()) {
            drawerResult.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    // Заглушка, работа с меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Заглушка, работа с меню
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mDBoxConnector.finishAuthentification(this);
    }
}