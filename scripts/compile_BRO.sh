#!/bin/bash
echo "Run Baggage Reclaim Office"
rm -r ../out/outBRO;
mkdir ../out/outBRO;
javac -d "../out/outBRO" commonInfra/*.java entities/*.java proxies/*.java sharedRegions/*.java stubs/*.java  MainProgram/SharedRegionMain.java
java -classpath "../out/outBRO" MainProgram.SharedRegionMain BgReclaimOffice