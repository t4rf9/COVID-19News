package com.java.linzexi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetWork {
    String websiteURL;
    String wholeContent = null;
    public NetWork(String website){
        websiteURL = website;
    }
    public NetWork(){}
    public void changeURL(String website){
        websiteURL = website;
    }
    private void run1(){
        System.out.println("build thread");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection con = null;
                BufferedReader br = null;
                try{
                    URL url = new URL(websiteURL);
                    con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setConnectTimeout(8000);
                    con.setReadTimeout(8000);
                    InputStream in = con.getInputStream();
                    br = new BufferedReader(new InputStreamReader(in));

                    StringBuilder sb = new StringBuilder();
                    String s;
                    while((s = br.readLine()) != null){
                        sb.append(s);
                    }
                    wholeContent = sb.toString();
                }catch(Exception e){
                    e.printStackTrace();
                }finally{
                    if(con != null){
                        con.disconnect();
                    }
                    if(br != null){
                        try{
                            br.close();
                        }catch(IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        thread.start();
        try{
            thread.join(4000);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public String getStringResult(){
        run1();
        return wholeContent;
    }
}
