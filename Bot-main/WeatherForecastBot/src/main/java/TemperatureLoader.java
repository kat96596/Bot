import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class TemperatureLoader {
    private final OkHttpClient httpClient;

    public TemperatureLoader(){
        httpClient = new OkHttpClient();
    }

    public Temperature Load() throws IOException {
        String minTemp;
        String maxTemp;

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("dataservice.accuweather.com")
                .addPathSegment("forecasts")
                .addPathSegment("v1")
                .addPathSegment("daily")
                .addPathSegment("1day")
                .addPathSegment("295863")
                .addQueryParameter("apikey", "t2cVAklY2TNAPLX9JqSyftAywCDLXTws")
                .addQueryParameter("metric", "true")
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .build();

        Response response = httpClient.newCall(request).execute();
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

        return new Temperature(
                Double.parseDouble(minTemp),
                Double.parseDouble(maxTemp)
        );
    }
}
