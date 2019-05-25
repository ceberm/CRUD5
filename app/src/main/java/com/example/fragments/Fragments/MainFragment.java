package com.example.fragments.Fragments;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fragments.Adapters.CustomAdapter;
import com.example.fragments.Database.AppDatabase;
import com.example.fragments.FormActivity;
import com.example.fragments.Interfaces.PersonaDAO;
import com.example.fragments.R;
import com.example.fragments.models.Persona;

import java.text.Normalizer;
import java.util.ArrayList;

public class MainFragment extends Fragment {

    private ArrayList<Persona> listaActual = new ArrayList<Persona>();;
    private ArrayAdapter<Persona> personaAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        /*
        Bundle bundle = getArguments();
        if (bundle != null) {
            if(bundle.containsKey("listaActual")){
                listaActual = (ArrayList<Persona>) bundle.getSerializable("listaActual");
            }else {
                if (bundle != null && bundle.containsKey("Persona")) {
                    Persona persona = (Persona) bundle.getSerializable("Persona");
                    listaActual.add(persona);
                }
            }
        }*/
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        setupUI(view);
        return view;
    }

    /***
     *
     * @param view
     */
    public void setupUI(View view){
        //Button btn_newPersonasRandom = (Button) view.findViewById(R.id.btn_newPersonasRandom);
        FloatingActionButton btn_newPersonasForm = (FloatingActionButton) view.findViewById(R.id.btn_newPersonasForm);
        final Context context = getContext();

        final AppDatabase database = Room.databaseBuilder(getContext(), AppDatabase.class, "localDB")
                .allowMainThreadQueries() .build();

        final PersonaDAO personaDAO = database.getItemDAO();

        //Extraer de shared preferences
        //SharedPreferences sharedPref = context.getSharedPreferences("preferences", context.MODE_PRIVATE);
        SharedPreferences sharedPref = context.getSharedPreferences("preferences", context.MODE_PRIVATE);
        int size = sharedPref.getInt("var", 0);

        //int size = personaDAO.getCount();

        CharSequence text = "Cantidad de personas = " + size;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        btn_newPersonasForm.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //List Between Fragments

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                FormFragment formFragment = new FormFragment();
                //Bundle bundle = new Bundle();
                //bundle.putSerializable("listaActual", listaActual);
                //formFragment.setArguments(bundle);
                ft.replace(android.R.id.content, formFragment);
                ft.addToBackStack(null); //Add fragment in back stack
                ft.commit();
            }
        });

        /*
        btn_newPersonasRandom.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //DATABASE

                Persona persona = new Persona();

                persona.setName("Leonardo");
                persona.setLastName("Di Caprio");
                persona.setEmail("LCaprio@hotmail.com");
                persona.setPhoneNumber("98765412");

                personaDAO.insert(persona);

                listaActual = (ArrayList) personaDAO.getPersonas();

                //Guardar en Shared Preferences el tama;o de la lista
                SharedPreferences sharedPref = context.getSharedPreferences("preferences", context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("var", personaDAO.getCount());
                editor.commit(); //guarda los datos en el fichero


            }
        });
*/

        personaAdapter = new CustomAdapter(view.getContext(), R.layout.fragment_show, personaDAO.getPersonas());
        ListView mListView = (ListView) view.findViewById(R.id.mListView);
        mListView.setAdapter(personaAdapter);



    }

}
