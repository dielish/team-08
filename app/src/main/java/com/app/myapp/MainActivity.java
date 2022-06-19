package com.app.myapp;

import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private ImageView mImgViewDice;
    private TextView mTxtResult;
    private Button mBtnRollDice;

    // 建立一個靜態的Handler類別
    private static class StaticHandler extends Handler {
        private final WeakReference<MainActivity> mActivity;

        public StaticHandler(MainActivity activity) {
            mActivity = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            // 檢查App是否還存在，如果不存在就離開
            MainActivity activity = mActivity.get();
            if (activity == null) return;

            // 用亂數決定骰子點數
            int iRand = (int)(Math.random()*15 + 1);

            // 顯示結果
            String p = null;


            activity.mTxtResult.setText("");
            switch (iRand) {
                case 1:
                    p="滷肉飯";

                    break;
                case 2:
                    p="乾麵";

                    break;
                case 3:
                    p="壽司";

                    break;
                case 4:
                    p="拉麵";

                    break;
                case 5:
                    p="水餃";

                    break;
                case 6:
                    p="鍋貼";

                    break;
                case 7:
                    p="咖哩";

                    break;
                case 8:
                    p="雞肉飯";

                    break;
                case 9:
                    p="披薩";

                    break;
                case 10:
                    p="炸雞";

                    break;
                case 11:
                    p="肉羹麵";

                    break;
                case 12:
                    p="餛飩麵";

                    break;
                case 13:
                    p="黃檸檬優格蛋糕";

                    break;
                case 14:
                    p="無水紅燒肉";

                    break;
                case 15:
                    p="作仙";

                    break;

            }
            activity.mTxtResult.setText(p );
        }
    }

    // 使用自己建立的靜態Handler避免出現記憶體漏洞。
    public final StaticHandler mHandler = new StaticHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImgViewDice = findViewById(R.id.imgViewDice);
        mTxtResult = findViewById(R.id.txtResult);
        mBtnRollDice = findViewById(R.id.btnRollDice);

        mBtnRollDice.setOnClickListener(btnRollDiceOnClick);
    }

    private View.OnClickListener btnRollDiceOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String s = getString(R.string.dice_result);
            mTxtResult.setText(s);

            // 從程式資源載入動畫，設定給ImageView物件，然後開始播放
            Resources res = getResources();
            final AnimationDrawable animDraw =
                    (AnimationDrawable) res.getDrawable(R.drawable.anim_roll_dice);
            mImgViewDice.setImageDrawable(animDraw);
            animDraw.start();

            // 啟動Background Thread計時
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(5000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    animDraw.stop();
                    mHandler.sendMessage(mHandler.obtainMessage());
                }
            }).start();
        }
    };
}
