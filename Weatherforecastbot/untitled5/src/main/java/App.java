import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws IOException {



        String minTemp;
        String maxTemp;

        OkHttpClient okHttpClient = new OkHttpClient();

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("dataservice.accuweather.com")
                .addPathSegment("forecasts")
                .addPathSegment("v1")
                .addPathSegment("daily")
                .addPathSegment("1day")
                .addPathSegment("292712")
                .addQueryParameter("apikey", "t2cVAklY2TNAPLX9JqSyftAywCDLXTws")
                .addQueryParameter("metric", "true")
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        String jsonString = response.body().string();


        //СТРОКА -> JSON -> СТРОКА min/max
        //CLASS -> JSON
        //JSON -> CLASS

        //Jackson
        ObjectMapper objectMapper = new ObjectMapper();

        minTemp = objectMapper.readTree(jsonString)
                .at("/DailyForecasts")
                .get(0)
                .at("/Temperature")
                .at("/Minimum")
                .at("/Value")
                .asText();

        maxTemp = objectMapper.readTree(jsonString)
                .at("/DailyForecasts")
                .get(0)
                .at("/Temperature")
                .at("/Maximum")
                .at("/Value")
                .asText();


        System.out.println(minTemp + " " + maxTemp);

        Temperature temperature = new Temperature(
                Double.parseDouble(minTemp),
                Double.parseDouble(maxTemp)
        );

        try {
            DbHandler dbHandler = new DbHandler();
            dbHandler.addTemperature(temperature);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            DbHandler dbHandler = new DbHandler();
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            BotSession botSession = telegramBotsApi.registerBot(new TestBot(dbHandler.getTemperature()));
        } catch (TelegramApiException | SQLException e) {
            e.printStackTrace();
        }

    }
}
