/*
 * Copyright (C) 2012 The Serval Project
 *
 * This file is part of the Serval Mesh Sampler
 *
 * Serval Mesh Sampler is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This source code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this source code; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package org.servalproject.sampler.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.text.TextUtils;

/**
 * a variety of file related utility methods
 */
public class FileUtils {
	
	/**
	 * tests to see if the given path is a directory and can be written to
	 * 
	 * @param path the full path to test
	 * @return true if the path is a directory and be be written to
	 */
	public static boolean isDirectoryWritable(String path) {
		
		if(TextUtils.isEmpty(path) == true) {
			throw new IllegalArgumentException("the path parameter is required");
		}
		
		File mPath = new File(path);
		
		if(mPath.isDirectory() && mPath.canWrite()) {
			return true;
		} else {
			return mPath.mkdirs();
		}
	}
	
	/**
	 * tests to see if the given path is a file and is readable
	 * 
	 * @param path the full path to test
	 * @return true if the path is a file and is readable
	 */
	public static boolean isFileReadable(String path) {
		
		if(TextUtils.isEmpty(path) == true) {
			throw new IllegalArgumentException("the path parameter is required");
		}
		
		File mFile = new File(path);
		
		if(mFile.isFile() && mFile.canRead()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * copies a file into a directory
	 * 
	 * @param inputStream an inputStream to the source data
	 * @param outputFile name of the destination file
	 * @param outputPath path to the destination directory
	 * @return the full path of the destination file
	 * @throws IOException 
	 */
	public static String copyFileToDir(InputStream inputStream, String outputPath, String outputFile) throws IOException {
		
		// check the parameters
		if(TextUtils.isEmpty(outputPath) == true) {
			throw new IllegalArgumentException("the outputPath paramter is required");
		}
		
		if(TextUtils.isEmpty(outputFile) == true) {
			throw new IllegalArgumentException("the outputFile parameter is required");
		}
		
		if(isDirectoryWritable(outputPath) == false) {
			throw new IOException("unable to access the destination directory");
		}
		
		if(outputPath.endsWith(File.separator) == false) {
			outputPath = outputPath + File.separator;
		}
		
		String mFilePath = outputPath + outputFile;
		
		OutputStream mOutputStream = new FileOutputStream(mFilePath);
		
		byte[] mBuffer = new byte[1024];
	    int mRead;
	    while((mRead = inputStream.read(mBuffer)) != -1){
	    	mOutputStream.write(mBuffer, 0, mRead);
	    }
	    
	    mOutputStream.close();
		
		return mFilePath;
	}

}
