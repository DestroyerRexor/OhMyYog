package com.example.evidenciafinalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DeliveryInformationActivity extends AppCompatActivity {

    // Validar el horario según el día de la semana
    int minHour, minMinute, maxHour, maxMinute;

    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItem;

    private EditText datePickerEditText;
    private EditText hourPickerEditText;
    private int dayOfWeek;

    private List<CartViewActivity.CartItem> cartItems = ShoppingCartSingleton.getInstance().getArray();

    String[] item = {"Av. Fundadores (Del Paseo)","Plaza OMNIA (El Uro)","Plaza Fórum Leones (Cumbres)"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_information);

        datePickerEditText = findViewById(R.id.date_picker);
        hourPickerEditText = findViewById(R.id.hour_picker);


        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        adapterItem = new ArrayAdapter<String>(this, R.layout.list_item, item);

        autoCompleteTextView.setAdapter(adapterItem);
        autoCompleteTextView.setText(item[0].toString(), false);

        Button endDeliveryButton = findViewById(R.id.endDeliveryButton);

        endDeliveryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeliveryInformation deliveryInformation = getDeliveryInformation();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();
                DatabaseReference ordersRef = myRef.child("orders").push();

                Order order = new Order(deliveryInformation, cartItems);

                ordersRef.setValue(order);

                ShoppingCartSingleton.getInstance().setEmptyCart();

                Intent resumeDelivery = new Intent(getApplicationContext(), ResumeDeliveryActivity.class);
                startActivity(resumeDelivery);

            }
        });

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
                dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
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

                Calendar tempDate = Calendar.getInstance();
                tempDate.set(Calendar.HOUR_OF_DAY,hourOfDay);
                tempDate.set(Calendar.MINUTE,minute);

                minHour = dayOfWeek >= Calendar.MONDAY && dayOfWeek <= Calendar.FRIDAY ? 13 : 14;
                minMinute = 30;
                maxHour = 21;
                maxMinute = 0;

                //Hora minima
                Calendar dateTimeMin=Calendar.getInstance();
                dateTimeMin.set(Calendar.HOUR_OF_DAY,minHour);
                dateTimeMin.set(Calendar.MINUTE,minMinute);

                //Hora maxima
                Calendar dateTimeMax=Calendar.getInstance();
                dateTimeMax.set(Calendar.HOUR_OF_DAY,maxHour);
                dateTimeMax.set(Calendar.MINUTE,maxMinute);

                //*Valida si la hora seleccionada es permitida.
                if(tempDate.after(dateTimeMin) && tempDate.before(dateTimeMax)){
                    Calendar datetime=Calendar.getInstance();
                    datetime.set(Calendar.HOUR_OF_DAY,hourOfDay);
                    datetime.set(Calendar.MINUTE,minute);
                    hourPickerEditText.setText(selectedTime);
                } else {
                    Toast.makeText(getApplicationContext(), "Hora no permitida! ", Toast.LENGTH_SHORT).show();
                }
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

    private DeliveryInformation getDeliveryInformation() {
        TextInputLayout iNameClient = findViewById(R.id.iNameClient);
        TextInputEditText nameClientText = (TextInputEditText) iNameClient.getEditText();
        String nameClient = nameClientText.getText().toString();

        TextInputLayout iPhoneClient = findViewById(R.id.iPhoneClient);
        TextInputEditText phoneClientText = (TextInputEditText) iPhoneClient.getEditText();
        String phone = phoneClientText.getText().toString();

        TextInputLayout iEmailClient = findViewById(R.id.iEmailClient);
        TextInputEditText emailClientText = (TextInputEditText) iEmailClient.getEditText();
        String email = emailClientText.getText().toString();

        String deliveryDate = String.valueOf(datePickerEditText.getText());

        String deliveryBranch = String.valueOf(autoCompleteTextView.getText());

        String deliveryHour = String.valueOf(hourPickerEditText.getText());

        DeliveryInformation deliveryInformation = new DeliveryInformation(
            nameClient,
            phone,
            email,
            deliveryDate,
            deliveryHour,
            deliveryBranch
        );

        return deliveryInformation;
    }
}
























