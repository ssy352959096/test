package com.aurora.util;

import java.math.BigInteger;

import org.junit.Test;

/**
 * 描述: 权限计算帮助类 创建: BYG 2017/5/24 修改:
 * 
 * @version 1.0
 */
public class RightsHelper {
	/**
	 * 利用BigInteger对权限进行2的权的和计算
	 * 
	 * @param rights
	 *            int型权限编码数组
	 * @return 2的权的和
	 */
	public static BigInteger sumRights(int[] rights) {
		BigInteger num = new BigInteger("0");
		for (int i = 0; i < rights.length; i++) {
			num = num.setBit(rights[i]);
		}
		return num;
	}

	/**
	 * 利用BigInteger对权限进行2的权的和计算
	 * 
	 * @param rights
	 *            String型权限编码数组
	 * @return 2的权的和
	 */
	public static BigInteger sumRights(String[] rights) {
		BigInteger num = new BigInteger("0");
		for (int i = 0; i < rights.length; i++) {//有权限的话这个位置就是1;
			num = num.setBit(Integer.parseInt(rights[i]));
		}
		return num;
	}

	/**
	 * 测试
	 */
	@Test
	public void test2() {
		// 计算和
		BigInteger num = new BigInteger("3");

		// 将1左移n位，与this做&运算，其实就是判断当前数(要写成二进制)第n+1位上的数是不是为1，是的话返回true
		System.out.println(num.testBit(1));

		num = num.setBit(1);// 原来的num+(1向左位移n个位置,也就是2^n)

		System.out.println(num);

		num = num.setBit(3);// 3+(1<<3=8)=11
		System.out.println(num);

		int sum = (int) (Math.pow(2, 1) + Math.pow(0, 2) + Math.pow(0, 3));// Math.pow(x,y)
																			// x底数,y幂数;
		System.out.println(sum);

	}

	/**
	 * 测试是否具有指定编码的权限
	 * 
	 * @param sum
	 * @param targetRights
	 * @return
	 */
	public static boolean testRights(BigInteger sum, int targetRights) {
		return sum.testBit(targetRights);// 将1左移n位，与this做&运算，其实就是判断当前数(要写成二进制)第n+1位上的数是不是为1，是的话返回true
	}

	/**
	 * 测试是否具有指定编码的权限
	 * 
	 * @param sum
	 * @param targetRights
	 * @return
	 */
	public static boolean testRights(String sum, int targetRights) {
		if (Tools.isEmpty(sum))
			return false;
		return testRights(new BigInteger(sum), targetRights);
	}

	/**
	 * 测试是否具有指定编码的权限
	 * 
	 * @param sum
	 * @param targetRights
	 * @return
	 */
	public static boolean testRights(String sum, String targetRights) {
		if (Tools.isEmpty(sum))
			return false;
		return testRights(new BigInteger(sum), targetRights);
	}

	/**
	 * 测试是否具有指定编码的权限
	 * 
	 * @param sum
	 * @param targetRights
	 * @return
	 */
	public static boolean testRights(BigInteger sum, String targetRights) {
		return testRights(sum, Integer.parseInt(targetRights));
	}
}
