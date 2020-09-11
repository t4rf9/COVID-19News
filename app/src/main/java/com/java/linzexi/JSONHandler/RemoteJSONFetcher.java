package com.java.linzexi.JSONHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RemoteJSONFetcher {
    private URL url;
    private String jsonFileString = null;

    public RemoteJSONFetcher(final URL url) {
        this.url = url;
    }

    public RemoteJSONFetcher(final String url) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace(System.err);
        }
    }

    public String getJSONFileString() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (RemoteJSONFetcher.class) {
                    HttpURLConnection connection = null;
                    BufferedReader bufferedReader = null;
                    try {
                        connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setConnectTimeout(8000);
                        connection.setReadTimeout(8000);
                        InputStream in = connection.getInputStream();
                        bufferedReader = new BufferedReader(new InputStreamReader(in));

                        StringBuilder stringBuilder = new StringBuilder();
                        String s;
                        while ((s = bufferedReader.readLine()) != null) {
                            stringBuilder.append(s);
                        }

                        jsonFileString = stringBuilder.toString();

                    } catch (Exception e) {
                        e.printStackTrace(System.err);
                    } finally {
                        if (connection != null) {
                            connection.disconnect();
                        }
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e) {
                                e.printStackTrace(System.err);
                            }
                        }
                    }
                }
            }
        });
        thread.start();
        try {
            thread.join(4000);
        } catch (InterruptedException e) {
            e.printStackTrace(System.err);
        }

        return jsonFileString;
    }
}
