import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileWriter;

import static org.junit.jupiter.api.Assertions.*;
//use the following VM "-Xms2G -Xmx3G"
class N2TimeTest1 {
    static NSquareSpace table;
    static int size;
    static int distinctWords;
    static WordGenerator generator;
    static String path = "src\\test\\test1.txt";
    static int wordCount = 20000;
    static String existWord = "0Tbrit1[hM";
    static String nonExistWord = "0Tbrit1[hM$$";
    @BeforeAll
    static void init() throws FileNotFoundException {
//        generator = new WordGenerator();
//        distinctWords = generator.createFile(path, wordCount);
        table = new NSquareSpace(wordCount);
        table.batchInsert(path);
    }
    @Test
    void testSearchFound(){
//        var randomWord = generator.getRandomWord();
        Long start = System.nanoTime();
        assertTrue(table.search(existWord));
        Long end = System.nanoTime();
        System.out.println("Search for exist word >> Time = " + ((end-start)/1000) + " microsecond, Size = " + table.getN());
    }
    @Test
    void testSearchNotFound(){
//        var randomWord = generator.getRandomWord();
//        randomWord += "@#$";
        Long start = System.nanoTime();
        assertFalse(table.search(nonExistWord));
        Long end = System.nanoTime();
        System.out.println("Search for not exist word >> Time = " + ((end-start)/1000) + " microsecond, Size = " + table.getN());
    }
    @Test
    void testDelete(){
//        var randomWord = generator.getRandomWord();
        Long start = System.nanoTime();
        assertTrue(table.delete(existWord));
        Long end = System.nanoTime();
        System.out.println("Delete exist word >> Time = " + ((end-start)/1000) + " microsecond, Size = " + table.getN());
    }
    @Test
    void testInsertionTime(){
//        var randomWord = generator.getRandomWord();
//        randomWord += "@#$";
        Long start = System.nanoTime();
        assertTrue(table.insert(nonExistWord));
        Long end = System.nanoTime();
        System.out.println("Insert not exist word >> Time = " + ((end-start)/1000) + " microsecond, Size = " + table.getN());
    }
    @Test
    void testInsertionWithDuplicateTime(){
        table.insert(existWord);
//        var randomWord = generator.getRandomWord();
        Long start = System.nanoTime();
        assertFalse(table.insert(existWord));
        Long end = System.nanoTime();
        System.out.println("Insert exist word >> Time = " + ((end-start)/1000) + " microsecond, Size = " + table.getN());
    }
}