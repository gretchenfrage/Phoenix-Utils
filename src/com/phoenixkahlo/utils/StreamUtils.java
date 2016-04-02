package com.phoenixkahlo.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * A static class for reading and writing data from streams.
 * @author <a href="mailto:kahlo.phoenix@gmail.com">Phoenix Kahlo</a>
 */
public class StreamUtils {

	/**
	 * Writes the byte array to the OutputStream, preceded by an integer signifying the length of the array.
	 * Symmetrical to readByteArray.
	 * @param array the array to by written to out
	 * @param out the OutputStream to be written to
	 * @throws IOException if out throws an IOException
	 * @see com.phoenixkahlo.utils.StreamUtils#readByteArray(InputStream)
	 */
	public static void writeByteArray(byte[] array, OutputStream out) throws IOException {
		out.write(intToBytes(array.length));
		out.write(array);
	}
	
	/**
	 * Reads a byte array from the InputStream, preceded by an integer signifying the length of the array.
	 * Symmetrical to writeByteArray.
	 * @param in in InputStream to read from
	 * @return the byte array read from in
	 * @throws IOException if in throws an IOException
	 * @see com.phoenixkahlo.utils.StreamUtils#writeByteArray(byte[], OutputStream)
	 */
	public static byte[] readByteArray(InputStream in) throws IOException {
		byte[] head = new byte[4];
		in.read(head);
		byte[] body = new byte[bytesToInt(head)];
		in.read(body);
		return body;
	}
	
	/**
	 * Writes the String to the OutputStream, preceded by an integer signifying the length of the String in bytes.
	 * Symmetrical to readString.
	 * @param string the String to by written to out
	 * @param out the OutputStream to be written to
	 * @throws IOException if out throws an IOException
	 * @see com.phoenixkahlo.utils.StreamUtils#readString(InputStream)
	 */
	public static void writeString(String string, OutputStream out) throws IOException {
		writeByteArray(stringToBytes(string), out);
	}
	
	/**
	 * Reads a String from the InputStream, preceded by an integer signifying the length of the String in bytes.
	 * Symmetrical to writeString.
	 * @param in in InputStream to read from
	 * @return the String read from in
	 * @throws IOException if in throws an IOException
	 * @see com.phoenixkahlo.utils.StreamUtils#writeString(String, OutputStream)
	 */
	public static String readString(InputStream in) throws IOException {
		return bytesToString(readByteArray(in));
	}
	
	/**
	 * Writes the int to the OutputStream.
	 * Symmetrical to readInt.
	 * @param n the int to write to out
	 * @param out the OutputStream to write to
	 * @throws IOException if out throws an IOException
	 * @see com.phoenixkahlo.utils.StreamUtils#readInt(InputStream)
	 */
	public static void writeInt(int n, OutputStream out) throws IOException {
		out.write(intToBytes(n));
	}
	
	/**
	 * Reads the int from the InputStream.
	 * Symmetrical to writeInt.
	 * @param in the InputStream to read from
	 * @return the int read from in
	 * @throws IOException if in throws an IOException
	 * @see com.phoenixkahlo.utils.StreamUtils#writeInt(int, OutputStream)
	 */
	public static int readInt(InputStream in) throws IOException {
		byte[] bytes = new byte[4];
		in.read(bytes);
		return bytesToInt(bytes);
	}
	
	/**
	 * Writes the long to the OutputStream.
	 * Symmetrical to readLong.
	 * @param n the long to write to out
	 * @param out the OutputStream to write to
	 * @throws IOException if out throws an IOException
	 * @see com.phoenixkahlo.utils.StreamUtils#readLong(InputStream)
	 */
	public static void writeLong(long n, OutputStream out) throws IOException {
		out.write(longToBytes(n));
	}
	
	/**
	 * Reads the long from the InputStream.
	 * Symmetrical to writeLong.
	 * @param in the InputStream to read from
	 * @return the long read from in
	 * @throws IOException if in throws an IOException
	 * @see com.phoenixkahlo.utils.StreamUtils#writeLong(long, OutputStream)
	 */
	public static long readLong(InputStream in) throws IOException {
		byte[] bytes = new byte[8];
		in.read(bytes);
		return bytesToLong(bytes);
	}
	
