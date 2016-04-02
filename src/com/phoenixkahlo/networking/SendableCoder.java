package com.phoenixkahlo.networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.phoenixkahlo.utils.StreamUtils;

/**
 * Correlates the applicable non-abstract Sendable types to integer headers, and is
 * responsible for reading and writing different types of Sendable object to and from streams.
 * @author <a href="mailto:kahlo.phoenix@gmail.com">Phoenix Kahlo</a>
 * @param <A> The class of clients that the Sendables are generic to
 * @param <B> The class of servers that the Sendables are generic to
 */
public class SendableCoder<A, B> {

	/**
	 * A type of Sendable registered within a SendableCoder.
	 * @author <a href="mailto:kahlo.phoenix@gmail.com">Phoenix Kahlo</a>
	 * @param <C> The class of clients that the Sendables are generic to 
	 * @param <D> The class of servers that the Sendables are generic to
	 */
	public static interface SendableCoderEntry<C, D> {

		/**
		 * Whether the Sendable if of the type of Sendable represented by this SendableCoderEntry.
		 * @param sendable the Sendable to check
		 * @return whether the Sendable is of the type of Sendable represented by this SendableCoderEntry
		 */
		boolean isType(Sendable<C, D> sendable);
		
		/**
		 * Creates the Sendable represented by this SendableCoderEntry from the InputStream.
		 * @param in the InputStream with which to create the Sendable
		 * @return the Sendable created from the InputStream
		 * @throws IOException if an IOException occurs in the creation of the Sendable
		 * @throws BadDataException if the data read is bad
		 */
		Sendable<C, D> create(InputStream in) throws IOException, BadDataException;
		
		/**
		 * Writes the Sendable object to the OutputStream.
		 * @param out the OutputStream to write the Sendable to
		 * @param sendable the Sendable to write
		 * @throws IOException if the OutputStream throws an IOException
		 */
		void write(OutputStream out, Sendable<C, D> sendable) throws IOException;
		
	}
	
	private Map<Integer, SendableCoderEntry<A, B>> entries = new HashMap<Integer, SendableCoderEntry<A, B>>();
	
	/**
	 * Registers the SendableCoderEntry with this SendableCoder to ensure that that type of Sendable can be
	 * read and written with this coder. Negative IDs are reserved.
	 * @param id the id with which to register the entry
	 * @param entry the entry to register
	 * @throws RuntimeException if there is already an entry registered with that id, or if the id is below 0.
	 */
	public void register(int id, SendableCoderEntry<A, B> entry) throws RuntimeException {
		if (entries.containsKey(id) || id < 0)
			throw new RuntimeException("Duplicate sendable headers");
		else
			entries.put(id, entry);
	}
	
	/**
	 * Reads a Sendable from the InputStream.
	 * @param in the InputStream from which to read the Sendable
	 * @return the Sendable decoded from the InputStream
	 * @throws IOException if the InputStream throws an IOException
	 * @throws BadDataException if the header is not linked to a type, if the creation of the Sendable
	 * throws a BadDataException
	 */
	public Sendable<A, B> read(InputStream in) throws IOException, BadDataException {
		int id = StreamUtils.readInt(in);
		if (entries.containsKey(id)) {
			return entries.get(id).create(in);
		} else {
			throw new IOException("Header " + id + " not registered");
		}
	}
	
	/**
	 * Writes the Sendable to the OutputStream
	 * @param out the OutputStream to write to
	 * @param sendable the Sendable to write to the OutputStream
	 * @throws IOException if the OutputStream throws an IOException
	 * @throws RuntimeException if the Sendable is not accepted by any of the registered types
	 */
	public void write(OutputStream out, Sendable<A, B> sendable) throws IOException, RuntimeException {
		for (Map.Entry<Integer, SendableCoderEntry<A, B>> entry : entries.entrySet()) {
			if (entry.getValue().isType(sendable)) {
				StreamUtils.writeInt(entry.getKey(), out);
				entry.getValue().write(out, sendable);
				return;
			}
		}
		throw new RuntimeException("Sendable " + sendable + " not registered");
	}

	/**
	 * Registers a type of Sendables using a class object. Sendables are of this type if their Class
	 * is the Class that was registered. Sendables of this type are constructed by calling the class's
	 * constructor of the format<br>
	 * {@code public Sendable(InputStream in) throws IOException}.<br>
	 * Sendables of this type are written by calling the 
	 * @param clazz the Class of Sendables to register
	 * @param id the id with which to register this entry
	 * @throws RuntimeException if there is already an entry registered with that id, if the Class
	 * doesn't have the correct kind of constructor
	 */
	public void register(int id, Class<? extends Sendable<A, B>> clazz) throws RuntimeException {
		try {
			Constructor<? extends Sendable<A, B>> constructor = clazz.getConstructor(InputStream.class);
			register(id, new SendableCoderEntry<A, B>() {
				
				@Override
				public boolean isType(Sendable<A, B> sendable) {
					return sendable.getClass() == clazz;
				}
				
				@Override
				public Sendable<A, B> create(InputStream in) throws IOException {
					try {
						return constructor.newInstance(in);
					} catch (InvocationTargetException e) {
						throw (IOException) e.getTargetException();
					} catch (InstantiationException | IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
						throw new RuntimeException("Sendable class invalidly designed for reflection SendableCoderEntry");
					}
				}

				@Override
				public void write(OutputStream out, Sendable<A, B> sendable) throws IOException {
					try {
						clazz.getMethod("write", OutputStream.class).invoke(sendable, out);
					} catch (InvocationTargetException e) {
						throw (IOException) e.getTargetException();
					} catch (IllegalAccessException | IllegalArgumentException | SecurityException
							| NoSuchMethodException e) {
						e.printStackTrace();
						throw new RuntimeException("Sendable class invalidly designed for reflection SendableCoderEntry");
					}
				}
				
			});
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new RuntimeException("Sendable class invalidly designed for reflection SendableCoderEntry");
		}
	}
	
