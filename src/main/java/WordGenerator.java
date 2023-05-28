import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;

public class WordGenerator {
    final int wordMaxSize = 10;
    String randomWord;
    public int createFile(String filePath, int wordCount){
        int count = 0;
        try {
            File myFile = new File(filePath);
            HashSet<String> hashSet = new HashSet<>();
            if(myFile.exists()){
                myFile.delete();
            }
            if (myFile.createNewFile()) {
                System.out.println("File created: " + myFile.getName() + " Size: " + myFile.length());
            } else {
                System.out.println("File already exists.");
            }
            FileWriter writer = new FileWriter(filePath);
            BufferedWriter buffer = new BufferedWriter(writer);
            randomWord = createRandomString(wordMaxSize);
            buffer.write(randomWord);
            buffer.newLine();
            if(hashSet.add(randomWord)){
                count++;
            };
            for(int i=1;i<wordCount;i++){
                String random = createRandomString(wordMaxSize);
                buffer.write(random);
                buffer.newLine();
                if(hashSet.add(random)){
                    count++;
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return -1;
        }
        return count;
    }
    public String createRandomString(int size) {
        // The string that we will return
        String rand = "";
        // The chars that are used to generate the random string
        // chars do not contain @ # $
        String chars = "1234567890-=!%^&*()_+qwertyuiop[]\\QWERTYUIOP{}|asdfghjkl;'ASDFGHJKL:\"zxcvbnm,./ZXCVBNM<>?";
        // Loop based on the requested size
        for (int i = 0; i < size; i++) {
            // Add a random char from the chars string to the rand string
            rand += chars.toCharArray()[new Random().nextInt(chars.length())];
        }
        // Return the random string
        return rand;
    }
    public String getRandomWord(){
        return this.randomWord;
    }
}
