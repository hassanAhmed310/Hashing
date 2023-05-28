import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class NSquareSpace implements UniversalHashing {
    final private int U = 32;
    private int size;
    final private int B;
    private int rehashCount = 0;
    private int m;
    private int[][] h;
    private String[] hash;
    private String[] temp;
    int n = 0;
    private boolean collision = false;
    public NSquareSpace(int size){
        this.size = size;
        this.B = (int)Math.ceil((Math.log(Math.pow(size, 2)) / Math.log(2)));
        this.m = (int)Math.pow(2, B);
//        System.out.println("B = " + B);
//        System.out.println("m = " + m);
        initHashFunction();
        this.hash = new String[m];
        Arrays.fill(this.hash, null);
    }
    public void initHashFunction(){
        Random random = new Random();
        h = new int[B][U];
        for (int i = 0; i < h.length; i++)
            for (int j = 0; j < h[0].length; j++)
                h[i][j] = random.nextInt(100) % 2;
    }

    private int getKey(String element){
        int key = Math.abs(element.hashCode());
//        System.out.println("key = " + key);
        int[] keyBits = convertToBits(key);
        int[] targetBits = new int[B];
        Arrays.fill(targetBits, 0);
        Random random = new Random();
        for (int i = 0; i < B; i++) {
            for (int j = 0; j < U; j++)
                targetBits[i] += h[i][j] * keyBits[j];
            targetBits[i] = targetBits[i]% 2;
        }
        return convertFromBits(targetBits);
    }
    private boolean handleCollision(){
        rehashCount++;
        initHashFunction();
        Arrays.fill(hash, null);
        for (int i = 0; i < temp.length; i++)
            if(temp[i] != null){
                if(!insert(temp[i])){
                    return false;
                }

            }
//        System.out.println("Created new Hash!!");
        return true;
    }
    private int[] convertToBits(int key){
        int[] bits = new int[U];
        Arrays.fill(bits, 0);
        for (int i = 0; i < U; i++) {
            bits[i] = key % 2;
            key = key >> 1;
        }
        return bits;
    }
    public int convertFromBits(int[] bits){
        int key = 0;
        for (int i = 0; i < bits.length; i++)
            if(bits[i] == 1)
                key += Math.pow(2, i);
        return key;
    }
    public boolean insert(String element) {
        if(element == null || element.length() == 0){
            System.out.println("Null Element Entered");
            return false;
        }
        int index = getKey(element);
//        System.out.println("index = " + index);
        if(hash[index]  == null) {
            hash[index] = element;
            n++;
            return true;
        }
        else {
            if(collision == true)
                return false;
            if(hash[index].equals(element)){
//                System.out.println("element = " + element + " " + hash[index]);
//                System.out.println("Element Already Exists!!");
                return false;
            }
            collision = true;
//            System.out.println("Collision at n = " + n);
            n = 0;
            temp = hash.clone();
            while (!handleCollision()){
                n = 0;
            }
            collision = false;
//            System.out.println("After collision n = " + n);
            return insert(element);
        }
    }

    public boolean delete(String element) {
        if(!search(element))
            return false;
        hash[getKey(element)] = null;
        n--;
        return true;
    }


    public boolean search(String element) {
        int index = getKey(element);
        if(hash[index] == null || !hash[index].equals(element)){
//            System.out.println("index = " + index);
//            System.out.println("Element Doesn't Exist!!");
            return false;
        }
        return true;
    }

    public int getN() {
        return n;
    }
    @Override
    public int getNumberOfRehashing() {
        return rehashCount;
    }
    public int batchInsert(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        int count = 0;
        while (scanner.hasNext()) {
//            if(scanner.nextLine().equals("h"))
//                count++;
//            if(list.contains(s)){
//                System.out.println(s + " is repeated");
//                continue;
//            }
            if(insert(scanner.nextLine())){
                count++;
            }
        }//C:\Users\Eng 02\Desktop\test\7247.txt
        //C:\Users\Eng 02\Desktop\test\500.txt
        System.out.println("count = " + count);
        scanner.close();
        return count;
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
}
