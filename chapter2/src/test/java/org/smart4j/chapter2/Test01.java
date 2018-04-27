package org.smart4j.chapter2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author yuanlai
 * @date 2014-03-29
 */
public class Test01 {
	public static void main(String[] args) {
		int age = 20;
		String name = "xinput";
		Person p = new Person(age, name);
		Person p1 = p;
		Person p2 = new Person(age, name);
		System.out.println(p);
		System.out.println(p1);
		System.out.println(p2);

		List<String> lists = new ArrayList<>();
		lists.add("a");
		lists.add("b");
		lists.add("c");
		lists.add("d");
		System.out.println(lists);
		a(lists);
		System.out.println(lists);
	}

	private static void a(List<String> list){
		list.remove(1);
	}
}
