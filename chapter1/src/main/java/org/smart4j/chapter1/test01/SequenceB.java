package org.smart4j.chapter1.test01;

/**
 * @author yuanlai
 * @date 2014-04-06
 */
public class SequenceB implements Sequence{

	private static ThreadLocal<Integer> numberContainer = new ThreadLocal<Integer>(){
		@Override
		protected Integer initialValue() {
			return 0;
		}
	};

	@Override
	public int getNumber() {
		numberContainer.set(numberContainer.get()+1);
		return numberContainer.get();
	}

	public static void main(String[] args) {
		Sequence sequence = new SequenceB();

		ClientThread t1 = new ClientThread(sequence);
		ClientThread t2 = new ClientThread(sequence);
		ClientThread t3 = new ClientThread(sequence);

		t1.start();
		t2.start();
		t3.start();

	}
}
