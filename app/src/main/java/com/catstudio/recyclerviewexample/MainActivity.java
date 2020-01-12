package com.catstudio.recyclerviewexample;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonReader;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okio.Buffer;


// https://opendata.epa.gov.tw/ws/Data/UV/?$format=json
public class MainActivity extends AppCompatActivity {

    //變數宣告
    private final String cert = "-----BEGIN CERTIFICATE-----\n" +
            "MIIFQzCCBCugAwIBAgIRALxxs3aEwvIH6GL2I9QlZAUwDQYJKoZIhvcNAQELBQAwRDELMAkGA1UE\n" +
            "BhMCVFcxEjAQBgNVBAoMCeihjOaUv+mZojEhMB8GA1UECwwY5pS/5bqc5oaR6K2J566h55CG5Lit\n" +
            "5b+DMB4XDTE3MDcyNDA4MTMyNVoXDTIwMDcyNDA4MTMyNVowdDELMAkGA1UEBhMCVFcxEjAQBgNV\n" +
            "BAoMCeihjOaUv+mZojEYMBYGA1UECwwP55Kw5aKD5L+d6K23572yMRwwGgYDVQQDExNvcGVuZGF0\n" +
            "YS5lcGEuZ292LnR3MRkwFwYDVQQFExAwMDAwMDAwMDEwMDM0MTg4MIIBIjANBgkqhkiG9w0BAQEF\n" +
            "AAOCAQ8AMIIBCgKCAQEAoKWi4Sgrv8eMYyAfaP3BF63WvUDkwMsMBBZSIu3Ge2tPsRx1yWO2JbxO\n" +
            "F2YfyJq45MBD2nTOXFq7pgMMHsU5PFqzjoXDiqx7POshI/Zwu6vQMDJJExuw55LV4nFjXHYYtupw\n" +
            "r7FsWHNq7sNOGZQzGDw85ooK1oXypC8TRQJ5NlYkwFyMVpvdfZDnCbgQNf3vw6ORikB4aF37LUSR\n" +
            "+inRXHJ6uOWqyp72QZfyR7Zk0/Tb1qById4aFDeD3aN6nfga6wzDO9RBCa7GksZWY1qjVBeJqN1S\n" +
            "dzwwAD2EDZf88P590p4oletIE5FJtCCQTbjT9FDONe/ekLvrdhIMPzSOaQIDAQABo4IB/jCCAfow\n" +
            "HwYDVR0jBBgwFoAU0Rhnw1f+EpqRa19fMeo+woSH+70wHQYDVR0OBBYEFCs/BwiQdYrDKwT3jFHj\n" +
            "qjPuCoZjMIGYBggrBgEFBQcBAQSBizCBiDBFBggrBgEFBQcwAoY5aHR0cDovL2djYS5uYXQuZ292\n" +
            "LnR3L3JlcG9zaXRvcnkvQ2VydHMvSXNzdWVkVG9UaGlzQ0EucDdiMD8GCCsGAQUFBzABhjNodHRw\n" +
            "Oi8vZ2NhLm5hdC5nb3YudHcvY2dpLWJpbi9PQ1NQMi9vY3NwX3NlcnZlci5leGUwDgYDVR0PAQH/\n" +
            "BAQDAgWgMB4GA1UdIAQXMBUwCQYHYIZ2ZQADAzAIBgZngQwBAgIwHgYDVR0RBBcwFYITb3BlbmRh\n" +
            "dGEuZXBhLmdvdi50dzAgBgNVHQkEGTAXMBUGB2CGdgFkAgExCgYIYIZ2AWQDAwEwgYgGA1UdHwSB\n" +
            "gDB+MD2gO6A5hjdodHRwOi8vZ2NhLm5hdC5nb3YudHcvcmVwb3NpdG9yeS9HQ0E0L0NSTDIvQ1JM\n" +
            "XzAwMDIuY3JsMD2gO6A5hjdodHRwOi8vZ2NhLm5hdC5nb3YudHcvcmVwb3NpdG9yeS9HQ0E0L0NS\n" +
            "TDIvY29tcGxldGUuY3JsMCAGA1UdJQEB/wQWMBQGCCsGAQUFBwMBBggrBgEFBQcDAjANBgkqhkiG\n" +
            "9w0BAQsFAAOCAQEAUUouHC1BaeHexR2lQ/lkshctinFwTZGtpPDZv2Z7E647qQWia0y1U2mVUXmm\n" +
            "tUZkFphPBOmRuzEnJ3Cmxel8YADRzj1lfajW92LFfgSXDlIJTKV73+gdzMDvrFCJaSqJ30QhFDza\n" +
            "aVSaps9XMWxEbPv0Wn7wLDYpi0wGxJtjB/W9tbnH1x+b6PAOzoL6yakYF/4vDn+npudW/oo8IaDu\n" +
            "QGHAG5i8E5V1YFRuJRxpUMfNuyXTKgqucoiBLk6dJlJ+AbvmS3046+Hvxwmw4ciHPc9yr9GsgOGW\n" +
            "5jLSmcYHzv5ydxuyWQSCXJ9obuWwwPWABrMiEuphrte2IHsiG44cwA==\n" +
            "-----END CERTIFICATE-----";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<UviInfo> data;
    private OkHttpClient mOkHttpClient = null;
    private final String TAG = "MainActivity";
    private AlertDialog.Builder builder;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //取得資料
        data = getData();

