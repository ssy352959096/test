package com.aurora.util;

import java.util.UUID;
/**
 * 描述: 获得UUID
 * 创建: BYG 2017/5/25
 * 修改:
 * @version
 */

public class UuidUtil {

	public static String get32UUID() {
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
		return uuid;
	}
	public static void main(String[] args) {
		System.out.println(get32UUID());
	}
}

