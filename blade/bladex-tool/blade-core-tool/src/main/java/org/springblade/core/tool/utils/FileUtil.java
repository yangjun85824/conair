/**
 * BladeX Commercial License Agreement
 * Copyright (c) 2018-2099, https://bladex.cn. All rights reserved.
 * <p>
 * Use of this software is governed by the Commercial License Agreement
 * obtained after purchasing a license from BladeX.
 * <p>
 * 1. This software is for development use only under a valid license
 * from BladeX.
 * <p>
 * 2. Redistribution of this software's source code to any third party
 * without a commercial license is strictly prohibited.
 * <p>
 * 3. Licensees may copyright their own code but cannot use segments
 * from this software for such purposes. Copyright of this software
 * remains with BladeX.
 * <p>
 * Using this software signifies agreement to this License, and the software
 * must not be used for illegal purposes.
 * <p>
 * THIS SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY. The author is
 * not liable for any claims arising from secondary or illegal development.
 * <p>
 * Author: DreamLu (596392912@qq.com)
 */

package org.springblade.core.tool.utils;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件工具类
 *
 * @author L.cm
 */
public class FileUtil extends org.springframework.util.FileCopyUtils {

	/**
	 * 默认为true
	 *
	 * @author L.cm
	 */
	public static class TrueFilter implements FileFilter, Serializable {
		private static final long serialVersionUID = -6420452043795072619L;

		public final static TrueFilter TRUE = new TrueFilter();

		@Override
		public boolean accept(File pathname) {
			return true;
		}
	}

	/**
	 * 扫描目录下的文件
	 *
	 * @param path 路径
	 * @return 文件集合
	 */
	public static List<File> list(String path) {
		File file = new File(path);
		return list(file, TrueFilter.TRUE);
	}

	/**
	 * 扫描目录下的文件
	 *
	 * @param path   路径
	 * @param fileNamePattern 文件名 * 号
	 * @return 文件集合
	 */
	public static List<File> list(String path, final String fileNamePattern) {
		File file = new File(path);
		return list(file, pathname -> {
			String fileName = pathname.getName();
			return PatternMatchUtils.simpleMatch(fileNamePattern, fileName);
		});
	}

	/**
	 * 扫描目录下的文件
	 *
	 * @param path   路径
	 * @param filter 文件过滤
	 * @return 文件集合
	 */
	public static List<File> list(String path, FileFilter filter) {
		File file = new File(path);
		return list(file, filter);
	}

	/**
	 * 扫描目录下的文件
	 *
	 * @param file 文件
	 * @return 文件集合
	 */
	public static List<File> list(File file) {
		List<File> fileList = new ArrayList<>();
		return list(file, fileList, TrueFilter.TRUE);
	}

	/**
	 * 扫描目录下的文件
	 *
	 * @param file   文件
	 * @param fileNamePattern Spring AntPathMatcher 规则
	 * @return 文件集合
	 */
	public static List<File> list(File file, final String fileNamePattern) {
		List<File> fileList = new ArrayList<>();
		return list(file, fileList, pathname -> {
			String fileName = pathname.getName();
			return PatternMatchUtils.simpleMatch(fileNamePattern, fileName);
		});
	}

	/**
	 * 扫描目录下的文件
	 *
	 * @param file   文件
	 * @param filter 文件过滤
	 * @return 文件集合
	 */
	public static List<File> list(File file, FileFilter filter) {
		List<File> fileList = new ArrayList<>();
		return list(file, fileList, filter);
	}