        recyclerView = findViewById(R.id.list);
        layoutManager = new LinearLayoutManager(this);
        mAdapter = new ItemAdapter(data);

        //recyclerView 設定 adapter & layoutManager
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(layoutManager);

        //https 驗證會有問題，這裡因為主要是說明recyclerView，想更深入了解的話請google或參考setCertificates()這個函式
        mOkHttpClient = setCertificates(new Buffer().writeUtf8(cert).inputStream());

        builder = new AlertDialog.Builder(this);

        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                if(recyclerView.findChildViewUnder(e.getX(), e.getY())!=null){
                    int pos = recyclerView.getChildAdapterPosition(recyclerView.findChildViewUnder(e.getX(), e.getY()));
                    UviInfo info = data.get(pos);
                    Log.d(TAG, String.valueOf(pos));
                    builder.setTitle("詳細資訊")
                            .setMessage("縣市："+info.getCounty()+
                                    "\n站名："+info.getSiteName()+
                                    "\nWGS84Lat："+info.getWgs84lat()+
                                    "\nWGS84Lon："+ info.getWgs84lon()+
                                    "\n發布單位："+info.getPublishAgency()+
                                    "\n發布時間："+info.getPublishTime())
                            .setPositiveButton("確認", null)
                            .create().show();
                    return true;
                }
                return false;
            }
        });
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
                if(gestureDetector.onTouchEvent(motionEvent))
                    return true;
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }
        });
    }

    private ArrayList<UviInfo> getData(){
        FutureTask<ArrayList<UviInfo>> task = new FutureTask<>(new Callable<ArrayList<UviInfo>>() {
            @Override
            public ArrayList<UviInfo> call() throws Exception {
                Request request = new Request.Builder().url("https://opendata.epa.gov.tw/ws/Data/UV/?$format=json")
                        .get()
                        .build();
                JsonArray jarr = new JsonParser().parse(mOkHttpClient.newCall(request).execute().body().string()).getAsJsonArray();
                ArrayList<UviInfo> data = new ArrayList<>();
                for(JsonElement elem : jarr){
                    data.add(new UviInfo(elem.getAsJsonObject()));
                }
                return data;
            }
        });
        new Thread(task).start();
        try {
            return task.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
            return new ArrayList<>();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public OkHttpClient setCertificates(InputStream... certificates)
    {
        try
        {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates)
            {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));

                try
                {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e)
                {
                }
            }

            SSLContext sslContext = SSLContext.getInstance("TLS");

            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            X509TrustManager trustManager = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            };

            trustManagerFactory.init(keyStore);
            sslContext.init
                    (
                            null,
                            trustManagerFactory.getTrustManagers(),
                            new SecureRandom()
                    );
            return new OkHttpClient.Builder().sslSocketFactory(sslContext.getSocketFactory(), trustManager)
                    .build();


        } catch (Exception e)
        {
            e.printStackTrace();
            return new OkHttpClient();
        }

    }
}
