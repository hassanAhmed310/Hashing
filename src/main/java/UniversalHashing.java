import java.io.FileNotFoundException;

public interface UniversalHashing {
    boolean insert(String element);
    int batchInsert(String filePath) throws FileNotFoundException;
    boolean delete(String element);
    int batchDelete(String filePath) throws FileNotFoundException;
    boolean search(String element);
    int getN();
    int getNumberOfRehashing();
}
