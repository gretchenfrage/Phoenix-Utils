package com.phoenixkahlo.testing;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.phoenixkahlo.networking.SimpleReply;
import com.phoenixkahlo.utils.StreamUtils;

public class QAAnswer extends SimpleReply<QAClient, QAServer> {

	private String answer;
	
	public QAAnswer(String answer) {
		this.answer = answer;
	}
	
	public QAAnswer(InputStream in) throws IOException {
		super(in);
		answer = StreamUtils.readString(in);
	}
	
	@Override
	public void write(OutputStream out) throws IOException {
		super.write(out);
		StreamUtils.writeString(answer, out);
	}
	
	@Override
	public void effectClient(QAClient connection) {
		System.out.println("Answer: " + answer);
	}

	@Override
	public void effectServer(QAServer connection) {}

	public String getAnswer() {
		return answer;
	}
	
}
