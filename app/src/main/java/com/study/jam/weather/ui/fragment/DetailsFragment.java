package com.study.jam.weather.ui.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.study.jam.weather.R;
import com.study.jam.weather.helper.DbHelper;
import com.study.jam.weather.model.Weather;

/**
 * A simple {@link Fragment} subclass.
 * @author cikeron 2016
 */
public class DetailsFragment extends DialogFragment {

    /**
     * The Marker Argument
     */
    private static final String ARG_PARAM1 = "data";
    private static Weather myweather;

    /**
     * The data to show in the DialogFragment
     */
    private String mData;

    /**
     * The details textview
     */
    private TextView mDetailsTextView;
    private ImageView mDetailsImage;
    private ImageButton btnGuardar;

    private DbHelper db; //Para manejar el uso de la BD Sqlite local.

    public static DetailsFragment newInstance(String data,Weather w) {
        myweather=w;
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, data);
        fragment.setArguments(args);
        return fragment;
    }

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mData = getArguments().getString(ARG_PARAM1);
        }
        //Inicializamos la BD por si queremos guardar la informacion.
        db=new DbHelper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        mDetailsTextView = (TextView) view.findViewById(R.id.fragment_details_title);
        mDetailsImage=(ImageView) view.findViewById(R.id.fragment_detail_image);
        btnGuardar=(ImageButton) view.findViewById(R.id.imageButton);

        String winfo=myweather.getTitle()+
                " -Max:"+myweather.getMaxtemp()+
                "/ Min: "+myweather.getMintemp()+
                " Hum.:"+myweather.getHumedad()+"%"+
                " - Previsión:"+myweather.getDescripcion();

        mDetailsTextView.setText(winfo);
        switch (myweather.getEstado()){
            case"Clear":
                mDetailsImage.setImageResource(R.drawable.weezle_sun);
                break;
            case"Rain":
                mDetailsImage.setImageResource(R.drawable.weezle_sun_and_rain);
                break;
            case"Clouds":
                mDetailsImage.setImageResource(R.drawable.weezle_cloud);
                break;
            case"Snow":
                mDetailsImage.setImageResource(R.drawable.weezle_snow);
                break;
            case"Drizzle":
                mDetailsImage.setImageResource(R.drawable.weezle_sun_and_rain);
                break;
            case"Thunderstorm":
                mDetailsImage.setImageResource(R.drawable.weezle_cloud_thunder_rain);
                break;
            case"Extreme":
                mDetailsImage.setImageResource(R.drawable.weezle_cloud_thunder_rain);
            default:
                mDetailsImage.setImageResource(R.drawable.pingu);
                break;
        }

        mDetailsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mDetailsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String title=myweather.getTitle();
                final String maxtemp=myweather.getMaxtemp();
                final String mintemp=myweather.getMintemp();
                final String humedad=myweather.getHumedad();
                final String estado=myweather.getEstado();
                final String descripcion=myweather.getDescripcion();
                // SqLite database handler
                Weather w=new Weather(
                        "",
                        title,
                        estado,
                        descripcion,
                        maxtemp,mintemp,humedad);
                db.addWeather(w);
                db.close();
                Toast.makeText(
                        getActivity(),
                        "Guardando previsión...",
                        Toast.LENGTH_LONG).show();
                //dismiss();
            }
        });
        return view;
    }

}
