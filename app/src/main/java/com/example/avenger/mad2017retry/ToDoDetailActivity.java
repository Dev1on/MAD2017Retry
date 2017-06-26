package com.example.avenger.mad2017retry;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.avenger.mad2017retry.database.DBApplication;
import com.example.avenger.mad2017retry.model.Todo;
import com.example.avenger.mad2017retry.presenter.ToDoDetailPresenter;
import com.example.avenger.mad2017retry.view.ToDoDetailView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class ToDoDetailActivity extends AppCompatActivity implements ToDoDetailView {

    public static final String HH_MM = "HH:mm";
    public static final String DD_MM_YYYY = "dd.MM.yyyy";


    private ToDoDetailPresenter presenter;

    private TextView idText;
    private EditText nameText;
    private EditText descriptionText;
    private CheckBox favouriteBox;
    private CheckBox doneBox;
    private EditText contactText;
    private EditText locationText;
    private EditText dateText;
    private EditText timeText;

    private ProgressDialog progressDialog;
    private Toolbar toolbar;
    private AlertDialog.Builder alertDialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_detail);

        presenter = new ToDoDetailPresenter(this, ((DBApplication)getApplication()));
        //get all ui elements
        idText = (TextView) findViewById(R.id.idText);
        nameText = (EditText) findViewById(R.id.nameTextDetail);
        descriptionText = (EditText) findViewById(R.id.descriptionTextDetail);
        favouriteBox = (CheckBox) findViewById(R.id.favouriteBox);
        doneBox = (CheckBox) findViewById(R.id.doneBox);
        contactText = (EditText) findViewById(R.id.contactText);
        locationText = (EditText) findViewById(R.id.locationText);
        dateText = (EditText) findViewById(R.id.dateText);
        timeText = (EditText) findViewById(R.id.timeText);

        progressDialog = new ProgressDialog(this);
        toolbar = (Toolbar) findViewById(R.id.detail_toolbar);

        setSupportActionBar(toolbar);
        setCalender();
        setConfirmAlert();

        boolean createItem = (boolean) getIntent().getSerializableExtra("createItem");

        if (createItem)
            initializeEmpty();
        else
            initializeScreenWithTodo();
    }

    @Override
    public void saveItem() {
        boolean createItem = (boolean) getIntent().getSerializableExtra("createItem");

        if (!createItem) {
            progressDialog.show();
            presenter.saveItem();
        } else {
            presenter.createItem();
        }

        progressDialog.dismiss();
        Toast.makeText(this, "Todo saved", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteItem() {
        presenter.deleteTodo((long) getIntent().getSerializableExtra("id"));

        Toast.makeText(this, "Todo deleted", Toast.LENGTH_SHORT).show();

        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void setTodoView(Todo todo) {
        idText.setText("" + todo.getId());
        nameText.setText(todo.getName());
        descriptionText.setText(todo.getDescription());

        DateFormat fullDateFormatter = new SimpleDateFormat(DD_MM_YYYY);
        DateFormat timeFormatter = new SimpleDateFormat(HH_MM);
        long dbTime = todo.getExpiry();
        String showDate = fullDateFormatter.format(dbTime);
        String showTime = timeFormatter.format(dbTime);
        dateText.setText(showDate);
        timeText.setText(showTime);

        favouriteBox.setChecked(todo.isFavourite());
        doneBox.setChecked(todo.isDone());
        //contactText.setText(todo.getContacts());
        locationText.setText(todo.getLocation().getName());

        progressDialog.dismiss();
    }


    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu_resource, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            alertDialogBuilder.show();
            return true;
        } else if(item.getItemId() == R.id.action_save) {
            saveItem();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public Todo getCurrentTodo() {
        long id = Long.parseLong(idText.getText().toString());
        String name = nameText.getText().toString();
        String description = descriptionText.getText().toString();
        boolean favourite = favouriteBox.isChecked();
        boolean done = doneBox.isChecked();
        String location_name = locationText.getText().toString();

        //date
        String dateString = dateText.getText().toString();
        String timeString = timeText.getText().toString();
        DateFormat formatter = new SimpleDateFormat(DD_MM_YYYY + ":" + HH_MM);
        Date date = null;
        try {
            date = formatter.parse(dateString + ":" + timeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long dateAsLong = date.getTime();

        //TODO location
        Todo.Location location = new Todo.Location();
        Todo.LatLng latlng = new Todo.LatLng();
        latlng.setLat(12);
        latlng.setLng(13);
        location.setLatlng(latlng);
        location.setName(location_name);
        //TODO save contacts

        Todo returnTodo = new Todo(name, description);
        returnTodo.setId(id);
        returnTodo.setFavourite(favourite);
        returnTodo.setDone(done);
        returnTodo.setLocation(location);
        returnTodo.setExpiry(dateAsLong);

        return returnTodo;
    }

    @Override
    public void displayTodoNotFound() {
        Toast.makeText(this, "Sorry, Todo not found", Toast.LENGTH_SHORT).show();

        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private void initializeEmpty() {
        Todo todo = new Todo("","");
        setTodoView(todo);
    }

    private void initializeScreenWithTodo() {
        progressDialog.show();
        long itemId = (long) getIntent().getSerializableExtra("id");

        presenter.readToDo(itemId);
        progressDialog.hide();
    }

    private void setCalender() {
        Calendar cal = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, monthOfYear);
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDateLabel(cal);
        };
        dateText.setOnClickListener(v -> new DatePickerDialog(ToDoDetailActivity.this, date, cal
                .get(Calendar.YEAR), cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show());

        TimePickerDialog.OnTimeSetListener time = (view, hourOfDay, minute) -> {
            cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
            cal.set(Calendar.MINUTE, minute);
            updateTimeLabel(cal);
        };
        timeText.setOnClickListener(v -> new TimePickerDialog(ToDoDetailActivity.this, time,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE), true).show());
    }

    private void updateDateLabel(Calendar cal) {
        SimpleDateFormat sdf = new SimpleDateFormat(DD_MM_YYYY);
        dateText.setText(sdf.format(cal.getTime()));
    }

    private void updateTimeLabel(Calendar cal) {
        SimpleDateFormat sdf = new SimpleDateFormat(HH_MM);
        timeText.setText(sdf.format(cal.getTime()));
    }

    private void setConfirmAlert() {
        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    deleteItem();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //Do nothing
                    break;
            }
        };
        alertDialogBuilder = new AlertDialog.Builder(ToDoDetailActivity.this);
        alertDialogBuilder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener);
    }

}
