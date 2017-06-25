package br.edu.ifspsaocarlos.sdm.trabalhofinalchat.services;

/**
 * Created by fabio on 25/06/2017.
 */
public interface ServiceListener {
    public void onSuccess(Object object);

    public void onError(Exception ex);
}
