/*******************************************************************************
 * Copyright (c) 2014 AmbieSense Limited.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package controllers;


import play.mvc.*;
//import play.data.*;
import play.*;

import com.wordnik.swagger.core.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import java.io.StringWriter;

import models.*;


public class BaseApiController extends Controller {
	static JavaRestResourceUtil ru = new JavaRestResourceUtil();

	protected static ObjectMapper mapper = JsonUtil.mapper();
	
	public static Result JsonResponse(Object obj) {
		return JsonResponse(obj, 200);
	}

	public static Result JsonResponse(Object obj, int code) {
		StringWriter w = new StringWriter();
		try {
			mapper.writeValue(w, obj);
		} catch (Exception e) {
			// TODO: handle proper return code
			e.printStackTrace();
		}

		response().setContentType("application/json");
		response().setHeader("Access-Control-Allow-Origin", "*");
    response().setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
    response().setHeader("Access-Control-Allow-Headers", "Content-Type, api_key, Authorization");

		return ok(w.toString());
	}
}