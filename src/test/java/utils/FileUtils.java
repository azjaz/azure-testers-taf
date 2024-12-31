package utils;

import java.io.File;
import java.util.Arrays;

import static constants.ServiceConstants.PATH_TO_DOWNLOAD_FOLDER;

public class FileUtils {

    public static boolean deleteFilesInDirectory() {
        File dir = new File(PATH_TO_DOWNLOAD_FOLDER.getValue());
        File[] files = dir.listFiles();

        return Arrays.stream(files)
                .filter(File::isFile)
                .map(File::delete)
                .reduce(true, (acc, deleted) -> acc && deleted);
    }

}
