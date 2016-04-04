package com.phoenixkahlo.testing;

import com.phoenixkahlo.networking.SendableCoder;

public class QACoder extends SendableCoder<QAClient, QAServer> {

	public QACoder() {
		register(1, QAQuestion.class);
		register(2, QAAnswer.class);
	}
	
}
