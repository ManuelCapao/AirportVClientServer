#!/bin/bash
echo "Run Arrival Lounge"
rm -r ../out/outAL;
mkdir ../out/outAL;
javac -d "../out/outAL" commonInfra/*.java entities/*.java proxies/*.java sharedRegions/*.java stubs/*.java  MainProgram/SharedRegionMain.java
java -classpath "../out/outAL" MainProgram.SharedRegionMain ArvLounge