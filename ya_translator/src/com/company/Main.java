package com.company;
/*
Создать консольное приложение для перевода текста
Запросить у пользователя фразу и направление перевода
Обратиться к API Яндекс-переводчика, сделать запрос методом POST
Полученный ответ десереализовать из JSON в объект,
Вывести результат перевода и статус запроса (успешно или нет)
*/
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

class TranslatedText {
    String text, code,lang;
    @Override
    public String toString() {
        return "{"+text+"}";
    }
}
public class Main {
    final static String API_KEY = "trnsl.1.1.20191007T114747Z.42023c5406dedaa4.c283e670320c21ad9e2362d95cb3f804bde399b8";


    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        TranslatedText t;

        System.out.print("Enter text to translate: ");
        String TERMINATOR_STRING = "//";

        java.util.Scanner a = new java.util.Scanner(System.in);
        StringBuilder b = new StringBuilder();
        String strLine;
        while (!(strLine = a.nextLine()).equals(TERMINATOR_STRING)) {
            b.append(strLine);
        }

        System.out.print("Translate from: ");
        String langaugeFrom = sc.nextLine();

        System.out.print("Translate to: ");
        String langaugeTo = sc.nextLine();

        String set_server_url = "https://translate.yandex.net/api/v1.5/tr.json/translate";

        // need to URL encode form fields
        // https://docs.oracle.com/javase/8/docs/api/java/net/URLEncoder.html
        String data = "lang=" + langaugeFrom + "-" + langaugeTo + "&text=" + b + "&format=plain";
        data += "&key=" + API_KEY;

        URL url = new URL(set_server_url);
        // creating connection
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setDoOutput(true); // setting POST method
        // creating stream for writing request
        OutputStream out = urlConnection.getOutputStream();
        out.write(data.getBytes());
        //InputStream stream = (InputStream) urlConnection.getContent();

        // reading response
        Scanner in = new Scanner(urlConnection.getInputStream());
        Gson gson = new Gson();
        if (in.hasNext()) {
            System.out.print("Translated text: ");
            String str = gson.toJson(in.nextLine());
            System.out.println(str);
            t = gson.fromJson(str, TranslatedText.class);
            System.out.println(t);
            } else System.out.println("No output returned");
            urlConnection.disconnect();
        }
    }
