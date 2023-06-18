package org.example;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import com.google.gson.Gson;
import com.google.gson.JsonObject;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

// in case if it's not executing the program use this command to allow it (works on linux)
// chmod +x /home/abbas/IdeaProjects/tmdbApi/target/tmdbApi-1.0-SNAPSHOT.jar


public class Main {
    private static  String date = "";
    public static void CreateGUI() {
        OkHttpClient client = new OkHttpClient();

        JFrame frame = new JFrame("Dynamic Component App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


        JLabel title = new JLabel("This data is fetched from tmdb");
        title.setOpaque(true);
        title.setBackground(Color.CYAN);  // Set background color to aqua
        title.setForeground(Color.BLACK);  // Set text color to white
        title.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        panel.add(title);
        try {
            // If you dont mind remove the api key after using.
            Request request = new Request.Builder()
                    .url("https://api.themoviedb.org/3/trending/all/day?language=en-US&api_key=3ffb1bd1d470218162a8522f86356fba")
                    .build();
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(responseBody, JsonObject.class);
            JsonArray results =  jsonObject.get("results").getAsJsonArray();


            for (JsonElement element : results) {
                JsonObject item = element.getAsJsonObject();
                String name = "";
                if(item.has("title")) {
                    name = item.get("title").getAsString();
                    date = item.get("release_date").getAsString();
                } else {
                    name = item.get("name").getAsString();
                    date = item.get("first_air_date").getAsString();
                }
                JPanel moviePanel = new JPanel();
                JLabel nameEl = new JLabel(name);
                JLabel voteAvg = new JLabel("Vote average: "+item.get("vote_average").getAsString());
                JLabel popularity = new JLabel("Popularity: "+item.get("popularity").getAsString());
                JButton seeDate = new JButton("See date");

                seeDate.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(frame, date);
                    }
                });


                nameEl.setOpaque(true);
                nameEl.setBackground(Color.magenta);  // Set background color to aqua
                nameEl.setForeground(Color.WHITE);  // Set text color to white
                nameEl.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

                moviePanel.setLayout(new BoxLayout(moviePanel, BoxLayout.Y_AXIS));
                panel.add(nameEl);
                panel.add(voteAvg);
                panel.add(popularity);
                panel.add(seeDate);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
//        frame.setBounds(300, 300, 300, 300);
        frame.setSize(300, 200);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            CreateGUI();
        });

    }

}