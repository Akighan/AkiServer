package telegrambot.bot;

import telegrambot.service.CommandContainer;
import telegrambot.service.SendBotMessageServiceImpl;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

import static telegrambot.command.CommandName.NO;


public class TelegramBot extends TelegramLongPollingBot {
    private static String username;
    private static String token;
    private static File file = Paths.get("src\\main\\resources\\application.properties").toFile();

    public static String COMMAND_PREFIX = "/";

    private final CommandContainer commandContainer;

    public TelegramBot() throws IOException {
        this.commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this));
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            Properties properties = new Properties();
            properties.load(fileInputStream);
            username = properties.getProperty("bot.username");
            token = properties.getProperty("bot.token");
        }
    }


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(" ")[0].toLowerCase();

                commandContainer.retrieveCommand(commandIdentifier).execute(update);
            } else {
                commandContainer.retrieveCommand(NO.getCommandName()).execute(update);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }


    @Override
    public String getBotToken() {
        return token;
    }

}
