package dev.weslley.aleatorio;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.sanojpunchihewa.glowbutton.GlowButton;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.Objects;

import dev.weslley.aleatorio.data.thread.RandomTask;
import dev.weslley.aleatorio.service.RandomService;
import dev.weslley.aleatorio.util.Constant;
import it.sephiroth.android.library.numberpicker.NumberPicker;

public class MainActivity extends AppCompatActivity {

    private static final String MAX_PREFERENCE = "dev.weslley.aleatorio.MAX_PREFERENCE";
    private static final String CURRENT_SAVED = "dev.weslley.aleatorio.CURRENT_VALIE";

    private TextView numero;
    private NumberPicker pickerMax;
    private RandomService random;

    private int min = 1, max = 999;
    private GlowButton button;
    private Handler handler;
    private Long current = 9999l;
    private Thread executor;
    private HandlerThread handlerThread;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pickerMax = findViewById(R.id.picker_max);
        numero = findViewById(R.id.tx_numero);

        random = new RandomService();
        button = findViewById(R.id.bt_generate);
        handlerThread = new HandlerThread("aleatoryThread");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
        executor = new Thread(new Runnable() {
            @Override
            public void run() {
                generate();
            }
        });
        
        preferences = getPreferences(MODE_PRIVATE);
        max = preferences.getInt(MAX_PREFERENCE, max);
        if (savedInstanceState!=null){
            current = savedInstanceState.getLong(CURRENT_SAVED, current);
        }
        updateNumberUi();
    }

    @Override
    protected void onStart() {
        super.onStart();
        pickerMax.setProgress(max);
        pickerMax.setNumberPickerChangeListener(new NumberPicker.OnNumberPickerChangeListener() {
            @Override
            public void onProgressChanged(@NotNull NumberPicker numberPicker, int i, boolean b) {
                max = i;
                savePref(MAX_PREFERENCE, max);
            }

            @Override
            public void onStartTrackingTouch(@NotNull NumberPicker numberPicker) {

            }

            @Override
            public void onStopTrackingTouch(@NotNull NumberPicker numberPicker) {

            }
        });

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (executor.getState().equals(Thread.State.TERMINATED)) {
                        executor = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                generate();
                            }
                        });
                    }
                    if (!executor.getState().equals(Thread.State.RUNNABLE)) {
                        executor.start();
                    }

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    executor.interrupt();
                }
                return true;
            }
        });
    }

    public void generate() {
        while (!Thread.currentThread().isInterrupted()) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(runner);
                }
            }, 150);
        }
    }

    private Runnable runner = new Runnable() {
        @Override
        public void run() {
            current = random.generateRandomNumber(max);
            updateNumberUi();
        }
    };

    private void updateNumberUi(){
        DecimalFormat df = new DecimalFormat();
        df.setGroupingUsed(false);
        df.setMinimumIntegerDigits(Integer.toString(max).length());
        numero.setText(df.format(current));
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(CURRENT_SAVED,current);
    }

    private void savePref(String key, int value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }
}
