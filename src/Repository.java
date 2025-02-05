import java.io.*;
import java.util.List;
import java.util.Stack;

public class Repository implements Serializable{
    public Repository(String name, List<String> usernames) {
        this.name = name;
        this.usernames = usernames;
    }

    public String getName() {
        return name + ".repo";
    }

    public List<String> getUsernames() {
        return usernames;
    }

    private String name;
    Stack<Commit> commits;

    private List<String> usernames;

    public void commit(Commit newCommit) {
        commits.push(newCommit);
    }

    public void revert(){
        commits.pop();
    }

    public void push() throws IOException {
        for(Commit commit : commits){
            saveFiles(commit);
        }
    }

    private void saveFiles(Commit commit) throws IOException {
        String fileName = commit.getFileName();
        String fileContent = commit.getFileContent();
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))){
            writer.write(fileContent);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
