package me.t3sl4.claim.util;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

public class Messages {

	public static String GUI_NAME = "Claim Meünüsü";
	public static int GUI_SIZE = 1;
	
	//public static List<String> COMMAND_HELP = new ArrayList<String>();
	public static String COMMAND_HELP1 = "&8&l&m-&f&l&m-&8&l&m-&f&l&m-&8&l&m-&f&l&m-&8&l&m[&6 &lT3SL4Claim &8&l&m]&f&l&m-&8&l&m-&f&l&m-&8&l&m-&f&l&m-&8&l&m-";
	public static String COMMAND_HELP2 = "&a/claim menü &f»&eClaim Menüsü Açılır.";
	public static String COMMAND_HELP3 = "&a/claim ekle &7<&bİsim&7> &f»&eClaime Oyuncu Ekler.";
	public static String COMMAND_HELP4 = "&a/claim sil &7<&bİsim&7> &f»&eClaimden oyuncu Siler.";
	public static String COMMAND_HELP5 = "&a/claim bilgi &f»&eYakındaki Claimleri Gösterir.";
	public static String COMMAND_HELP6 = "&a/claim sorgu &f»&eClaimlerinizin Kalan Zamanlarını Gösterir.";
	public static String COMMAND_HELP7 = "&a/claim yetkililer &f»&eBulunduğunuz Claimdeki Yetkilileri Gösterir.";
	public static String COMMAND_HELP8 = "&a/claim liste &f»&eClaimlerinizini Gösterir.";
	public static String COMMAND_HELP9 = "&a/claim chunk &f»&eBulunduğunuz Chunk Sınırlarını Gösterir.";
	public static String COMMAND_HELPRELOAD = "&a/claim reload &f»&eEklentiyi Yeniler.";
	
	public static String ENABLED_WORLD = "Orman";
	
	public static String CLAIM_END = "&6&lT3SL4Claim &8» &b%player% &eAdlı Oyuncunun &a%claim% &eNumaralı &7(&c%location%&7) &eClaimi Bitmiştir!";
	public static String CLAIM_DISABLED_WORLD = "&6&lT3SL4Claim &8» &4Bu Dünyada Claim Atılamaz!";
	public static String CLAIM_ALREADY_CLAIMED = "&6&lT3SL4Claim &8» &4Bu Alan Zaten Claimli!";
	public static String CLAIM_TIME_CHANGED = "&6&lT3SL4Claim &8» &eClaim &b%time% &eGününe Kadar Uzatıldı!";
	public static String CLAIM_SET = "&6&lT3SL4Claim &8» &aBulunduğunuz Alan Başarıyla Claimlendi!";
	public static String UNKNOWN_ERROR = "&6&lT3SL4Claim &8» &4Bilinmeyen Hata Oluştu Lütfen Sonra Tekrar Deneyin!";
	public static String CLAIM_INFO = "&6&lT3SL4Claim &8» &b%player% &eAdlı Oyuncunun &a%time% &eSüresince Sahip Olduğu Claimdesiniz!";
	public static String YOU_ARE_NOT_STAFF = "&6&lT3SL4Claim &8» &4Bu Claimde Yetkili Değilsiniz!";
	public static String HE_ALREADY_STAFF = "&6&lT3SL4Claim &8» &4Bu Oyuncu Zaten Yetkili!";
	public static String HE_NOT_STAFF = "&6&lT3SL4Claim &8» &4Bu Oyuncu Zaten Yetkili Değil!";
	public static String STAFF_ADDED = "&6&lT3SL4Claim &8» &b%player% &eClaime Yetkili Olarak Eklendi!";
	public static String STAFF_REMOVED = "&6&lT3SL4Claim &8» &b%player% &eAdlı Yetkili Claimden Silindi!";
	public static String STAFF_LIST = "&6&lT3SL4Claim &8» &eBu Claimdeki Yetkililer: &b%staffs-'&a,&f'%";
	public static String CLAIM_LIST = "&6&lT3SL4Claim &8» &eBu Claimleriniz: &b%claims-'&a,&f'%";
	public static String UNKNOWN_CLAIM = "&6&lT3SL4Claim &8» &4Bulunduğunuz Yerde Bir Claim Yok!";
	public static String HAS_NOT_VALUE = "&6&lT3SL4Claim &8» &4Yeterli %value%&e'ün Yok!";
	public static String CHUNK_VIEWED = "&6&lT3SL4Claim &8» &eEtrafındaki Camlar Bulunduğun Bölgedeki Chunk Sınırlarını İşaret Ediyor!";
	public static String CHUNK_REMOVED= "&6&lT3SL4Claim &8» &eHareket Ettiğin İçin Etrafındaki Camlar Kaldırıldı!";
	public static String CONSOLE= "&6&lT3SL4Claim &8» &4Bu Komutu Konsoldan Giremezsin!";
	public static String RELOAD = "&6&lT3SL4Claim &8» &aEklenti Başarıyla Yenilendi!";
	
