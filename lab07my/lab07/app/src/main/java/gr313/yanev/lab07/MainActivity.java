package gr313.yanev.lab07;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView quantity;
    EditText key;
    EditText value;
    Database db;

    @SuppressLint("StaticFieldLeak")
    public static Context context;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quantity = findViewById(R.id.textQuantity);
        key = findViewById(R.id.key);
        value = findViewById(R.id.value);
        db = new Database(this, "my_notes.db", null, 1);
        context = this.getApplicationContext();


        quantity.setText("Количество: " + db.getQuantity());
    }


    @SuppressLint("SetTextI18n")
    public void onInsertClick(View v) {
        String keyStr = key.getText().toString();
        String valueStr = value.getText().toString();

        if (keyStr.equals("") || valueStr.equals("")) {
            Toast.makeText(this, "Заполните оба поля!", Toast.LENGTH_LONG).show();
            return;
        }


        db.insert(keyStr, valueStr);
        clearFields();
        quantity.setText("Количество: " + db.getQuantity());
    }

    @SuppressLint("SetTextI18n")
    public void onUpdateClick(View v) {
        String keyStr = key.getText().toString();
        String valueStr = value.getText().toString();

        if (keyStr.equals("") || valueStr.equals("")) {
            Toast.makeText(this, "Заполните оба поля!", Toast.LENGTH_LONG).show();
            return;
        }

        db.update(keyStr, valueStr);
        clearFields();
        quantity.setText("Количество: " + db.getQuantity());
    }

    public void onSelectClick(View v) {
        String keyStr = key.getText().toString();

        if (keyStr.equals("")) {
            Toast.makeText(this, "Заполните поле Key!", Toast.LENGTH_LONG).show();
            clearFields();
            return;
        }

        String valueStr = db.select(keyStr);

        key.setText(keyStr);
        value.setText(valueStr);
    }

    @SuppressLint("SetTextI18n")
    public void onDeleteClick(View v) {
        String keyStr = key.getText().toString();

        if (keyStr.equals("")) {
            Toast.makeText(this, "Заполните поле Key!", Toast.LENGTH_LONG).show();
            clearFields();
            return;
        }

        db.delete(keyStr);
        clearFields();
        quantity.setText("Количество: " + db.getQuantity());
    }


    public void onDropAllClick(View v) {
        // TODO: AlertDialog с подтверждением удаления всех

        if (db.getQuantity() == 0) {
            Toast.makeText(this, "Нет записей для удаления!", Toast.LENGTH_LONG).show();
            return;
        }

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.stat_sys_warning)
                .setTitle("Удалить всё?")
                .setMessage("Записи невозможно будет восстановить!!!")

                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.dropAll();
                        quantity.setText("Количество: " + db.getQuantity());
                    }
                })

                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "Nothing Happened", Toast.LENGTH_LONG).show();
                    }
                })
                .show();
    }


    private void clearFields() {
        key.setText("");
        value.setText("");
    }
}