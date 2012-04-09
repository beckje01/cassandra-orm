/*
 * Copyright 2012 ReachLocal Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */





package com.reachlocal.grails.plugins.cassandra.test

import org.apache.commons.codec.binary.Base64
import com.reachlocal.grails.plugins.cassandra.uuid.UuidDynamicMethods

/**
 * @author: Bob Florian
 */
class UuidDynamicMethodsIntegrationTests extends GroovyTestCase
{
	void testInteger_getBytes()
	{
		def bytes = Integer.MAX_VALUE.bytes
		println bytes
		
		assertEquals 4, bytes.size()
		assertEquals(127, bytes[0])
		assertEquals(-1, bytes[1])
		assertEquals(-1, bytes[2])
		assertEquals(-1, bytes[3])
	}

	void testLong_getBytes()
	{
		def bytes = Long.MAX_VALUE.bytes
		println bytes

		assertEquals 8, bytes.size()
		assertEquals(127, bytes[0])
		assertEquals(-1, bytes[1])
		assertEquals(-1, bytes[2])
		assertEquals(-1, bytes[3])
		assertEquals(-1, bytes[4])
		assertEquals(-1, bytes[5])
		assertEquals(-1, bytes[6])
		assertEquals(-1, bytes[7])
	}

	void testTimeUUID()
	{
		def u1 = UUID.timeUUID()
		Thread.sleep(10)
		def u2 = UUID.timeUUID()
		
		assertNotNull u1
		assertNotNull u2
		assertTrue u2 > u1
	}

	void testReverseTimeUUID()
	{
		def u1 = UUID.reverseTimeUUID()
		Thread.sleep(10)
		def u2 = UUID.reverseTimeUUID()

		assertNotNull u1
		assertNotNull u2
		assertTrue u2 < u1
	}
	
	void testFromBytes()
	{
		def list = (Long.MAX_VALUE.bytes as List) + (Long.MAX_VALUE.bytes as List) 
		def uuid = UUID.fromBytes(list as byte[])
		println uuid
		assertEquals "7fffffff-ffff-ffff-7fff-ffffffffffff", uuid.toString()
	}
	
	void testUUID_getBytes()
	{
		def bytes = UUID.randomUUID().bytes
		assertEquals 16, bytes.size()
	}

	void testUUID_toUrlSafeString()
	{
		def uuid = UUID.randomUUID()
		def string = uuid.toUrlSafeString()
		def bytes = Base64.decodeBase64(string)
		def u2 = UUID.fromBytes(bytes)
		println string
		assertEquals uuid, u2
	}
}