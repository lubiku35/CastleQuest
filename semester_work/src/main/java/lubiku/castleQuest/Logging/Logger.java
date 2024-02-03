package lubiku.castleQuest.Logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <h2>Logger</h2>
 * The Logger class provides logging functionality for the game.
 * It allows logging messages to a log file with the current timestamp.
 */
public class Logger {
    private static final String LOG_FILE_NAME_FORMAT = "CastleQuest_%s.log";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd_MM_yyyy_HH_mm");
    private String logFileName;

    /**
     * <h3>Logger Constructor</h3>
     * Initializes the log file with the current timestamp in its name.
     */
    public Logger() { initializeLogFile(); }

    /**
     * <h3>initializeLogFile</h3>
     * Initializes the log file name based on the current date and time.
     */
    private void initializeLogFile() {
        String currentTime = DATE_FORMAT.format(new Date());
        logFileName = String.format(LOG_FILE_NAME_FORMAT, currentTime);
        String os = System.getProperty("os.name").toLowerCase();

        // Determine the appropriate temporary directory based on the operating system
        String tempDirectory;
        if (os.contains("win")) {
            tempDirectory = System.getenv("TEMP");
        } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
            tempDirectory = System.getenv("TMPDIR");
            if (tempDirectory == null) {
                tempDirectory = "/tmp";
            }
        } else {
            // Use a default temporary directory if the operating system is not recognized
            tempDirectory = System.getProperty("java.io.tmpdir");
        }

        // Create the log file directory if it does not exist
        File logFileDirectory = new File(tempDirectory, "CastleQuestLogs");
        if (!logFileDirectory.exists()) {
            logFileDirectory.mkdirs();
        }

        // Set the log file path to the temporary directory
        logFileName = new File(logFileDirectory, logFileName).getPath();
        this.logFileName = logFileName;
    }


    /**
     * <h3>log</h3>
     * Logs a message to the log file.
     * @param message the message to be logged
     */
    public void log(String message) {
        String logEntry = String.format("[%s] %s%n", getCurrentTime(), message);
        saveToLogFile(logEntry);
    }

    /**
     * <h3>getCurrentTime</h3>
     * Retrieves the current time in the format "yyyy-MM-dd HH:mm:ss".
     * @return The current time as a formatted string.
     */
    private String getCurrentTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return timeFormat.format(new Date());
    }

    /**
     * <h3>saveToLogFile</h3>
     * Saves the log entry to the log file.
     * @param logEntry The log entry to be saved.
     */
    private void saveToLogFile(String logEntry) {
        try (FileWriter writer = new FileWriter(logFileName, true)) {
            writer.write(logEntry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
