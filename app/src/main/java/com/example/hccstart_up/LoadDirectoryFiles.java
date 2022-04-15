package com.example.hccstart_up;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.File;

public class LoadDirectoryFiles extends AppCompatActivity {
    private File storage;
    private String[] allPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.files_list);

        //Load Data Here
        allPath = StorageUtil.getStorageDirectories(this);

        for (String path: allPath){
            storage = new File(path);
            Method.load_Directory_Files(storage);
        }
    }
}