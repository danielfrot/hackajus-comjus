package com.example.root.appmultitec.Activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.root.appmultitec.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoFragment extends Fragment {

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    String foto;

    public PhotoFragment() {
        // Required empty public constructor


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //((AppCompatActivity)getActivity()).getSupportActionBar().hide();


        Window g =((AppCompatActivity)getActivity()).getWindow();
        g.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,WindowManager.LayoutParams.TYPE_STATUS_BAR);





        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        ImageView imageView = view.findViewById(R.id.fotoFragment);
        Picasso.get().load(foto).into(imageView);
        Button btFechar = view.findViewById(R.id.btFecharFragment);

       //getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
       // mActionBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
       // mActionBar.setElevation(0);


        btFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // Intent intent = new Intent(getContext(),DetalhesRmeActivity.class);
               // startActivity(intent);
                //()getActivity().getSupportFragmentManager().popBackStack();

                Toast.makeText(getContext(),"debug",Toast.LENGTH_LONG).show();

            }
        });




        return view;
    }

}
