package pages;

import java.io.File;

import static constants.ServiceConstants.PATH_TO_DOWNLOAD_FOLDER;

public class DownloadReportPage extends AbstractPage{

    public File findDownloadedFile() {
        File dir = new File(PATH_TO_DOWNLOAD_FOLDER.getValue());
        File[] files = dir.listFiles();
        File mostRecentFile = null;

        if (files != null || files.length > 0) {
            long lastModifiedTime = Long.MIN_VALUE;
            for (File file : files) {
                if (file.isFile() && file.lastModified() > lastModifiedTime) {
                    lastModifiedTime = file.lastModified();
                    mostRecentFile = file;
                }
            }
        }
        return mostRecentFile;
    }

}

