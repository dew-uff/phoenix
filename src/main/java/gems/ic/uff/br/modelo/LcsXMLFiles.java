/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gems.ic.uff.br.modelo;

import java.io.*;

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

    public float similarityFile(String path1, String path2) throws FileNotFoundException, IOException {

        XML from = new XML(path1);
        XML to = new XML(path2);

        LcsXML lcs = new LcsXML(from, to);

        return lcs.similaridade();
    }

    //method that returns the similarity of files in a directory
    public void similarityDirectory() throws FileNotFoundException, IOException {

        

        File directory = new File(xml1);

        if (!directory.isDirectory()) {
            return;
        }

//        String[] list = directory.list();

        files = directory.listFiles();

        similarityMatrix = new float[files.length][files.length];

        for (int i = 0; i < files.length; ++i) {
            for (int j = 0; j < files.length; j++) {

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
