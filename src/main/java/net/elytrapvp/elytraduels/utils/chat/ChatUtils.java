package net.elytrapvp.elytraduels.utils.chat;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

public class ChatUtils {
    private final static int CENTER_PX = 154;

    /**
     * A quicker way to send colored messages to a command sender.
     * @param sender Command Sender
     * @param message Message to be sent.
     */
    public static void chat(CommandSender sender, String message) {
        sender.sendMessage(translate(message));
    }

    /**
     * Sender a centered chat message to a CommandSender.
     * @param sender Command Sender
     * @param message Message
     */
    public static void centeredChat(CommandSender sender, String message) {
        message = translate(message);

        if(message == null || message.equals("")) sender.sendMessage("");
        message = net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', message);

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for(char c : message.toCharArray()) {
            if(c == '§') {
                previousCode = true;
            }
            else if(previousCode) {
                previousCode = false;
                isBold = c == 'l' || c == 'L';
            }
            else {
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }

        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = CENTER_PX - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while(compensated < toCompensate) {
            sb.append(" ");
            compensated += spaceLength;
        }
        sender.sendMessage(sb.toString() + message);
    }

    /**
     * Translate a String to a colorful String.
     * @param message Message to translate.
     * @return Translated Message.
     */
    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Translate an array of strings.
     * @param arr Strings
     * @return array of translated strings.
     */
    public static String[] translate(String[] arr) {
        for(int i = 0; i < arr.length; i++) {
            arr[i] = translate(arr[i]);
        }

        return arr;
    }
}