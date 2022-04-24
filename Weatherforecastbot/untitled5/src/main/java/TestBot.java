import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;



public class TestBot extends TelegramLongPollingBot {

    Temperature temperature;

    public TestBot(Temperature temperature) {
        this.temperature = temperature;
    }

    @Override
    public String getBotUsername() {
        return "Weatheeerrrforeeecassstbot";
    }

    @Override
    public String getBotToken() {
        return "5372786635:AAEyvhyIYK9YNQ-i8oJwZGxgWDK2djeN-Tc";
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId().toString());

        if(update.hasMessage() && update.getMessage().hasText()
                && update.getMessage().getText().equals("Какая сегодня погода?")) {
            message.setText("Сегодняшняя погода в г. Екатеринбург: " + temperature.getMin() + "..." + temperature.getMax()+"°C");

        }
        else
        if (update.hasMessage() && update.getMessage().hasText()
                && update.getMessage().getText().equals("/start")) {
            message.setText("С помощью этого бота Вы можете узнать погоду в г. Екатеринбург на текущий день.");

        }
        else
        if (update.hasMessage() && update.getMessage().hasText()
                && update.getMessage().getText().equals("/help")) {
            message.setText("Чтобы узнать погоду, напишите \"Какая сегодня погода?\" без кавычек.");
        }
        else
        {
            message.setText("Некорректная команда. Попробуйте ещё раз.");

        }
        try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
   }
}
