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
 * Author: Chill Zhuang (bladejava@qq.com)
 */
package org.springblade.core.tool.support;

/**
 * 图片操作类
 *
 * @author Chill
 */
public class ImagePosition {

	/**
	 * 图片顶部.
	 */
	public static final int TOP = 32;

	/**
	 * 图片中部.
	 */
	public static final int MIDDLE = 16;

	/**
	 * 图片底部.
	 */
	public static final int BOTTOM = 8;

	/**
	 * 图片左侧.
	 */
	public static final int LEFT = 4;

	/**
	 * 图片居中.
	 */
	public static final int CENTER = 2;

	/**
	 * 图片右侧.
	 */
	public static final int RIGHT = 1;

	/**
	 * 横向边距，靠左或靠右时和边界的距离.
	 */
	private static final int PADDING_HORI = 6;

	/**
	 * 纵向边距，靠上或靠底时和边界的距离.
	 */
	private static final int PADDING_VERT = 6;


	/**
	 * 图片中盒[左上角]的x坐标.
	 */
	private int boxPosX;

	/**
	 * 图片中盒[左上角]的y坐标.
	 */
	private int boxPosY;

	/**
	 * Instantiates a new image position.
	 *
	 * @param width     the width
	 * @param height    the height
	 * @param boxWidth  the box width
	 * @param boxHeight the box height
	 * @param style     the style
	 */
	public ImagePosition(int width, int height, int boxWidth, int boxHeight, int style) {
		switch (style & 7) {
			case LEFT:
				boxPosX = PADDING_HORI;
				break;
			case RIGHT:
				boxPosX = width - boxWidth - PADDING_HORI;
				break;
			case CENTER:
			default:
				boxPosX = (width - boxWidth) / 2;
		}
		switch (style >> 3 << 3) {
			case TOP:
				boxPosY = PADDING_VERT;
				break;
			case MIDDLE:
				boxPosY = (height - boxHeight) / 2;
				break;
			case BOTTOM:
			default:
				boxPosY = height - boxHeight - PADDING_VERT;
		}
	}


	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public int getX() {
		return getX(0);
	}

	/**
	 * Gets the x.
	 *
	 * @param x 横向偏移
	 * @return the x
	 */
	public int getX(int x) {
		return this.boxPosX + x;
	}

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public int getY() {
		return getY(0);
	}

	/**
	 * Gets the y.
	 *
	 * @param y 纵向偏移
	 * @return the y
	 */
	public int getY(int y) {
		return this.boxPosY + y;
	}

}