	/**
	 * Registers a singleton Sendable. Sendables are of this type if they are the same object registered.
	 * Sendables of this type are created by returning the singleton. Sendables of this type have no content
	 * writing, and so the write method of the singleton will not be called.
	 * @param id the id with which to register this entry
	 * @param singleton the singleton Sendable
	 * @throws RuntimeException if there is already an entry registered with that id
	 */
	public void register(int id, Sendable<A, B> singleton) throws RuntimeException {
		register(id, new SendableCoderEntry<A, B>() {
			
			@Override
			public boolean isType(Sendable<A, B> sendable) {
				return sendable == singleton;
			}
			
			@Override
			public Sendable<A, B> create(InputStream in) {
				return singleton;
			}

			@Override
			public void write(OutputStream out, Sendable<A, B> sendable) throws IOException {}
			
		});
	}
	
	/**
	 * Checks if a Sendable is of the type of Sendable represented by this object. Designed to be used with
	 * {@code com.phoenixkahlo.networking.SendableCoder.register(int id, sendableTypeChecker<A, B> typeChecker, SendableFactory<A, B> factory>)
	 * throws RuntimeException}.
	 * @author <a href="mailto:kahlo.phoenix@gmail.com">Phoenix Kahlo</a>
	 * @param <A> The class of clients that the Sendables are generic to
	 * @param <B> The class of servers that the Sendables are generic to
	 * @see com.phoenixkahlo.networking.SendableCoder#register(int, SendableTypeChecker, SendableFactory)
	 */
	public static interface SendableTypeChecker<B, C> {
		
		boolean isType(Sendable<B, C> sendable);
		
	}
	
	/**
	 * Creates a Sendable from the InputStream or throws an IOException. Designed to be used with
	 * {@code com.phoenixkahlo.networking.SendableCoder.register(int id, sendableTypeChecker<A, B> typeChecker, SendableFactory<A, B> factory>)
	 * throws RuntimeException}.
	 * @author <a href="mailto:kahlo.phoenix@gmail.com">Phoenix Kahlo</a>
	 * @param <A> The class of clients that the Sendables are generic to
	 * @param <B> The class of servers that the Sendables are generic to
	 * @see com.phoenixkahlo.networking.SendableCoder#register(int, SendableTypeChecker, SendableFactory)
	 */
	public static interface SendableFactory<B, C> {
		
		Sendable<B, C> create(InputStream in) throws IOException;
		
	}
	
	/**
	 * Writes a Sendable to the OutputStream. Designed to be used with
	 * {@code com.phoenixkahlo.networking.SendableCoder.register(int id, sendableTypeChecker<A, B> typeChecker, SendableFactory<A, B> factory>)
	 * throws RuntimeException}.
	 * @author <a href="mailto:kahlo.phoenix@gmail.com">Phoenix Kahlo</a>
	 * @param <A> The class of clients that the Sendables are generic to
	 * @param <B> The class of servers that the Sendables are generic to
	 * @see com.phoenixkahlo.networking.SendableCoder#register(int, SendableTypeChecker, SendableFactory)
	 */
	public static interface SendableWriter<B, C> {
		void write(OutputStream out, Sendable<B, C> sendable);
	}
	
	/**
	 * Registers a type of Sendables using a SendableFactory and a SendableTypeChecker. Exists to make
	 * use of lambda statements.
	 * @param id the id with which to register this entry
	 * @param typeChecker the TypeChecker with which to check if a Sendable is of this type
	 * @param factory the SendableFactory with which to produce the Sendables represented by this type
	 * @throws RuntimeException if there is already an entry registered with that id
	 */
	public void register(int id, SendableTypeChecker<A, B> typeChecker, SendableFactory<A, B> factory,
			SendableWriter<A, B> writer) throws RuntimeException {
		register(id, new SendableCoderEntry<A, B>() {
			
			@Override
			public boolean isType(Sendable<A, B> sendable) {
				return typeChecker.isType(sendable);
			}
			
			@Override
			public Sendable<A, B> create(InputStream in) throws IOException {
				return factory.create(in);
			}

			@Override
			public void write(OutputStream out, Sendable<A, B> sendable) throws IOException {
				writer.write(out, sendable);
			}
			
		});
	}
	
}
