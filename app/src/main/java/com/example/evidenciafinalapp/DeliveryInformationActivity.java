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
import android.widget.ImageView;
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

    // Información de contacto

    private TextInputLayout iNameClient;
    private TextInputEditText nameClientText;

    private TextInputLayout iPhoneClient;
    private TextInputEditText phoneClientText;

    private TextInputLayout iEmailClient;
    private TextInputEditText emailClientText;

    // Información de entrega

    private EditText datePickerEditText;
    private int dayOfWeek;

    private EditText hourPickerEditText;
    // Validar el horario según el día de la semana
    int minHour, minMinute, maxHour, maxMinute;

    // Sucursal
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItem;
    private List<CartViewActivity.CartItem> cartItems = ShoppingCartSingleton.getInstance().getArray();
    String[] item = {"Av. Fundadores (Del Paseo)","Plaza OMNIA (El Uro)","Plaza Fórum Leones (Cumbres)"};

    private ImageView cartImageView;
    private ImageView returnArrowImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_information);

        cartImageView = findViewById(R.id.cartImageView);
        cartImageView.setImageAlpha(0);

        returnArrowImageView = findViewById(R.id.returnArrowImageView);


        iNameClient = findViewById(R.id.iNameClient);
        nameClientText = (TextInputEditText) iNameClient.getEditText();

        iPhoneClient = findViewById(R.id.iPhoneClient);
        phoneClientText = (TextInputEditText) iPhoneClient.getEditText();

        iEmailClient = findViewById(R.id.iEmailClient);
        emailClientText = (TextInputEditText) iEmailClient.getEditText();

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

                if (validateInputData()) {
                    DeliveryInformation deliveryInformation = getDeliveryInformation();

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference();
                    DatabaseReference ordersRef = myRef.child("orders").push();

                    Order order = new Order(deliveryInformation, cartItems);

                    ordersRef.setValue(order);

                    ShoppingCartSingleton.getInstance().setEmptyCart();

                    Intent resumeDelivery = new Intent(getApplicationContext(), ResumeDeliveryActivity.class);
                    startActivity(resumeDelivery);
                } else {
                    Toast.makeText(getApplicationContext(), "Se deben rellenar todos los campos antes de enviar el formulario", Toast.LENGTH_SHORT).show();
                }
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
                    Toast.makeText(getApplicationContext(), "Hora no permitida", Toast.LENGTH_SHORT).show();
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
                timePickerDialog.show();
            }
        });

    }

    private DeliveryInformation getDeliveryInformation() {

        String nameClient = nameClientText.getText().toString();

        String phone = phoneClientText.getText().toString();

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

    private boolean validateInputData() {
        boolean isValid = true;

        if(nameClientText.getText().toString().trim().isEmpty()) {
            nameClientText.setError("El campo del nombre debe ser completado");
            isValid = false;
        }

        if(emailClientText.getText().toString().trim().isEmpty()) {
            emailClientText.setError("El campo del correo debe ser completado");
            isValid = false;
        }

        if(phoneClientText.getText().toString().trim().isEmpty()) {
            phoneClientText.setError("El campo del celular debe ser completado");
            isValid = false;
        }

        if(datePickerEditText.getText().toString().isEmpty()) {
            datePickerEditText.setError("Se debe seleccionar una fecha de recolección");
            isValid = false;
        }

        if(hourPickerEditText.getText().toString().isEmpty()) {
            hourPickerEditText.setError("Se debe seleccionar un horario de recolección");
            isValid = false;
        }

        if(autoCompleteTextView.getText().toString().isEmpty()) {
            autoCompleteTextView.setError("Se debe seleccionar una sucursal");
            isValid = false;
        }

        return isValid;
    }
}
























