/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gems.ic.uff.br.modelo;

import org.junit.Test;

import gems.ic.uff.br.Exception.LcsXMLFilesException;
import gems.ic.uff.br.modelo.LcsXMLFiles;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 
 * @author gmenezes
 */
public class LcsXMLFilesTest {

    @Test
    public void testingFiles() throws FileNotFoundException, IOException,
            LcsXMLFilesException {

        LcsXMLFiles lcs = new LcsXMLFiles("resources/test");

        // System.out.println((new File(".")).getAbsolutePath());
        lcs.similarityDirectory();

        float[][] similarityMatrix = lcs.getSimilarityMatrix();

        for (float[] fs : similarityMatrix) {
            for (float f : fs) {
                System.out.print(f + " ");
            }

            System.out.println("");
        }

        File[] files = lcs.getFiles();
        for (File file : files) {
            System.out.println(file.getAbsolutePath() + " ");
        }

    }
}
