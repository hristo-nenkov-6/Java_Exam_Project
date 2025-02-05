import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Socket server = null;
        Scanner console = null;
        Scanner sc = null;
        PrintStream out = null;

        try{
            server = new Socket("localhost", 8080);

            console = new Scanner(System.in);

            sc = new Scanner(server.getInputStream());
            out = new PrintStream(server.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally
        {
            if (server != null)
            {
                try
                {
                    server.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (console != null)
                console.close();
            if (sc != null)
                sc.close();
        }
    }
}
