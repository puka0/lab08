package gr313.yanev.lab08;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter <Note> adp;
    int sel;
    static Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new Database(this, "notes.db", null, 1);
        adp = new ArrayAdapter<Note>(this, android.R.layout.simple_list_item_1);
        ListView lst = findViewById(R.id.list_notes);
        lst.setAdapter(adp);
        this.update_adp();

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            long lastClickTimeMillis = 0;

            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                sel = position;
                TextView deb = findViewById(R.id.debugSelect);
                deb.setText("Выбрано: " + position);

                long currTime = System.currentTimeMillis();
                if (currTime - lastClickTimeMillis < ViewConfiguration.getDoubleTapTimeout()) {
                    onItemDoubleClick();
                }
                lastClickTimeMillis = currTime;

            }
        });


    }

    // ----------------------------------------------
    private void onItemDoubleClick() {
        if (sel != AdapterView.INVALID_POSITION) {
            Note selectedNote = adp.getItem(sel);
            Intent i = new Intent(this, ThirdActivity.class);
            i.putExtra("note index", selectedNote.id);
            startActivityForResult(i, 333);
        }
    }
    // ----------------------------------------------


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.update_adp();
    }

    public void update_adp(){
        adp.clear();
        adp.addAll(db.getAllNotes());
    }

    public void on_new_click(View v){
        Intent i = new Intent(this, SecondActivity.class);
        startActivityForResult(i, 12345);
    }
    public void on_edit_click(View v){

        if (checkIsZeroNotes("редактирования")) {
            return;
        }

        if (sel != AdapterView.INVALID_POSITION) {
            Note selectedNote = adp.getItem(sel);
            Intent i = new Intent(this, SecondActivity.class);
            if(selectedNote != null){
                i.putExtra("note index", selectedNote.id);
            }
            startActivityForResult(i, 12345);
        }
    }

    public void on_delete_click(View v) {

        if (checkIsZeroNotes("удаления")) {
            return;
        }

        if (sel != AdapterView.INVALID_POSITION) {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Вы уверены в удалении?")
                    .setMessage("Заметку будет невозможно восстановить")

                    .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            db.deleteNote(adp.getItem(sel).id);
                            update_adp();
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
    }


    private boolean checkIsZeroNotes(String action) {
        if (adp.getCount() == 0) {
            Toast.makeText(this, "Нет заметок для " + action, Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

}