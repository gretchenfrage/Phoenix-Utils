package com.phoenixkahlo.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * A bidirectional, symmetrical map in which lookups can be performed from either side.
 * @author <a href="mailto:kahlo.phoenix@gmail.com">Phoenix Kahlo</a>
 * @param <A> the type for one side of the map
 * @param <B> the type for one side of the map
 */
public class BiMap<A, B> {

	private class Pair {
		A a;
		B b;
		Pair(A a, B b) {
			this.a = a;
			this.b = b;
		}
	}
	
	private List<Pair> pairs = new ArrayList<Pair>();
	
	/**
	 * Links a and b, such that getB(a) will return b and getA(b) will return a
	 * @param a the object to link to b
	 * @param b the object to link to a
	 * @throws RuntimeException if the linkage would force an invalid state, eg. if objects equal to
	 * a and b both exist in the map and are not linked together
	 */
	public void link(A a, B b) throws RuntimeException {
		for (Pair pair : pairs) {
			if (pair.a.equals(a)) {
				// a exists
				if (pair.b.equals(b)) {
					// a and b both exist and are linked together
					return;
				} else {
					// a exists and is not linked to b, if b exists
					for (Pair pair2 : pairs) {
						if (pair2.b.equals(b)) {
							// a and b both exist and are not linked together
							throw new RuntimeException("Duplicate items");
						}
					}
					// a exists and b does not exist
					pair.b = b;
					return;
				}
			}
		}
		// a does not exist
		for (Pair pair : pairs) {
			if (pair.b.equals(b)) {
				// a does not exist but b does
				pair.a = a;
				return;
			}
		}
		// neither a nor b exist
		pairs.add(new Pair(a, b));
	}
	
	/**
	 * Looks up the A linked to b
	 * @param b the item by which to look up the A
	 * @return the A linked to b, or null if b is not present
	 */
	public A getA(B b) {
		for (Pair pair : pairs) {
			if (pair.b.equals(b)) return pair.a;
		}
		return null;
	}
	
	/**
	 * Looks up the B linked to a
	 * @param a the item by which to look up the B
	 * @return the B linked to a, or null if a is not present
	 */
	public B getB(A a) {
		for (Pair pair : pairs) {
			if (pair.a.equals(a)) return pair.b;
		}
		return null;
	}
	
	/**
	 * Returns if contains the A value
	 * @param a the value to look for
	 * @return if a is present in the A side
	 */
	public boolean containsA(A a) {
		for (Pair pair : pairs) {
			if (pair.a.equals(a)) return true;
		}
		return false;
	}
	
	/**
	 * Returns if contains the B value
	 * @param b the value to look for
	 * @return if b is present in the B side
	 */
	public boolean containsB(B b) {
		for (Pair pair : pairs) {
			if (pair.b.equals(b)) return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		String out = "[";
		for (int i = 0; i < pairs.size(); i++) {
			out += pairs.get(i).a + ":" + pairs.get(i).b;
			if (i < pairs.size() - 1) out += ", ";
		}
		out += "]";
		return out;
	}
	
}
