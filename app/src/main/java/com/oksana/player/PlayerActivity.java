package com.oksana.player;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PlayerActivity extends AppCompatActivity {

    public class Song {
        public String Id;
        public String Path;
        public String Name;
        public String Size;
        public String Time;
        public String Owner;
        public String Image;


        public Song(String id, String path, String name, String size, String time, String owner, String img) {
            Id = id;
            Path = path;
            Name = name;
            Owner = owner;
            Size = size;
            Time = time;
            Image = img;
        }

        public Bitmap getImage() {
            return BitmapFactory.decodeFile(Image);
        }

    }

    private ArrayList<Song> devicesList;
    private MaterialCardView card;
    private int REQUEST_CODE_PERMISSION_READ_STORAGE = 1000;
    private RecyclerView listView;
    private SongAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        listView = findViewById(R.id.songsList);
       // listView.setNestedScrollingEnabled(false);
        Toolbar toolbar = findViewById(R.id.toolbar);
        if(toolbar !=null) {
            setSupportActionBar(toolbar);
        }

        int permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if(permissionStatus == PackageManager.PERMISSION_GRANTED){
            fillMediaList();
        }
        else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.MEDIA_CONTENT_CONTROL,
                    Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSION_READ_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult  (int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSION_READ_STORAGE){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                fillMediaList();
            }
        }
    }

    private void fillMediaList() {

        devicesList = new ArrayList<>();

        ContentResolver cr = getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.SIZE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM_ID
        };

        try {
            Cursor cursor = cr.query(uri, projection, selection, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    devicesList.add(new Song(
                            cursor.getString(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6)
                    ));
                }
            }
        } catch (Exception e) {
            return;
        }
        adapter = new SongAdapter(this, devicesList);
        RecyclerView.LayoutManager mngr = new LinearLayoutManager(this);
        listView.setLayoutManager(mngr);
        listView.setAdapter(adapter);

    }
    @Override public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        try {
            return super.dispatchTouchEvent(motionEvent);
        } catch (NullPointerException e) {
            return false;
        }
    }
}
