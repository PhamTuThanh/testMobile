package com.phamtuthanh.learn26_4_vd3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.phamtuthanh.learn26_4_vd3.databinding.ActivityMainBinding;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Random random = new Random();
    //main/UI thread
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            int percent = message.arg1;
            int ranNumb = (int) message.obj;

            binding.txtPercent.setText(percent + "%");
            binding.pgPercent.setProgress(percent);
            //update UI
            ImageView imageView = new ImageView(MainActivity.this);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            imageView.setLayoutParams(params);
            if(ranNumb%2 ==0)
            imageView.setImageDrawable(getDrawable(R.drawable.baseline_android_24));
            else
                imageView.setImageDrawable(getDrawable(R.drawable.baseline_autorenew_24));
            binding.containerLayout.addView(imageView);


            if(percent == 100){
                binding.txtPercent.setText("DONE");
            }
            return false;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addEvent();
    }

    private void addEvent() {
        binding.btnDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawBackground();
            }
        });
    }

    private void drawBackground(){
        binding.containerLayout.removeAllViews();
        int numbOfViews = Integer.parseInt(binding.edtNumOfViews.getText().toString());
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i =0; i <= numbOfViews; i++){
                    Message message = handler.obtainMessage();
                    message.arg1 = i*100 /numbOfViews; //computing percent
                    message.obj = random.nextInt(100);//random number
                    handler.sendMessage(message);
                    SystemClock.sleep(100);
                }
            }
        });
        thread.start();
    }

}