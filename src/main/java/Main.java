import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

//public class Main {
//    public static void main(String[] args) {
//        String[] strings = {"adw", "omar", "Ali", "khaled","heigh", "no", "except", "koo"};
//        NSpace hash = new NSpace(8);
//        hash.init(strings);
//        System.out.println("hash.search(\"Ali\") = " + hash.search("Ali"));
//        System.out.println("hash.search(\"omar\") = " + hash.search("omar"));
//        System.out.println("hash.delete(\"Ali\") = " + hash.delete("Ali"));
//        System.out.println("hash.delete(\"omar\") = " + hash.delete("omar"));
//        System.out.println("hash.search(\"Ali\") = " + hash.search("Ali"));
//        System.out.println("hash.search(\"omar\") = " + hash.search("omar"));
//    }
//}
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String space = "\n\n\n\n\n\n";
        Scanner sc = new Scanner(System.in);
        String filePath;
        int count;
        int size, hashChoice;

        UniversalHashing hash;
        while (true){
            while (true){
                System.out.println(space+"Enter The Desired Method: \n1. O(N^2) Solution\n\n2. O(N) Solution");
                try{
                    hashChoice = Integer.parseInt(sc.nextLine());
                    break;
                }catch (Exception e){
                    System.out.println(space+"Invalid Input");
                }
            }// Get Hash Type
            while (true){
                System.out.println("Enter The Size: ");
                try{
                    size = Integer.parseInt(sc.nextLine());
                    break;
                }catch (Exception e){
                    System.out.println(space+"Invalid Input");
                }
            }// Get Hash Size
            if((hash = getHashType(hashChoice, size)) == null){
                System.out.println(space+"Enter Only 1 Or 2!!");
                continue;
            }
            boolean working = true;
            while(working){
                System.out.println("Choose:\n1. insert\n2. delete\n3. search\n4. batch insert\n5. batch delete\n6. return");
                int choice;
                try {
                    choice = Integer.parseInt((sc.nextLine()));
                } catch (NumberFormatException e) {
                    System.out.println(space+"Invalid Input!!");
                    continue;
                }
                switch (choice){
                    case 1:
                        while (true){
                            System.out.println("\nEnter Your Insert Word: \t*Enter -1 to leave*");
                            String element = sc.nextLine();
                            if(element.equals("-1"))
                                break;
                            System.out.println(space + "Status: "+hash.insert(element));
                        }
                        break;
                    case 2:
                        while (true){
                            System.out.println("\nEnter Your Delete Word: \t*Enter -1 to leave*");
                            String element = sc.nextLine();
                            if(element.equals("-1"))
                                break;
                            System.out.println(space+"Status: "+hash.delete(element));
                        }
                        break;
                    case 3:
                        while (true){

                            System.out.println("\nEnter Your Search Word: \t*Enter -1 to leave*");
                            String element = sc.nextLine();
                            if(element.equals("-1"))
                                break;
                            System.out.println(space+"Status: "+hash.search(element));
                        }
                        break;
                    case 4:
                        System.out.println("Enter File Path: ");
                        filePath = sc.nextLine();
                        try {
                            count = hash.batchInsert(filePath);
                        }catch (Exception e){
                            System.out.println(space+"Invalid File Path!!");
                            break;
                        }
                        System.out.println(space+"Successful = " + count + " n = " + hash.getN() + ", rehash count = " + hash.getNumberOfRehashing());
                        break;
                    case 5:
                        System.out.println("Enter File Path: ");
                        filePath = sc.nextLine();
                        try {
                            count = hash.batchDelete(filePath);
                        }catch (Exception e){
                            System.out.println(space+"Invalid File Path!!");
                            break;
                        }
                        System.out.println(space+"Successful = " + count + " n = " + hash.getN());
                        break;
                    case 6:

                        working = false;
                        break;
                }
            }
        }
    }
    public static UniversalHashing getHashType(int choice, int size){
        if (choice == 1)
            return new NSquareSpace(size);
        if(choice == 2)
            return new NSpace(size);
        return null;
    }
}
