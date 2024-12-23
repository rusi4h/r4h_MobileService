package com.example.r4h_mobileservice;

import java.sql.*;
import android.os.Bundle;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
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
    private Statement statement = null;

    private Button b_add;
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
        b_add = findViewById(R.id.b_Add);


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

        String host = "localhost";
        String user = "u2923335_Giyasid";
        String password = "DYytVA3Y2!-~eX'";
        String database = "u2923335_Giyasidinov_Rustam";
        int port = 3306;

        Connection connection = connectToMySQL(host, user, password, database, port);

        if (connection != null) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("INSERT INTO amen(Title, Cost) values ('test', 300)");
            } catch (SQLException e) {
                System.out.println("Ошибка при выполнении запроса: " + e.getMessage());
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Ошибка при закрытии подключения: " + e.getMessage());
                }
            }
        }

        b_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public static Connection connectToMySQL(String host, String user, String password, String database, int port) {
        String url = String.format("jdbc:mysql://%s:%d/%s", host, port, database);
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Подключение к MySQL успешно!");
            return connection;
        } catch (ClassNotFoundException e) {
            System.out.println("Не найден JDBC драйвер: " + e.getMessage());
            return null;
        } catch (SQLException e) {
            System.out.println("Ошибка при подключении к MySQL: " + e.getMessage());
            return null;
        }
    }


}

