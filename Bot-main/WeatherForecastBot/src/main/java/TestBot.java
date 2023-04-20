import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

public class TestBot extends TelegramLongPollingBot {

    private final TemperatureService temperatureService;

    public TestBot() throws Exception {
        temperatureService = new TemperatureService();
    }
    @Override
    public String getBotUsername() {
        return "Weatheeerrrforeeecassstbot.";
    }

    
    @Override
    public String getBotToken() {
        return "5372786635:AAEyvhyIYK9YNQ-i8oJwZGxgWDK2djeN-Tc";
    }

    @Override
    public void onUpdateReceived(Update update) {

        Message receivedMessage = update.getMessage();

        if (receivedMessage != null && receivedMessage.hasText()) {

            String chatId = receivedMessage.getChatId().toString();
            String receivedText = update.getMessage().getText();

            switch (receivedText) {
                case "Какая сегодня погода?" -> {
                    Temperature temperature = null;
                    try {
                        temperature = temperatureService.GetTemperature();
                    } catch (Exception e) {
                        SendAnswer(chatId, "Произошла ошибка, повторите запрос позднее");
                    }
                    SendAnswer(chatId, "Сегодняшняя погода в г. Екатеринбург: " + temperature.getMin() + "..." + temperature.getMax() + "°C");
                }
                case "/start" -> SendAnswer(chatId, "С помощью этого бота Вы можете узнать погоду в г. Екатеринбург на текущий день.");
                case "/help" -> SendAnswer(chatId, "Чтобы узнать погоду, напишите \"Какая сегодня погода?\" без кавычек.");
                default -> SendAnswer(chatId, "Некорректная команда. Попробуйте ещё раз.");
            }
        }
    }

    private void SendAnswer(String chatId, String answer){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(answer);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }



}
