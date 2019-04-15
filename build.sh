#!/bin/bash
docker build -t demo .
id=$(docker create demo)
docker cp $id:/app/demo-0.0.1-SNAPSHOT .
