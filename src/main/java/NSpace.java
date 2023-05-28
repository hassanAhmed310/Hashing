import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class NSpace implements UniversalHashing {
    private NSquareSpace[] hash;
    final private int PRIME = 2_147_483_647;
    private int rehashCount = 0;
    private int a;
    private int b;
    private int m;
    int n;
    private int count = 0;
    boolean initialized = false;
    public NSpace(int size){
        this.m = size;
        randomizeAB();
        hash = new NSquareSpace[m];
        Arrays.fill(hash, null);
    }
    private void randomizeAB(){
        Random random = new Random();
        a = random.nextInt(1, PRIME);
        b = random.nextInt(1, PRIME);
    }
    private boolean checkFirstLevel(String[] strings){
        int[] ns = new int[m];
        Arrays.fill(ns, 0);
        Arrays.fill(hash, null);
        for (int i = 0; i < strings.length; i++) {
            if (strings[i] == null)
                continue;
            int key = getKey(strings[i]);
            ns[key]++;
        }
        int sum = Arrays.stream(ns).map((a) -> (int)Math.pow(a, 2)).sum();
        if (sum < 2 * m){
            count = insertAll(ns, strings);
//            System.out.println("Count = "+insertAll(ns, strings));
            return true;
        }
        else{
            randomizeAB();
            rehashCount++;
            return checkFirstLevel(strings);
        }
    }
    private int insertAll(int[] ns, String[] strings){
        int count = 0;
        for (int i = 0; i < ns.length; i++) {
            if(ns[i] != 0)
                hash[i] = new NSquareSpace((int)Math.pow(ns[i], 2));
        }
        for (String s :
                strings) {
            if(s == null)
                continue;
            if(insert(s))
                count++;
        }
        return count;
    }
    private int getKey(String element){
        int key = Math.abs(((a * element.hashCode() + b) % PRIME) % m);
        return key;
    }
    public boolean insert(String element) {
        int key = getKey(element);
        if(hash[key] == null){
            hash[key] = new NSquareSpace(10);
        }
        if(hash[key].search(element))
            return false;
        if(hash[key].insert(element)){
            n++;
            return true;
        }
        return false;
    }
    public int getN(){
        return n;
    }

    @Override
    public int getNumberOfRehashing() {
        return rehashCount;
    }

    public int batchInsert(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        int index = 0;
        String[] strings = new String[m];
        if(!initialized){
            while (scanner.hasNext())
                strings[index++] = scanner.nextLine();
            //C:\Users\Eng 02\Desktop\test\7247.txt
            checkFirstLevel(strings);
            initialized = true;
        }else {
            int count = 0;
            while (scanner.hasNext()) {
                if(insert(scanner.nextLine()))
                    this.count++;
            }
            scanner.close();
            return count;
        }
        scanner.close();
        return count;
    }

    public boolean delete(String element) {
        if(!search(element))
            return false;
        if (hash[getKey(element)].delete(element)){
            n--;
            return true;
        }
        return false;
    }

    public int batchDelete(String filePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filePath));
        int count = 0;
        while (scanner.hasNext()) {
            if(delete(scanner.nextLine()))
                count++;
        }
        scanner.close();
        return count;
    }

    public boolean search(String element) {
        int key = getKey(element);
        if(hash[key] != null && hash[key].search(element))
            return true;
        return false;
    }
}
