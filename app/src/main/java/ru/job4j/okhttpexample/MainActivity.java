package ru.job4j.okhttpexample;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = findViewById(R.id.result);

        OkHttpClient client = new OkHttpClient();
        String url = "https://api.github.com/users/NiyazFazlyev/repos";//"https://jsonplaceholder.typicode.com/posts/2";

        final Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String strResponse = response.body().string();

                    try {
                        Object obj = new JSONParser().parse(strResponse);
                        final JSONArray array = (JSONArray) obj;
                        final List<String> names = new ArrayList<>();
                        for (Object var : array) {
                            JSONObject repos = (JSONObject) var;
                            names.add((String) repos.get("name"));
                        }

                        System.out.println(names.toString());

                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                result.setText("");
                                for(String name : names){
                                    result.append(name + "\n");
                                }

                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }
}
