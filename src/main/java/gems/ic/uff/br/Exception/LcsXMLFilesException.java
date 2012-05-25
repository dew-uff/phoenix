/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gems.ic.uff.br.Exception;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author GEMS
 */
public class LcsXMLFilesException extends Exception {
    
    List<File> files;

    public LcsXMLFilesException() {
        files = new ArrayList<File>();
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    @Override
    public String getMessage() {
        String message;
        
        message = "Os seguintes arquivos estão inconsistentes:\n";
        for (File file : files) {
            message += file.getAbsolutePath();
            message += "\n";
        }
        
        return message;
    }

    
}
