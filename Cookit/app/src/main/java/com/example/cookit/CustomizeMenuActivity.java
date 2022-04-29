package com.example.cookit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CustomizeMenuActivity extends AppCompatActivity {
    // private DatePicker custom_DatePicker;
    private Button btn_customSend, btn_datePicker;
    private EditText custom_date,calorieMinHolder,calorieMaxHolder;
    private DatePickerDialog datePickerDialog;
    private Spinner spinner,categorySpinner;
    String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_menu);
        initView();
        getSupportActionBar().setTitle("客製化菜單");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected void initView() {
        //custom_DatePicker = findViewById(R.id.custom_datePicker);
        btn_customSend = findViewById(R.id.btn_customSend);
        custom_date = findViewById(R.id.Custom_date);
        btn_datePicker = findViewById(R.id.btn_custom_date);
        spinner = findViewById(R.id.customMenuChooseTime);
        calorieMinHolder = findViewById(R.id.text_CalorieMin);
        calorieMaxHolder = findViewById(R.id.text_CalorietMax);
        categorySpinner = findViewById(R.id.CustomType);
    }

    protected void onResume() {
        super.onResume();
//        custom_DatePicker.init(2020, 10, 20, new DatePicker.OnDateChangedListener() {
//            @Override
//            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                Calendar calendar = Calendar.getInstance();
//                calendar.set(year, monthOfYear, dayOfMonth);
//                SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月DD日");
//                Toast.makeText(CustomizeMenuActivity.this, format.format(calendar.getTime()), Toast.LENGTH_SHORT).show();
//            }
//        });
        //TODO:DateTimePicker
        GregorianCalendar calendar = new GregorianCalendar();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                custom_date.setText(year + "-" + (month+1) + "-" + dayOfMonth);
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        btn_customSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder AlertDialog = new AlertDialog.Builder(CustomizeMenuActivity.this);
                AlertDialog.setTitle("系統訊息");
                AlertDialog.setMessage("輸入成功,已依照您的條件進行客製化菜單");
                AlertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        date = custom_date.getText().toString();
                        String[] timeArray = getResources().getStringArray(R.array.time);
                        int indexOfTimeArray = spinner.getSelectedItemPosition();
                        String time =  timeArray[indexOfTimeArray];
                        String[] categoryArray = getResources().getStringArray(R.array.type);
                        int indexOfCategoryArray = categorySpinner.getSelectedItemPosition();
                        String category =  categoryArray[indexOfCategoryArray];
                        double calorieMin = Double.valueOf(calorieMinHolder.getText().toString());
                        double calorieMax = Double.valueOf(calorieMaxHolder.getText().toString());
                        Intent intent;
                        intent = new Intent(CustomizeMenuActivity.this, ChooseCustomMenuActivity.class);
                        intent.putExtra("category",category);
                        intent.putExtra("date",date);
                        intent.putExtra("time",time);
                        intent.putExtra("minCalorie",calorieMin);
                        intent.putExtra("maxCalorie",calorieMax);
                        startActivity(intent);
                    }
                });
                AlertDialog.setCancelable(false);
                AlertDialog.show();
            }
        });

    }
    //TODO:DateTimePicker onclick
    public void setDate(View v){
        datePickerDialog.show();
    }
}