	/**
	 * 扫描目录下的文件
	 *
	 * @param file   文件
	 * @param filter 文件过滤
	 * @return 文件集合
	 */
	private static List<File> list(File file, List<File> fileList, FileFilter filter) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			if (files != null) {
				for (File f : files) {
					list(f, fileList, filter);
				}
			}
		} else {
			// 过滤文件
			boolean accept = filter.accept(file);
			if (file.exists() && accept) {
				fileList.add(file);
			}
		}
		return fileList;
	}

	/**
	 * 获取文件后缀名
	 * @param fullName 文件全名
	 * @return {String}
	 */
	public static String getFileExtension(String fullName) {
		if (StringUtil.isBlank(fullName)) return StringPool.EMPTY;
		String fileName = new File(fullName).getName();
		int dotIndex = fileName.lastIndexOf(CharPool.DOT);
		return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
	}

	/**
	 * 获取文件名，去除后缀名
	 * @param fullName 文件全名
	 * @return {String}
	 */
	public static String getNameWithoutExtension(String fullName) {
		if (StringUtil.isBlank(fullName)) return StringPool.EMPTY;
		String fileName = new File(fullName).getName();
		int dotIndex = fileName.lastIndexOf(CharPool.DOT);
		return (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);
	}

	/**
	 * Returns the path to the system temporary directory.
	 *
	 * @return the path to the system temporary directory.
	 */
	public static String getTempDirPath() {
		return System.getProperty("java.io.tmpdir");
	}

	/**
	 * Returns a {@link File} representing the system temporary directory.
	 *
	 * @return the system temporary directory.
	 */
	public static File getTempDir() {
		return new File(getTempDirPath());
	}

	/**
	 * Reads the contents of a file into a String.
	 * The file is always closed.
	 *
	 * @param file the file to read, must not be {@code null}
	 * @return the file contents, never {@code null}
	 */
	public static String readToString(final File file) {
		return readToString(file, Charsets.UTF_8);
	}

	/**
	 * Reads the contents of a file into a String.
	 * The file is always closed.
	 *
	 * @param file     the file to read, must not be {@code null}
	 * @param encoding the encoding to use, {@code null} means platform default
	 * @return the file contents, never {@code null}
	 */
	public static String readToString(final File file, final Charset encoding) {
		try (InputStream in = Files.newInputStream(file.toPath())) {
			return IoUtil.readToString(in, encoding);
		} catch (IOException e) {
			throw Exceptions.unchecked(e);
		}
	}

	/**
	 * Reads the contents of a file into a String.
	 * The file is always closed.
	 *
	 * @param file     the file to read, must not be {@code null}
	 * @return the file contents, never {@code null}
	 */
	public static byte[] readToByteArray(final File file) {
		try (InputStream in = Files.newInputStream(file.toPath())) {
			return IoUtil.readToByteArray(in);
		} catch (IOException e) {
			throw Exceptions.unchecked(e);
		}
	}

	/**
	 * Writes a String to a file creating the file if it does not exist.
	 *
	 * @param file the file to write
	 * @param data the content to write to the file
	 */
	public static void writeToFile(final File file, final String data) {
		writeToFile(file, data, Charsets.UTF_8, false);
	}

	/**
	 * Writes a String to a file creating the file if it does not exist.
	 *
	 * @param file   the file to write
	 * @param data   the content to write to the file
	 * @param append if {@code true}, then the String will be added to the
	 *               end of the file rather than overwriting
	 */
	public static void writeToFile(final File file, final String data, final boolean append){
		writeToFile(file, data, Charsets.UTF_8, append);
	}

	/**
	 * Writes a String to a file creating the file if it does not exist.
	 *
	 * @param file     the file to write
	 * @param data     the content to write to the file
	 * @param encoding the encoding to use, {@code null} means platform default
	 */
	public static void writeToFile(final File file, final String data, final Charset encoding) {
		writeToFile(file, data, encoding, false);
	}

	/**
	 * Writes a String to a file creating the file if it does not exist.
	 *
	 * @param file     the file to write
	 * @param data     the content to write to the file
	 * @param encoding the encoding to use, {@code null} means platform default
	 * @param append   if {@code true}, then the String will be added to the
	 *                 end of the file rather than overwriting
	 */
	public static void writeToFile(final File file, final String data, final Charset encoding, final boolean append) {
		try (OutputStream out = new FileOutputStream(file, append)) {
			IoUtil.write(data, out, encoding);
		} catch (IOException e) {
			throw Exceptions.unchecked(e);
		}
	}

	/**
	 * 转成file
	 * @param multipartFile MultipartFile
	 * @param file File
	 */
	public static void toFile(MultipartFile multipartFile, final File file) {
		try {
			FileUtil.toFile(multipartFile.getInputStream(), file);
		} catch (IOException e) {
			throw Exceptions.unchecked(e);
		}
	}

	/**
	 * 转成file
	 * @param in InputStream
	 * @param file File
	 */
	public static void toFile(InputStream in, final File file) {
		try (OutputStream out = new FileOutputStream(file)) {
			FileUtil.copy(in, out);
		} catch (IOException e) {
			throw Exceptions.unchecked(e);
		}
	}

	/**
	 * Moves a file.
	 * <p>
	 * When the destination file is on another file system, do a "copy and delete".
	 *
	 * @param srcFile  the file to be moved
	 * @param destFile the destination file
	 * @throws NullPointerException if source or destination is {@code null}
	 * @throws IOException          if source or destination is invalid
	 * @throws IOException          if an IO error occurs moving the file
	 */
	public static void moveFile(final File srcFile, final File destFile) throws IOException {
		Assert.notNull(srcFile, "Source must not be null");
		Assert.notNull(destFile, "Destination must not be null");
		if (!srcFile.exists()) {
			throw new FileNotFoundException("Source '" + srcFile + "' does not exist");
		}
		if (srcFile.isDirectory()) {
			throw new IOException("Source '" + srcFile + "' is a directory");
		}
		if (destFile.exists()) {
			throw new IOException("Destination '" + destFile + "' already exists");
		}
		if (destFile.isDirectory()) {
			throw new IOException("Destination '" + destFile + "' is a directory");
		}
		final boolean rename = srcFile.renameTo(destFile);
		if (!rename) {
			FileUtil.copy(srcFile, destFile);
			if (!srcFile.delete()) {
				FileUtil.deleteQuietly(destFile);
				throw new IOException("Failed to delete original file '" + srcFile + "' after copy to '" + destFile + "'");
			}
		}
	}

	/**
	 * Deletes a file, never throwing an exception. If file is a directory, delete it and all sub-directories.
	 * <p>
	 * The difference between File.delete() and this method are:
	 * <ul>
	 * <li>A directory to be deleted does not have to be empty.</li>
	 * <li>No exceptions are thrown when a file or directory cannot be deleted.</li>
	 * </ul>
	 *
	 * @param file file or directory to delete, can be {@code null}
	 * @return {@code true} if the file or directory was deleted, otherwise
	 * {@code false}
	 */
	public static boolean deleteQuietly(@Nullable final File file) {
		if (file == null) {
			return false;
		}
		try {
			if (file.isDirectory()) {
				FileSystemUtils.deleteRecursively(file);
			}
		} catch (final Exception ignored) {
		}

		try {
			return file.delete();
		} catch (final Exception ignored) {
			return false;
		}
	}

}
