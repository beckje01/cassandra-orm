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

import com.reachlocal.grails.plugins.cassandra.test.orm.User
import com.reachlocal.grails.plugins.cassandra.test.orm.UserGroup
import com.reachlocal.grails.plugins.cassandra.test.orm.UserGroupMeeting
import com.reachlocal.grails.plugins.cassandra.test.orm.UserGroupPost

/**
 * @author: Bob Florian
 */
class InstanceMethodTests extends OrmTestCase
{
	void testAll()
	{
		initialize()

		def userGroup = new UserGroup(
				uuid: "group1-zzzz-zzzz",
				name: "JUG")

		def userGroup2 = new UserGroup(
				uuid: "group2-zzzz-zzzz",
				name: "CUG")

		def user = new User(
				uuid: "user1-zzzz-zzzz",
				name: "Jane",
				phone:  "301-555-2222",
				city: "Reston",
				state:  "VA",
				gender:  "Female")

		def user2 = new User(
				uuid: "user2-zzzz-zzzz",
				name: "Jim",
				phone:  "301-555-1111",
				city: "Olney",
				state:  "MD",
				gender:  "Male")

		def user3 = new User(
				uuid: "user3-zzzz-zzzz",
				name: "Jill",
				phone:  "301-555-1111",
				city: "Olney",
				state:  "MD",
				gender:  "Female")

		def user4 = new User(
				uuid: "user4-zzzz-zzzz",
				name: "John",
				phone:  "301-555-1212",
				city: "Ellicott City",
				state:  "MD",
				gender:  "Male")

		def meeting1 = new UserGroupMeeting(date:  new Date())
		def meeting2 = new UserGroupMeeting(date:  new Date()+1)
		def meeting3 = new UserGroupMeeting(date:  new Date()+2)

		def post1 = new UserGroupPost(text: "Four score")
		def post2 = new UserGroupPost(text: "and seven years ago")

		println "\n--- getCassandra() ---"
		assertEquals client, user.cassandra
		persistence.printClear()

		println "\n--- getKeySpace() ---"
		assertEquals "mock", user.keySpace
		assertEquals "mockDefault", userGroup.keySpace
		persistence.printClear()

		println "\n--- getColumnFamily() ---"
		assertEquals "MockUser_CFO", user.columnFamily
		assertEquals "UserGroup_CFO", userGroup.columnFamily
		persistence.printClear()

		println "\n--- getIndexColumnFamily() ---"
		assertEquals "MockUser_IDX_CFO", user.indexColumnFamily
		assertEquals "UserGroup_IDX_CFO", userGroup.indexColumnFamily
		persistence.printClear()

		println "\n--- getId() ---"
		assertEquals "user1-zzzz-zzzz", user.id
		persistence.printClear()
		println user.id


		println "\n--- userGroup.save() ---"
		userGroup.save()
		persistence.printClear()
		println user.id

		println "\n--- userGroup.addToUsers(user) ---"
		userGroup.addToUsers(user)
		persistence.printClear()

		println "\n--- userGroup.addToUsers(user2) ---"
		userGroup.addToUsers(user2)
		persistence.printClear()

		println "\n--- userGroup.addToUsers(user3) ---"
		userGroup.addToUsers(user3)
		persistence.printClear()


		println "\n--- userGroup2.save() ---"
		userGroup2.save()
		persistence.printClear()
		println user.id

		println "\n--- userGroup2.addToUsers(user4) ---"
		userGroup2.addToUsers(user4)
		persistence.printClear()


		println "\n--- userGroup.users ---"
		def r = userGroup.users
		persistence.printClear()
		println r
		assertEquals 3, r.size()

		println "\n--- userGroup.users(max: 2) ---"
		r = userGroup.users(max: 2)
		persistence.printClear()
		println r
		assertEquals 2, r.size()

		println "\n--- userGroup.users(max: 50, column: 'name') ---"
		r = userGroup.users(max: 2, column: 'name') as List
		persistence.printClear()
		println r
		assertEquals 2, r.size()
		assertEquals "Jane", r[0]


		println "\n--- userGroup.users(columns: ['name','city']) ---"
		r = userGroup.users(columns: ['name','city']) as List
		persistence.printClear()
		println r
		assertEquals 3, r.size()
		assertEquals "Olney", r[-1].city
		assertNull r[-1].state

		println "\n--- userGroup.usersCount() ---"
		r = userGroup.usersCount()
		persistence.printClear()
		assertEquals 3, r

		println "\n--- userGroup.usersCount(start: 'user1', finish: 'user2') ---"
		r = userGroup.usersCount(start: 'user1', finish: 'user2')
		persistence.printClear()
		assertEquals 2, r

		println "\n--- userGroup.removeFromUsers(user) ---"
		userGroup.removeFromUsers(user)
		persistence.printClear()

		println "\n--- userGroup.usersCount() ---"
		r = userGroup.usersCount()
		persistence.printClear()
		assertEquals 2, r

		println "\n--- delete() ---"
		userGroup.delete()
		persistence.printClear()


		println "\n--- userGroup2.addToMeetings(meeting1) ---"
		userGroup2.addToMeetings(meeting1)
		persistence.printClear()

		println "\n--- userGroup2.addToMeetings(meeting2) ---"
		userGroup2.addToMeetings(meeting2)
		persistence.printClear()

		println "\n--- userGroup.save() ---"
		userGroup.save()
		persistence.printClear()
		println user.id

		println "\n--- userGroup.addToMeetings(meeting3) ---"
		userGroup.addToMeetings(meeting3)
		persistence.printClear()


		println "\n--- userGroup.meetings ---"
		r = userGroup.meetings
		persistence.printClear()
		assertEquals 1, r.size()
		assertTrue r instanceof List

		println "\n--- userGroup2.meetings ---"
		r = userGroup2.meetings
		persistence.printClear()
		assertEquals 2, r.size()


		println "\n--- userGroup.addToPosts(post1) ---"
		userGroup.addToPosts(post1)
		persistence.printClear()

		println "\n--- userGroup.addToPosts(post2) ---"
		userGroup.addToPosts(post2)
		persistence.printClear()

		println "\n--- userGroup.posts ---"
		r = userGroup.posts
		persistence.printClear()
		assertEquals 2, r.size()
		assertTrue r instanceof Set

		println "\n--- user4.userGroup ---"
		r = user4.userGroup
		persistence.printClear()
		println "${r} (${r?.uuid})"
		assertNotNull r

		println "\n--- user4.userGroupId ---"
		r = user4.userGroupId
		persistence.printClear()
		println r
		assertEquals "group2-zzzz-zzzz", r

		println "\n--- user4.userGroup = userGroup2 ---"
		user4.userGroup = userGroup
		persistence.printClear()
		assertEquals userGroup, user4.userGroup

		println "\n--- user4.userGroupId ---"
		r = user4.userGroupId
		persistence.printClear()
		println r
		assertEquals "group1-zzzz-zzzz", r
	}
}
