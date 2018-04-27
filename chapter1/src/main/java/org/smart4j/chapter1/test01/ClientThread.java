package org.smart4j.chapter1.test01;

/**
 * @author yuanlai
 * @date 2014-04-06
 */
public class ClientThread extends Thread {

	private Sequence sequence;

	public ClientThread(Sequence sequence){
		this.sequence = sequence;
	}

	@Override
	public void run() {
		for(int i=0; i<3; i++){
			System.out.println(Thread.currentThread().getName() + " => " + sequence.getNumber());
		}
	}
}