	/**
	 * Writes the double to the OutputStream.
	 * Symmetrical to readDouble.
	 * @param n the double to write to out
	 * @param out the OutputStream to write to
	 * @throws IOException if out throws an IOException
	 * @see com.phoenixkahlo.utils.StreamUtils#readDouble(InputStream)
	 */
	public static void writeDouble(double n, OutputStream out) throws IOException {
		out.write(doubleToBytes(n));
	}
	
	/**
	 * Reads the double from the InputStream.
	 * Symmetrical to writeDouble.
	 * @param in the InputStream to read from
	 * @return the double read from in
	 * @throws IOException if in throws an IOException
	 * @see com.phoenixkahlo.utils.StreamUtils#writeDouble(double, OutputStream)
	 */
	public static double readDouble(InputStream in) throws IOException {
		byte[] bytes = new byte[8];
		in.read(bytes);
		return bytesToDouble(bytes);
	}
	
	/**
	 * Writes the float to the OutputStream.
	 * Symmetrical to readFloat.
	 * @param n the float to write to out
	 * @param out the OutputStream to write to
	 * @throws IOException if out throws an IOException
	 * @see com.phoenixkahlo.utils.StreamUtils#readFloat(InputStream)
	 */
	public static void writeFloat(float n, OutputStream out) throws IOException {
		out.write(floatToBytes(n));
	}
	
	/**
	 * Reads the float from the InputStream.
	 * Symmetrical to writeFloat.
	 * @param in the InputStream to read from
	 * @return the float read from in
	 * @throws IOException if in throws an IOException
	 * @see com.phoenixkahlo.utils.StreamUtils#writeFloat(float, OutputStream)
	 */
	public static float readFloat(InputStream in) throws IOException {
		byte[] bytes = new byte[4];
		in.read(bytes);
		return bytesToFloat(bytes);
	}
	
	/**
	 * Writes the short to the OutputStream.
	 * Symmetrical to readShort.
	 * @param n the short to write to out
	 * @param out the OutputStream to write to
	 * @throws IOException if out throws an IOException
	 * @see com.phoenixkahlo.utils.StreamUtils#readShort(InputStream)
	 */
	public static void writeShort(short n, OutputStream out) throws IOException {
		out.write(shortToBytes(n));
	}
	
	/**
	 * Reads the short from the InputStream.
	 * Symmetrical to writeShort.
	 * @param in the InputStream to read from
	 * @return the short read from in
	 * @throws IOException if in throws an IOException
	 * @see com.phoenixkahlo.utils.StreamUtils#writeShort(short, OutputStream)
	 */
	public static short readShort(InputStream in) throws IOException {
		byte[] bytes = new byte[2];
		in.read(bytes);
		return bytesToShort(bytes);
	}
	
	/**
	 * Writes the char to the OutputStream.
	 * Symmetrical to readChar.
	 * @param c the char to write to out
	 * @param out the OutputStream to write to
	 * @throws IOException if out throws an IOException
	 * @see com.phoenixkahlo.utils.StreamUtils#readChar(InputStream)
	 */
	public static void writeChar(char c, OutputStream out) throws IOException {
		out.write(charToBytes(c));
	}
	
	/**
	 * Reads the char from the InputStream.
	 * Symmetrical to writeChar.
	 * @param in the InputStream to read from
	 * @return the char read from in
	 * @throws IOException if in throws an IOException
	 * @see com.phoenixkahlo.utils.StreamUtils#writeChar(char, OutputStream)
	 */
	public static char readChar(InputStream in) throws IOException {
		byte[] bytes = new byte[2];
		in.read(bytes);
		return bytesToChar(bytes);
	}
	
	/**
	 * Writes the boolean to the OutputStream.
	 * Symmetrical to readBoolean.
	 * @param b the boolean to write to out
	 * @param out the OutputStream to write to
	 * @throws IOException if out throws an IOException
	 * @see com.phoenixkahlo.utils.StreamUtils#readBoolean(InputStream)
	 */
	public static void writeBoolean(boolean b, OutputStream out) throws IOException {
		out.write(b ? 1 : 0);
	}
	
