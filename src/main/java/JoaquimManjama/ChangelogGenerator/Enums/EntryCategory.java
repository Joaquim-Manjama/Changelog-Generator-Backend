package JoaquimManjama.ChangelogGenerator.Enums;

public enum EntryCategory {
    BUG_FIX,
    IMPROVEMENT,
    NEW_FEATURE;

    public static EntryCategory fromString(String category) {
        for (EntryCategory e : EntryCategory.values()) {
            if (e.name().equalsIgnoreCase(category)) {
                return e;
            }
        }

        return null;
    }
}
