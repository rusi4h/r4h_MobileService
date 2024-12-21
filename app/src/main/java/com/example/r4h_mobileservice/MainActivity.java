package com.example.r4h_mobileservice;

import android.os.Bundle;
import android.text.Spanned;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.text.InputFilter;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Spinner s_amenities;
    private EditText tb_surname;
    private EditText tb_name;
    private EditText tb_num;
    private EditText tb_device;
    private EditText tb_address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        s_amenities = findViewById(R.id.s_amenities);
        tb_surname = findViewById(R.id.tb_Surname);
        tb_name = findViewById(R.id.tb_Name);
        tb_num = findViewById(R.id.tb_Num);
        tb_device = findViewById(R.id.tb_Device);
        tb_address = findViewById(R.id.tb_Address);

        String[] items = {"Choose amenity", "Replacing the display", "Replacing the speaker", "Battery Replacement", "Cleaning the speaker"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        s_amenities.setAdapter(adapter);

        InputFilter lettersFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String regex = "[a-zA-Zа-яА-ЯёЁ]*";
                if (source.toString().matches(regex)) {
                    return source;
                } else {
                    return "";
                }
            }
        };

        InputFilter numbersFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                StringBuilder sb = new StringBuilder();
                for (int i = start; i < end; i++) {
                    char c = source.charAt(i);
                    if (Character.isDigit(c)) {
                        sb.append(c);
                    }
                }
                return sb.toString();
            }
        };

        InputFilter emailFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                StringBuilder sb = new StringBuilder();
                for (int i = start; i < end; i++) {
                    char c = source.charAt(i);
                    if (Character.isLetterOrDigit(c) || c == '.' || c == '_' || c == '-' || c == '@' ) {
                        sb.append(c);
                    }
                }
                return sb.toString();
            }
        };

        tb_surname.setFilters(new InputFilter[]{lettersFilter});
        tb_name.setFilters(new InputFilter[]{lettersFilter});
        tb_device.setFilters(new InputFilter[]{lettersFilter});
        tb_num.setFilters(new InputFilter[]{numbersFilter});
        tb_address.setFilters(new InputFilter[]{emailFilter});
    }
}