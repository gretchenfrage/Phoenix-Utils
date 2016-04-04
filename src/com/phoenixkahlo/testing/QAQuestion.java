package com.phoenixkahlo.testing;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

import com.phoenixkahlo.networking.SimpleRepliable;
import com.phoenixkahlo.utils.StreamUtils;

public class QAQuestion extends SimpleRepliable<QAClient, QAServer> {

	private String question;
	
	public QAQuestion(String question) {
		this.question = question;
	}
	
	public QAQuestion(InputStream in) throws IOException {
		super(in);
		question = StreamUtils.readString(in);
	}
	
	@Override
	public void write(OutputStream out) throws IOException {
		super.write(out);
		StreamUtils.writeString(question, out);
	}
	
	@Override
	public void effectClient(QAClient connection) {}

	@Override
	public void effectServer(QAServer connection) {
		System.out.println("Question: " + question);
		Scanner scanner = new Scanner(System.in);
		connection.sendReplyTo(new QAAnswer(scanner.nextLine()), this);
		scanner.close();
	}

}
