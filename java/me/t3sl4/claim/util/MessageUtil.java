package me.t3sl4.claim.util;

import me.t3sl4.claim.gui.ClaimGUIItem;

import java.util.ArrayList;
import java.util.List;

public class MessageUtil {
    public static String PREFIX;
    public static String GUI_NAME;
    public static int GUI_SIZE;
    public static String COMMAND_HELP1;
    public static String COMMAND_HELP2;
    public static String COMMAND_HELP3;
    public static String COMMAND_HELP4;
    public static String COMMAND_HELP5;
    public static String COMMAND_HELP6;
    public static String COMMAND_HELP7;
    public static String COMMAND_HELP8;
    public static String COMMAND_HELP9;
    public static String COMMAND_HELPRELOAD;
    public static List<String> ENABLED_WORLDS = new ArrayList<>();
    public static String SPACE = "\n";
    public static String SPACETWO = " ";
    public static String WORLD;
    public static String X;
    public static String Z;
    public static String CLAIM;
    public static String SPLITTER;
    public static String CLAIM_END;
    public static String CLAIM_DISABLED_WORLD;
    public static String CLAIM_ALREADY_CLAIMED;
    public static String CLAIM_TIME_CHANGED;
    public static String CLAIM_SET;
    public static String UNKNOWN_ERROR;
    public static String CLAIM_INFO;
    public static String YOU_ARE_NOT_STAFF;
    public static String HE_ALREADY_STAFF;
    public static String HE_NOT_STAFF;
    public static String STAFF_ADDED;
    public static String STAFF_REMOVED;
    public static String STAFF_LIST;
    public static String CLAIM_LIST;
    public static String UNKNOWN_CLAIM;
    public static String HAS_NOT_VALUE;
    public static String CHUNK_VIEWED;
    public static String CHUNK_REMOVED;
    public static String CONSOLE;
    public static String ERROR_CMD;
    public static String RELOAD;

    static SettingsManager manager = SettingsManager.getInstance();

    public static void loadMessages() {
        PREFIX = colorize(manager.getConfig().getString("Prefix"));
        GUI_NAME = colorize(manager.getGUIConfig().getString("Gui.name"));
        GUI_SIZE = manager.getGUIConfig().getInt("Gui.size");
        COMMAND_HELP1 = colorize(manager.getConfig().getString("Commands.help1"));
        COMMAND_HELP2 = colorize(manager.getConfig().getString("Commands.help2"));
        COMMAND_HELP3 = colorize(manager.getConfig().getString("Commands.help3"));
        COMMAND_HELP4 = colorize(manager.getConfig().getString("Commands.help4"));
        COMMAND_HELP5 = colorize(manager.getConfig().getString("Commands.help5"));
        COMMAND_HELP6 = colorize(manager.getConfig().getString("Commands.help6"));
        COMMAND_HELP7 = colorize(manager.getConfig().getString("Commands.help7"));
        COMMAND_HELP8 = colorize(manager.getConfig().getString("Commands.help8"));
        COMMAND_HELP9 = colorize(manager.getConfig().getString("Commands.help9"));
        COMMAND_HELPRELOAD = colorize(manager.getConfig().getString("Commands.helpreload"));
        ENABLED_WORLDS = manager.getConfig().getStringList("Settings.enabled-worlds");
        WORLD = manager.getConfig().getString("World.world");
        X = manager.getConfig().getString("World.X");
        Z = manager.getConfig().getString("World.Z");
        CLAIM = SPACE + colorize(manager.getConfig().getString("World.claim"));
        SPLITTER = colorize(manager.getConfig().getString("World.splitter"));
        CLAIM_END = PREFIX + colorize(manager.getConfig().getString("Messages.claim-end"));
        CLAIM_DISABLED_WORLD = PREFIX + colorize(manager.getConfig().getString("Messages.claim-disabled-world"));
        CLAIM_ALREADY_CLAIMED = PREFIX + colorize(manager.getConfig().getString("Messages.chunk-already-claimed"));
        CLAIM_TIME_CHANGED = PREFIX + colorize(manager.getConfig().getString("Messages.claim-time-changed"));
        CLAIM_SET = PREFIX + colorize(manager.getConfig().getString("Messages.claim-set"));
        UNKNOWN_ERROR = PREFIX + colorize(manager.getConfig().getString("Messages.unknown-error"));
        CLAIM_INFO = PREFIX + colorize(manager.getConfig().getString("Messages.claim-info"));
        YOU_ARE_NOT_STAFF = PREFIX + colorize(manager.getConfig().getString("Messages.you-are-not-staff"));
        HE_ALREADY_STAFF = PREFIX + colorize(manager.getConfig().getString("Messages.he-already-staff"));
        HE_NOT_STAFF = PREFIX + colorize(manager.getConfig().getString("Messages.he-not-staff"));
        STAFF_ADDED = PREFIX + colorize(manager.getConfig().getString("Messages.staff-added"));
        STAFF_REMOVED = PREFIX + colorize(manager.getConfig().getString("Messages.staff-removed"));
        HAS_NOT_VALUE = PREFIX + colorize(manager.getConfig().getString("Messages.has-not-value"));
        STAFF_LIST = PREFIX + manager.getConfig().getString("Messages.staff-list");
        CLAIM_LIST = PREFIX + manager.getConfig().getString("Messages.claim-list");
        UNKNOWN_CLAIM = PREFIX + colorize(manager.getConfig().getString("Messages.unknown-claim"));
        CHUNK_VIEWED = PREFIX + colorize(manager.getConfig().getString("Messages.chunk-viewed"));
        CHUNK_REMOVED = PREFIX + colorize(manager.getConfig().getString("Messages.chunk-removed"));
        CONSOLE = PREFIX + colorize(manager.getConfig().getString("Messages.console"));
        ERROR_CMD = PREFIX + colorize(manager.getConfig().getString("Messages.error-cmd"));
        RELOAD = PREFIX + colorize(manager.getConfig().getString("Messages.reload"));

        for(String str: manager.getGUIConfig().getConfigurationSection("Gui.items").getKeys(false)) {
            new ClaimGUIItem(manager.getGUIConfig(), str, manager.getConfig());
        }

    }

    public static String colorize(String str) {
        return str.replace("&", "§");
    }

    public static List<String> colorizeList(List<String> str) {
        for(int x=0; x<str.size(); x++) {
            str.set(x, str.get(x).replace("&", "§"));
        }
        return str;
    }
}
