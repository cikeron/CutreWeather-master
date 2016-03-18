package com.study.jam.weather.ui.adapter;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.study.jam.weather.R;
import com.study.jam.weather.helper.DbHelper;
import com.study.jam.weather.model.Weather;
import com.study.jam.weather.ui.fragment.DetailsFragment;

import java.util.ArrayList;

import static com.study.jam.weather.R.drawable;

public class RecyclerAdapter extends RecyclerView.Adapter implements ItemClickListener {
    private ArrayList<Weather> data;
    private MyViewHolder viewHolder;
    private Context context;

    public RecyclerAdapter(Context context, ArrayList<Weather> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        viewHolder=new MyViewHolder(itemView, this);
/* DE ESTA FORMA NO PUEDO SABER QUE ELEMENTO SE HA CLICADO PARA ENVIARLO COMO OBJETO Y TRATARLO
        viewHolder = new MyViewHolder(itemView, new MyViewHolder.MyViewHolderClicks() {
            @Override
            public void onTextViewIsClicked(View view) { // Escucha los click sobre los TextView de los items de la lista
                TextView textView = (TextView) view;

                // Creamos un fragment y lo mostramos como Dialog
                FragmentManager manager = ((Activity) context).getFragmentManager();
                DialogFragment newFragment =
                        DetailsFragment.newInstance(textView.getText().toString(), "");//data.get(viewHolder.getAdapterPosition()));
                Log.d("Weather pasao: ", String.valueOf(getItemCount()));
                newFragment.show(manager, "Dialog");
            }

            @Override
            public void onImageViewIsClicked(View view) { }
        });
*/
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//Pasamos los datos necearios para dibujar cada elemento de la lista.
/*        viewHolder.bindData(data.get(position).getTitle(),
                data.get(position).getEstado(),
                data.get(position).getMaxtemp(),
                data.get(position).getMintemp());
*/
        //Mejor pasamos un objeto completo donde si necesitamos un cambio lo hacemos en su clase.
        viewHolder.bindData(data.get(position));
    }

    @Override
    public int getItemCount() {

        return data.size();
    }

    @Override
    public void onItemClick(View view, int position) {
        // Creamos un fragment y lo mostramos como Dialog
        FragmentManager manager = ((Activity) context).getFragmentManager();
        DialogFragment newFragment =
                DetailsFragment.newInstance(data.get(position).getTitle(),data.get(position));//data.get(viewHolder.getAdapterPosition()));
        newFragment.show(manager, "Dialog");
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title;
        private ImageView weather_img;
        private ItemClickListener listener;
//        private MyViewHolderClicks listener;

        public MyViewHolder(View itemView, ItemClickListener listener) {
            super(itemView);
            this.listener = listener;
            itemView.setOnClickListener(this);

            title = (TextView) itemView.findViewById(R.id.title_item);
            //title.setOnClickListener(this);
            weather_img = (ImageView) itemView.findViewById(R.id.image_item);
            //weather_img.setOnClickListener(this);
        }

        public void bindData(Weather w) {
//        public void bindData(String titleStr,String estado,String max,String min) {
            String winfo=w.getTitle()+" -Max:"+w.getMaxtemp()+
                    "/ Min: "+w.getMintemp()+
                    " Hum.:"+w.getHumedad()+"%"+
                    " - Previsión:"+w.getDescripcion();
            title.setText(winfo);
            switch (w.getEstado()){
                case"Clear":
                    weather_img.setImageResource(drawable.weezle_sun);
                    break;
                case"Rain":
                    weather_img.setImageResource(drawable.weezle_sun_and_rain);
                    break;
                case"Clouds":
                    weather_img.setImageResource(drawable.weezle_cloud);
                    break;
                case"Snow":
                    weather_img.setImageResource(drawable.weezle_snow);
                    break;
                case"Drizzle":
                    weather_img.setImageResource(drawable.weezle_sun_and_rain);
                    break;
                case"Thunderstorm":
                    weather_img.setImageResource(drawable.weezle_cloud_thunder_rain);
                    break;
                case"Extreme":
                    weather_img.setImageResource(drawable.weezle_cloud_thunder_rain);
                    break;

                default:
                    weather_img.setImageResource(drawable.heartocat);
                    break;
            }

        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getAdapterPosition());
/*PARA USAR EN CASO DE QUERER DIFERENCIAR DONDE SE CLICKEA
            if(v instanceof TextView) {
                listener.onTextViewIsClicked(v);
            } else if (v instanceof ImageView) {
                listener.onImageViewIsClicked(v);
            }
*/
        }

        public interface MyViewHolderClicks {
            /*PARA USAR EN CASO DE QUERER DIFERENCIAR DONDE SE CLICKEA
            void onTextViewIsClicked(View v);
            void onImageViewIsClicked(View v);
            */
        }
    }

}

interface ItemClickListener {
    void onItemClick(View view, int position);
}
