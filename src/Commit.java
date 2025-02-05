import java.io.IOException;

public class Commit {
    private int id;
    private String message;
    private String fileName;
    private String fileContent;

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    public String getFileName() {
        return fileName;
    }

    private void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileContent() {
        return fileContent;
    }

    private void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public Commit(int id, String message, String fileName, String fileContent) {
        this.id = id;
        this.message = message;
        this.fileName = fileName;
        this.fileContent = fileContent;
    }
}
