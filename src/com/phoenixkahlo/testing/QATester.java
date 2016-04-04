package com.phoenixkahlo.testing;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.phoenixkahlo.networking.BadDataException;

public class QATester {

	public static void main(String[] args) throws IOException, BadDataException {
		QACoder coder = new QACoder();
		QAQuestion question = new QAQuestion("why");
		QAAnswer answer = new QAAnswer("because");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		coder.write(out, question);
		coder.write(out, answer);
		
		byte[] data = out.toByteArray();
		
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		QAQuestion question2 = (QAQuestion) coder.read(in);
		QAAnswer answer2 = (QAAnswer) coder.read(in);
		System.out.println(question2);
		System.out.println(answer2);
	}

}
