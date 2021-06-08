package com.example.pickt.UtilService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

public class OcrProc {

    // OCR 사용을 위한 네이버 GateWay 연결 및 인식할 이미지 전송
    public static String main(String ocrApiUrl, String ocrSecretKey, String encodedImage){
        String ocrMessage = "";

        try{
            String apiUrl = ocrApiUrl;
            String secretKey = ocrSecretKey;
            String Image = "";

            URL url = new URL(ocrApiUrl);
            String message = getReqMessage(encodedImage);

            long timestamp = new Date().getTime();

            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json;UTF-8");
            con.setRequestProperty("X-OCR-SECRET", secretKey);

            // post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.write(message.getBytes("UTF-8"));
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();

            if (responseCode == 200){   // 정상 호출
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                con.getInputStream()));
                String decodedString;
                while ((decodedString = in.readLine()) != null){
                    ocrMessage = decodedString;
                }
                in.close();
            }else {
                ocrMessage = con.getResponseMessage();
            }

        } catch (Exception e) {

        }
        return ocrMessage;
    }

    // 네이버 CLOVA OCR API의 Input 생성 (JSON 구조 Input)
    public static String getReqMessage(String encodedImage){
        String requestBody = "";
        try {
            long timestamp = new Date().getTime();

            JSONObject json = new JSONObject();
            json.put("version", "V2");
            json.put("requestId", UUID.randomUUID().toString());
            json.put("timestamp", Long.toString(timestamp));
            JSONObject image = new JSONObject();
            image.put("format", "png");
            image.put("data", encodedImage);
            image.put("name", "test_ocr");
            JSONArray images = new JSONArray();
            images.put(image);
            json.put("images", images);

            requestBody = json.toString();
        }catch (Exception e){

        }
        return requestBody;
    }
}
