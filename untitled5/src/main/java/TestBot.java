import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.sql.SQLException;

public class TestBot extends TelegramLongPollingBot {

    //*Заменить имя бота на "0"
    @Override
    public String getBotUsername() {
        return "exampleforuniversitybot";
    }

    //*Заменить токен бота на "0"
    @Override
    public String getBotToken() {
        return "5104347139:AAHCsYKynTcQieT9vweoS7ob0JgPoCVGoTU";
    }

    @Override
    public void onUpdateReceived(Update update) {
        DbHandler dbHandler = null;
        try {
            dbHandler = new DbHandler();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId().toString());

        if (update.hasMessage() && update.getMessage().hasText()) {

            String messageFrom = update.getMessage().getText();

            switch (messageFrom) {
                case "Какая сегодня погода?" -> {
                    try {
                        addWeatherFromAccuweatherToDb();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    assert dbHandler != null;
                    Temperature temperature = dbHandler.getTemperature();
                    message.setText("Сегодняшняя погода в г. Екатеринбург: " + temperature.getMin() + "..." + temperature.getMax() + "°C");
                }
                case "/start" -> message.setText("С помощью этого бота Вы можете узнать погоду в г. Екатеринбург на текущий день.");
                case "/help" -> message.setText("Чтобы узнать погоду, напишите \"Какая сегодня погода?\" без кавычек.");
                default -> message.setText("Некорректная команда. Попробуйте ещё раз.");
            }
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

    }


    private static void addWeatherFromAccuweatherToDb() throws IOException {
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

        //СТРОКА -> JSON -> СТРОКУ min/max
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
    }

}
