package com.example.spark.webserver;


import static spark.Spark.*;

public class WebServer {
	public static void main(String[] args) {
		secure("localhost.jks", "password", null, null);
		get("/hello", (req, res) -> "Hello World");
	}
}
