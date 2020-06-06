#!/bin/bash
echo "Run Airport Server Client"
rm -r ../out/outASC;
mkdir ../out/outASC;
javac -d "../out/outASC" commonInfra/*.java entities/*.java proxies/*.java sharedRegions/*.java stubs/*.java  MainProgram/AirportVClientServer.java
java -classpath "../out/outASC" MainProgram.AirportVClientServer