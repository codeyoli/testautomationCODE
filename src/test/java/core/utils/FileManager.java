package core.utils;

import core.Automation;

import java.io.File;

public class FileManager {

    /*
     * Use this to delete the recorded video of passed test cases. This method is effective when
     * the video recording is enabled for test execution and TestDetection is registered as test listener.
     *
     */
    public static void deletePassedRecordings(String prefix) {
        File directory = new File(Automation.util.root()+"/video/");
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isFile() && file.getName().startsWith(prefix)) {
                if (file.delete()) {
                    String message = "The file " + file.getName() + " was successfully deleted";
                    System.out.println(message);
                } else {
                    String message = "The file " + file.getName() + " could not be deleted";
                    System.out.println(message);
                }
            }
        }//end::for
    }

    public static  void uploadRecording(String prefix) {
        String videoPath = System.getProperty("user.dir")+"/video/";
        File directory = new File(videoPath);
        File[] files = directory.listFiles();
        String recordPath = "";
        for (File file : files) {
            String fileName = file.getName();
            if(fileName.startsWith(prefix)) {
                recordPath = videoPath + fileName;
                break;
            }
        }//for
        File recordingVid = new File(recordPath);
        String msg = "Test recording, download to watch it";
        Logs._RPFILE(recordingVid, msg);
    }


    public static  void uploadMp4ToRP(String name) {
        String videoPath = System.getProperty("user.dir") +"/";
        File directory = new File(videoPath);
        File[] files = directory.listFiles();
        String recordPath = "";
        for (File file : files) {
            String fileName = file.getName();
            if(fileName.startsWith(name)) {
                recordPath = videoPath + fileName;
                break;
            }
        }//for
        System.out.println("Recorded Path: " + recordPath);
        File recordingVid = new File(recordPath);
        String msg = "Test recording, download to watch it";
        Logs._RPFILE(recordingVid, msg);
    }


    /**
     * Given the file name that starts with given prefix, renames the file to the
     * desired file name. Note that resulting file will be saved to the project root directory.
     *
     * @param prefix
     * @param desired
     */
    public static  void renameFile(String prefix, String desired) {
        String videoPath = System.getProperty("user.dir")+"/video/";
        File directory = new File(videoPath);
        File[] files = directory.listFiles();
        String recordPath = "";
        for (File file : files) {
            String fileName = file.getName();
            if(fileName.startsWith(prefix)) {
                recordPath = videoPath + fileName;
                break;
            }
        }//for
        File recordingVid = new File(recordPath);
        File newRecordVid = new File(desired);
        recordingVid.renameTo(newRecordVid);
    }


    /**
     * Given the directory and the prefix of the file, it will find
     * the matching file and returns to the user.
     *
     * @param directory
     * @param prefix
     * @return
     */
    public static File findFileAt(String directory, String prefix) {
        File dir = new File(directory);
        File[] files = dir.listFiles();
        for (File file : files) {
            String fileName = file.getName();
            if(fileName.startsWith(prefix)) {
                return file;
            }
        }//for
        return null;
    }
}//end::class