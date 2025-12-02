package faction;

/**
 * Represents the four elemental factions in ElementarClash.
 * Each faction has unique units, playstyles, and elemental advantages.
 */
public enum Faction {
    FIRE("Feuer", "🔥", "Aggressiv"),
    WATER("Wasser", "💧", "Defensiv"),
    EARTH("Erde", "🪨", "Kontrollierend"),
    AIR("Luft", "💨", "Mobil");

    private final String germanName;
    private final String icon;
    private final String playstyle;

    Faction(String germanName, String icon, String playstyle) {
        this.germanName = germanName;
        this.icon = icon;
        this.playstyle = playstyle;
    }

    public String getGermanName() { return germanName; }
    public String getIcon() { return icon; }
    public String getPlaystyle() { return playstyle; }
}