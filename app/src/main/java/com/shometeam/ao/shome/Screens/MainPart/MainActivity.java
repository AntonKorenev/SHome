package com.shometeam.ao.shome.Screens.MainPart;
import com.shometeam.ao.shome.CustomGuiElements.NavigationDrawer.DrawerActivity;
import com.shometeam.ao.shome.SyncClasses.DropboxConnector;

public class MainActivity extends DrawerActivity {
    private DropboxConnector mDBoxConnector;

    @Override
    protected void onCreateAdditional() {

        //начнем коннект к Dropbox
        //mDBoxConnector = new DropboxConnector();
        //mDBoxConnector.startAuthentification(this);

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
    }

    @Override
    protected void onResume() {
        super.onResume();

        //mDBoxConnector.finishAuthentification(this);
    }
}