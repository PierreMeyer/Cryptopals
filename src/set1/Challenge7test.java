package set1;

import org.junit.jupiter.api.Test;
import utilities.DataConverter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/*
    Set 1 Challenge 7: AES in ECB mode

    The Base64-encoded content in file7.txt has been encrypted via AES-128 in ECB mode under the key
    "YELLOW SUBMARINE"
    Decrypt it.

    Easiest way: use OpenSSL::Cipher and give it AES-128-ECB as the cipher.
*/
public class Challenge7test {

    @Test
    public void decryptFile() throws IOException {

        InputStream inputStream = new FileInputStream("src/set1/File7.txt");
        InputStreamReader isReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(isReader);

        OutputStream outputStream = new FileOutputStream("src/set1/File7Byte.txt");

        int n;
        String buffer ="";
        while((buffer = reader.readLine()) != null) {
            outputStream.write(DataConverter.base64TobyteArray(buffer));   // Don't allow any extra bytes to creep in, final write
        }

        inputStream.close();
        outputStream.close();

        String hexKey = DataConverter.byteArrayToHex("YELLOW SUBMARINE".getBytes(StandardCharsets.UTF_8));
        System.out.println(hexKey);
        Process externalProcess;

        String command="c:\\OpenSSL-Win64\\bin\\openssl.exe enc -d -AES-128-ECB"+
                " -in "+ System.getProperty("user.dir")+"\\src\\set1\\File7Byte.txt"+
                " -out "+ System.getProperty("user.dir")+"\\src\\set1\\File7Deciphered.txt"+
                " -K "+hexKey;
        Runtime runtime=Runtime.getRuntime();
        externalProcess = runtime.exec(command);
    }


}
