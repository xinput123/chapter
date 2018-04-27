package org.smart4j.chapter1.test01;

/**
 * @author yuanlai
 * @date 2014-04-06
 */
public class SequenceA implements Sequence{

	private static int number = 0;

	@Override
	public int getNumber() {
		number = number + 1;
		return number;
	}

	public static void main(String[] args) {
		Sequence sequence = new SequenceA();

		ClientThread t1 = new ClientThread(sequence);
		ClientThread t2 = new ClientThread(sequence);
		ClientThread t3 = new ClientThread(sequence);

		t1.start();
		t2.start();
		t3.start();

	}
}