	public static void loadMessages(FileConfiguration fc) {
		GUI_NAME = colorize(fc.getString("Gui.name"));
		GUI_SIZE = fc.getInt("Gui.size");
		
		//COMMAND_HELP = colorizeList(fc.getStringList("Commands.help"));
		COMMAND_HELP1 = colorize(fc.getString("Commands.help1"));
		COMMAND_HELP2 = colorize(fc.getString("Commands.help2"));
		COMMAND_HELP3 = colorize(fc.getString("Commands.help3"));
		COMMAND_HELP4 = colorize(fc.getString("Commands.help4"));
		COMMAND_HELP5 = colorize(fc.getString("Commands.help5"));
		COMMAND_HELP6 = colorize(fc.getString("Commands.help6"));
		COMMAND_HELP7 = colorize(fc.getString("Commands.help7"));
		COMMAND_HELP8 = colorize(fc.getString("Commands.help8"));
		COMMAND_HELP9 = colorize(fc.getString("Commands.help9"));
		COMMAND_HELPRELOAD = colorize(fc.getString("Commands.helpreload"));
		
		ENABLED_WORLD = fc.getString("Settings.world");
		
		CLAIM_END = colorize(fc.getString("Messages.claim-end"));
		CLAIM_DISABLED_WORLD = colorize(fc.getString("Messages.claim-disabled-world"));
		CLAIM_ALREADY_CLAIMED = colorize(fc.getString("Messages.chunk-already-claimed"));
		CLAIM_TIME_CHANGED = colorize(fc.getString("Messages.claim-time-changed"));
		CLAIM_SET = colorize(fc.getString("Messages.claim-set"));
		UNKNOWN_ERROR = colorize(fc.getString("Messages.unknown-error"));
		CLAIM_INFO = colorize(fc.getString("Messages.claim-info"));
		YOU_ARE_NOT_STAFF = colorize(fc.getString("Messages.you-are-not-staff"));
		HE_ALREADY_STAFF = colorize(fc.getString("Messages.he-already-staff"));
		HE_NOT_STAFF = colorize(fc.getString("Messages.he-not-staff"));
		STAFF_ADDED = colorize(fc.getString("Messages.staff-added"));
		STAFF_REMOVED = colorize(fc.getString("Messages.staff-removed"));
		HAS_NOT_VALUE = colorize(fc.getString("Messages.has-not-value"));
		STAFF_LIST = fc.getString("Messages.staff-list");
		CLAIM_LIST = fc.getString("Messages.claim-list");
		UNKNOWN_CLAIM = colorize(fc.getString("Messages.unknown-claim"));
		CHUNK_VIEWED = colorize(fc.getString("Messages.chunk-viewed"));
		CHUNK_REMOVED = colorize(fc.getString("Messages.chunk-removed"));
		CONSOLE = colorize(fc.getString("Messages.console"));
		RELOAD = colorize(fc.getString("Messages.reload"));
		
		for(String str: fc.getConfigurationSection("Gui.items").getKeys(false)) {
			new ClaimGuiItem(fc, str);
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
