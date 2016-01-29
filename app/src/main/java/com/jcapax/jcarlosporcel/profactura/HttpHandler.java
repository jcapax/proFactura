package com.jcapax.jcarlosporcel.profactura;

/**
 * Created by jcarlos.porcel on 04/12/2015.
 *
 *
 */

import android.os.StrictMode;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;


public class HttpHandler {
    String _global = "http://www.sids.com.bo/sureguila/sfv/";

    public String getInformacionBasica(String _service, String _text) {

        String _res;

        try{

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            String _url = _global + _service;

            HttpClient httpClient = (HttpClient) new DefaultHttpClient();

            HttpPost httppost = new HttpPost(_url);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("text", _text));

            httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

            HttpResponse resp = httpClient.execute(httppost);

            HttpEntity ent = resp.getEntity();

            _res = EntityUtils.toString(ent);

            return _res;

        }
        catch (Exception e){
            return "error";
        }

    }



    public String registarVenta(String _service, String imei, String json) {

        String _res;

        try{

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            String _url = _global + _service;

            HttpClient httpClient = (HttpClient) new DefaultHttpClient();

            HttpPost httppost = new HttpPost(_url);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("imei", imei));
            params.add(new BasicNameValuePair("json", json));

            httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

            HttpResponse resp = httpClient.execute(httppost);

            HttpEntity ent = resp.getEntity();

            _res = EntityUtils.toString(ent);

            return _res;

        }
        catch (Exception e){
            return "error";
        }
    }

    public String registarFactura(String _service, String idVenta, String nit, String razonSocial) {

        String _res;

        try{

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            String _url = _global + _service;

            HttpClient httpClient = (HttpClient) new DefaultHttpClient();

            HttpPost httppost = new HttpPost(_url);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("idVenta", idVenta));
            params.add(new BasicNameValuePair("nit", nit));
            params.add(new BasicNameValuePair("razonSocial", razonSocial));

            httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

            HttpResponse resp = httpClient.execute(httppost);

            HttpEntity ent = resp.getEntity();

            _res = EntityUtils.toString(ent);

            return _res;

        }
        catch (Exception e){
            return "error";
        }

    }

    public String respuestaRazonSocial(String _service, String nit) {

        String _res;

        try{

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            String _url = _global + _service;

            HttpClient httpClient = (HttpClient) new DefaultHttpClient();

            HttpPost httppost = new HttpPost(_url);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("nit", nit));

            httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

            HttpResponse resp = httpClient.execute(httppost);

            HttpEntity ent = resp.getEntity();

            _res = EntityUtils.toString(ent);

            return _res;

        }
        catch (Exception e){
            return "error";
        }

    }


    public String obtenerFactura(String _service, String idVenta) {

        String _res;

        try{

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            String _url = _global + _service;

            HttpClient httpClient = (HttpClient) new DefaultHttpClient();

            HttpPost httppost = new HttpPost(_url);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("idVenta", idVenta));


            httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

            HttpResponse resp = httpClient.execute(httppost);

            HttpEntity ent = resp.getEntity();

            _res = EntityUtils.toString(ent);

            return _res;

        }
        catch (Exception e){
            return "error";
        }

    }

    public String obtenerDireccionMac(String _service, String imei) {

        String _res;

        try{

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            String _url = _global + _service;

            HttpClient httpClient = (HttpClient) new DefaultHttpClient();

            HttpPost httppost = new HttpPost(_url);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("idVenta", imei));


            httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

            HttpResponse resp = httpClient.execute(httppost);

            HttpEntity ent = resp.getEntity();

            _res = EntityUtils.toString(ent);

            return _res;

        }
        catch (Exception e){
            return "error";
        }

    }

    public String autenticar(String _service, String patron, String imei) {

        String _res;

        try{

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            String _url = _global + _service;

            HttpClient httpClient = (HttpClient) new DefaultHttpClient();

            HttpPost httppost = new HttpPost(_url);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("imei", imei));
            params.add(new BasicNameValuePair("patron", patron));

            httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

            HttpResponse resp = httpClient.execute(httppost);

            HttpEntity ent = resp.getEntity();

            _res = EntityUtils.toString(ent);

            return _res;

        }
        catch (Exception e){
            return "error";
        }

    }

}
