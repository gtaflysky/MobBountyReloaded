package info.hawksharbor.MobBounty.Managers;

import info.hawksharbor.MobBounty.MobBountyReloaded;
import info.hawksharbor.MobBounty.Utils.MobBountyConfFile;

public class MobBountyLocale
{
	private final MobBountyReloaded _plugin;

	public MobBountyLocale(MobBountyReloaded plugin)
	{
		_plugin = plugin;
	}

	/**
	 * Gets a string from Locale.yml
	 * 
	 * @param String
	 *            Key to look for
	 * @return String String requested
	 */
	public String getString(String key)
	{
		String locale = _plugin.getAPIManager().getConfigManager()
				.getProperty(MobBountyConfFile.GENERAL, "locale");

		if (locale != null)
			locale = locale.toLowerCase();
		else
			locale = "en";

		String value = _plugin.getAPIManager().getConfigManager()
				.getProperty(MobBountyConfFile.LOCALE, locale + "." + key);

		if (value == null)
			value = _plugin.getAPIManager().getConfigManager()
					.getProperty(MobBountyConfFile.LOCALE, "en." + key);

		if (value != null)
			value = value.replace('&', '\u00A7').replace("\u00A7\u00A7", "&");

		return value;
	}
}