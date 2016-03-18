package com.study.jam.weather.model;

/**
 * @author cikeron 2016
 */
public class Weather {

    private String id;//PAra la BD SQLite
    private String title;
    private String maxtemp;
    private String mintemp;
    private String humedad;
    private String estado;
    private String descripcion;

    public Weather() {

    }

    public Weather(String id, String title,String estado, String descripcion,String maxtemp,String mintemp,String humedad) {
        this.id=id;
        this.title = title;
        this.estado=estado;
        this.maxtemp=maxtemp;
        this.mintemp=mintemp;
        this.humedad=humedad;
        this.descripcion=descripcion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Weather settTitle(String title) {
        this.title=title;
        return this;
    }

    public String getMaxtemp() {
        return maxtemp;
    }

    public void setMaxtemp(String maxtemp) {
        this.maxtemp = maxtemp;
    }

    public String getMintemp() {
        return mintemp;
    }

    public void setMintemp(String mintemp) {
        this.mintemp = mintemp;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getHumedad() {
        return humedad;
    }
    public void setHumedad(String humedad) {
        this.humedad = humedad;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
