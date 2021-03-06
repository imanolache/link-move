package com.nhl.link.move.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.nhl.link.move.mapper.ByteArrayKeyAdapter;

public class ByteArrayKeyAdapterTest {
	
	@Test
	public void testToFrom() {
		ByteArrayKeyAdapter builder = new ByteArrayKeyAdapter();

		byte[] b1 = new byte[] { 1, 2 };
		Object k1 = builder.toMapKey(b1);
		assertFalse(k1.equals(b1));
		assertSame(b1, builder.fromMapKey(k1));
	}

	@Test
	public void testEquals() {
		ByteArrayKeyAdapter builder = new ByteArrayKeyAdapter();

		byte[] b1 = new byte[] { 1, 2 };
		assertTrue(builder.toMapKey(b1).equals(builder.toMapKey(b1)));

		byte[] b2 = new byte[] { 1, 2 };
		assertTrue(builder.toMapKey(b1).equals(builder.toMapKey(b2)));

		byte[] b3 = new byte[] { 1 };
		assertFalse(builder.toMapKey(b1).equals(builder.toMapKey(b3)));

		byte[] b4 = new byte[] {};
		assertFalse(builder.toMapKey(b1).equals(builder.toMapKey(b4)));
	}

	@Test
	public void testHashCode() {
		ByteArrayKeyAdapter builder = new ByteArrayKeyAdapter();

		byte[] b1 = new byte[] { 1, 2 };
		assertEquals(builder.toMapKey(b1).hashCode(), builder.toMapKey(b1).hashCode());

		byte[] b2 = new byte[] { 1, 2 };
		assertEquals(builder.toMapKey(b1).hashCode(), builder.toMapKey(b2).hashCode());

		byte[] b3 = new byte[] { 1 };
		assertFalse(builder.toMapKey(b1).hashCode() == builder.toMapKey(b3).hashCode());

		byte[] b4 = new byte[] {};
		assertFalse(builder.toMapKey(b1).hashCode() == builder.toMapKey(b4).hashCode());
	}

}
