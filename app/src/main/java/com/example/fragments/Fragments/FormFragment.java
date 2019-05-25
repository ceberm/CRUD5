package com.example.fragments.Fragments;


import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.fragments.Database.AppDatabase;
import com.example.fragments.FormActivity;
import com.example.fragments.Interfaces.PersonaDAO;
import com.example.fragments.R;
import com.example.fragments.models.Persona;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FormFragment extends Fragment {

    private ArrayList<Persona> listaActual = new ArrayList<Persona>();;

    public FormFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Bundle bundle = getArguments();
        if (bundle != null) {
            if(bundle.containsKey("listaActual")){
                listaActual = (ArrayList<Persona>) bundle.getSerializable("listaActual");
            }
        }

        View view = inflater.inflate(R.layout.fragment_form, container, false);
        setupUI(view);
        return view;
    }

    public void setupUI(final View view){
        Button btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
        final EditText etNombre = (EditText) view.findViewById(R.id.etNombre);
        final EditText etApellido = (EditText) view.findViewById(R.id.etApellido);
        final EditText etCorreo = (EditText) view.findViewById(R.id.etCorreo);
        final EditText etNum = (EditText) view.findViewById(R.id.etNum);
        final AppDatabase database = Room.databaseBuilder(getContext(), AppDatabase.class, "localDB")
                .allowMainThreadQueries() .build();



        btnSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                PersonaDAO personaDAO = database.getItemDAO();

                Persona persona = new Persona(etNombre.getText().toString(),etApellido.getText().toString(),
                        etCorreo.getText().toString(),etNum.getText().toString());

                personaDAO.insert(persona);

                Snackbar mySnackbar = Snackbar.make(view,
                        R.string.persona_archived, Snackbar.LENGTH_SHORT);
                //mySnackbar.setAction(R.string.undo_string, new MyUndoListener());
                mySnackbar.show();

                Context context = getContext();
                SharedPreferences sharedPref = context.getSharedPreferences("preferences", context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("var", personaDAO.getCount());
                editor.commit(); //guarda los datos en el fichero


                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                MainFragment mainFragment = new MainFragment();
                /*Bundle bundle = new Bundle();
                Persona persona = new Persona(etNombre.getText().toString(),etApellido.getText().toString(),
                        etCorreo.getText().toString(),etNum.getText().toString());
                bundle.putSerializable("Persona", persona);
                listaActual.add(persona);
                bundle.putSerializable("listaActual", listaActual);
                mainFragment.setArguments(bundle);
                */
                ft.replace(android.R.id.content, mainFragment);
                ft.addToBackStack(null); //Add fragment in back stack
                ft.commit();


            }
        });
    }

}
