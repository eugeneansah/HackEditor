package com.hackathon.hackmsit.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bettervectordrawable.VectorDrawableCompat;
import com.hackathon.hackmsit.data.NoteManager;
import com.hackathon.hackmsit.models.Note;
import com.hackathon.hackmsit.utilities.Constants;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikepenz.materialize.util.KeyboardUtil;
import com.hackathon.hackmsit.R;
import com.hackathon.hackmsit.data.DatabaseHelper;
import com.hackathon.hackmsit.fragments.NoteListFragment;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private JSONObject languages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        CoordinatorLayout mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        FloatingActionButton mFab = (FloatingActionButton) findViewById(R.id.fab);
        final Drawable fileDrawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.file_outline);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        try {
            languages = new JSONObject(Constants.languagesString);
        } catch (JSONException e) {
            e.printStackTrace();
            languages = new JSONObject();
        }

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("pref", 0);
        SharedPreferences.Editor editor = prefs.edit();
        String flag = prefs.getString("deleted", "");
        if (flag.equals("1")) {
            Snackbar snackbar = Snackbar
                    .make(mCoordinatorLayout, "Code deleted", Snackbar.LENGTH_SHORT);
            snackbar.show();
            editor.putString("deleted", "0");
            editor.apply();
        }

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = MainActivity.this.getLayoutInflater().inflate(R.layout.alert_dialog, null);
                final EditText fileName = (EditText) view.findViewById(R.id.edit_text_title);
                final TextInputLayout inputLayoutTitle = (TextInputLayout) view.findViewById(R.id.input_layout_title);
                final Button continueButton = (Button) view.findViewById(R.id.continue_button);
                final Button cancelButton = (Button) view.findViewById(R.id.cancel_button);

                final AlertDialog builder = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("New File")
                        .setView(view)
                        .setIcon(fileDrawable)
                        .create();

                builder.show();

                continueButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int flag;
                        String extCode = null;
                        String name = fileName.getText().toString().trim();
                        if (TextUtils.isEmpty(name)) {
                            inputLayoutTitle.setError("Title is required");
                            flag = 1;
                        } else {
                            inputLayoutTitle.setErrorEnabled(false);
                            flag = 0;
                            int i = name.indexOf(".");
                            if (i != -1) {
                                String ext = name.substring(i + 1, name.length());
                                try {
                                    extCode = languages.getString(ext);
                                } catch (JSONException e) {
                                    flag = 1;
                                    inputLayoutTitle.setError("Invalid extension");
                                    e.printStackTrace();
                                }
                                /*switch (ext) {
                                    case "c":
                                        extCode = "1";
                                        break;
                                    case "py":
                                        extCode = "5";
                                        break;
                                    case "cpp":
                                        extCode = "2";
                                        break;
                                    default:
                                        flag = 1;
                                        inputLayoutTitle.setError("Invalid extension");
                                }*/
                            } else {
                                inputLayoutTitle.setError("Input file extension");
                                flag = 1;
                            }
                        }
                        if (flag == 0) {
                            Note note = new Note();
                            note.setTitle(name);
                            note.setExt(extCode);
                            note.setContent("");
                            NoteManager.newInstance(MainActivity.this).create(note);
                            Intent i = new Intent(MainActivity.this, NotePlainEditorActivity.class);
                            i.putExtra("name", name);
                            i.putExtra("ext", extCode);
                            i.putExtra("id", note.getId());
                            builder.dismiss();
                            startActivity(i);
                        }
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder.dismiss();
                    }
                });
            }
        });

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.getWritableDatabase();

        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withActionBarDrawerToggle(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.title_home).withIcon(FontAwesome.Icon.faw_home).withIdentifier(1),
                        //new PrimaryDrawerItem().withName(R.string.title_editor).withIcon(FontAwesome.Icon.faw_edit).withIdentifier(2),
                        new PrimaryDrawerItem().withName(R.string.title_settings).withIcon(FontAwesome.Icon.faw_list).withIdentifier(2)

                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int i, IDrawerItem drawerItem) {
                        if (drawerItem != null && drawerItem instanceof Nameable) {
                            String name = ((Nameable) drawerItem).getName().getText(MainActivity.this);
                            mToolbar.setTitle(name);
                        }

                        if (drawerItem != null) {
                            int selectedScreen = drawerItem.getIdentifier();
                            switch (selectedScreen) {
                                case 1:
                                    //go to List of Notes
                                    openFragment(new NoteListFragment(), "Codes");
                                    break;
                                /*case 2:
                                    //go the editor screen
                                    startActivity(new Intent(MainActivity.this, NoteEditorActivity.class));*/
                                case 2:
                                    //go to settings screen, yet to be added
                                    //this will be your home work
                                    Toast.makeText(MainActivity.this, "Settings Clicked", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                        return false;
                    }


                })
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View view) {
                        KeyboardUtil.hideKeyboard(MainActivity.this);
                    }

                    @Override
                    public void onDrawerClosed(View view) {

                    }

                    @Override
                    public void onDrawerSlide(View view, float v) {

                    }
                })
                .withFireOnInitialOnClick(true)
                .withSavedInstance(savedInstanceState)
                .build();
        if (savedInstanceState == null) {
            result.setSelection(1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openFragment(final Fragment fragment, String title) {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.container, fragment)
                        //.addToBackStack(null)
                .commit();
        getSupportActionBar().setTitle(title);
    }
}
