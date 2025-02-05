import java.io.*;

public class Editor {
    public StringBuilder getInternalBuffer() {
        return internalBuffer;
    }

    private StringBuilder internalBuffer;

    public void read(String filePath)
        throws IOException {

        try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            if(line == null){
                internalBuffer = new StringBuilder();
                return;
            }
            while((line = br.readLine()) != null) {
                internalBuffer.append(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void insert(int offset, String str){
        internalBuffer.insert(offset, str);
    }

    public void delete(int start, int end){
        internalBuffer.delete(start, end - 1);
    }

    public void write(String filePath)
            throws IOException {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write(internalBuffer.toString());
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
