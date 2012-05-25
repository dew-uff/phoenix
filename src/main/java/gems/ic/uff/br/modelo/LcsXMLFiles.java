/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gems.ic.uff.br.modelo;

import gems.ic.uff.br.Exception.LcsXMLFilesException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author gmenezes
 */
public class LcsXMLFiles {

    private String xml1;
    private String xml2;
    private float[][] similarityMatrix;
    private File[] files;

    public LcsXMLFiles(String xml1, String xml2) {
        this.xml1 = xml1;
        this.xml2 = xml2;
        similarityMatrix = null;
        files = null;
    }

    public LcsXMLFiles(String xml1) {
        this.xml1 = xml1;
    }

    private String FileToString(String path) throws FileNotFoundException, IOException {

        FileInputStream fis = new FileInputStream(path);
        DataInputStream dis = new DataInputStream(fis);

        BufferedReader br = new BufferedReader(new InputStreamReader(dis));

        String strLine;
        String sb = "";

        while ((strLine = br.readLine()) != null) {
            sb += strLine;
        }

        return sb;
    }

    private float similarityFile() throws FileNotFoundException, IOException {

        XML from = new XML(xml1);
        XML to = new XML(xml2);

        LcsXML lcs = new LcsXML(from, to);

        return lcs.similaridade();
    }

    private File[] selectingXML(File[] fileList) {

        List<File> aux = new ArrayList<File>();


        for (File file : fileList) {
            if (file.getAbsolutePath().endsWith(".xml")) {
                aux.add(file);
            }
        }

        File[] result = new File[aux.size()];

        int i = 0;

        for (File file : aux) {
            result[i++] = file;
        }
        return result;
    }

    public float similarityFile(String path1, String path2) throws FileNotFoundException, IOException {

        XML from = new XML(path1);
        XML to = new XML(path2);

        LcsXML lcs = new LcsXML(from, to);

        return lcs.similaridade();
    }

    public List<File> validateDirectory() {

        List<File> wrong = new ArrayList<File>();

        for (int i = 0; i < files.length; ++i) {
            try {
                similarityFile(files[i].getAbsolutePath(), files[i].getAbsolutePath());
            } catch (Exception ex) {
                wrong.add(files[i]);
                Logger.getLogger(LcsXMLFiles.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return wrong;
    }

    //method that returns the similarity of files in a directory
    public void similarityDirectory() throws FileNotFoundException, IOException, LcsXMLFilesException {



        File directory = new File(xml1);

        if (!directory.isDirectory()) {
            return;
        }

//        String[] list = directory.list();


        files = selectingXML(directory.listFiles());

        //begin validating xml files
        List<File> validateDirectory = validateDirectory();

        if (!validateDirectory.isEmpty()) {
            LcsXMLFilesException e = new LcsXMLFilesException();
            e.setFiles(validateDirectory);
            throw e;
        }
        //end validating xml files


        similarityMatrix = new float[files.length][files.length];

        for (int i = 0; i < files.length; ++i) {
            similarityMatrix[i][i] = 1;
            for (int j = i + 1; j < files.length; j++) {
                System.out.println("---------------------------------------------------------------------------");
                System.out.println(files[i].getAbsolutePath() + "   <-->   " + files[j].getAbsolutePath());
                similarityMatrix[i][j] = similarityFile(files[i].getAbsolutePath(), files[j].getAbsolutePath());
                similarityMatrix[j][i] = similarityMatrix[i][j];


            }
        }


    }

    public File[] getFiles() {
        return files;
    }

    public void setFiles(File[] files) {
        this.files = files;
    }

    public float[][] getSimilarityMatrix() {
        return similarityMatrix;
    }

    public void setSimilarityMatrix(float[][] similarityMatrix) {
        this.similarityMatrix = similarityMatrix;
    }

    public String getXml1() {
        return xml1;
    }

    public void setXml1(String xml1) {
        this.xml1 = xml1;
    }

    public String getXml2() {
        return xml2;
    }

    public void setXml2(String xml2) {
        this.xml2 = xml2;
    }
}
