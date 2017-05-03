package com.gaos.dropdialogex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;


public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        Button btnDialog = (Button) findViewById(R.id.btn_dialog_main);
        btnDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void showDialog() {
        final FrameLayout flMain = (FrameLayout) findViewById(R.id.activity_main);
        final DropDialogView dropDialogView = new DropDialogView(MainActivity.this);
        flMain.addView(dropDialogView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dropDialogView.showDialog(new DropDialogView.IEvent() {
            @Override
            public void onDismissCompleted() {
                flMain.removeView(dropDialogView);
            }
        });
    }
}
