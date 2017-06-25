package br.edu.ifspsaocarlos.sdm.trabalhofinalchat.services;


import android.content.Context;

import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.R;

/**
 * Created by fabio on 25/06/2017.
 */
public abstract class ServiceBase {

    public static final String BASE_URL = "http://www.nobile.pro.br/sdm/mensageiro";

    public String getUrl(String path){
        return BASE_URL + path;
    }
}
