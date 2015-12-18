/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Pinto
 */
public class ListHandler {
    // The name of the file where the global list of files will be stored
    private static final String GLOBAL_LIST_FILENAME = "globalList";
    private static final String ASK_PATH = "Pasta: ";
    private static final String PATH_FILENAME = "path";
    
    /** Makes a nicely formatted list of the globalList.
     * 
     * @return A String containing a nicely formatted list of the globalList
     * file.
     */
    public static String globalToString() {
        String stringGlobalList = null;
        String[][] globalList;
        try {
            globalList = getGlobalFileList();
            for (int i = 0; i < globalList.length; i++) {
                if (stringGlobalList == null) {
                    stringGlobalList = globalList[i][0] + " | " + globalList[i][1];
                } else {
                    stringGlobalList = 
                        stringGlobalList + "\n" +
                        globalList[i][0] + " | " + globalList[i][1];    
                }
            }
        } catch (IOException | ClassNotFoundException ex) {}
        return stringGlobalList;
    }
    
    /** Retrieves the local file list.
     * 
     * @return  A string array containing all the files from this system.
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     */
    public String[] getFileList() throws IOException, ClassNotFoundException {
        String[] localFileList;
        String path = getPath();

        
        File folder = new File(path);
        File[] pathList = folder.listFiles();
        localFileList = new String[folder.listFiles().length];
        
        for (int i = 0; i < pathList.length; i++) {
            if (pathList[i].isFile()) {
                localFileList[i] = pathList[i].getName();
            }
        }
        return localFileList;
    }
    
    /** Asks the user to set the folder to be watched for files.
     * 
     * @throws java.io.IOException
     */
    public void setPath() throws IOException {
        System.out.println(ASK_PATH);
        Scanner scan = new Scanner(System.in);
        setPath(scan.nextLine());
        //scan.close();
    }
    
    /** Gets the path as a string.
     * 
     * @return String containing the path.
     * @throws IOException Unable to open path file.
     * @throws ClassNotFoundException Path file didn't contain a string as
     * expected.
     */
    public String getPath() throws IOException, ClassNotFoundException {        
        ObjectInputStream ois = new ObjectInputStream(
            new FileInputStream(PATH_FILENAME));
        String path = (String)ois.readObject();
        ois.close();
        
        return path;
    }
    
    /** Sets the path of the folder to be worked on.
     * 
     * @param path - The path of the folder.
     */
    private void setPath(String path) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(
            new FileOutputStream(PATH_FILENAME));
        oos.writeObject(path);
        oos.close();
    }
    
    /** Adds a list of files to the global list of files.
     * 
     * If there's any file with the same name, it assumes the file is the same
     * and it doesn't add that one to the global list.
     * 
     * @param list List to be added.
     * @param ip IP address of the system where the files from the list are.
     * @throws IOException Error setting globalList file.
     * @throws ClassNotFoundException Class found could not be unserialized.
     */
    public static void addToGlobalFileList(String[] list, String ip) throws IOException, ClassNotFoundException {
        String[][] currentGlobalList = getGlobalFileList();
        String[][] newGlobalList = null;
        boolean found;
        ArrayList diff = new ArrayList();
        for (int i = 0; i < list.length; i++) {
            found = false;
            for (int j = 0; j < currentGlobalList.length; j++) {
                if (!list[i].equals(currentGlobalList[j][0])) {
                    found = true;
                }
            }
            if (found == true) {
                diff.add(list[i]);
            }
        }
        if (!diff.isEmpty()) {
            newGlobalList = 
                    new String[currentGlobalList.length + diff.size()][2];
            
            // Copiar da antiga para a nova global list
            for (int i = 0; i < currentGlobalList.length; i++) {
                newGlobalList[i][0] = currentGlobalList[i][0];
                newGlobalList[i][1] = currentGlobalList[i][1];
            }
            int z = 0;
            for (int i = currentGlobalList.length; i < newGlobalList.length; i++) {
                newGlobalList[i][0] = (String)diff.get(z);
                newGlobalList[i][1] = ip;
                z++;
            }
        } else {
            newGlobalList = currentGlobalList;
        }        
        setGlobalFileList(newGlobalList);
    }
    
    /** Sets the globalList file as the globalList parameter provided.
     * 
     * @param globalList The global list of files containing a matrix of 
     * files where the first column is the name of the file and the second is 
     * the IP address where that file is located.
     * @throws IOException Error opening file to write.
     */
    public static void setGlobalFileList(String[][] globalList) throws IOException {
        ObjectOutputStream ooS = new ObjectOutputStream(
            new FileOutputStream(GLOBAL_LIST_FILENAME));
        
        ooS.writeObject(globalList);
        ooS.close();
    }
    
    /** Copies the localFileList to the globalFileList.
     * 
     * @throws java.io.IOException Error opening file to write.
     */
    public void localToGlobalFileList() throws IOException, ClassNotFoundException {
        ObjectOutputStream ooS = new ObjectOutputStream(
            new FileOutputStream(GLOBAL_LIST_FILENAME));
        String[][] globalList = new String[getFileList().length][2];
        
        // globalFileList logic
        for (int i = 0; i < getFileList().length; i++) {
            globalList[i][0] = getFileList()[i];
            //globalList[i][1] = Util.GetIp();
            globalList[i][1] = "172.20.129.42";
        }
        
        ooS.writeObject(globalList);
        ooS.close();
    }

    /** Gets the global file list matrix from the file.
     * 
     * @return A matrix representing the global file list.
     * @throws IOException Couldn't open file.
     * @throws ClassNotFoundException If object found in the file could not be
     * unserialized.
     */
    public static String[][] getGlobalFileList() throws IOException, ClassNotFoundException {
        ObjectInputStream oiS = new ObjectInputStream(
            new FileInputStream(GLOBAL_LIST_FILENAME));
        String[][] globalList = (String[][])oiS.readObject();
        oiS.close();
        
        return globalList;
    }

    /** Transforms the localFileList into a transferable byte array.
     * 
     * @return byte[] containing the fileList
     * @throws IOException 
     * @throws java.lang.ClassNotFoundException 
     */
    public byte[] getByteFileList() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream baoS = new ByteArrayOutputStream(5000);
        ObjectOutputStream ooS = new ObjectOutputStream(new BufferedOutputStream(baoS));
        ooS.flush();
        ooS.writeObject(getFileList());
        ooS.flush();
        return baoS.toByteArray();
    }
}
