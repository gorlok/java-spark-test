package com.example.spark.webserver;


import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.post;
import static spark.Spark.secure;
import static spark.Spark.*;

public class WebServer {
	
	public static void main(String[] args) {
		//System.setProperty("java.library.path", "/opt/graalvm-ce-1.0.0-rc15/jre/lib/amd64/libsunec.so");
		System.setProperty("java.library.path", "/opt/graalvm-ce-1.0.0-rc15/jre/lib/amd64");
		
		System.out.println("java.library.path=" + System.getProperty("java.library.path"));
		
		secure("localhost.jks", "password", null, null);

		// curl -k https://localhost:4567/hello
		get("/hello", (req, res) -> "Hello World");
		
		// curl -i -k -X POST https://localhost:4567/login -d username=admin -d password=1234
		post("/login", (req, res) -> {
			if ("admin".equals(req.queryParams("username"))
					&& "1234".equals(req.queryParams("password"))) {
				req.session().attribute("authenticated", true);
				return "logged in!";
			} else {
				halt(401);
			}
			return null;
		});
		
		// try without auth:
		// curl -v -k https://localhost:4567/protected/ping
		//
		// try with auth:
		// curl -v -k -c cookies -X POST https://localhost:4567/login -d username=admin -d password=1234
		// curl -v -k -b cookies https://localhost:4567/protected/ping
		path("/protected", () -> {
			get("/ping", (req, res) -> "pong!");
		});
		before("/protected/*", (req, rest) -> {
			boolean authenticated = req.session().attribute("authenticated") != null;
			
			if (!authenticated) {
				halt(401, "Go away!");
			}
		});
		
	}
}
