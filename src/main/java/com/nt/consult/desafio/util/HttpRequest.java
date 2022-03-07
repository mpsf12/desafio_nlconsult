package com.nt.consult.desafio.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nt.consult.desafio.model.UserPodeVotar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequest<T> {

    public T get(String url, Class<T> classReturn) throws IOException {

        BufferedReader in;
        try {

            URL uri = new URL(url);
            HttpURLConnection con = (HttpURLConnection) uri.openConnection();
            con.setRequestMethod("GET");

            int status = con.getResponseCode();


            if(status == 200) {
                in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
            } else {
                in = new BufferedReader(
                        new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            ObjectMapper mapper = new ObjectMapper();
            in.close();

            return mapper.readValue(content.toString(), classReturn);

        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }

    }
}
