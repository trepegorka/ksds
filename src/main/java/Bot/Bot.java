package Bot;import HltvPath.Hltv;import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;import org.telegram.telegrambots.api.methods.send.SendDocument;import org.telegram.telegrambots.api.methods.send.SendMessage;import org.telegram.telegrambots.api.objects.Update;import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;import org.telegram.telegrambots.bots.TelegramLongPollingBot;import org.telegram.telegrambots.exceptions.TelegramApiException;import starter.HltvBuilder;import java.io.*;import java.util.ArrayList;import java.util.HashSet;import java.util.List;import java.util.Set;public class Bot extends TelegramLongPollingBot {    @Override    public void onUpdateReceived(Update update) {            // We check if the update has a message and the message has text            if (update.hasMessage() && update.getMessage().hasText()) {                String message_text = update.getMessage().getText();                long chat_id = update.getMessage().getChatId();               // if (update.getMessage().getText().equals("sd")) {                    SendMessage message = new SendMessage() // Create a message object object                            .setChatId(chat_id)                            .setText("Hello I'm cs predictor");                    InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();                    List<List<InlineKeyboardButton>> rowsInline = new ArrayList<List<InlineKeyboardButton>>();                    List<InlineKeyboardButton> rowInline = new ArrayList<InlineKeyboardButton>();                    //rowInline.add(new InlineKeyboardButton().setText("test").setCallbackData("test"));                    rowInline.add(new InlineKeyboardButton().setText("Get Live 1").setCallbackData("Get Live 1"));                    rowInline.add(new InlineKeyboardButton().setText("Get Live 2").setCallbackData("Get Live 2"));                // Set the keyboard to the markup                    rowsInline.add(rowInline);                    // Add it to the message                    markupInline.setKeyboard(rowsInline);                    message.setReplyMarkup(markupInline);                    try {                        execute(message); // Sending our message object to user                    } catch (TelegramApiException e) {                        e.printStackTrace();                    }            } else if (update.hasCallbackQuery()) {                String call_data = update.getCallbackQuery().getData();                if (call_data.equals("Get Live 1")) {                    try {                        ArrayList<String> list = HltvBuilder.start();                        String chat_id="569502265";                        Hltv.requestTelegram(list, chat_id);                    } catch (IOException e) {                        e.printStackTrace();                    } catch (Exception e) {                        e.printStackTrace();                    }                }                else if (call_data.equals("Get Live 2")) {                    try {                        String chat_id="596158716";                        ArrayList<String> list = HltvBuilder.start();                        Hltv.requestTelegram(list,chat_id);                    } catch (IOException e) {                        e.printStackTrace();                    } catch (Exception e) {                        e.printStackTrace();                    }                }            }    }    private void sendDocUploadingAFile(Long chatId, File save,String caption) throws TelegramApiException {        SendDocument sendDocumentRequest = new SendDocument();        sendDocumentRequest.setChatId(chatId);        sendDocumentRequest.setNewDocument(save);        sendDocumentRequest.setCaption(caption);        sendDocument(sendDocumentRequest);    }    public synchronized void sendMsg(String chatId, String s) {        SendMessage sendMessage = new SendMessage();        sendMessage.setChatId(chatId);        sendMessage.setText(s);        try {            sendMessage(sendMessage);        } catch (TelegramApiException e) {            System.out.println();        }    }    public synchronized void answerCallbackQuery(String callbackId, String message) {        AnswerCallbackQuery answer = new AnswerCallbackQuery();        answer.setCallbackQueryId(callbackId);        answer.setText(message);        answer.setShowAlert(true);        try {            answerCallbackQuery(answer);        } catch (TelegramApiException e) {            e.printStackTrace();        }    }    @Override    public String getBotUsername() {        return "@CsFutureBot";    }    @Override    public String getBotToken() {        return "1278410888:AAHaO2JkgP4NjGrVJSSS91t4fNJt0aCc2js";    }    public synchronized void setButtons(SendMessage sendMessage) {        // Создаем клавиуатуру        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();        sendMessage.setReplyMarkup(replyKeyboardMarkup);        replyKeyboardMarkup.setSelective(true);        replyKeyboardMarkup.setResizeKeyboard(true);        replyKeyboardMarkup.setOneTimeKeyboard(true);        // Создаем список строк клавиатуры        List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>();        // Первая строчка клавиатуры        KeyboardRow keyboardFirstRow = new KeyboardRow();        // Добавляем кнопки в первую строчку клавиатуры        keyboardFirstRow.add(new KeyboardButton("get"));        // Вторая строчка клавиатуры        KeyboardRow keyboardSecondRow = new KeyboardRow();        // Добавляем кнопки во вторую строчку клавиатуры        keyboardSecondRow.add(new KeyboardButton("Help"));        // Добавляем все строчки клавиатуры в список        keyboard.add(keyboardFirstRow);        keyboard.add(keyboardSecondRow);        // и устанваливаем этот список нашей клавиатуре        replyKeyboardMarkup.setKeyboard(keyboard);    }    private void setInline() {        List<List<InlineKeyboardButton>> buttons = new ArrayList<List<InlineKeyboardButton>>();        List<InlineKeyboardButton> buttons1 = new ArrayList<InlineKeyboardButton>();        buttons1.add(new InlineKeyboardButton().setText("new").setCallbackData("but"));        buttons.add(buttons1);        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();        markupKeyboard.setKeyboard(buttons);    }}