	/**
	 * Reads the boolean from the InputStream.
	 * Symmetrical to writeBoolean.
	 * @param in the InputStream to read from
	 * @return the boolean read from in
	 * @throws IOException if in throws an IOException
	 * @see com.phoenixkahlo.utils.StreamUtils#writeBoolean(boolean, OutputStream)
	 */
	public static boolean readBoolean(InputStream in) throws IOException {
		return in.read() != 0;
	}
	
	/**
	 * Converts an int to an array of 4 bytes
	 * @param n the int to convert to bytes
	 * @return the byte array representation of n
	 */
	public static byte[] intToBytes(int n) {
		return ByteBuffer.allocate(4).putInt(n).array();
	}
	
	/**
	 * Converts an array of 4 bytes to an int
	 * @param bytes the bytes to convert to an int
	 * @return the int interpretation of bytes
	 */
	public static int bytesToInt(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getInt();
	}
	
	/**
	 * Converts a long to an array of 8 bytes
	 * @param n the long to convert to bytes
	 * @return the byte array representation of n
	 */
	public static byte[] longToBytes(long n) {
		return ByteBuffer.allocate(4).putDouble(n).array();
	}
	
	/**
	 * Converts an array of 8 bytes to a long
	 * @param bytes the bytes to convert to a long
	 * @return the long interpretation of bytes
	 */
	public static long bytesToLong(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getLong();
	}
	
	/**
	 * Converts a double to an array of 8 bytes
	 * @param n the double to convert to bytes
	 * @return the byte array representation of n
	 */
	public static byte[] doubleToBytes(double n) {
		return ByteBuffer.allocate(8).putDouble(n).array();
	}
	
	/**
	 * Converts an array of 8 bytes to a double
	 * @param bytes the bytes to convert to a double
	 * @return the double interpretation of bytes
	 */
	public static double bytesToDouble(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getDouble();
	}
	
	/**
	 * Converts a float to an array of 4 bytes
	 * @param n the float to convert to bytes
	 * @return the byte array representation of n
	 */
	public static byte[] floatToBytes(float n) {
		return ByteBuffer.allocate(4).putFloat(n).array();
	}
	
	/**
	 * Converts an array of 4 bytes to a float
	 * @param bytes the bytes to convert to a float
	 * @return the float interpretation of bytes
	 */
	public static float bytesToFloat(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getFloat();
	}
	
	/**
	 * Converts a short to an array of 2 bytes
	 * @param n the short to convert to bytes
	 * @return the byte array representation of n
	 */
	public static byte[] shortToBytes(short n) {
		return ByteBuffer.allocate(2).putShort(n).array();
	}
	
	/**
	 * Converts an array of 2 bytes to a short
	 * @param bytes the bytes to convert to a short
	 * @return the short interpretation of bytes
	 */
	public static short bytesToShort(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getShort();
	}
	
	/**
	 * Converts a char to an array of 2 bytes
	 * @param c the char to convert to bytes
	 * @return the byte array representation of c
	 */
	public static byte[] charToBytes(char c) {
		return ByteBuffer.allocate(2).putChar(c).array();
	}
	
	/**
	 * Converts an array of 2 bytes to a char
	 * @param bytes the bytes to convert to a char
	 * @return the char interpretation of bytes
	 */
	public static char bytesToChar(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getChar();
	}
	
	/**
	 * Converts a String to an array of bytes by UTF 8 standards
	 * @param string the String to convert to bytes
	 * @return the byte array representation of string
	 */
	public static byte[] stringToBytes(String string) {
    	return string.getBytes(StandardCharsets.UTF_8);
    }
    
	/**
	 * Converts an array of bytes to a String by UTF 8 standards
	 * @param bytes to bytes to convert to a String
	 * @return the String interpretation of bytes
	 */
	public static String bytesToString(byte[] bytes) {
    	return new String(bytes, StandardCharsets.UTF_8);
    }
	
}
