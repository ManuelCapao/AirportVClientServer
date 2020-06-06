#!/bin/bash
echo "Run Store Room"
rm -r ../out/outSR;
mkdir ../out/outSR;
javac -d "../out/outSR" commonInfra/*.java entities/*.java proxies/*.java sharedRegions/*.java stubs/*.java  MainProgram/SharedRegionMain.java
java -classpath "../out/outSR" MainProgram.SharedRegionMain StoreRoom