import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Server {
    private ServerSocket server;

    private Map<String, Object> repoLocks;

    private int id = 0;

    public Server() {
        repoLocks = new HashMap<String, Object>();
        addRepo("HristoUni.repo");
        addRepo("HristoSchool.repo");
        addRepo("MamaWork.repo");
    }

    public void addRepo(String repoName) {
        if(new File(repoName).exists())
            return;
        repoLocks.put(repoName, new Object());
    }

    public void start(){
        try{
            System.out.println("Server listening...");
            server = new ServerSocket(8080);
            while (true){
                Socket client = server.accept();

                Thread clientThread = new Thread(() -> {
                    System.out.println("Client connected");
                    Scanner sc = null;
                    PrintStream out = null;

                    try{
                        sc = new Scanner(client.getInputStream());
                        out = new PrintStream(client.getOutputStream());
                        userMenu(sc, out);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                clientThread.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Repository loadRepository(String name)
            throws IOException {
        final String repoName =
                name + ".repo";

        try(ObjectInputStream in =
                new ObjectInputStream(new FileInputStream(repoName))){
            return (Repository) in.readObject();
        }catch (IOException ex){
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    private void userMenu(Scanner sc, PrintStream out) throws IOException {
        while(true){
            out.println("Type the name of " +
                    "the repository you want to use");
            String repoName = sc.nextLine();

            Repository repo = loadRepository(repoName);

            out.println("Functions EDIT" +
                    " | REMOVE LAST COMMIT " +
                    "| SAVE CHANGES");

            String command = sc.nextLine();
            switch (command.toUpperCase()){
                case "EDIT":
                {
                    out.println("Type file name");
                    String filename = sc.nextLine();
                    edit(repo, filename, sc, out);
                }
                case "REMOVE LAST COMMIT":
                {
                    removeCommit(repo, sc, out);
                }
                case "SAVE CHANGES":
                {
                    saveChanges(repo, sc, out);
                }
            }
        }
    }

    private void edit(Repository repo,
                      String fileName,
                      Scanner sc,
                      PrintStream out) throws IOException {
        Editor editor = new Editor();
        String filePath = repo.getName() + "/" + fileName;

        String command;
        synchronized (repoLocks.get(repo.getName())) {
            try {
                editor.read(filePath);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            out.println("INSERT(offset, string) " +
                    "| DELETE(start, end)");
            command = sc.nextLine();
            switch (command.toUpperCase()) {
                case "INSERT": {
                    out.println("Offset");
                    int offset = Integer.parseInt(sc.nextLine());
                    out.println("String");
                    String string = sc.nextLine();
                    editor.insert(offset, string);
                    break;
                }
                case "DELETE": {
                    out.println("Start");
                    int start = Integer.parseInt(sc.nextLine());
                    out.println("End");
                    int end = Integer.parseInt(sc.nextLine());
                    editor.delete(start, end);
                    break;
                }
            }

            try {
                editor.write(filePath);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        Commit commit = new Commit(id++,
                command,
                filePath,
                editor.getInternalBuffer().toString());

        repo.commit(commit);
    }

    private void removeCommit(Repository repo,
                              Scanner sc,
                              PrintStream out) throws IOException {
        synchronized (repoLocks.get(repo.getName())) {
            repo.revert();
        }
        out.println("Commit Removed");
    }

    private void saveChanges(Repository repo,
                             Scanner sc,
                             PrintStream out) throws IOException {
        synchronized (repoLocks.get(repo.getName())) {
            repo.push();
            out.println("Files saved successfully");
        }
    }
}
