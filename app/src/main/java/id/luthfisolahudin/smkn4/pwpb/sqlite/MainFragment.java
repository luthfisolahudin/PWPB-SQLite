package id.luthfisolahudin.smkn4.pwpb.sqlite;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.luthfisolahudin.smkn4.pwpb.sqlite.adapter.RecycleViewAdapter;
import id.luthfisolahudin.smkn4.pwpb.sqlite.helper.DatabaseHelper;
import id.luthfisolahudin.smkn4.pwpb.sqlite.model.Person;

public class MainFragment extends Fragment implements View.OnClickListener, RecycleViewAdapter.OnUserClickListener {

    Context context;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    EditText editName;
    EditText editAge;

    Button buttonSubmit;

    List<Person> personList;

    public MainFragment() {
        // Required empty public constructor
    }

    private void setupRecyclerView() {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);

        personList = databaseHelper.getPersonList();

        RecycleViewAdapter adapter = new RecycleViewAdapter(context, this, personList);

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getActivity();

        editName = view.findViewById(R.id.edit_name);

        context = getActivity();

        recyclerView = view.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        editName = view.findViewById(R.id.edit_name);
        editAge = view.findViewById(R.id.edit_age);

        buttonSubmit = view.findViewById(R.id.button_submit);
        buttonSubmit.setOnClickListener(this);

        setupRecyclerView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_submit:
                DatabaseHelper databaseHelper = new DatabaseHelper(context);
                String buttonSubmitText = buttonSubmit.getText().toString();

                if (buttonSubmitText.equals(getResources().getString(R.string.label_button_submit))) {
                    databaseHelper.insert(
                            new Person()
                                    .setName(editName.getText().toString())
                                    .setAge(Integer.parseInt(editAge.getText().toString()))
                    );
                }

                if (buttonSubmitText.equals(getResources().getString(R.string.label_button_update))) {
                    databaseHelper.update(
                            new Person()
                                    .setName(editName.getText().toString())
                                    .setAge(Integer.parseInt(editAge.getText().toString()))
                    );
                }

                setupRecyclerView();

                editName.setText("");
                editAge.setText("");
                editName.setFocusable(true);

                buttonSubmit.setText(getResources().getString(R.string.label_button_submit));

                break;
            default:
                break;
        }
    }

    @Override
    public void onUserClick(Person currentPerson, String action) {
        if (action.equals(getResources().getString(R.string.action_update))) {
            editName.setText(currentPerson.getName());
            editName.setFocusable(false);

            editAge.setText(String.valueOf(currentPerson.getAge()));

            buttonSubmit.setText(getResources().getString(R.string.label_button_update));

            return;
        }

        if (action.equals(getResources().getString(R.string.action_delete))) {
            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            databaseHelper.delete(currentPerson.getName());
            setupRecyclerView();
        }
    }
}