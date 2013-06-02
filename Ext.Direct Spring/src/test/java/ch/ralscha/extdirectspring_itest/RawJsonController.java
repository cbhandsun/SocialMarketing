/**
 * Copyright 2010-2013 Ralph Schaer <ralphschaer@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.ralscha.extdirectspring_itest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectRawJsonStoreReadResult;

@Service
public class RawJsonController {

	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectRawJsonStoreReadResult listUsers1() {
		List<String> records = new ArrayList<String>();

		records.add("{ \"_id\" : { \"$oid\" : \"4cf8e5b8924e23349fb99454\"} , \"username\" : \"johnd\" , \"firstName\" : \"John\" , \"name\" : \"Doe\" , \"email\" : \"john.doe@test.com\" , \"enabled\" : true , \"dob\" : { \"$date\" : \"1985-02-03T23:00:00Z\"} , \"noOfLogins\" : 5 , \"password\" : \"91dfd9ddb4198affc5c194cd8ce6d338fde470e2\" , \"groups\" : [ \"admin\" , \"user\"] , \"phoneNumber\" : [ { \"type\" : \"home\" , \"number\" : \"212 555-1234\"} , { \"type\" : \"fax\" , \"number\" : \"646 555-4567\"}]}");
		records.add("{ \"_id\" : { \"$oid\" : \"4cf8e5b8924e2334a0b99454\"} , \"username\" : \"francol\" , \"firstName\" : \"Franco\" , \"name\" : \"Lawrence\" , \"email\" : \"franco.lawrence@test.com\" , \"enabled\" : true , \"dob\" : { \"$date\" : \"1979-12-01T23:00:00Z\"} , \"noOfLogins\" : 3 , \"password\" : \"5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8\" , \"groups\" : [ \"user\"] , \"phoneNumber\" : [ { \"type\" : \"home\" , \"number\" : \"212 555-4321\"} , { \"type\" : \"fax\" , \"number\" : \"646 555-7654\"}]}");

		return new ExtDirectRawJsonStoreReadResult(records);
	}

	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectRawJsonStoreReadResult listUsers2() {
		List<String> records = new ArrayList<String>();

		records.add("{ \"_id\" : { \"$oid\" : \"4cf8e5b8924e23349fb99454\"} , \"username\" : \"johnd\" , \"firstName\" : \"John\" , \"name\" : \"Doe\" , \"email\" : \"john.doe@test.com\" , \"enabled\" : true , \"dob\" : { \"$date\" : \"1985-02-03T23:00:00Z\"} , \"noOfLogins\" : 5 , \"password\" : \"91dfd9ddb4198affc5c194cd8ce6d338fde470e2\" , \"groups\" : [ \"admin\" , \"user\"] , \"phoneNumber\" : [ { \"type\" : \"home\" , \"number\" : \"212 555-1234\"} , { \"type\" : \"fax\" , \"number\" : \"646 555-4567\"}]}");
		records.add("{ \"_id\" : { \"$oid\" : \"4cf8e5b8924e2334a0b99454\"} , \"username\" : \"francol\" , \"firstName\" : \"Franco\" , \"name\" : \"Lawrence\" , \"email\" : \"franco.lawrence@test.com\" , \"enabled\" : true , \"dob\" : { \"$date\" : \"1979-12-01T23:00:00Z\"} , \"noOfLogins\" : 3 , \"password\" : \"5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8\" , \"groups\" : [ \"user\"] , \"phoneNumber\" : [ { \"type\" : \"home\" , \"number\" : \"212 555-4321\"} , { \"type\" : \"fax\" , \"number\" : \"646 555-7654\"}]}");

		return new ExtDirectRawJsonStoreReadResult(2, records);
	}

	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectRawJsonStoreReadResult listUsers3() {
		List<String> records = new ArrayList<String>();

		records.add("{ \"_id\" : { \"$oid\" : \"4cf8e5b8924e23349fb99454\"} , \"username\" : \"johnd\" , \"firstName\" : \"John\" , \"name\" : \"Doe\" , \"email\" : \"john.doe@test.com\" , \"enabled\" : true , \"dob\" : { \"$date\" : \"1985-02-03T23:00:00Z\"} , \"noOfLogins\" : 5 , \"password\" : \"91dfd9ddb4198affc5c194cd8ce6d338fde470e2\" , \"groups\" : [ \"admin\" , \"user\"] , \"phoneNumber\" : [ { \"type\" : \"home\" , \"number\" : \"212 555-1234\"} , { \"type\" : \"fax\" , \"number\" : \"646 555-4567\"}]}");
		records.add("{ \"_id\" : { \"$oid\" : \"4cf8e5b8924e2334a0b99454\"} , \"username\" : \"francol\" , \"firstName\" : \"Franco\" , \"name\" : \"Lawrence\" , \"email\" : \"franco.lawrence@test.com\" , \"enabled\" : true , \"dob\" : { \"$date\" : \"1979-12-01T23:00:00Z\"} , \"noOfLogins\" : 3 , \"password\" : \"5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8\" , \"groups\" : [ \"user\"] , \"phoneNumber\" : [ { \"type\" : \"home\" , \"number\" : \"212 555-4321\"} , { \"type\" : \"fax\" , \"number\" : \"646 555-7654\"}]}");

		return new ExtDirectRawJsonStoreReadResult(2, records, false);
	}

	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectRawJsonStoreReadResult listUsers4() {
		List<String> records = new ArrayList<String>();

		records.add("{ \"_id\" : { \"$oid\" : \"4cf8e5b8924e23349fb99454\"} , \"username\" : \"johnd\" , \"firstName\" : \"John\" , \"name\" : \"Doe\" , \"email\" : \"john.doe@test.com\" , \"enabled\" : true , \"dob\" : { \"$date\" : \"1985-02-03T23:00:00Z\"} , \"noOfLogins\" : 5 , \"password\" : \"91dfd9ddb4198affc5c194cd8ce6d338fde470e2\" , \"groups\" : [ \"admin\" , \"user\"] , \"phoneNumber\" : [ { \"type\" : \"home\" , \"number\" : \"212 555-1234\"} , { \"type\" : \"fax\" , \"number\" : \"646 555-4567\"}]}");
		records.add("{ \"_id\" : { \"$oid\" : \"4cf8e5b8924e2334a0b99454\"} , \"username\" : \"francol\" , \"firstName\" : \"Franco\" , \"name\" : \"Lawrence\" , \"email\" : \"franco.lawrence@test.com\" , \"enabled\" : true , \"dob\" : { \"$date\" : \"1979-12-01T23:00:00Z\"} , \"noOfLogins\" : 3 , \"password\" : \"5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8\" , \"groups\" : [ \"user\"] , \"phoneNumber\" : [ { \"type\" : \"home\" , \"number\" : \"212 555-4321\"} , { \"type\" : \"fax\" , \"number\" : \"646 555-7654\"}]}");

		return new ExtDirectRawJsonStoreReadResult(2l, records);
	}

	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectRawJsonStoreReadResult listUsers5() {
		List<String> records = new ArrayList<String>();

		records.add("{ \"_id\" : { \"$oid\" : \"4cf8e5b8924e23349fb99454\"} , \"username\" : \"johnd\" , \"firstName\" : \"John\" , \"name\" : \"Doe\" , \"email\" : \"john.doe@test.com\" , \"enabled\" : true , \"dob\" : { \"$date\" : \"1985-02-03T23:00:00Z\"} , \"noOfLogins\" : 5 , \"password\" : \"91dfd9ddb4198affc5c194cd8ce6d338fde470e2\" , \"groups\" : [ \"admin\" , \"user\"] , \"phoneNumber\" : [ { \"type\" : \"home\" , \"number\" : \"212 555-1234\"} , { \"type\" : \"fax\" , \"number\" : \"646 555-4567\"}]}");
		records.add("{ \"_id\" : { \"$oid\" : \"4cf8e5b8924e2334a0b99454\"} , \"username\" : \"francol\" , \"firstName\" : \"Franco\" , \"name\" : \"Lawrence\" , \"email\" : \"franco.lawrence@test.com\" , \"enabled\" : true , \"dob\" : { \"$date\" : \"1979-12-01T23:00:00Z\"} , \"noOfLogins\" : 3 , \"password\" : \"5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8\" , \"groups\" : [ \"user\"] , \"phoneNumber\" : [ { \"type\" : \"home\" , \"number\" : \"212 555-4321\"} , { \"type\" : \"fax\" , \"number\" : \"646 555-7654\"}]}");

		return new ExtDirectRawJsonStoreReadResult(2l, records, true);
	}

}
