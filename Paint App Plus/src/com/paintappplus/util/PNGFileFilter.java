package com.paintappplus.util;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class PNGFileFilter extends FileFilter {
    public boolean accept(File file) {
        return file.getName().toLowerCase().endsWith(".png") || file.isDirectory();
    }
    
    public String getDescription() {
        return "PNG image  (*.png) ";
    }
}
