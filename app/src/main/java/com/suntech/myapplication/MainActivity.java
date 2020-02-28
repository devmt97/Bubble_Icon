package com.suntech.myapplication;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.fangxu.allangleexpandablebutton.AllAngleExpandableButton;
import com.fangxu.allangleexpandablebutton.ButtonData;
import com.nex3z.notificationbadge.NotificationBadge;
import com.suntech.bubble_drag.widget.BubbleLayout;
import com.suntech.bubble_drag.widget.BubblesManager;
import com.suntech.bubble_drag.widget.OnInitializedCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private BubblesManager bubblesManager;
    private NotificationBadge mBadge;

    private int MY_PERMISSION = 1000;
    private RelativeLayout rlLeft;
    private ImageView iv1, iv2, iv3, iv4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initBubble();

        Button btnAdd = (Button) findViewById(R.id.btnAddBubble);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewBubble();
            }
        });

        //Check permission
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(MainActivity.this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, MY_PERMISSION);
            }
        } else {
            Intent intent = new Intent(MainActivity.this, Service.class);
            startService(intent);
        }
    }

    private void initBubble() {
        bubblesManager = new BubblesManager.Builder(this)
                .setTrashLayout(R.layout.bubble_remove)
                .setInitializationCallback(new OnInitializedCallback() {
                    @Override
                    public void onInitialized() {
                        addNewBubble();
                    }
                }).build();
        bubblesManager.initialize();
    }

    private boolean isClick;

    private void addNewBubble() {
        BubbleLayout bubbleView = (BubbleLayout) LayoutInflater.from(MainActivity.this)
                .inflate(R.layout.frame_item, null);
        iv1 = bubbleView.findViewById(R.id.iv1);
        iv1.setOnClickListener(this);
        iv2 = bubbleView.findViewById(R.id.iv2);
        iv3 = bubbleView.findViewById(R.id.iv3);
        iv4 = bubbleView.findViewById(R.id.iv4);

//        AllAngleExpandableButton expanded_menu = bubbleView.findViewById(R.id.expanded_menu);
//        initMenu(expanded_menu);
//        mBadge = (NotificationBadge)bubbleView.findViewById(R.id.count);
//        mBadge.setNumber(88);

        bubbleView.setOnBubbleRemoveListener(new BubbleLayout.OnBubbleRemoveListener() {
            @Override
            public void onBubbleRemoved(BubbleLayout bubble) {
                Toast.makeText(MainActivity.this, "Removed", Toast.LENGTH_SHORT).show();
            }
        });

        bubbleView.setOnBubbleClickListener(new BubbleLayout.OnBubbleClickListener() {
            @Override
            public void onBubbleClick(BubbleLayout bubble) {
                if (isClick) {
                    iv1.setVisibility(View.INVISIBLE);
                    iv2.setVisibility(View.INVISIBLE);
                    iv3.setVisibility(View.INVISIBLE);
                    iv4.setVisibility(View.INVISIBLE);
                } else {
                    iv1.setVisibility(View.VISIBLE);
                    iv2.setVisibility(View.VISIBLE);
                    iv3.setVisibility(View.VISIBLE);
                    iv4.setVisibility(View.VISIBLE);
                }
                isClick = !isClick;

                Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        bubbleView.setOnBubbleRemoveListener(new BubbleLayout.OnBubbleRemoveListener() {
            @Override
            public void onBubbleRemoved(BubbleLayout bubble) {

            }

        });
        bubbleView.setShouldStickToWall(true);
        bubblesManager.addBubble(bubbleView, 100, 500);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bubblesManager.recycle();
    }

    private void initMenu(AllAngleExpandableButton expanded_menu) {
        List<ButtonData> buttonDatas = new ArrayList<>();
        int[] drawable = new int[]{
                R.drawable.ic_menu_record, R.drawable.ic_recording, R.drawable.ic_camera,
                R.drawable.ic_settings, R.drawable.ic_record_done
        };
        for (int i = 0; i < drawable.length; i++) {
            ButtonData buttonData = ButtonData.buildIconButton(this, drawable[i], 0f);
            buttonData.setBackgroundColorId(this, R.color.colorWhite);
            buttonDatas.add(buttonData);
        }
        expanded_menu.setButtonDatas(buttonDatas);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "video", Toast.LENGTH_SHORT).show();
    }
}
