package uk.co.alt236.btlescan.ui.main;

/*package*/ class CsvWriterHelper {
    private static final String QUOTE = "\"";

    public static String addStuff(final Integer text) {
        return QUOTE + text + QUOTE + ",";
    }

    public static String addStuff(final Long text) {
        return QUOTE + text + QUOTE + ",";
    }

    public static String addStuff(final boolean value) {
        return QUOTE + value + QUOTE + ",";
    }

    public static String addStuff(String text) {
        if (text == null) {
            text = "<blank>";
        }
        text = text.replace(QUOTE, "'");

        return QUOTE + text.trim() + QUOTE + ",";
    }
}
