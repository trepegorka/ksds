package telegramBot;import com.pengrad.telegrambot.TelegramBot;import com.pengrad.telegrambot.model.request.ParseMode;import com.pengrad.telegrambot.request.SendMessage;public class Bot extends TelegramBot {    private static String message;    private static final Bot bot = new Bot(Bot.getBotToken());    public Bot(String botToken) {        super(botToken);    }    public static String getBotUsername() {        return "@CsBotyara_bot";    }    public static String getBotToken() {        return "1265280075:AAF9Gb7_jvSOwOE0FHDxLHlCO6rxwIi2QHo";    }    public static String getChatId() {        return "@predik";    }    public static void sendMessage() {        bot.execute(new SendMessage(getChatId(), getMessage()).parseMode(ParseMode.HTML));    }    public static String getMessage() {        return message;    }    public static void setMessage(String message) {        Bot.message = message;    }}