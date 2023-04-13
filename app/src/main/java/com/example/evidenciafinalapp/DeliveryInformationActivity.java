package com.example.evidenciafinalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Locale;

public class DeliveryInformationActivity extends AppCompatActivity {

    // Validar el horario según el día de la semana
    int minHour, minMinute, maxHour, maxMinute;

    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItem;

    String[] item = {"Av. Fundadores (Del Paseo)","Plaza OMNIA (El Uro)","Plaza Fórum Leones (Cumbres)"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_information);

        EditText datePickerEditText = findViewById(R.id.date_picker);
        EditText hourPickerEditText = findViewById(R.id.hour_picker);


        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        adapterItem = new ArrayAdapter<String>(this, R.layout.list_item, item);

        autoCompleteTextView.setAdapter(adapterItem);
        autoCompleteTextView.setText(item[0].toString(), false);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
            }
        });


        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                datePickerEditText.setText(selectedDate);

                // Obtener el día de la semana seleccionado
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

                if (dayOfWeek >= Calendar.MONDAY && dayOfWeek <= Calendar.FRIDAY) {
                    // Lunes a viernes: de 13:30 a 21:00
                    minHour = 13;
                    minMinute = 30;
                    maxHour = 21;
                    maxMinute = 0;
                } else {
                    // Sábado o domingo: de 14:30 a 21:00
                    minHour = 14;
                    minMinute = 30;
                    maxHour = 21;
                    maxMinute = 0;
                }
            }
        };

        datePickerEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(DeliveryInformationActivity.this, dateSetListener, year, month, day);
                datePickerDialog.show();
            }
        });

        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                hourPickerEditText.setText(selectedTime);
            }
        };
        hourPickerEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(DeliveryInformationActivity.this, timeSetListener, hour, minute, false);

                // TODO: Agregar validación de horas
                // Establece la hora mínima como 13:30
                // timePickerDialog.show();

                // Crear el TimePickerDialog con el rango de horas correspondiente
                // TimePickerDialog timePickerDialog = new TimePickerDialog(DeliveryInformationActivity.this, timeSetListener, hour, minute, false);
                // timePickerDialog.setRange(minHour, minMinute, maxHour, maxMinute);

                //TimePickerDialog timePickerDialog = new TimePickerDialog(DeliveryInformationActivity.this, timeSetListener, hour, minute, false);
                // timePickerDialog.setOnTimeChangedListener(timeChangedListener);

                timePickerDialog.show();
            }
        });

    }
}