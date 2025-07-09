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
package org.springblade.core.log4j2;

import lombok.extern.slf4j.Slf4j;

import java.io.PrintStream;
import java.util.Locale;

/**
 * 替换 系统 System.err 和 System.out 为log
 *
 * @author L.cm
 */
@Slf4j
public class LogPrintStream extends PrintStream {
	private final boolean error;

	private LogPrintStream(boolean error) {
		super(error ? System.err : System.out);
		this.error = error;
	}

	public static LogPrintStream out() {
		return new LogPrintStream(false);
	}

	public static LogPrintStream err() {
		return new LogPrintStream(true);
	}

	@Override
	public void print(String s) {
		if (error) {
			log.error(s);
		} else {
			log.info(s);
		}
	}

	/**
	 * 重写掉它，因为它会打印很多无用的新行
	 */
	@Override
	public void println() {
	}

	@Override
	public void println(String x) {
		if (error) {
			log.error(x);
		} else {
			log.info(x);
		}
	}

	@Override
	public PrintStream printf(String format, Object... args) {
		if (error) {
			log.error(String.format(format, args));
		} else {
			log.info(String.format(format, args));
		}
		return this;
	}

	@Override
	public PrintStream printf(Locale l, String format, Object... args) {
		if (error) {
			log.error(String.format(l, format, args));
		} else {
			log.info(String.format(l, format, args));
		}
		return this;
	}
}
